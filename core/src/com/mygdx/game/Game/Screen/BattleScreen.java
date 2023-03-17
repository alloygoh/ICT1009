package com.mygdx.game.Game.Screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.Game.Characters.Girl;
import com.mygdx.game.Game.Characters.Player;
import com.mygdx.game.Engine.Utils.Controls;
import com.mygdx.game.Game.Utils.Globals;
import com.mygdx.game.Engine.Manager.ScreenManager;
import com.mygdx.game.Engine.Screen.AbstractScreen;

public class BattleScreen extends AbstractScreen {
    private final Skin skin;
    private final ScreenManager screenManager;
    private final Random random;
    private final Player player;
    private final int powerDiff;
    private final ArrayList<Player> players;
    private final ArrayList<TextField> directions;
    private final float maxTimeout = 6f;
    private float countDown;
    private Label countDownLabel;

    public BattleScreen(Game game, Player player, int powerDiff, ArrayList<Player> players) {
        super(game);
        this.screenManager = Globals.getScreenManager();
        this.skin = Globals.getAssetManager().get("comic/skin/comic-ui.json", Skin.class);
        this.getCamera().update();
        this.player = player;
        this.powerDiff = powerDiff;
        this.random = new Random();
        this.directions = new ArrayList<TextField>();
        this.countDown = 3f;
        this.players = players;
        initStage();
    }

    @Override
    public void show() {
        // prevent inputs from cluttering at first
        // provide buffer time to prevent inputs from being intercepted from the start
        Gdx.input.setInputProcessor(null);
    }

    private void refreshCount(float delta) {
        countDown -= delta;
        if (countDown < 0) {
            this.countDownLabel.setText("Ends in " + (maxTimeout + countDown));
        } else {
            this.countDownLabel.setText("Starts in " + countDown);
        }
        if (Gdx.input.getInputProcessor() == null && countDown <= 0) {
            // reactivate input
            Sound SFXstart = Globals.getAssetManager().get("sound/start.mp3");
            SFXstart.play(1.0f);
            Gdx.input.setInputProcessor(this.getStage());
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        this.getStage().act();
        refreshCount(delta);
        if (exceedTime()) {
            triggerLose();
        }
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

    private ArrayList<Integer> generateSequence() {
        int numToGenerate = powerDiff / 2;
        Controls control = player.getControl();
        ArrayList<Integer> selection = new ArrayList<Integer>(
                Arrays.asList(control.getUp(), control.getDown(), control.getLeft(), control.getRight()));
        ArrayList<Integer> sequence = new ArrayList<Integer>();
        if (powerDiff / 2 > 15) {
            numToGenerate = 15;
        }
        for (int i = 0; i < numToGenerate; i++) {
            sequence.add(selection.get(random.nextInt(selection.size())));
        }
        return sequence;

    }

    private void triggerLose() {
        player.reactToEvent("lose life", player);
        for (Player p : players) {
            p.reactToEvent("reset", p);
        }
        Globals.getScreenManager().setScreen(GameScreen.class);
    }

    private boolean exceedTime() {
        // exceeded max timeout (5 secs)
        return Math.abs(this.countDown) > maxTimeout;
    }

    @Override
    public void initStage() {
        Table mainTable = new Table();
        // set table to fill stage
        mainTable.setFillParent(true);
        // set alignment of contents in table
        mainTable.top().center();

        BitmapFont battleLabelFont = Globals.getAssetManager().get("battleFont.ttf", BitmapFont.class);
        Label.LabelStyle battleLabelStyle = new Label.LabelStyle();
        battleLabelStyle.font = battleLabelFont;

        BitmapFont timerFont = Globals.getAssetManager().get("battleLabelFont.ttf", BitmapFont.class);
        Label.LabelStyle timerStyle = new Label.LabelStyle();
        timerStyle.font = timerFont;
        this.countDownLabel = new Label("Starts in " + countDown, timerStyle);
        this.countDownLabel.setPosition(
                (this.getStage().getViewport().getWorldWidth() - this.countDownLabel.getWidth()) / 2,
                this.getStage().getViewport().getWorldHeight() - this.countDownLabel.getHeight() - 30);

        int playerNumber = 1;
        if (player instanceof Girl) {
            playerNumber = 2;
        }

        String label = "Player " + playerNumber + " Battle Sequence:";
        Label battleLabel = new Label(label, battleLabelStyle);
        ArrayList<Integer> sequence = generateSequence();
        mainTable.add(battleLabel).colspan(sequence.size() + 1).padBottom(10);
        mainTable.row();
        for (int target : sequence) {
            TextField text = new TextField(Input.Keys.toString(target), skin);
            mainTable.add(text).padRight(2);
            directions.add(text);
            if (directions.size() % 5 == 0) {
                mainTable.row();
            }
        }

        this.getStage().addActor(mainTable);
        this.getStage().addActor(countDownLabel);

        this.getStage().addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                TextField currentField = directions.remove(0);
                // if has not hit timeout and correct key
                if (Input.Keys.toString(keycode).equals(currentField.getText()) && !exceedTime()) {
                    // same key pressed
                    currentField.setVisible(false);
                    // if finished
                    if (directions.size() == 0) {
                        player.reactToEvent("defended", player);
                        for (Player p : players) {
                            p.reactToEvent("reset position", p);
                        }
                        screenManager.setScreen(GameScreen.class);
                    }
                } else {
                    triggerLose();
                }
                return super.keyUp(event, keycode);
            }
        });
    }

}
