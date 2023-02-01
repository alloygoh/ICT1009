package com.mygdx.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class LoadingScreen implements Screen {
    public static AssetManager assetManager;
    Skin skin;
    Game game;
    ProgressBar progressBar;
    SpriteBatch batch;
    Stage stage;
    OrthographicCamera camera;
    Label loadingLabel;
    public LoadingScreen(Game game) {
        this.game = game;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera(screenWidth, screenHeight);
        this.stage = new Stage(new StretchViewport(screenWidth, screenHeight, this.camera));
        camera.update();
        assetManager = new AssetManager();
        batch = new SpriteBatch();
        skin = new Skin();
        skin.addRegions(new TextureAtlas(Gdx.files.internal("comic/skin/comic-ui.atlas")));
        skin.load(Gdx.files.internal("comic/skin/comic-ui.json"));
        progressBar = new ProgressBar(0, 1, 0.1f, false, skin);
        loadingLabel = new Label("Loading...", skin);
    }
    @Override
    public void show() {
        FileHandle folder = Gdx.files.internal("assets");
        FileHandle[] files = folder.list();
        for (FileHandle file : files) {
            if (file.extension().equals("png")) { // Change this to include sounds and etc later on
                System.out.print(file.path());
                assetManager.load(file.path(), Texture.class);
            }
        }
        assetManager.finishLoading();
        stage.addActor(progressBar);
        stage.addActor(loadingLabel);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (assetManager.update()) {
            // transition to the next screen
            game.setScreen(new MainScreen(game, assetManager));
        }
//        batch.begin();
        progressBar.setValue(assetManager.getProgress());
        loadingLabel.setText("Loading... " + (int)(assetManager.getProgress() * 100) + "%");
//        progressBar.draw(batch, 1);
//        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        assetManager.dispose();
    }
}
