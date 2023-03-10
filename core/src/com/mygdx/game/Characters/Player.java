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
    private int lifeCount = 5; //player life count
    private boolean isDead = false; //to check if lifeCount is 0

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

    public boolean isDead()
    {
        return this.isDead;
    }

    @Override
    public void handleCollision(iCollidable collidable){
        if (collidable instanceof BaseObject){
            BaseObject object = (BaseObject)collidable;
            this.power += object.getPowerPoints();
            System.out.println(this.power);
            object.reactToEvent("eaten", object);
        } else if (collidable instanceof Player){
            // collided with another player
            Player player = (Player) collidable;
            if(this.power > player.getPower())
            {
                player.reactToEvent("lose life", player);
                player.reactToEvent("resetPosition", player);
                player.reactToEvent("resetPoints", player);
            } else if (this.power < player.getPower()){
                this.reactToEvent("lose life", this);
                player.reactToEvent("resetPosition", this);
                player.reactToEvent("resetPower", this);
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
        //Player otherPlayer = (Player)others;
        if (event.equals("lose life")) // this works
        {
            if (this.lifeCount > 0)
            {
                this.lifeCount--;
            }
        }
        if (event.equals("resetPower")) // doesnt work
        {
            if (this.power > 0 || this.power < 0)
            {
                this.power = 0;
            }
        }
        //if (event.equals("reset"))



        if (this.lifeCount == 0) //  somewhere in our code, boolean isDead will be checked to display end game screen and reset everybody
        {
            this.isDead = true;
            this.setX(-1); // for testing stuff
            this.setY(-1); // for testing stuff
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
