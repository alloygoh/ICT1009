package com.mygdx.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Leaderboard.Leaderboard;
import com.mygdx.game.Utils.Globals;

public class LeaderboardScreen extends AbstractScreen {
    private Skin skin;
    private Table scoreTable;

    public LeaderboardScreen(Game game) {
        super(game);
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
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        this.getStage().act();
        generateLeaderboardDisplay();
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
        return;
    }

    @Override
    public void resume() {
        return;
    }

    @Override
    public void hide() {
        return;
    }

    private void generateLeaderboardDisplay(){
        this.scoreTable.clear();

        // add buttons to table
        Leaderboard scoreBoard = Globals.getLeaderboard();
        for (int i = 1; i < scoreBoard.size() + 1; i++) {
            TextField nameField = new TextField(
                    (String.valueOf(i) + ". " + scoreBoard.getLeaderboardEntryOfPosition(i).getName()), skin);
            TextField scoreField = new TextField(String.valueOf(scoreBoard.getLeaderboardEntryOfPosition(i).getScore()),
                    skin);
            scoreTable.add(nameField);
            scoreTable.add(scoreField);
            scoreTable.row();
        }
        
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
        TextButton backButton = new TextButton("Go Back", skin);


        // fonts
        BitmapFont titleFont = Globals.getAssetManager().get("GamePlayedTitle.ttf", BitmapFont.class);
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = titleFont;
        // end of fonts config
        Label title = new Label("Leaderboards", labelStyle2);
        mainTable.add(title);
        mainTable.row();

        this.scoreTable = new Table(skin);
        scoreTable.top();

        // add listeners to buttons
        // go to main screen if clicked
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                Globals.getScreenManager().setScreen(Globals.getScreenManager().getPreviousScreen().getClass());
            }
        });

        generateLeaderboardDisplay();

        mainTable.row();
        mainTable.add(this.scoreTable);
        mainTable.row();
        mainTable.add(backButton);

        this.getStage().addActor(mainTable);
    }

}
