package com.mygdx.game.Game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Engine.Screen.AbstractScreen;
import com.mygdx.game.Game.Utils.Globals;
import com.mygdx.game.Engine.Manager.ScreenManager;
import com.mygdx.game.Engine.Manager.SettingsManager;

public class PauseScreen extends AbstractScreen {

    private final Skin skin;
    private final SettingsManager settingsManager;
    private final ScreenManager screenManager;

    public PauseScreen(Game game) {
        super(game);
        this.settingsManager = Globals.getSettingsManager();
        this.screenManager = Globals.getScreenManager();
        this.skin = Globals.getAssetManager().get("comic/skin/comic-ui.json", Skin.class);
        this.getCamera().update();
        initStage();
    }

    @Override
    public void show() {
        // Stage should control input:
        Gdx.input.setInputProcessor(this.getStage());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        this.getStage().act();
        this.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        this.getCamera().setToOrtho(false, width, height);
        this.getStage().getViewport().setWorldSize(width, height);
        this.getStage().getViewport().update(width, height, true);
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

    // disposing of stage is handled by parent class
    // only overwrite when screen has additional objects to dispose

    @Override
    public void initStage() {
        Table mainTable = new Table();
        // set table to fill stage
        mainTable.setFillParent(true);
        // set alignment of contents in table
        mainTable.top();

        // button creation
        TextButton resumeButton = new TextButton("Resume", skin);
        TextButton scoreButton = new TextButton("Leaderboard", skin);
        TextButton instructionsButton = new TextButton("Instructions", skin);
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        // fonts
        BitmapFont titleFont = Globals.getAssetManager().get("GamePlayedTitle.ttf", BitmapFont.class);
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = titleFont;

        Label title = new Label("GAME PAUSED", labelStyle2);

        // add listeners to buttons
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                screenManager.setScreen(GameScreen.class);

            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                if (screenManager.getScreen((SettingsScreen.class)) == null) {
                    screenManager.addScreen(new SettingsScreen(getGame(), settingsManager));
                }
                screenManager.setScreen(SettingsScreen.class);
            }
        });
        // enter leaderboard screen if clicked
        scoreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                if (screenManager.getScreen((LeaderboardScreen.class)) == null) {
                    screenManager.addScreen(new LeaderboardScreen(getGame()));
                }
                screenManager.setScreen(LeaderboardScreen.class);
            }
        });

        instructionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                if (screenManager.getScreen((InstructionScreen.class)) == null) {
                    screenManager.addScreen(new InstructionScreen(getGame()));
                }
                screenManager.setScreen(InstructionScreen.class);
            }
        });

        // exit when exit button is clicked
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                screenManager.disposeAll();
                Gdx.app.exit();
            }
        });

        // add buttons to table
        mainTable.add(title);
        mainTable.row();
        mainTable.add(resumeButton);
        mainTable.row();
        mainTable.add(scoreButton);
        mainTable.row();
        mainTable.add(instructionsButton);
        mainTable.row();
        mainTable.add(settingsButton);
        mainTable.row();
        mainTable.add(exitButton);

        this.getStage().addActor(mainTable);
    }

}
