package com.mygdx.game.Screen;

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
import com.mygdx.game.Utils.Globals;

public class InstructionScreen extends AbstractScreen {
    private Skin skin;

    public InstructionScreen(Game game) {
        super(game);
        this.skin = Globals.getAssetManager().get("comic/skin/comic-ui.json", Skin.class);
        this.getCamera().update();

        initStage();
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
        Table mainTable = new Table();
        Table buttons = new Table();
        // set table to fill stage
        mainTable.setFillParent(true);
        // set alignment of contents in table
        mainTable.top();
        buttons.bottom();
        buttons.setX((Gdx.graphics.getWidth() - buttons.getWidth()) / 2);

        // button creation
        TextButton backButton = new TextButton("Go Back", skin);

        // fonts
        BitmapFont titleFont = Globals.getAssetManager().get("GamePlayedTitle.ttf", BitmapFont.class);
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = titleFont;

        BitmapFont contentFont = Globals.getAssetManager().get("GamePlayedContent.ttf", BitmapFont.class);
        Label.LabelStyle labelStyleContent = new Label.LabelStyle();
        labelStyleContent.font = contentFont;


        // end of fonts config

        Label title = new Label("Game Instructions", labelStyle2);
        title.setFontScale(1f);

        Label instructions1 = new Label("\n\n\nPlayers gets to control a male and female character around the screen.\n"
                + "\n" +
                "Healhty Food and Unhealthy Food will spawn around the map. Players will gain points from eating healthy food such as " +
                "Apple, Toast and Carrot. Player will lose points from eating unhealthy food such as Pizza, Fries and Boba.\n\n" +
                "Points Allocation System:\n\n" +
                "\tHealthy Food:\t\t Unhealthy Food:\n" +
                "\n\tToast = 10pts\t\t        Pizza = -10pts\n" +
                "\tCarrot = 15pts\t\t      Fries = -20pts\n" +
                "\tApple = 20pts\t\t       Boba = -20pts\n" +
                "\nAt the start of the game, each player will be given 5pts (Lives).\n" +
                "\nAs player consume tons of healthy food, players will become stronger(more health) and faster(speed)\n" +
                "\nIf player consume tons of unhealhty food, players will become weaker(less health) and slower(speed)\n" +
                "\n", skin);
        // End of mock samples and code
        instructions1.setWidth(750);
        instructions1.setWrap(true);
        instructions1.setY((Gdx.graphics.getHeight() - instructions1.getHeight()) / 2);
        instructions1.setX((Gdx.graphics.getWidth() - instructions1.getWidth()) / 2);
        instructions1.setFontScale(2);
        // add listeners to buttons
        // go to main screen if clicked
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                Globals.getScreenManager().setScreen(Globals.getScreenManager().getPreviousScreen().getClass());
            }
        });

        // add fields to table
        mainTable.add(title);
        mainTable.row();
        buttons.row();
        buttons.add(backButton);

        this.getStage().addActor(instructions1);
        this.getStage().addActor(mainTable);
        this.getStage().addActor(buttons);
    }

}
