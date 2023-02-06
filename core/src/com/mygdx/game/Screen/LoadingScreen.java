package com.mygdx.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Utils.Globals;

public class LoadingScreen implements Screen {
    Skin skin;
    Game game;
    ProgressBar progressBar;
    Stage stage;
    OrthographicCamera camera;
    Label loadingLabel;
    OnLoadListener onLoadListener;
    AssetManager assetManager;

    public interface OnLoadListener{
        void onLoad();
    }

    public LoadingScreen(Game game, OnLoadListener onLoadListener) {
        this.game = game;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera(screenWidth, screenHeight);
        this.stage = new Stage(new StretchViewport(screenWidth, screenHeight, this.camera));
        this.onLoadListener = onLoadListener;
        this.assetManager = Globals.getAssetManager();

        camera.update();
        this.skin = new Skin(Gdx.files.internal("comic/skin/comic-ui.json"));
        this.progressBar = new ProgressBar(0, 1, 0.1f, false, skin);
        this.loadingLabel = new Label("Loading...", skin);

        stage.addActor(progressBar);
        stage.addActor(loadingLabel);
    }
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (assetManager.update()) {
            onLoadListener.onLoad();
        }
        progressBar.setValue(assetManager.getProgress());
        loadingLabel.setText("Loading... " + (int)(assetManager.getProgress() * 100) + "%");
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
        stage.dispose();
    }
}
