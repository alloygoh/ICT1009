package com.mygdx.game.Screen;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Manager.SettingsManager;
import com.mygdx.game.Utils.Controls;
import com.mygdx.game.Utils.Globals;

public class SettingsScreen implements Screen {

    TextureAtlas atlas;
    Skin skin;
    Stage stage;
    OrthographicCamera camera;
    Game game;
    SettingsManager settingsManager;

    public SettingsScreen(Game game, SettingsManager settingsManager) {
        this.game = game;
        this.atlas = Globals.getAssetManager().get("comic/skin/comic-ui.atlas", TextureAtlas.class);
        this.skin = Globals.getAssetManager().get("comic/skin/comic-ui.json", Skin.class);
        this.settingsManager = settingsManager;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera(screenWidth, screenHeight);
        this.stage = new Stage(new StretchViewport(screenWidth, screenHeight, this.camera));
        camera.update();
    }

    @Override
    public void show() {
        // Stage should controll input:
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        // set table to fill stage
        mainTable.setFillParent(true);
        // set alignment of contents in table
        mainTable.top();

        // labels
        Label player1Label = new Label("Player 1 Controls",skin,"big");
        Label player2Label = new Label("Player 2 Controls", skin,"big");
        Label p1UpLabel = new Label("Up Key",skin);
        Label p1DownLabel = new Label("Down Key",skin);
        Label p1LeftLabel = new Label("Left Key",skin);
        Label p1RightLabel = new Label("Right Key",skin);

        Label p2UpLabel = new Label("Up Key",skin);
        Label p2DownLabel = new Label("Down Key",skin);
        Label p2LeftLabel = new Label("Left Key",skin);
        Label p2RightLabel = new Label("Right Key",skin);

        final Controls player1Controls = settingsManager.getControlSettings().getControlOf(1);
        final Controls player2Controls = settingsManager.getControlSettings().getControlOf(2);

        // button creation
        final TextButton player1UpButton = new TextButton(Input.Keys.toString(player1Controls.getUp()), skin);
        final TextButton player1DownButton = new TextButton(Input.Keys.toString(player1Controls.getDown()), skin);
        final TextButton player1LeftButton = new TextButton(Input.Keys.toString(player1Controls.getLeft()), skin);
        final TextButton player1RightButton = new TextButton(Input.Keys.toString(player1Controls.getRight()), skin);

        final TextButton player2UpButton = new TextButton(Input.Keys.toString(player2Controls.getUp()), skin);
        final TextButton player2DownButton = new TextButton(Input.Keys.toString(player2Controls.getDown()), skin);
        final TextButton player2LeftButton = new TextButton(Input.Keys.toString(player2Controls.getLeft()), skin);
        final TextButton player2RightButton = new TextButton(Input.Keys.toString(player2Controls.getRight()), skin);

        TextButton backButton = new TextButton("Back", skin);

        final Dialog configDialog = new Dialog("Configuration", skin,"dialog");
        configDialog.row().expandY();
        configDialog.add(new Label("Enter desired key!", skin,"big")).height(400);
        configDialog.row().expandY();
        configDialog.setColor(0,0,0,0);

        // add listeners to buttons
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                settingsManager.writeToConfig();
                game.setScreen(new MainScreen(game, settingsManager));
            }
        });

        // attach listener to each configurable button
        player1UpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                // display dialog asking for new key
                configDialog.show(stage);
                stage.addListener(new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        if(keycode != Input.Keys.ESCAPE){
                            player1Controls.setUp(keycode);
                            configDialog.hide();
                            player1UpButton.setText(Input.Keys.toString(keycode));
                        }
                        stage.removeListener(this);
                        configDialog.hide();
                        return super.keyDown(event, keycode);
                    }
                });
            }
        });
        player1DownButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                // display dialog asking for new key
                configDialog.show(stage);
                stage.addListener(new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        if(keycode != Input.Keys.ESCAPE){
                            player1Controls.setDown(keycode);
                            configDialog.hide();
                            player1DownButton.setText(Input.Keys.toString(keycode));
                        }
                        stage.removeListener(this);
                        configDialog.hide();
                        return super.keyDown(event, keycode);
                    }
                });
            }
        });
        player1LeftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                // display dialog asking for new key
                configDialog.show(stage);
                stage.addListener(new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        if(keycode != Input.Keys.ESCAPE){
                            player1Controls.setLeft(keycode);
                            configDialog.hide();
                            player1LeftButton.setText(Input.Keys.toString(keycode));
                        }
                        stage.removeListener(this);
                        configDialog.hide();
                        return super.keyDown(event, keycode);
                    }
                });
            }
        });
        player1RightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                // display dialog asking for new key
                configDialog.show(stage);
                stage.addListener(new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        if(keycode != Input.Keys.ESCAPE){
                            player1Controls.setRight(keycode);
                            configDialog.hide();
                            player1RightButton.setText(Input.Keys.toString(keycode));
                        }
                        stage.removeListener(this);
                        configDialog.hide();
                        return super.keyDown(event, keycode);
                    }
                });
            }
        });
        player2UpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                // display dialog asking for new key
                configDialog.show(stage);
                stage.addListener(new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        if(keycode != Input.Keys.ESCAPE){
                            player2Controls.setUp(keycode);
                            configDialog.hide();
                            player2UpButton.setText(Input.Keys.toString(keycode));
                        }
                        stage.removeListener(this);
                        configDialog.hide();
                        return super.keyDown(event, keycode);
                    }
                });
            }
        });

        player2DownButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                // display dialog asking for new key
                configDialog.show(stage);
                stage.addListener(new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        if(keycode != Input.Keys.ESCAPE){
                            player2Controls.setDown(keycode);
                            configDialog.hide();
                            player2DownButton.setText(Input.Keys.toString(keycode));
                        }
                        stage.removeListener(this);
                        configDialog.hide();
                        return super.keyDown(event, keycode);
                    }
                });
            }
        });

        player2LeftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                // display dialog asking for new key
                configDialog.show(stage);
                stage.addListener(new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        if(keycode != Input.Keys.ESCAPE){
                            player2Controls.setLeft(keycode);
                            configDialog.hide();
                            player2LeftButton.setText(Input.Keys.toString(keycode));
                        }
                        stage.removeListener(this);
                        configDialog.hide();
                        return super.keyDown(event, keycode);
                    }
                });
            }
        });

        player2RightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                // display dialog asking for new key
                configDialog.show(stage);
                stage.addListener(new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        if(keycode != Input.Keys.ESCAPE){
                            player2Controls.setRight(keycode);
                            configDialog.hide();
                            player2RightButton.setText(Input.Keys.toString(keycode));
                        }
                        stage.removeListener(this);
                        configDialog.hide();
                        return super.keyDown(event, keycode);
                    }
                });
            }
        });

        // add buttons to table
        mainTable.add(player1Label);
        mainTable.row();
        mainTable.add(p1UpLabel);
        mainTable.add(player1UpButton);
        mainTable.row();
        mainTable.add(p1DownLabel);
        mainTable.add(player1DownButton);
        mainTable.row();
        mainTable.add(p1LeftLabel);
        mainTable.add(player1LeftButton);
        mainTable.row();
        mainTable.add(p1RightLabel);
        mainTable.add(player1RightButton);
        mainTable.row();
        mainTable.add(player2Label);
        mainTable.row();
        mainTable.add(p2UpLabel);
        mainTable.add(player2UpButton);
        mainTable.row();
        mainTable.add(p2DownLabel);
        mainTable.add(player2DownButton);
        mainTable.row();
        mainTable.add(p2LeftLabel);
        mainTable.add(player2LeftButton);
        mainTable.row();
        mainTable.add(p2RightLabel);
        mainTable.add(player2RightButton);
        mainTable.row();
        mainTable.add(backButton);
        mainTable.row();
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
        stage.dispose();
    }

}