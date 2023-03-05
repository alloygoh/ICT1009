package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

    public Player() {
        this(40,60);
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
        super(drawable, width, height, x, y, movementSpeed, control);
    }

    public Player(TextureRegionDrawable drawable, float width, float height, float x, float y, float movementSpeed,
               Controls control) {
        super(drawable, width, height, x, y, movementSpeed, control);
        this.power = 0;
    }

    public int getPower(){
        return this.power;
    }

    @Override
    public void handleCollision(iCollidable collidable){
        if (collidable instanceof BaseObject){
            BaseObject object = (BaseObject)collidable;
            this.power += object.getPowerPoints();
            object.reactToEvent("eaten", object);
        } else if (collidable instanceof Player){
            // collided with another player
            Player player = (Player) collidable;
            if(this.power > player.getPower()){
                player.reactToEvent("lose life", player);
            } else if (this.power < player.getPower()){
                this.reactToEvent("lose life", this);
            }
            // if same power, nothing happens
            super.handleCollision(collidable);
        }

    }

    @Override
    public void reactToEvent(String event, Object others){
        // TODO
        // add life logic here
        // deduction of life all that

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
