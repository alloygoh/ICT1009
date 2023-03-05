package com.mygdx.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private ArrayList<MovingAI> aiList;
    private Viewport viewport;
    private SettingsManager settingsManager;
    private ScreenManager screenManager;
    private HashMap<Class, ArrayList<MovingAI>> stash;
    private Random random;
    private float timeSinceGeneration;
    private int maxObjects = 15;

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

        this.stash = new HashMap<Class, ArrayList<MovingAI>>();
        ArrayList<Class> objectClasses = new ArrayList<Class>(Arrays.asList(Carrot.class, Toast.class, Fruit.class, Boba.class, Pizza.class, Fries.class));
        for (Class targetClass:objectClasses){
            stash.put(targetClass, new ArrayList<MovingAI>());
        }

        this.entities = (ArrayList<AbstractActor>) entities;
        this.aiList = new ArrayList<>();

        initStage();
        this.getCamera().update();
        this.getStage().setDebugAll(true);

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
            if (exceedingBorder(actor)) {
                correctMovement(actor);
            }
        }
    }

    // determine if actors exceed game bounds
    public boolean exceedingBorder(AbstractActor actor) {
        float maxX = this.getStage().getViewport().getWorldWidth() - actor.getWidth();
        float maxY = this.getStage().getViewport().getWorldHeight() - actor.getHeight();
        if (actor instanceof Ball) {
            // specially handle for circles as x starts from middle
            maxX = this.getStage().getViewport().getWorldWidth() - actor.getWidth() * 2;
            maxY = this.getStage().getViewport().getWorldHeight() - actor.getHeight() * 2;
        }

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
        if (actor instanceof Ball) {
            // specially handle for circles as x starts from middle
            maxX = this.getStage().getViewport().getWorldWidth() - actor.getWidth() * 2;
            maxY = this.getStage().getViewport().getWorldHeight() - actor.getHeight() * 2;
        }
        actor.setX(Math.min(maxX, Math.max(actor.getX(), 0)));
        actor.setY(Math.min(maxY, Math.max(actor.getY(), 0)));
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        Globals.incrementScore();

        if (aiList.size() < maxObjects && timeSinceGeneration > 1) {
            ArrayList<MovingAI> objects = generateGameObjects();
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
        ArrayList<MovingAI> holdingArea = new ArrayList<>();
        for (MovingAI ai : aiList) {
            if (ai.shouldDisappear()) {
                freeActor(ai);
                addToStash(ai);
                holdingArea.add(ai);
            }
        }
        for (MovingAI ai : holdingArea) {
            aiList.remove(ai);
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

    private void addToStash(MovingAI actor) {
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

    private boolean checkCollision(MovingAI actor, ArrayList<MovingAI> actorList) {
        for (iCollidable collidable : actorList) {
            if (collidable.collidesWith(actor)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<MovingAI> generateGameObjects() {
        int numToGenerate = random.nextInt(6);
        ArrayList<MovingAI> staging = new ArrayList<>();
        for (int i = 0; i < numToGenerate; i++) {
            MovingAI object = generateNewObject();
            // make sure generation does not result in collision right away
            while (checkCollision(object, aiList)) {
                // regen coordinates
                object.setX(random.nextFloat() * viewport.getWorldWidth());
                object.setY(random.nextFloat() * viewport.getWorldHeight());
            }
            staging.add(object);
            // add to list to ensure generation does not result in collision
            aiList.add(object);
        }
        return staging;
    }

    private MovingAI generateNewObject() {
        int choice = random.nextInt(6);
        MovingAI actor;
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

    @Override
    public void initStage() {
        Controls c1 = new Controls(Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT);
        Ball ball1 = new Ball(this.renderer, 20.0f, Color.RED, c1);
        // faster moving car
        TextureAtlas atlas = Globals.getAssetManager().get("characters.atlas", TextureAtlas.class);
        TextureRegionDrawable carDrawable = new TextureRegionDrawable(atlas.findRegion("car"));

        MovingAI carAI = new MovingAI(carDrawable, 80, 80, 150, 100, 150);

        // medium moving pen
        Controls p1 = settingsManager.getControlSettings().getControlOf(1);
        Controls p2 = settingsManager.getControlSettings().getControlOf(2);
        Pen player1;
        Car player2;
        if (this.entities.size() == 0) {
            player1 = new Pen(80, 80, 200, 0, 100, p1);
            player2 = new Car(80, 80, p2);
        } else {
            if (this.entities.get(0) instanceof Pen) {
                player1 = (Pen) this.entities.get(0);
                player1.setControl(p1);
                player2 = (Car) this.entities.get(1);
                player2.setControl(p2);
            } else {
                player1 = (Pen) this.entities.get(1);
                player1.setControl(p1);
                player2 = (Car) this.entities.get(0);
                player2.setControl(p2);
            }
        }

        // entities.addAll(Arrays.asList(ball1, ball2, car1, pen1));
        entities.clear();
        entities.addAll(Arrays.asList(player1, player2, ball1, carAI));

        for (Actor actor : entities) {
            this.getStage().addActor(actor);
        }

        // read keystrokes
        Gdx.input.setInputProcessor(this.getStage());
    }
}
