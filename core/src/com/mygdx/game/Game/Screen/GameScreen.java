package com.mygdx.game.Game.Screen;

import java.lang.Math;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.mygdx.game.Game.Characters.*;
import com.mygdx.game.Game.Objects.*;
import com.mygdx.game.Engine.Utils.Controls;
import com.mygdx.game.Game.Utils.Globals;
import com.mygdx.game.Engine.Interfaces.iCollidable;
import com.mygdx.game.Engine.Interfaces.iSaveable;
import com.mygdx.game.Engine.Manager.ScreenManager;
import com.mygdx.game.Engine.Manager.SettingsManager;
import com.mygdx.game.Engine.Screen.AbstractScreen;
import com.mygdx.game.Engine.Characters.AbstractActor;
import com.mygdx.game.Engine.Characters.CollidableActor;
import com.mygdx.game.Engine.Characters.MovingAI;

import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class GameScreen extends AbstractScreen {
    private static final Sound SFXcountDown = Globals.getAssetManager().get("sound/countdown.mp3");
    private static final Sound bgm = Globals.getAssetManager().get("sound/meow-defence.mp3");
    private static final Sound SFXFight = Globals.getAssetManager().get("sound/pvp-fight.mp3");
    private static final Sound bgmFight = Globals.getAssetManager().get("sound/pvp-bgm.mp3");
    private final SpriteBatch batch;
    private final ShapeRenderer renderer;
    private final ArrayList<AbstractActor> entities;
    private final ArrayList<BaseObject> objectList;
    private final Viewport viewport;
    private final SettingsManager settingsManager;
    private final ScreenManager screenManager;
    private final HashMap<Class, ArrayList<BaseObject>> stash;
    private final ArrayList<Player> players;
    private final Random random;
    private final int maxObjects = 15;
    private final HashMap<Integer, ArrayList<Image>> comboLabelMap;
    private float timeSinceGeneration;
    private Label player1LifeLabel;
    private Label player2LifeLabel;
    private Label player1PowerLabel;
    private Label player2PowerLabel;
    private Label countDownLabel;
    private boolean hasPlayedEffect = false;
    private static Texture backgroundTexture = new Texture("sky.png");
    private static Sprite backgroundSprite = new Sprite(backgroundTexture);

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
        this.comboLabelMap = new HashMap<Integer, ArrayList<Image>>();
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

        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundSprite.setPosition(0,0);

        initStage();
        this.getCamera().update();
    }

    public GameScreen(Game game, SettingsManager settingsManager) {
        this(game, settingsManager, new ArrayList<>());
    }

    private void initBattle(Player player1, Player player2) {
        int powerDiff = Math.abs(player1.getPower() - player2.getPower());
        if (powerDiff == 0) {
            // do not trigger battle animation is same power
            player1.reactToEvent("collision", player2);
            return;
        }
        Player losingPlayer = player1;
        if (player2.getPower() < player1.getPower()) {
            losingPlayer = player2;
        }
        Globals.getScreenManager().addScreen(new BattleScreen(getGame(), losingPlayer, powerDiff, players));
        Globals.getScreenManager().setScreen(BattleScreen.class);
        Globals.setInBattle(true);
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
                    if (subject != collidableActor && subject instanceof Player && collidableActor instanceof Player) {
                        // initiate battle
                        if (!Globals.isInBattle() && Globals.getCountDown() <= 0) {
                            initBattle((Player) subject, (Player) collidableActor);
                        }
                    }
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
        return forecastedPosition.x > maxX || forecastedPosition.y > maxY || forecastedPosition.x < 0
                || forecastedPosition.y < 0;
    }

    public void correctMovement(Actor actor) {
        float maxX = this.getStage().getViewport().getWorldWidth() - actor.getWidth();
        float maxY = this.getStage().getViewport().getWorldHeight() - actor.getHeight();
        actor.setX(Math.min(maxX, Math.max(actor.getX(), 0)));
        actor.setY(Math.min(maxY, Math.max(actor.getY(), 0)));
    }

    @Override
    public void show() {
        hasPlayedEffect = false;
        bgmFight.stop();
        bgm.play(0.5f);
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
        ScreenUtils.clear(118, 167, 169, 0);

        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();

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
        // refresh timer
        refreshTimer(delta);
        // play sound if fight time
        if (Globals.getCountDown() <= 0 && !hasPlayedEffect) {
            SFXFight.play(1.0f);
            bgm.stop();
            long fightID = bgmFight.play(0.5f);
            bgmFight.stop();
            fightID = bgmFight.play(0.5f);
            bgmFight.setLooping(fightID, true);
            hasPlayedEffect = true;
        }

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

    /*public void renderBackground()
    {
        backgroundSprite.draw(spr)
    }*/

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
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {


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

    private void resetComboVisibility() {
        ArrayList<Image> player1Combo = this.comboLabelMap.get(0);
        ArrayList<Image> player2Combo = this.comboLabelMap.get(1);

        for (Image image : player1Combo) {
            image.setVisible(true);
        }

        for (Image image : player2Combo) {
            image.setVisible(true);
        }
    }

    private void refreshTimer(float delta) {
        Globals.setCountDown(Globals.getCountDown() - delta);
        BitmapFont timerLabelFont = Globals.getAssetManager().get("scoreLabelFont.ttf", BitmapFont.class);
        Label.LabelStyle timerLabelStyle = new Label.LabelStyle();
        timerLabelStyle.font = timerLabelFont;
        if (Globals.getCountDown() <= 0) {
            this.countDownLabel.setText("BATTLE!");
            this.countDownLabel.setPosition((this.getStage().getViewport().getWorldWidth() - countDownLabel.getPrefWidth()) / 2,
                    this.getStage().getViewport().getWorldHeight() - countDownLabel.getHeight());
        } else {
            this.countDownLabel.setText("Time till battle: " + Globals.getCountDown());
            this.countDownLabel.setPosition((this.getStage().getViewport().getWorldWidth() - countDownLabel.getWidth()) / 2,
                    this.getStage().getViewport().getWorldHeight() - countDownLabel.getHeight());
        }
    }

    private void refreshScore() {
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        this.player1LifeLabel.setText(player1.getLifeCount());
        this.player1PowerLabel.setText(player1.getPower());
        this.player2LifeLabel.setText(player2.getLifeCount());
        this.player2PowerLabel.setText(player2.getPower());

        resetComboVisibility();
        ArrayList<Image> player1Combo = this.comboLabelMap.get(0);
        ArrayList<Image> player2Combo = this.comboLabelMap.get(1);
        for (Class food : player1.getFoodsEaten()) {
            if (food == Carrot.class) {
                if (player1Combo.get(0).isVisible()) {
                    player1Combo.get(0).setVisible(false);
                } else {
                    player1Combo.get(1).setVisible(false);
                }
            } else if (food == Fruit.class) {
                player1Combo.get(2).setVisible(false);
            } else if (food == Toast.class) {
                player1Combo.get(3).setVisible(false);
            }
        }

        for (Class food : player2.getFoodsEaten()) {
            if (food == Carrot.class) {
                if (player2Combo.get(0).isVisible()) {
                    player2Combo.get(0).setVisible(false);
                } else {
                    player2Combo.get(1).setVisible(false);
                }
            } else if (food == Fruit.class) {
                player2Combo.get(2).setVisible(false);
            } else if (food == Toast.class) {
                player2Combo.get(3).setVisible(false);
            }
        }
    }

    // only called once
    private void initScoreTable() {
        ArrayList<Image> player1Combo = this.comboLabelMap.get(0);
        ArrayList<Image> player2Combo = this.comboLabelMap.get(1);

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        TextureAtlas atlas = Globals.getAssetManager().get("objects.atlas", TextureAtlas.class);
        Image player1CarrotImage = new Image(atlas.findRegion("carrot"));
        Image player1SecondaryCarrotImage = new Image(atlas.findRegion("carrot"));
        Image player1FruitImage = new Image(atlas.findRegion("fruits"));
        Image player1ToastImage = new Image(atlas.findRegion("toast"));
        player1Combo.addAll(
                Arrays.asList(player1CarrotImage, player1SecondaryCarrotImage, player1FruitImage, player1ToastImage));

        Image player2CarrotImage = new Image(atlas.findRegion("carrot"));
        Image player2SecondaryCarrotImage = new Image(atlas.findRegion("carrot"));
        Image player2FruitImage = new Image(atlas.findRegion("fruits"));
        Image player2ToastImage = new Image(atlas.findRegion("toast"));
        player2Combo.addAll(
                Arrays.asList(player2CarrotImage, player2SecondaryCarrotImage, player2FruitImage, player2ToastImage));

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
        player1ScoreTable.add(player1CarrotImage).size(20, 20).padLeft(5);
        player1ScoreTable.add(player1SecondaryCarrotImage).size(20, 20);
        player1ScoreTable.add(player1FruitImage).size(20, 20);
        player1ScoreTable.add(player1ToastImage).size(20, 20);

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
        player2ScoreTable.add(player2CarrotImage).size(20, 20).padLeft(5);
        player2ScoreTable.add(player2SecondaryCarrotImage).size(20, 20);
        player2ScoreTable.add(player2FruitImage).size(20, 20);
        player2ScoreTable.add(player2ToastImage).size(20, 20);

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
            player1 = new Guy(30, 60, 200, 0, 150, p1);
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

        // countdown timer
        BitmapFont timerLabelFont = Globals.getAssetManager().get("timerFont.ttf", BitmapFont.class);
        Label.LabelStyle timerLabelStyle = new Label.LabelStyle();
        timerLabelStyle.font = timerLabelFont;
        this.countDownLabel = new Label("Time till battle: " + Globals.getCountDown(), timerLabelStyle);
        this.countDownLabel.setPosition((this.getStage().getViewport().getWorldWidth() - countDownLabel.getWidth()) / 2,
                this.getStage().getViewport().getWorldHeight() - countDownLabel.getHeight());
        this.getStage().addActor(this.countDownLabel);

        // read keystrokes
        Gdx.input.setInputProcessor(this.getStage());
        SFXcountDown.play(1.0f);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // account for 3 sec delay above
        Globals.setCountDown(33f);
        long bgmID = bgm.play(0.5f);
        bgm.stop();
        bgmID = bgm.play(0.5f);
        bgm.setLooping(bgmID, true);
    }
}
