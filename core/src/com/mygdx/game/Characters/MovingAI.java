package com.mygdx.game.Characters;

import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Utils.Controls;
import com.mygdx.game.Utils.Direction;

public class MovingAI extends MovingImageActor {
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
    public void act(float delta){
        if (directionCount < 0) {
            generateRandomMovement();
            this.directionCount = 50;
        }
        moveRandomly(delta);
    }

    public void generateRandomMovement(){
        this.directions.clear();
        Direction direction = Direction.values()[random.nextInt(Direction.values().length)];
        this.directions.add(direction);
    }

    public void moveRandomly(float delta){
        Direction direction = this.directions.get(0);
        if(direction == Direction.UP){
            this.moveUp(delta);
        } else if(direction == Direction.DOWN){
            this.moveDown(delta);
        }else if(direction == Direction.LEFT){
            this.moveLeft(delta);
        }else if(direction == Direction.RIGHT){
            this.moveRight(delta);
        }
        this.directionCount -= 1;
    }
}
