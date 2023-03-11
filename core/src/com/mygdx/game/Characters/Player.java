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

    public Player() {
        this(40, 60);
    }

    public Player(float width, float height) {
        this(drawable, width, height, 0, 0, 100, Controls.Presets.DEFAULT);
    }

    public Player(float width, float height, Controls control) {
        this(drawable, width, height, 0, 0, 100, control);
    }

    public Player(float width, float height, float movementSpeed, Controls control) {
        this(drawable, width, height, 0, 0, movementSpeed, control);
    }

    public Player(float width, float height, float x, float y) {
        this(drawable, width, height, x, y, 100, Controls.Presets.DEFAULT);
    }

    public Player(float width, float height, float x, float y, Controls control) {
        this(drawable, width, height, x, y, 100, control);
    }

    public Player(float width, float height, float x, float y, float movementSpeed, Controls control) {
        this(drawable, width, height, x, y, movementSpeed, control);
    }

    public Player(TextureRegionDrawable drawable, float width, float height, float x, float y, float movementSpeed,
            Controls control) {
        super(drawable, width, height, x, y, movementSpeed, control);
        this.power = 0;
        this.lifeCount = 5;
        this.isDead = false;
        this.originCoordinates = new Vector2(x, y);
    }

    public int getPower() {
        return this.power;
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
            this.power += object.getPowerPoints();
            object.reactToEvent("eaten", this);
        } else if (collidable instanceof Player && collidable != this) {
            // collided with another player
            Player player = (Player) collidable;
            if (this.power > player.getPower()) {
                player.reactToEvent("lose life", this);
                player.reactToEvent("reset", this);
                this.reactToEvent("reset", player);
            }
            // if same power, nothing happens
            super.handleCollision(collidable);
        }

    }

    @Override
    public void reactToEvent(String event, Object others) {
        if (event.equals("lose life")) {
            // TODO
            // check isDead to change screen to game over screen
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
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setMovementSpeed(movementSpeed);
        this.setControl(controls);
    }

    @Override
    public HashMap<String, Object> stashOptions() {
        HashMap<String, Object> options = new HashMap<>();
        options.put("x", this.getX());
        options.put("y", this.getY());
        options.put("width", this.getWidth());
        options.put("height", this.getHeight());
        options.put("speed", this.getMovementSpeed());
        options.put("controls", this.getControl());
        return options;
    }
}
