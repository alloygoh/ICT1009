package com.mygdx.game.Screen;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Characters.Ball;
import com.mygdx.game.Characters.Car;
import com.mygdx.game.Characters.CollidableActor;
import com.mygdx.game.Characters.MovingImageActor;
import com.mygdx.game.Characters.MovingShapeActor;
import com.mygdx.game.Characters.Pen;
import com.mygdx.game.Utils.Controls;

public class GameScreen implements Screen{

    private Stage stage;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer renderer;
    private ArrayList<Actor> entities = new ArrayList<>();
    private Viewport viewport;

    public GameScreen(){
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera(screenWidth,screenHeight);
        this.viewport = new StretchViewport(screenWidth, screenHeight,camera);
        this.stage = new Stage(viewport);

        this.batch = new SpriteBatch();
        this.renderer = new ShapeRenderer();

        initStage();
        camera.update();
    }

    private void initStage(){
        Controls c1 = new Controls(Input.Keys.K, Input.Keys.J, Input.Keys.H, Input.Keys.L);
        Ball ball1 = new Ball(this.renderer, 20.0f, Color.RED, c1);
        // faster moving ball
        Controls c2 = new Controls(Input.Keys.E, Input.Keys.W, Input.Keys.Q, Input.Keys.R);
        Ball ball2 = new Ball(this.renderer, 20.0f, 0, Gdx.graphics.getHeight(), Color.YELLOW, 200,  c2);

        // medium moving pen
        Controls c3 = new Controls(Input.Keys.O, Input.Keys.I, Input.Keys.U, Input.Keys.P);
        Pen pen1 = new Pen(80, 80,200 ,0,100, c3);

        // default controls car
        Car car1 = new Car(80, 80);

        entities.addAll(Arrays.asList(ball1, ball2, car1, pen1));

        for(Actor actor: entities){
            stage.addActor(actor);
        }

        // read keystrokes
        Gdx.input.setInputProcessor(stage);

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
        float maxX = stage.getViewport().getWorldWidth() - actor.getWidth();
        float maxY = stage.getViewport().getWorldHeight() - actor.getHeight();
        if(actor instanceof Ball){
            // specially handle for circles as x starts from middle
            maxX = stage.getViewport().getWorldWidth() - actor.getWidth()*2;
            maxY = stage.getViewport().getWorldHeight() - actor.getHeight()*2;
        }
        if(actor.getX() > maxX || actor.getY() > maxY || actor.getX() < 0 || actor.getY() < 0){
            return true;
        }
        return false;
    }

    public void correctMovement(Actor actor){
        float maxX = stage.getViewport().getWorldWidth() - actor.getWidth();
        float maxY = stage.getViewport().getWorldHeight() - actor.getHeight();
        if(actor instanceof Ball){
            // specially handle for circles as x starts from middle
            maxX = stage.getViewport().getWorldWidth() - actor.getWidth()*2;
            maxY = stage.getViewport().getWorldHeight() - actor.getHeight()*2;
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

        stage.act(delta);
        governBorders();
        governCollisions();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        stage.getViewport().update(width, height);
        camera.update(); 
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
    //    renderer.dispose(); 
    //    batch.dispose();
    }
    
}
