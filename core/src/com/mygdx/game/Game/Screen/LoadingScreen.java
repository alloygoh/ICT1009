package com.mygdx.game.Game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Game.Utils.Globals;
import com.mygdx.game.Engine.Screen.AbstractScreen;

public class LoadingScreen extends AbstractScreen {
    private final Skin skin;
    private final OnLoadListener onLoadListener;
    private final AssetManager assetManager;
    private ProgressBar progressBar;
    private Label loadingLabel;

    public LoadingScreen(Game game, OnLoadListener onLoadListener) {
        super(game);
        this.onLoadListener = onLoadListener;
        this.assetManager = Globals.getAssetManager();
        this.getCamera().update();
        this.skin = new Skin(Gdx.files.internal("comic/skin/comic-ui.json"));
        initStage();
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
        loadingLabel.setText("Loading... " + (int) (assetManager.getProgress() * 100) + "%");
        this.getStage().draw();
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
    public void initStage() {
        this.progressBar = new ProgressBar(0, 1, 0.1f, false, skin);
        this.loadingLabel = new Label("Loading...", skin);
        this.getStage().addActor(progressBar);
        this.getStage().addActor(loadingLabel);
    }

    // disposing of stage is handled by parent class
    // only overwrite when screen has additional objects to dispose

    public interface OnLoadListener {
        void onLoad();
    }
}
