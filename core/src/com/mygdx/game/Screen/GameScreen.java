package com.mygdx.game.Screen;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Characters.Ball;
import com.mygdx.game.Characters.Car;
import com.mygdx.game.Characters.CollidableActor;
import com.mygdx.game.Characters.MovingImageActor;
import com.mygdx.game.Characters.MovingShapeActor;
import com.mygdx.game.Characters.Pen;
import com.mygdx.game.Manager.SettingsManager;
import com.mygdx.game.Utils.Controls;

public class GameScreen extends AbstractScreen{
    SpriteBatch batch;
    ShapeRenderer renderer;
    ArrayList<Actor> entities;
    Viewport viewport;
    SettingsManager settingsManager;

    public GameScreen(Game game, SettingsManager settingsManager){
        super(game);
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        this.viewport = new StretchViewport(screenWidth, screenHeight,this.getCamera());
        this.getStage().setViewport(viewport);
        this.settingsManager = settingsManager;

        this.batch = new SpriteBatch();
        this.renderer = new ShapeRenderer();
        this.entities = new ArrayList<>();

        initStage();
        this.getCamera().update();
        this.getStage().setDebugAll(true);
    }


    private void governCollisions(){

        ArrayList<CollidableActor> collidables = new ArrayList<>();
        for(Actor actor:entities){
            if(actor instanceof CollidableActor){
                collidables.add((CollidableActor)actor);
            }
        }
        for(CollidableActor subject: collidables){
            for(CollidableActor collidableActor: collidables){
                if (subject.collidesWith(collidableActor)){
                    subject.handleCollision(collidableActor);
                }
            }
        }
    }


    public void governBorders(){
        for(Actor actor:entities){
            if (exceedingBorder(actor)){
                correctMovement(actor);
            }
        }
    }

    // determine if actors exceed game bounds
    public boolean exceedingBorder(Actor actor){
        float maxX = this.getStage().getViewport().getWorldWidth() - actor.getWidth();
        float maxY = this.getStage().getViewport().getWorldHeight() - actor.getHeight();
        if(actor instanceof Ball){
            // specially handle for circles as x starts from middle
            maxX = this.getStage().getViewport().getWorldWidth() - actor.getWidth()*2;
            maxY = this.getStage().getViewport().getWorldHeight() - actor.getHeight()*2;
        }
        if(actor.getX() > maxX || actor.getY() > maxY || actor.getX() < 0 || actor.getY() < 0){
            return true;
        }
        return false;
    }

    public void correctMovement(Actor actor){
        float maxX = this.getStage().getViewport().getWorldWidth() - actor.getWidth();
        float maxY = this.getStage().getViewport().getWorldHeight() - actor.getHeight();
        if(actor instanceof Ball){
            // specially handle for circles as x starts from middle
            maxX = this.getStage().getViewport().getWorldWidth() - actor.getWidth()*2;
            maxY = this.getStage().getViewport().getWorldHeight() - actor.getHeight()*2;
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
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        ScreenUtils.clear(169, 169, 169, 0);

        for (Actor actor: entities){
            if (actor instanceof MovingImageActor){
                MovingImageActor movingImageActor = (MovingImageActor) actor;
                movingImageActor.processKeyStrokes();
            } else if (actor instanceof MovingShapeActor){
                MovingShapeActor movingShapeActor = (MovingShapeActor) actor;
                movingShapeActor.processKeyStrokes();
            } 
        }

        this.getStage().act(delta);
        governBorders();
        governCollisions();
        this.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        this.getStage().getViewport().update(width, height,true);
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

    @Override
    public void initStage() {
        Controls c1 = new Controls(Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT);
        Ball ball1 = new Ball(this.renderer, 20.0f, Color.RED, c1);
        // faster moving ball
        // Controls c2 = new Controls(Input.Keys.E, Input.Keys.W, Input.Keys.Q, Input.Keys.R);
        // Ball ball2 = new Ball(this.renderer, 20.0f, 0, Gdx.graphics.getHeight(), Color.YELLOW, 200,  c2);

        // medium moving pen
        Controls p1 = settingsManager.getControlSettings().getControlOf(1);
        Controls p2 = settingsManager.getControlSettings().getControlOf(2);
        Pen pen1 = new Pen(80, 80,200 ,0,100, p1);
        Car car1 = new Car(80, 80,p2);

        // entities.addAll(Arrays.asList(ball1, ball2, car1, pen1));
        entities.addAll(Arrays.asList(car1, pen1, ball1));

        for(Actor actor: entities){
            this.getStage().addActor(actor);
        }

        // read keystrokes
        Gdx.input.setInputProcessor(this.getStage());

    }
    
}
