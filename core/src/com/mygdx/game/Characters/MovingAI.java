package com.mygdx.game.Characters;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Utils.Controls;
import com.mygdx.game.Utils.Direction;

import java.util.Random;

public class MovingAI extends CollidableActor {
    private Random random;
    private int directionCount;
    private boolean shouldDisappear = false;

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
        if (directionCount <= 0) {
            generateRandomMovement();
            this.directionCount = 50;
            return;
        }
        super.act(delta);
        this.directionCount -=1;
        
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
    
    public boolean shouldDisappear(){
        return this.shouldDisappear;
    }

    public void resetStatus(){
        this.shouldDisappear = false;
        this.directions.clear();
        this.directionCount = 0;
        this.setCollided(false);
    }

    @Override
    public void reactToEvent(String event, Object others){
        if (event.equals("eaten")){
            this.shouldDisappear = true;
            this.setX(-1);
            this.setY(-1);
        }
        super.reactToEvent(event, others);
    }

}
