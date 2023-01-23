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
import com.mygdx.game.Characters.Ball;
import com.mygdx.game.Characters.Car;
import com.mygdx.game.Characters.MovingImageActor;
import com.mygdx.game.Characters.MovingShapeActor;
import com.mygdx.game.Characters.Pen;
import com.mygdx.game.Utils.Controls;

public class GameScreen implements Screen{

    Stage stage;
    OrthographicCamera camera;
    SpriteBatch batch;
    ShapeRenderer renderer;
    ArrayList<Actor> entities = new ArrayList<>();

    public GameScreen(){
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(screenWidth,screenHeight);
        this.stage = new Stage(new StretchViewport(screenWidth, screenHeight, camera));

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
        Ball ball2 = new Ball(this.renderer, 20.0f, 0, Gdx.graphics.getHeight(), Color.YELLOW, 20,  c2);

        // medium moving pen
        Controls c3 = new Controls(Input.Keys.O, Input.Keys.I, Input.Keys.U, Input.Keys.P);
        Pen pen1 = new Pen(80, 80, 10, c3);

        // default controls car
        Car car1 = new Car(80, 80);

        entities.addAll(Arrays.asList(ball1, ball2, car1, pen1));

        for(Actor actor: entities){
            stage.addActor(actor);
        }

        // read keystrokes
        Gdx.input.setInputProcessor(stage);

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
            Class c = actor.getClass().getSuperclass();
            if (c == MovingImageActor.class){
                MovingImageActor movingImageActor = (MovingImageActor) actor;
                movingImageActor.processKeyStrokes();
            } else if (c == MovingShapeActor.class){
                MovingShapeActor movingShapeActor = (MovingShapeActor) actor;
                movingShapeActor.processKeyStrokes();
            }
        }

        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        
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
