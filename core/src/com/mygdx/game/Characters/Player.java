package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Interfaces.iCollidable;
import com.mygdx.game.Interfaces.iSaveable;
import com.mygdx.game.Objects.BaseObject;
import com.mygdx.game.Utils.Controls;
import com.mygdx.game.Utils.Globals;

import java.util.HashMap;

public class Player extends CollidableActor implements iSaveable {
    private static TextureAtlas atlas = Globals.getAssetManager().get("characters.atlas", TextureAtlas.class);
    private static TextureRegionDrawable drawable = new TextureRegionDrawable(atlas.findRegion("player-base"));
    private int power;
    private int lifeCount;
    private boolean isDead;
    private Vector2 originCoordinates;
    private int highScore;

    // public Player() {
    //     this(40, 60);
    // }

    // public Player(float width, float height) {
    //     this(drawable, width, height, 0, 0, 100, Controls.Presets.DEFAULT);
    // }

    // public Player(float width, float height, Controls control) {
    //     this(drawable, width, height, 0, 0, 100, control);
    // }

    // public Player(float width, float height, float movementSpeed, Controls control) {
    //     this(drawable, width, height, 0, 0, movementSpeed, control);
    // }

    // public Player(float width, float height, float x, float y) {
    //     this(drawable, width, height, x, y, 100, Controls.Presets.DEFAULT);
    // }

    // public Player(float width, float height, float x, float y, Controls control) {
    //     this(drawable, width, height, x, y, 100, control);
    // }

    // public Player(float width, float height, float x, float y, float movementSpeed, Controls control) {
    //     this(drawable, width, height, x, y, movementSpeed, control);
    // }

    public Player(TextureRegionDrawable drawable, float width, float height, float x, float y, float movementSpeed,
            Controls control) {
        super(drawable, width, height, x, y, movementSpeed, control);
        this.highScore = 0;
        this.power = 0;
        this.lifeCount = 2;
        this.isDead = false;
        this.originCoordinates = new Vector2(x, y);
    }

    public int getPower() {
        return this.power;
    }
    
    public int getHighScore(){
        return this.highScore;
    }

    public boolean isDead() {
        return this.isDead;
    }
    
    private void reset(){
        this.power = 0;
        this.setX(this.originCoordinates.x);
        this.setY(this.originCoordinates.y);
    }
    
    private void loseLife(){
        this.lifeCount -= 1;
        this.isDead = (this.lifeCount <= 0);
    }

    @Override
    public void handleCollision(iCollidable collidable) {
        if (collidable instanceof BaseObject) {
            BaseObject object = (BaseObject) collidable;
            if(!this.isIdle()){
                this.power += object.getPowerPoints();
                object.reactToEvent("eaten", this);
            }
        } else if (collidable instanceof Player && collidable != this) {
            // collided with another player
            Player player = (Player) collidable;
            if (this.power > player.getPower()){
                // win
                if(this.highScore < this.power){
                    this.highScore = this.power;
                }

                player.reactToEvent("lose life", this);
                player.reactToEvent("reset", this);
                this.reactToEvent("reset", player);
                return;
            }else if (this.power < player.getPower()){
                // since forecasting is the method for detecting collisions, the other player does not see collision if idle
                // manually trigger collision event
                player.reactToEvent("collision", this);
                return;
            }

            // if same power, nothing happens
            super.handleCollision(collidable);
        }

    }

    @Override
    public void reactToEvent(String event, Object others) {
        if (event.equals("lose life")) {
            loseLife();
            return;
        } else if (event.equals("reset")) {
            reset();
            return;
        }

        super.reactToEvent(event, others);
    }

    @Override
    public void populate(HashMap<String, Object> options) {
        float x = (float) options.get("x");
        float y = (float) options.get("y");
        float width = (float) options.get("width");
        float height = (float) options.get("height");
        float movementSpeed = (float) options.get("speed");
        Controls controls = (Controls) options.get("controls");
        Vector2 origin = (Vector2) options.get("origin");
        int power = (int) options.get("power");
        int life = (int) options.get("life");
        int highScore = (int) options.get("highscore");
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setMovementSpeed(movementSpeed);
        this.setControl(controls);
        this.originCoordinates = origin;
        this.power = power;
        this.lifeCount = life;
        this.highScore = highScore;
    }

    @Override
    public HashMap<String, Object> stashOptions() {
        HashMap<String, Object> options = new HashMap<>();
        options.put("x", this.getX());
        options.put("y", this.getY());
        options.put("origin", this.originCoordinates);
        options.put("power", this.power);
        options.put("life", this.lifeCount);
        options.put("highscore", this.highScore);
        options.put("width", this.getWidth());
        options.put("height", this.getHeight());
        options.put("speed", this.getMovementSpeed());
        options.put("controls", this.getControl());
        return options;
    }
}
