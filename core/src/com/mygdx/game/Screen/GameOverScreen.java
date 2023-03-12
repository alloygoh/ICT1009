package com.mygdx.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Leaderboard.LeaderboardEntry;
import com.mygdx.game.Utils.Globals;

public class GameOverScreen extends AbstractScreen{

    private Skin skin;
    private int score;

    public GameOverScreen(Game game, int score) {
        super(game);
        this.skin = Globals.getAssetManager().get("comic/skin/comic-ui.json", Skin.class);
        this.getCamera().update();
        this.score = score;

        initStage();
    }

    @Override
    public void initStage() {
        Table mainTable = new Table();
        Table buttons = new Table();
        // set table to fill stage
        mainTable.setFillParent(true);
        // set alignment of contents in table
        mainTable.top();
        buttons.bottom();
        buttons.setX((Gdx.graphics.getWidth() - buttons.getWidth()) / 2);

        // button creation
        TextButton backButton = new TextButton("Submit", skin);

        // fonts
        BitmapFont titleFont = Globals.getAssetManager().get("GamePlayedTitle.ttf", BitmapFont.class);
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = titleFont;

        BitmapFont contentFont = Globals.getAssetManager().get("GamePlayedContent.ttf", BitmapFont.class);
        Label.LabelStyle labelStyleContent = new Label.LabelStyle();
        labelStyleContent.font = contentFont;


        // end of fonts config

        Label title = new Label("GAME OVER!\nSCORE: " + score , labelStyle2);
        title.setFontScale(1f);

        Label header = new Label("Name: ", skin);
        header.setFontScale(3f);

        // Create a text field using the skin
        final TextField textField = new TextField("", skin);
        textField.getStyle().font.getData().setScale(3f);
        textField.getStyle().cursor.setTopHeight(40);
        textField.getStyle().cursor.setBottomHeight(40);

        // Set the position and size of the text field
        textField.setSize(400, 100);
        textField.setPosition((Gdx.graphics.getWidth() - textField.getWidth()) / 2, (Gdx.graphics.getHeight() - textField.getHeight()) / 2);

        // Set the position and size of header
        header.setWidth(750);
        header.setWrap(true);
        header.setY((Gdx.graphics.getHeight() - header.getHeight()) / 2);
        header.setX((Gdx.graphics.getWidth() - header.getWidth()) / 2);

        // add listeners to buttons
        // Save to file if clicked -> return to menu after
        backButton.addListener(new ClickListener() {
            private LeaderboardEntry entry;
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                //
                this.entry = new LeaderboardEntry(textField.getText(), score);
                Globals.getLeaderboard().reviseScoreboard(this.entry);
                Globals.getScreenManager().setScreen(Globals.getScreenManager().getScreen(MainScreen.class).getClass());
            }
        });

        // add fields to table
        mainTable.add(title);
        mainTable.row();
        buttons.row();
        buttons.add(backButton);

        this.getStage().addActor(header);
        this.getStage().addActor(textField);
        this.getStage().addActor(mainTable);
        this.getStage().addActor(buttons);
    }

    @Override
    public void show() {
        //Stage should control input:
        Gdx.input.setInputProcessor(this.getStage());
    }

    @Override
    public void render(float delta) {
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
}
