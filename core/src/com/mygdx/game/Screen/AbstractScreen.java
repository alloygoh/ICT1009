package com.mygdx.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class AbstractScreen implements Screen{
    private Game game;
    private Stage stage;
    private OrthographicCamera camera;

    public AbstractScreen(Game game){
        this.game = game;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera(screenWidth, screenHeight);
        this.stage = new Stage();
    }

    public Game getGame() {
        return game;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
    
    // to be overwritten by all screens to add actors onto stage
    public abstract void initStage();

    @Override
    public void dispose(){
        stage.dispose();
    }
}
