package com.mygdx.game.Characters;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Interfaces.iCollidable;
import com.mygdx.game.Utils.Controls;
import com.mygdx.game.Utils.Direction;

import java.util.ArrayList;
import java.util.Random;

public class MovingAI extends CollidableActor {
    private Random random;
    private int directionCount;

    public MovingAI(TextureRegionDrawable texture, float width, float height) {
        this(texture, width, height, 100, 100, 100);
    }

    public MovingAI(TextureRegionDrawable texture, float width, float height, float x, float y) {
        this(texture, width, height, x, y, 100);
    }

    public MovingAI(TextureRegionDrawable texture, float width, float height, float x, float y,
                    float movementSpeed) {
        super(texture, width, height, x, y, movementSpeed, Controls.Presets.NONE);
        this.random = new Random();
        this.directionCount = 50;
        generateRandomMovement();
    }

    @Override
    public void act(float delta) {
        if (directionCount < 0) {
            generateRandomMovement();
            this.directionCount = 50;
        }
        moveRandomly(delta);
    }

    public void generateRandomMovement() {
        this.directions.clear();
        Direction direction = Direction.values()[random.nextInt(Direction.values().length)];
        this.directions.add(direction);
    }

    public void moveRandomly(float delta) {
        Direction direction = this.directions.get(0);
        if (direction == Direction.UP) {
            this.moveUp(delta);
        } else if (direction == Direction.DOWN) {
            this.moveDown(delta);
        } else if (direction == Direction.LEFT) {
            this.moveLeft(delta);
        } else if (direction == Direction.RIGHT) {
            this.moveRight(delta);
        }
        this.directionCount -= 1;
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
}
