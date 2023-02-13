package com.mygdx.game.Characters;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Utils.Controls;
import com.mygdx.game.Utils.Direction;

public abstract class AbstractActor extends Actor {
    protected ArrayList<Direction> directions;
    protected float movementSpeed;

    private Controls control;

    public AbstractActor(float width, float height, float x, float y,
            float movementSpeed, Controls control) {
        this.setWidth(width);
        this.setHeight(height);
        this.setX(x);
        this.setY(y);
        this.control = control;
        this.movementSpeed = movementSpeed;
        this.directions = new ArrayList<>();
    }

    public abstract void reactToEvent(String event, Object others);

    public ArrayList<Direction> getDirections() {
        return this.directions;
    }

    public float getMovementSpeed() {
        return movementSpeed;
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
        if (directions.size() == 0) {
            this.directions.add(Direction.IDLE);
        }
    }
}
