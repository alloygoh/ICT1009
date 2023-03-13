package com.mygdx.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Characters.*;
import com.mygdx.game.Interfaces.iCollidable;
import com.mygdx.game.Interfaces.iSaveable;
import com.mygdx.game.Manager.ScreenManager;
import com.mygdx.game.Manager.SettingsManager;
import com.mygdx.game.Objects.*;
import com.mygdx.game.Utils.Controls;
import com.mygdx.game.Utils.Globals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


public class GameScreen extends AbstractScreen {
    private SpriteBatch batch;
    private ShapeRenderer renderer;
    private ArrayList<AbstractActor> entities;
    private ArrayList<BaseObject> objectList;
    private Viewport viewport;
    private SettingsManager settingsManager;
    private ScreenManager screenManager;
    private HashMap<Class, ArrayList<BaseObject>> stash;
    private ArrayList<Player> players;
    private Random random;
    private float timeSinceGeneration;
    private int maxObjects = 15;
    private Label player1LifeLabel;
    private Label player2LifeLabel;
    private Label player1PowerLabel;
    private Label player2PowerLabel;
    private HashMap<Integer, ArrayList<Image>> comboLabelMap;

    public GameScreen(Game game, SettingsManager settingsManager, ArrayList entities) {
        super(game);
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        this.viewport = new StretchViewport(screenWidth, screenHeight, this.getCamera());
        this.getStage().setViewport(viewport);
        this.settingsManager = settingsManager;
        this.screenManager = Globals.getScreenManager();

        this.random = new Random();
        this.batch = new SpriteBatch();
        this.renderer = new ShapeRenderer();
        // player 1 & 2 combo images
        this.comboLabelMap = new HashMap<Integer,ArrayList<Image>>();
        this.comboLabelMap.put(0, new ArrayList<Image>());
        this.comboLabelMap.put(1, new ArrayList<Image>());

        this.stash = new HashMap<Class, ArrayList<BaseObject>>();
        ArrayList<Class> objectClasses = new ArrayList<Class>(
                Arrays.asList(Carrot.class, Toast.class, Fruit.class, Boba.class, Pizza.class, Fries.class));
        for (Class targetClass : objectClasses) {
            stash.put(targetClass, new ArrayList<BaseObject>());
        }

        this.entities = (ArrayList<AbstractActor>) entities;
        this.players = new ArrayList<Player>();
        this.objectList = new ArrayList<>();

        initStage();
        this.getCamera().update();
        // this.getStage().setDebugAll(true);

        for (Actor actor : this.entities) {
            if (actor instanceof MovingShapeActor) {
                ((MovingShapeActor) actor).setRenderer(renderer);
            }
        }
    }

    public GameScreen(Game game, SettingsManager settingsManager) {
        this(game, settingsManager, new ArrayList<>());
    }

    private void governCollisions() {
        ArrayList<CollidableActor> collidables = new ArrayList<>();
        for (Actor actor : entities) {
            if (actor instanceof CollidableActor) {
                collidables.add((CollidableActor) actor);
            }
        }
        for (CollidableActor subject : collidables) {
            for (CollidableActor collidableActor : collidables) {
                if (subject.collidesWith(collidableActor)) {
                    subject.reactToEvent("collision", collidableActor);
                }
            }
        }
    }

    public void governBorders() {
        for (AbstractActor actor : entities) {
            // don't correct movement for eaten food
            if (actor instanceof BaseObject) {
                BaseObject food = (BaseObject) actor;
                if (food.shouldDisappear()) {
                    continue;
                }
            }
            if (exceedingBorder(actor)) {
                correctMovement(actor);
            }
        }
    }

    // determine if actors exceed game bounds
    public boolean exceedingBorder(AbstractActor actor) {
        float maxX = this.getStage().getViewport().getWorldWidth() - actor.getWidth();
        float maxY = this.getStage().getViewport().getWorldHeight() - actor.getHeight();
        Vector2 forecastedPosition = actor.getForecastedPosition();
        if (forecastedPosition.x > maxX || forecastedPosition.y > maxY || forecastedPosition.x < 0
                || forecastedPosition.y < 0) {
            return true;
        }
        return false;
    }

    public void correctMovement(Actor actor) {
        float maxX = this.getStage().getViewport().getWorldWidth() - actor.getWidth();
        float maxY = this.getStage().getViewport().getWorldHeight() - actor.getHeight();
        actor.setX(Math.min(maxX, Math.max(actor.getX(), 0)));
        actor.setY(Math.min(maxY, Math.max(actor.getY(), 0)));
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    private void populateHighScore() {
        for (Player player : players) {
            // since 2 players only, can safely assume the player that is not dead is the
            // winner
            if (!player.isDead()) {
                Globals.setScore(player.getHighScore());
            }
        }
    }

    @Override
    public void render(float delta) {

        if (objectList.size() < maxObjects && timeSinceGeneration > 1) {
            ArrayList<BaseObject> objects = generateGameObjects();
            for (Actor object : objects) {
                this.getStage().addActor(object);
            }
            timeSinceGeneration = 0;
            entities.addAll(objects);
        } else {
            timeSinceGeneration += delta;
        }

        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        // to go to Pause screen
        if (Gdx.input.isKeyPressed((Input.Keys.ESCAPE))) {
            delta = 0;
            ArrayList<iSaveable> saveables = new ArrayList<>();
            saveables.add((iSaveable) this.entities.get(0));
            saveables.add((iSaveable) this.entities.get(1));
            Globals.getGameStateManager().updateSaveables(saveables);
            Globals.getGameStateManager().saveState();
            if (screenManager.getScreen((PauseScreen.class)) == null) {
                screenManager.addScreen(new PauseScreen(getGame()));
            }
            screenManager.setScreen(PauseScreen.class);
        }
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        ScreenUtils.clear(169, 169, 169, 0);

        for (AbstractActor actor : entities) {
            if (actor instanceof MovingAI) {
                continue;
            }
            actor.processKeyStrokes(delta);
        }

        governBorders();
        governCollisions();
        this.getStage().act(delta);

        // refresh scoretable on screen
        refreshScore();

        // check if should end game
        for (Player player : players) {
            if (player.isDead()) {
                populateHighScore();
                Globals.getScreenManager().setScreen(GameOverScreen.class);
                return;
            }
        }

        ArrayList<BaseObject> holdingArea = new ArrayList<>();
        for (BaseObject ai : objectList) {
            if (ai.shouldDisappear()) {
                freeActor(ai);
                addToStash(ai);
                holdingArea.add(ai);
            }
        }
        for (MovingAI ai : holdingArea) {
            objectList.remove(ai);
        }
        this.getStage().draw();

    }

    // to remove actor from stage
    private void freeActor(Actor actor) {
        actor.remove();
        entities.remove(actor);
    }

    @Override
    public void resize(int width, int height) {
        this.getStage().getViewport().update(width, height, true);
        this.getCamera().update();
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
        super.dispose();
    }

    private void addToStash(BaseObject actor) {
        actor.resetStatus();
        if (actor instanceof Pizza) {
            stash.get(Pizza.class).add(actor);
        } else if (actor instanceof Boba) {
            stash.get(Boba.class).add(actor);
        } else if (actor instanceof Fries) {
            stash.get(Fries.class).add(actor);
        } else if (actor instanceof Carrot) {
            stash.get(Carrot.class).add(actor);
        } else if (actor instanceof Toast) {
            stash.get(Toast.class).add(actor);
        } else if (actor instanceof Fruit) {
            stash.get(Fruit.class).add(actor);
        }
    }

    private boolean checkCollision(BaseObject actor, ArrayList<BaseObject> actorList) {
        for (iCollidable collidable : actorList) {
            if (collidable.collidesWith(actor)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<BaseObject> generateGameObjects() {
        int numToGenerate = random.nextInt(6);
        ArrayList<BaseObject> staging = new ArrayList<>();
        for (int i = 0; i < numToGenerate; i++) {
            BaseObject object = generateNewObject();
            // make sure generation does not result in collision right away
            while (checkCollision(object, objectList)) {
                // regen coordinates
                object.setX(random.nextFloat() * viewport.getWorldWidth());
                object.setY(random.nextFloat() * viewport.getWorldHeight());
            }
            staging.add(object);
            // add to list to ensure generation does not result in collision
            objectList.add(object);
        }
        return staging;
    }

    private BaseObject generateNewObject() {
        int choice = random.nextInt(6);
        BaseObject actor;
        switch (choice) {
            // generate Pizza
            case 0:
                if (stash.get(Pizza.class).size() != 0) {
                    actor = stash.get(Pizza.class).remove(0);
                    break;
                }
                actor = new Pizza();
                break;
            // generate Boba
            case 1:
                if (stash.get(Boba.class).size() != 0) {
                    actor = stash.get(Boba.class).remove(0);
                    break;
                }
                actor = new Boba();
                break;
            // generate Fries
            case 2:
                if (stash.get(Fries.class).size() != 0) {
                    actor = stash.get(Fries.class).remove(0);
                    break;
                }
                actor = new Fries();
                break;
            // generate Toast
            case 3:
                if (stash.get(Toast.class).size() != 0) {
                    actor = stash.get(Toast.class).remove(0);
                    break;
                }
                actor = new Toast();
                break;
            // generate Carrot 
            case 4:
                if (stash.get(Carrot.class).size() != 0) {
                    actor = stash.get(Carrot.class).remove(0);
                    break;
                }
                actor = new Carrot();
                break;
            // generate Fruit
            case 5:
            default:
                if (stash.get(Fruit.class).size() != 0) {
                    actor = stash.get(Fruit.class).remove(0);
                    break;
                }
                actor = new Fruit();
                break;

        }
        actor.setY(random.nextFloat() * viewport.getWorldHeight());
        actor.setX(random.nextFloat() * viewport.getWorldWidth());
        return actor;
    }
    
    private void resetComboVisibility(){
        ArrayList<Image> player1Combo = this.comboLabelMap.get(0);
        ArrayList<Image> player2Combo = this.comboLabelMap.get(1);
        
        for (Image image: player1Combo){
            image.setVisible(true);
        }
        
        for (Image image: player2Combo){
            image.setVisible(true);
        }
    }

    private void refreshScore(){
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        this.player1LifeLabel.setText(player1.getLifeCount());
        this.player1PowerLabel.setText(player1.getPower());
        this.player2LifeLabel.setText(player2.getLifeCount());
        this.player2PowerLabel.setText(player2.getPower());
        
        resetComboVisibility();
        ArrayList<Image> player1Combo = this.comboLabelMap.get(0);
        ArrayList<Image> player2Combo = this.comboLabelMap.get(1);
        for (Class food: player1.getFoodsEaten()){
            if (food == Carrot.class){
                if (player1Combo.get(0).isVisible()){
                    player1Combo.get(0).setVisible(false);
                }else{
                    player1Combo.get(1).setVisible(false);
                }
            } else if(food == Fruit.class){
                player1Combo.get(2).setVisible(false);
            } else if (food == Toast.class){
                player1Combo.get(3).setVisible(false);
            }
        }

        for (Class food: player2.getFoodsEaten()){
            if (food == Carrot.class){
                if (player2Combo.get(0).isVisible()){
                    player2Combo.get(0).setVisible(false);
                }else{
                    player2Combo.get(1).setVisible(false);
                }
            } else if(food == Fruit.class){
                player2Combo.get(2).setVisible(false);
            } else if (food == Toast.class){
                player2Combo.get(3).setVisible(false);
            }
        }
    }
    
    // only called once
    private void initScoreTable(){
        ArrayList<Image> player1Combo = this.comboLabelMap.get(0);
        ArrayList<Image> player2Combo = this.comboLabelMap.get(1);

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        TextureAtlas atlas = Globals.getAssetManager().get("objects.atlas", TextureAtlas.class);
        Image player1CarrotImage = new Image(atlas.findRegion("carrot"));
        Image player1SecondaryCarrotImage = new Image(atlas.findRegion("carrot"));
        Image player1FruitImage = new Image(atlas.findRegion("fruits"));
        Image player1ToastImage = new Image(atlas.findRegion("toast"));
        player1Combo.addAll(Arrays.asList(player1CarrotImage, player1SecondaryCarrotImage, player1FruitImage, player1ToastImage));
        
        Image player2CarrotImage = new Image(atlas.findRegion("carrot"));
        Image player2SecondaryCarrotImage = new Image(atlas.findRegion("carrot"));
        Image player2FruitImage = new Image(atlas.findRegion("fruits"));
        Image player2ToastImage = new Image(atlas.findRegion("toast"));
        player2Combo.addAll(Arrays.asList(player2CarrotImage, player2SecondaryCarrotImage, player2FruitImage, player2ToastImage));

        Table player1ScoreTable = new Table();
        // top left
        player1ScoreTable.setX(0);
        player1ScoreTable.setY(this.getStage().getViewport().getWorldHeight());

        BitmapFont scoreLabelFont = Globals.getAssetManager().get("scoreLabelFont.ttf", BitmapFont.class);
        Label.LabelStyle scoreLabelStyle = new Label.LabelStyle();
        scoreLabelStyle.font = scoreLabelFont;

        BitmapFont scoreFont = Globals.getAssetManager().get("scoreFont.ttf", BitmapFont.class);
        Label.LabelStyle scoreStyle = new Label.LabelStyle();
        scoreStyle.font = scoreFont;

        Label player1LifeTextLabel = new Label("Player 1 Life:", scoreLabelStyle);
        player1ScoreTable.add(player1LifeTextLabel).padLeft(2).fillX();
        this.player1LifeLabel = new Label(String.valueOf(player1.getLifeCount()), scoreStyle);
        player1ScoreTable.add(player1LifeLabel).padLeft(10);
        player1ScoreTable.row();
        Label player1PowerTextLabel = new Label("Player 1 Power:", scoreLabelStyle);
        player1ScoreTable.add(player1PowerTextLabel).padLeft(2).fillX();
        this.player1PowerLabel = new Label(String.valueOf(player1.getPower()), scoreStyle);
        player1ScoreTable.add(player1PowerLabel).padLeft(10);
        player1ScoreTable.row();
        Label player1ComboLabel = new Label("Player 1 Combo:", scoreLabelStyle);
        player1ScoreTable.add(player1ComboLabel).padLeft(2);
        player1ScoreTable.add(player1CarrotImage).size(20,20).padLeft(5);
        player1ScoreTable.add(player1SecondaryCarrotImage).size(20,20);
        player1ScoreTable.add(player1FruitImage).size(20,20);
        player1ScoreTable.add(player1ToastImage).size(20,20);
        

        Table player2ScoreTable = new Table();
        player2ScoreTable.setX(this.getStage().getViewport().getWorldWidth());
        player2ScoreTable.setY(this.getStage().getViewport().getWorldHeight());

        Label player2LifeTextLabel = new Label("Player 2 Life:", scoreLabelStyle);
        player2ScoreTable.add(player2LifeTextLabel).padLeft(2).fillX();
        this.player2LifeLabel = new Label(String.valueOf(player2.getLifeCount()), scoreStyle);
        player2ScoreTable.add(player2LifeLabel).padLeft(10).padRight(2);
        player2ScoreTable.row();
        Label player2PowerTextLabel = new Label("Player 2 Power:", scoreLabelStyle);
        player2ScoreTable.add(player2PowerTextLabel).padLeft(2).fillX();
        this.player2PowerLabel = new Label(String.valueOf(player2.getPower()), scoreStyle);
        player2ScoreTable.add(player2PowerLabel).padLeft(10).padRight(2);
        player2ScoreTable.row();
        Label player2ComboLabel = new Label("Player 2 Combo:", scoreLabelStyle);
        player2ScoreTable.add(player2ComboLabel).padLeft(2);
        player2ScoreTable.add(player2CarrotImage).size(20,20).padLeft(5);
        player2ScoreTable.add(player2SecondaryCarrotImage).size(20,20);
        player2ScoreTable.add(player2FruitImage).size(20,20);
        player2ScoreTable.add(player2ToastImage).size(20,20);

        player1ScoreTable.top().left();
        player2ScoreTable.top().right();
        this.getStage().addActor(player1ScoreTable);
        this.getStage().addActor(player2ScoreTable);
    }

    @Override
    public void initStage() {
        Controls p1 = settingsManager.getControlSettings().getControlOf(1);
        Controls p2 = settingsManager.getControlSettings().getControlOf(2);
        Player player1;
        Player player2;
        if (this.entities.size() == 0) {
            player1 = new Guy(30, 60, 200, 0, 100, p1);
            player2 = new Girl(30, 60, p2);
        } else {
            player1 = (Player) this.entities.get(0);
            player1.setControl(p1);
            player2 = (Player) this.entities.get(1);
            player2.setControl(p2);
        }

        entities.clear();
        entities.addAll(Arrays.asList(player1, player2));
        players.addAll(Arrays.asList(player1, player2));

        for (Actor actor : entities) {
            this.getStage().addActor(actor);
        }
        
        // score overlay
        initScoreTable();

        // read keystrokes
        Gdx.input.setInputProcessor(this.getStage());
    }
}
