package com.mygdx.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Manager.SettingsManager;

public class MainScreen implements Screen {

    TextureAtlas atlas;
    Skin skin;
    Stage stage;
    OrthographicCamera camera;
    SpriteBatch batch;
    Game game;
    SettingsManager settingsManager;

    public MainScreen(Game game, SettingsManager settingsManager) {
        this.game = game;
        this.atlas = new TextureAtlas("comic/skin/comic-ui.atlas");
        this.skin = new Skin(Gdx.files.internal("comic/skin/comic-ui.json"));
        this.settingsManager = settingsManager;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera(screenWidth, screenHeight);
        this.stage = new Stage(new StretchViewport(screenWidth, screenHeight, this.camera));
        camera.update();
    }

    @Override
    public void show() {
        //Stage should controll input:
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        // set table to fill stage
        mainTable.setFillParent(true); 
        // set alignment of contents in table
        mainTable.top();

        // button creation
        TextButton playButton = new TextButton("Start", skin);
        TextButton instructionsButton = new TextButton("Instructions", skin);
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        // add listeners to buttons
        // create new game screen if clicked
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent inputEvent, float x, float y){
                game.setScreen(new GameScreen(settingsManager));
            }
        });

        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent inputEvent, float x, float y){
                game.setScreen(new SettingsScreen(game, settingsManager));
            }
        });

        // exit when exit button is clicked
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent inputEvent, float x, float y){
                Gdx.app.exit();
            }
        });

        // add buttons to table
        mainTable.add(playButton);
        mainTable.row();
        mainTable.add(instructionsButton);
        mainTable.row();
        mainTable.add(settingsButton);
        mainTable.row();
        mainTable.add(exitButton);
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        stage.getViewport().setWorldSize(width, height);
        stage.getViewport().update(width, height, true);
        // camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);
        // camera.update();
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
        skin.dispose();
        atlas.dispose();
    }

}
