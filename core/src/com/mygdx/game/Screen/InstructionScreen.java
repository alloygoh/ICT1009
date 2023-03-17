package com.mygdx.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Utils.Globals;
import com.badlogic.gdx.utils.Align;


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

        BitmapFont contentFont = Globals.getAssetManager().get("GamePlayedInstructions.ttf", BitmapFont.class);
        Label.LabelStyle labelStyleContent = new Label.LabelStyle();
        labelStyleContent.font = contentFont;


        // end of fonts config

        Label title = new Label("Game Instructions", labelStyle2);
        title.setFontScale(1f);

        Label instructions1 = new Label("\nHelp Bobby and Candice improve to be a better version of themselves!" +
                "\n\nPlayer 1 and Player 2 will control Bobby and Candice respectively.\n" +
                "\nPlease adjust the controls through the Settings screen in the main menu.\n" +
                "Different foods will regularly spawn in the arena.\n" +
                "Consuming healthy foods will gain points and level up your character.\n" +
                "Consuming unhealthy foods will result in a loss of points.\n\n" +
                "Points Allocation System:\n\n" +
                "\tHealthy Food:\t\t\t\t\t      Unhealthy Food:\n" +
                "\n\tToast = 10pts\t\t        Pizza = -10pts\n" +
                "\tCarrot = 15pts\t\t      Fries = -20pts\n" +
                "\tApple = 20pts\t\t       Boba = -20pts\n" +
                "\nEach player will have 2 lives before being eliminated." +
                "\n\nThere is a grace period at the start of the game. Players are recommended to consume the right foods during this time frame.\n" +
                "\nOnce the grace period has ended, players will lose their invulnerability and be subjected to being defeated by the other player." +
                "\n\nWhen Bobby or Candice have consumed enough healthy food, they will level up and attain increased movement speed.\n" +
                "Unhealthy foods will weaken them overtime.\n" +
                "\nWhen Bobby or Candice has fallen, the weaker player will have a chance to prevent a loss of life.\n" +
                "The player has to complete the sequence of keystrokes presented on the screen in a set amount of time.\n" +
                "Successfully completing the challenge will allow the player to try again and redeem their bad eating habits.\n" +
                "\nPlayers are able to use the EXERCISE button to increase their character's fitness and power level. However, characters will be immobile and vulnerable while exercising.\n" +
                "\n", labelStyleContent);
        // End of mock samples and code
        instructions1.setFontScale(1f);
        instructions1.setWrap(true);

        Table instructionsTable = new Table();
        instructionsTable.setSize(this.getStage().getWidth() / 3f - instructionsTable.getWidth() / 3f, this.getStage().getHeight() / 2f - instructionsTable.getHeight() / 2f);
        instructionsTable.setPosition(this.getStage().getWidth() / 2f - instructionsTable.getWidth() / 2f, this.getStage().getHeight() / 2f - instructionsTable.getHeight() / 2f);
        instructionsTable.align(Align.topLeft);
        ScrollPane instructionsPane = new ScrollPane(instructions1, skin);
        instructionsPane.setScrollingDisabled(true,false);
        instructionsPane.setForceScroll(false, true); // Allow scrolling only vertically
        instructionsPane.setWidth(1000);
        instructionsTable.add(instructionsPane).expand().fill();
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

        this.getStage().addActor(instructionsTable);
        this.getStage().addActor(mainTable);
        this.getStage().addActor(buttons);
    }

}
