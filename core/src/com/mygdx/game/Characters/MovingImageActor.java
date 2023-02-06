package com.mygdx.game.Characters;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Utils.Controls;
import com.mygdx.game.Utils.Direction;
import com.mygdx.game.Utils.Globals;

public class MovingImageActor extends Actor {
    float width;
    float height;
    float x;
    float y;
    float movementSpeed;
    Controls control;
    ArrayList<Direction> directions;
    static TextureAtlas atlas = Globals.getAssetManager().get("characters.atlas",TextureAtlas.class);
    TextureRegionDrawable texture;

    public MovingImageActor(TextureRegionDrawable texture, float width, float height) {
        this(texture, width, height, 0, 0, 100, Controls.Presets.DEFAULT);
    }

    public MovingImageActor(TextureRegionDrawable texture, float width, float height, Controls control) {
        this(texture, width, height, 0, 0, 100, control);
    }

    public MovingImageActor(TextureRegionDrawable texture, float width, float height, float x, float y) {
        this(texture, width, height, x, y, 100, Controls.Presets.DEFAULT);
    }

    public MovingImageActor(TextureRegionDrawable texture, float width, float height, float x, float y,
            Controls control) {
        this(texture, width, height, x, y, 100, control);
    }

    public MovingImageActor(TextureRegionDrawable texture, float width, float height, float x, float y,
            float movementSpeed, Controls control) {
        super();
        this.texture = texture;
        this.setWidth(width);
        this.setHeight(height);
        this.setX(x);
        this.setY(y);
        this.control = control;
        this.movementSpeed = movementSpeed;
        this.directions = new ArrayList<>();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        texture.draw(batch, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public ArrayList<Direction> getDirections(){
        return this.directions;
    }

    // provide basic movments
    public void moveUp() {
        this.setY(this.getY() + this.movementSpeed * Gdx.graphics.getDeltaTime());
    }

    public void moveDown() {
        this.setY(this.getY() - this.movementSpeed * Gdx.graphics.getDeltaTime());
    }

    public void moveLeft() {
        this.setX(this.getX() - this.movementSpeed * Gdx.graphics.getDeltaTime());
    }

    public void moveRight() {
        this.setX(this.getX() + this.movementSpeed * Gdx.graphics.getDeltaTime());
    }

    public void processKeyStrokes() {
        directions.clear();
        if (Gdx.input.isKeyPressed(control.getUp())) {
            this.moveUp();
            this.directions.add(Direction.UP);
        }

        if (Gdx.input.isKeyPressed(control.getDown())) {
            this.moveDown();
            this.directions.add(Direction.DOWN);
        }

        if (Gdx.input.isKeyPressed(control.getLeft())) {
            this.moveLeft();
            this.directions.add(Direction.LEFT);
        }

        if (Gdx.input.isKeyPressed(control.getRight())) {
            this.moveRight();
            this.directions.add(Direction.RIGHT);
        }
        if(directions.size() == 0){
            this.directions.add(Direction.IDLE);
        }
    }
}
