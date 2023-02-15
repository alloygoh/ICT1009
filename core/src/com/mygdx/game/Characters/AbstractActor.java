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
    public void moveUp(float delta) {
        this.setY(this.getY() + this.movementSpeed * delta);
    }

    public void moveDown(float delta) {
        this.setY(this.getY() - this.movementSpeed * delta);
    }

    public void moveLeft(float delta) {
        this.setX(this.getX() - this.movementSpeed * delta);
    }

    public void moveRight(float delta) {
        this.setX(this.getX() + this.movementSpeed * delta);
    }

    public void processKeyStrokes(float delta) {
        directions.clear();
        if (Gdx.input.isKeyPressed(control.getUp())) {
            this.moveUp(delta);
            this.directions.add(Direction.UP);
        }

        if (Gdx.input.isKeyPressed(control.getDown())) {
            this.moveDown(delta);
            this.directions.add(Direction.DOWN);
        }

        if (Gdx.input.isKeyPressed(control.getLeft())) {
            this.moveLeft(delta);
            this.directions.add(Direction.LEFT);
        }

        if (Gdx.input.isKeyPressed(control.getRight())) {
            this.moveRight(delta);
            this.directions.add(Direction.RIGHT);
        }
        if (directions.size() == 0) {
            this.directions.add(Direction.IDLE);
        }
    }
}
