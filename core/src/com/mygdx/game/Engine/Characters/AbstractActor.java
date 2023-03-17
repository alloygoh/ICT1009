package com.mygdx.game.Engine.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Engine.Utils.Controls;
import com.mygdx.game.Engine.Utils.Direction;

import java.util.ArrayList;

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

    public Vector2 getForecastedPosition() {
        float delta = Gdx.graphics.getDeltaTime();
        float forecastX = this.getX();
        float forecastY = this.getY();
        if (directions.contains(Direction.UP)) {
            forecastY += this.movementSpeed * delta;
        }
        if (directions.contains(Direction.DOWN)) {
            forecastY -= this.movementSpeed * delta;
        }
        if (directions.contains(Direction.LEFT)) {
            forecastX -= this.movementSpeed * delta;
        }
        if (directions.contains(Direction.RIGHT)) {
            forecastX += this.movementSpeed * delta;
        }
        return new Vector2(forecastX, forecastY);
    }

    @Override
    public void act(float delta) {
        if (directions.contains(Direction.UP)) {
            moveUp(delta);
        }
        if (directions.contains(Direction.DOWN)) {
            moveDown(delta);
        }
        if (directions.contains(Direction.LEFT)) {
            moveLeft(delta);
        }
        if (directions.contains(Direction.RIGHT)) {
            moveRight(delta);
        }
    }

    public abstract void reactToEvent(String event, Object others);

    public ArrayList<Direction> getDirections() {
        return this.directions;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public Controls getControl() {
        return control;
    }

    public void setControl(Controls control) {
        this.control = control;
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
            this.directions.add(Direction.UP);
        }

        if (Gdx.input.isKeyPressed(control.getDown())) {
            this.directions.add(Direction.DOWN);
        }

        if (Gdx.input.isKeyPressed(control.getLeft())) {
            this.directions.add(Direction.LEFT);
        }

        if (Gdx.input.isKeyPressed(control.getRight())) {
            this.directions.add(Direction.RIGHT);
        }
        if (directions.size() == 0) {
            this.directions.add(Direction.IDLE);
        }
    }
}
