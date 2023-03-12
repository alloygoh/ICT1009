package com.mygdx.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Leaderboard.Leaderboard;
import com.mygdx.game.Leaderboard.LeaderboardEntry;
import com.mygdx.game.Utils.Globals;

public class LeaderboardScreen extends AbstractScreen {
    private Skin skin;
    private Table scoreTable;
    private Table mainTable;

    public LeaderboardScreen(Game game) {
        super(game);
        this.skin = Globals.getAssetManager().get("comic/skin/comic-ui.json", Skin.class);
        this.getCamera().update();
        initStage();
    }

    private void reloadScoreTable() {
        this.scoreTable.clear();
        // TODO make this a function cause repeated code..
        for (int i = 1; i < Globals.getLeaderboard().size() + 1; i++) {
            Label nameField = new Label(
                    (String.valueOf(i) + ". " + Globals.getLeaderboard().getLeaderboardEntryOfPosition(i).getName() + "\t"), skin);
            Label scoreField = new Label(String.valueOf(Globals.getLeaderboard().getLeaderboardEntryOfPosition(i).getScore()),
                    skin);
            this.scoreTable.add(nameField).padRight(50);
            this.scoreTable.add(scoreField);
            this.scoreTable.row();
        }
    }

    @Override
    public void show() {
        // Stage should control input:
        reloadScoreTable();
        this.getStage().clear();
        this.getStage().addActor(this.mainTable);

        Gdx.input.setInputProcessor(this.getStage());

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
//        this.scoreBoard.load();
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

    // disposing of stage is handled by parent class
    // only overwrite when screen has additional objects to dispose

    @Override
    public void initStage() {
         this.mainTable = new Table();
        // set table to fill stage
        this.mainTable.setFillParent(true);
        // set alignment of contents in table
        this.mainTable.top();

        // button creation
        TextButton backButton = new TextButton("Go Back", skin);

        // fonts
        BitmapFont titleFont = Globals.getAssetManager().get("GamePlayedTitle.ttf", BitmapFont.class);
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = titleFont;
        // end of fonts config
        Label title = new Label("Leaderboards", labelStyle2);
        this.mainTable.add(title);
        this.mainTable.row();

        this.scoreTable = new Table(skin);
        this.scoreTable.top();
        this.scoreTable.setWidth(100);
        // add listeners to buttons
        // go to main screen if clicked
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                Globals.getScreenManager().setScreen(Globals.getScreenManager().getPreviousScreen().getClass());
            }
        });

        // add buttons to table
        reloadScoreTable();

        this.mainTable.row();
        this.mainTable.add(this.scoreTable);
        this.mainTable.row();
        this.mainTable.add(backButton);

        this.getStage().addActor(this.mainTable);
    }

}
