package com.mygdx.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Utils.Globals;

public class InstructionScreen implements Screen {
    Skin skin;
    Stage stage;
    OrthographicCamera camera;
    Game game;

    public InstructionScreen(Game game){
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
        //Stage should control input:
        Gdx.input.setInputProcessor(stage);

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
        Label title = new Label("Game Instructions",skin);
        title.setFontScale(5f);

        Label instructions1 = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Est velit egestas dui id ornare arcu odio ut sem. Viverra nibh cras pulvinar mattis. Lacinia quis vel eros donec ac odio tempor. Massa tincidunt dui ut ornare lectus. Mi eget mauris pharetra et ultrices neque ornare aenean. Eget velit aliquet sagittis id consectetur purus ut faucibus pulvinar. Vestibulum lectus mauris ultrices eros in cursus turpis massa tincidunt. Eleifend mi in nulla posuere. Cras fermentum odio eu feugiat. Mattis rhoncus urna neque viverra justo nec ultrices. Leo in vitae turpis massa sed elementum tempus egestas sed. Fames ac turpis egestas integer eget aliquet nibh praesent. Lacinia at quis risus sed. Nibh sit amet commodo nulla facilisi nullam vehicula ipsum. Ac tincidunt vitae semper quis lectus. Non arcu risus quis varius quam quisque id diam vel.\n" +
                "\n" +
                "Nunc congue nisi vitae suscipit tellus mauris a. Eu sem integer vitae justo eget magna. Hendrerit gravida rutrum quisque non. Libero nunc consequat interdum varius sit amet mattis vulputate enim. Elit eget gravida cum sociis natoque penatibus et magnis dis. Eget nullam non nisi est sit amet facilisis. Nulla aliquet porttitor lacus luctus accumsan tortor posuere ac ut. Dictumst vestibulum rhoncus est pellentesque elit ullamcorper dignissim cras. Praesent tristique magna sit amet purus gravida quis blandit. Eleifend mi in nulla posuere sollicitudin aliquam. Justo eget magna fermentum iaculis eu. Vitae aliquet nec ullamcorper sit amet.\n" +
                "\n" +
                "Adipiscing commodo elit at imperdiet dui. Pellentesque sit amet porttitor eget dolor. Volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim. In massa tempor nec feugiat nisl pretium fusce id velit. Non arcu risus quis varius quam. Facilisi etiam dignissim diam quis enim lobortis scelerisque fermentum. Nisl vel pretium lectus quam id leo in. Molestie ac feugiat sed lectus. Ultrices eros in cursus turpis massa tincidunt dui. Placerat duis ultricies lacus sed turpis.\n" +
                "\n" +
                "Mi ipsum faucibus vitae aliquet. Augue ut lectus arcu bibendum at varius vel pharetra vel. Nam at lectus urna duis convallis convallis tellus id. Dignissim cras tincidunt lobortis feugiat. Fusce id velit ut tortor. Risus feugiat in ante metus dictum at tempor. In nisl nisi scelerisque eu ultrices vitae. Pretium aenean pharetra magna ac placerat vestibulum lectus mauris. Proin sagittis nisl rhoncus mattis rhoncus urna neque. Nunc mattis enim ut tellus. Libero enim sed faucibus turpis in eu mi bibendum. Ornare lectus sit amet est placerat in egestas.\n" +
                "\n" +
                "At ultrices mi tempus imperdiet nulla malesuada pellentesque elit. At volutpat diam ut venenatis tellus in. Enim nec dui nunc mattis enim ut tellus. Urna cursus eget nunc scelerisque. Sed ullamcorper morbi tincidunt ornare massa eget egestas purus viverra. Dui vivamus arcu felis bibendum. Ultrices eros in cursus turpis massa tincidunt dui ut. Nulla pellentesque dignissim enim sit. At tempor commodo ullamcorper a lacus vestibulum sed arcu non. In fermentum posuere urna nec tincidunt. Urna et pharetra pharetra massa massa ultricies.\n" +
                "\n" +
                "Fames ac turpis egestas integer eget aliquet nibh praesent tristique. Laoreet id donec ultrices tincidunt arcu. Egestas pretium aenean pharetra magna ac placerat vestibulum lectus. Phasellus vestibulum lorem sed risus ultricies tristique. Egestas fringilla phasellus faucibus scelerisque eleifend donec pretium vulputate. Aenean vel elit scelerisque mauris pellentesque pulvinar. Tempus quam pellentesque nec nam aliquam sem. Suspendisse potenti nullam ac tortor vitae purus faucibus ornare suspendisse. Tempor commodo ullamcorper a lacus. Posuere sollicitudin aliquam ultrices sagittis orci a scelerisque purus semper. Ut eu sem integer vitae justo eget magna. Tellus mauris a diam maecenas sed enim ut. In iaculis nunc sed augue. Ornare arcu dui vivamus arcu felis bibendum. Blandit massa enim nec dui nunc mattis. Posuere morbi leo urna molestie at elementum eu facilisis.\n" +
                "\n" +
                "Nisi quis eleifend quam adipiscing. Metus dictum at tempor commodo ullamcorper a lacus vestibulum sed. Condimentum id venenatis a condimentum vitae sapien pellentesque. Viverra vitae congue eu consequat. Facilisis volutpat est velit egestas dui. A pellentesque sit amet porttitor eget dolor morbi non arcu. Justo donec enim diam vulputate ut pharetra sit amet. Neque sodales ut etiam sit. Pulvinar pellentesque habitant morbi tristique senectus et netus et. Tristique et egestas quis ipsum suspendisse ultrices gravida dictum. Pulvinar elementum integer enim neque volutpat ac tincidunt vitae. Consectetur adipiscing elit pellentesque habitant. Metus aliquam eleifend mi in nulla posuere sollicitudin aliquam ultrices. Eu ultrices vitae auctor eu augue ut lectus arcu bibendum.\n" +
                "\n" +
                "Ut morbi tincidunt augue interdum velit. Integer vitae justo eget magna fermentum iaculis. Ullamcorper sit amet risus nullam eget. Quam elementum pulvinar etiam non quam. Non consectetur a erat nam at lectus urna duis. In vitae turpis massa sed elementum tempus egestas sed sed. Risus sed vulputate odio ut enim. Cursus turpis massa tincidunt dui ut. Vehicula ipsum a arcu cursus vitae congue. Nulla facilisi etiam dignissim diam quis enim lobortis scelerisque fermentum. Orci dapibus ultrices in iaculis nunc sed augue lacus viverra. Donec massa sapien faucibus et molestie ac. Risus in hendrerit gravida rutrum. Eros in cursus turpis massa tincidunt. Ipsum faucibus vitae aliquet nec ullamcorper sit amet risus. Nulla at volutpat diam ut venenatis.\n" +
                "\n" +
                "Euismod in pellentesque massa placerat duis ultricies lacus sed. Turpis egestas pretium aenean pharetra magna ac. Facilisis volutpat est velit egestas dui id. Morbi tincidunt ornare massa eget egestas purus. Fames ac turpis egestas maecenas. Rutrum quisque non tellus orci ac auctor augue. Egestas egestas fringilla phasellus faucibus scelerisque. Luctus accumsan tortor posuere ac ut consequat semper. In ante metus dictum at tempor commodo. Velit dignissim sodales ut eu sem integer vitae. Porttitor leo a diam sollicitudin tempor id eu nisl nunc. Ullamcorper velit sed ullamcorper morbi tincidunt ornare massa. Ornare suspendisse sed nisi lacus sed viverra tellus in.\n", skin);
        // End of mock samples and code
        instructions1.setWidth(750);
        instructions1.setWrap(true);
        instructions1.setY((Gdx.graphics.getHeight() - instructions1.getHeight()) / 2);
        instructions1.setX((Gdx.graphics.getWidth() - instructions1.getWidth()) / 2);

        // add listeners to buttons
        // go to main screen if clicked
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent inputEvent, float x, float y){
                game.setScreen(new MainScreen(game));
            }
        });

        // add fields to table
        mainTable.add(title);
        mainTable.row();
        buttons.row();
        buttons.add(backButton);
        stage.addActor(instructions1);
        stage.addActor(mainTable);
        stage.addActor(buttons);

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
