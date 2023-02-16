package com.mygdx.game.Characters;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Interfaces.iCollidable;
import com.mygdx.game.Interfaces.iSaveable;
import com.mygdx.game.Utils.Controls;
import com.mygdx.game.Utils.Direction;
import com.mygdx.game.Utils.Globals;

public class Pen extends CollidableActor implements iSaveable{

    private static TextureAtlas atlas = Globals.getAssetManager().get("characters.atlas",TextureAtlas.class);
    private static TextureRegionDrawable drawable = new TextureRegionDrawable(atlas.findRegion("pen"));

    public Pen() {
        this(drawable.getMinWidth(), drawable.getMinHeight());
    }

    public Pen(float width, float height) {
        this(drawable, width, height, 0, 0, 100, Controls.Presets.DEFAULT);
    }

    public Pen(float width, float height, Controls control) {
        this(drawable, width, height, 0, 0, 100, control);
    }

    public Pen(float width, float height, float movementSpeed, Controls control) {
        this(drawable, width, height, 0, 0, movementSpeed, control);
    }

    public Pen(float width, float height, float x, float y) {
        this(drawable, width, height, x, y, 100, Controls.Presets.DEFAULT);
    }

    public Pen(float width, float height, float x, float y, Controls control) {
        this(drawable, width, height, x, y, 100, control);
    }

    public Pen(float width, float height, float x, float y, float movementSpeed, Controls control) {
        super(drawable, width, height, x, y, movementSpeed, control);
    }

    public Pen(TextureRegionDrawable drawable, float width, float height, float x, float y, float movementSpeed,
            Controls control) {
        super(drawable, width, height, x, y, movementSpeed, control);
    }

    @Override
    public void handleCollision(iCollidable collidable) {
        ArrayList<Direction> directions = this.getDirections();
        for (Direction direction : directions) {
            if (direction == Direction.LEFT) {
                float deltaX = this.getBounds().getX() - (collidable.getBounds().x + collidable.getBounds().getWidth());
                if (collidable.isIdle()) {
                    // handle full movement back until point of no collision
                    float newX = this.getX() - deltaX;
                    this.setX(newX);
                } else {
                    // both move back equal amounts
                    float newX = this.getX() - deltaX / 2;
                    this.setX(newX);
                }
            }
            if (direction == Direction.RIGHT) {
                float deltaX = (getBounds().getX() + getBounds().getWidth()) - collidable.getBounds().x;
                if (collidable.isIdle()) {
                    // handle full movement back until point of no collision
                    float newX = this.getX() - deltaX;
                    this.setX(newX);
                } else {
                    // both move back equal amounts
                    float newX = this.getX() - deltaX / 2;
                    this.setX(newX);
                }
            }
            if (direction == Direction.UP) {
                float deltaY = (getBounds().getY() + getBounds().getHeight()) - collidable.getBounds().y;
                if (collidable.isIdle()) {
                    // handle full movement back until point of no collision
                    float newY = this.getY() - deltaY;
                    this.setY(newY);
                } else {
                    // both move back equal amounts
                    float newY = this.getY() - deltaY / 2;
                    this.setY(newY);
                }
            }
            if (direction == Direction.DOWN) {
                float deltaY = getBounds().getY()
                        - (collidable.getBounds().getY() + collidable.getBounds().getHeight());
                if (collidable.isIdle()) {
                    // handle full movement back until point of no collision
                    float newY = this.getY() - deltaY;
                    this.setY(newY);
                } else {
                    // both move back equal amounts
                    float newY = this.getY() - deltaY / 2;
                    this.setY(newY);
                }
            }
            // mark as idle to prevent re-positioning
            this.getDirections().clear();
            this.getDirections().add(Direction.IDLE);

        }

    }

    @Override
    public void populate(HashMap<String, Object> options) {
        float x = (float)options.get("x");
        float y = (float)options.get("y");
        float width = (float)options.get("width");
        float height = (float)options.get("height");
        float movementSpeed = (float)options.get("speed");
        Controls controls = (Controls)options.get("controls");
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
