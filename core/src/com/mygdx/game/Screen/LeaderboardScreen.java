package com.mygdx.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Leaderboard.Leaderboard;
import com.mygdx.game.Leaderboard.LeaderboardEntry;
import com.mygdx.game.Utils.Globals;

public class LeaderboardScreen implements Screen {
    Skin skin;
    Stage stage;
    OrthographicCamera camera;
    SpriteBatch batch;
    Game game;

    public LeaderboardScreen(Game game){
        this.game = game;
        this.skin = Globals.getAssetManager().get("comic/skin/comic-ui.json",Skin.class);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera(screenWidth, screenHeight);
        this.stage = new Stage(new StretchViewport(screenWidth, screenHeight, this.camera));
        camera.update();
    }

    @Override
    public void show() {
        //Stage should controll input:
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        // set table to fill stage
        mainTable.setFillParent(true); 
        // set alignment of contents in table
        mainTable.bottom();
        
        // button creation
        TextButton backButton = new TextButton("Go Back", skin);

        Leaderboard scoreBoard = new Leaderboard();

        // Mock Samples for testing
        LeaderboardEntry entry1 = new LeaderboardEntry("John", 50);
        LeaderboardEntry entry2 = new LeaderboardEntry("Bobby", 30);
        LeaderboardEntry entry3 = new LeaderboardEntry("Gabe", 60);
        LeaderboardEntry entry4 = new LeaderboardEntry("Daniel", 53);
        LeaderboardEntry entry5 = new LeaderboardEntry("Sammy", 80);
        LeaderboardEntry entry6 = new LeaderboardEntry("Dixie Normas",100);
        
        scoreBoard.reviseScoreboard(entry1);
        scoreBoard.reviseScoreboard(entry2);
        scoreBoard.reviseScoreboard(entry3);
        scoreBoard.reviseScoreboard(entry4);
        scoreBoard.reviseScoreboard(entry5);
        scoreBoard.reviseScoreboard(entry6);
        // End of mock samples and code

        Table scoreTable = new Table(skin);
        scoreTable.setFillParent(true);
        scoreTable.top();

        // add listeners to buttons
        // go to main screen if clicked
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent inputEvent, float x, float y){
                game.setScreen(new MainScreen(game));
            }
        });

        // add buttons to table
        for (int i = 1; i < scoreBoard.size()+1; i++)
        {
        TextField nameField = new TextField((String.valueOf(i) + ". " + scoreBoard.getLeaderboardEntryOfPosition(i).getName()), skin);
        TextField scoreField = new TextField(String.valueOf(scoreBoard.getLeaderboardEntryOfPosition(i).getScore()), skin);
        scoreTable.add(nameField);
        scoreTable.add(scoreField);
        scoreTable.row();
        }

        mainTable.row();
        mainTable.add(backButton);


        stage.addActor(scoreTable);
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
        // camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);
        // camera.update();
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
        skin.dispose();
        stage.dispose();
    }

}