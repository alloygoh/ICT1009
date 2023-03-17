package com.mygdx.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Leaderboard.LeaderboardEntry;
import com.mygdx.game.Utils.Globals;

public class GameOverScreen extends AbstractScreen {

    private final Skin skin;
    Label titleLabel;
    TextField nameField;

    public GameOverScreen(Game game) {
        super(game);
        this.skin = Globals.getAssetManager().get("comic/skin/comic-ui.json", Skin.class);
        this.getCamera().update();
        initStage();
    }


    private void addNewLeaderboardEntry() {
        LeaderboardEntry entry = new LeaderboardEntry(this.nameField.getText(), Globals.getScore());
        Globals.getLeaderboard().reviseScoreboard(entry);
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
        TextButton submitButton = new TextButton("Submit", skin);

        // fonts
        BitmapFont titleFont = Globals.getAssetManager().get("GamePlayedTitle.ttf", BitmapFont.class);
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = titleFont;

        BitmapFont contentFont = Globals.getAssetManager().get("GamePlayedName.ttf", BitmapFont.class);
        Label.LabelStyle labelStyleContent = new Label.LabelStyle();
        labelStyleContent.font = contentFont;


        // end of fonts config

        this.titleLabel = new Label("GAME OVER!\nSCORE: " + Globals.getScore(), labelStyle2);
        titleLabel.setFontScale(1f);

        Label header = new Label("Name:  ", labelStyleContent);

        // Create a text field using the skin
        this.nameField = new TextField("", skin);
        nameField.getStyle().cursor.setTopHeight(40);
        nameField.getStyle().cursor.setBottomHeight(40);

        // Set the position and size of the text field
        nameField.setSize(400, 100);
        nameField.setPosition((Gdx.graphics.getWidth() - nameField.getWidth()) / 2, (Gdx.graphics.getHeight() - nameField.getHeight()) / 2);

        // Set the position and size of header
        header.setWidth(750);
        header.setWrap(true);
        header.setY((Gdx.graphics.getHeight() - header.getHeight()) / 2);
        header.setX((Gdx.graphics.getWidth() - header.getWidth()) / 2);

        // add listeners to buttons
        // Save to file if clicked -> return to menu after
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                addNewLeaderboardEntry();
                nameField.setText("");
                Globals.getScreenManager().setScreen(MainScreen.class);
            }
        });

        // add fields to table
        mainTable.add(titleLabel);
        mainTable.row();
        buttons.row();
        buttons.add(submitButton);

        this.getStage().addActor(header);
        this.getStage().addActor(nameField);
        this.getStage().addActor(mainTable);
        this.getStage().addActor(buttons);
    }

    @Override
    public void show() {
        // stage should control input:
        Gdx.input.setInputProcessor(this.getStage());
        Sound SFXGameEnd = Globals.getAssetManager().get("sound/game-end.mp3");
        SFXGameEnd.play(1.0f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        this.getStage().act();
        this.titleLabel.setText("GAME OVER!\nSCORE: " + Globals.getScore());
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
