package com.mygdx.game.Objects;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Characters.MovingAI;

public class BaseObject extends MovingAI {
    private int powerPoints;
    private boolean shouldDisappear = false;

    public BaseObject(TextureRegionDrawable drawable, float width, float height, float x, float y, float movementSpeed, int powerPoints) {
        super(drawable, width, height, x, y, movementSpeed);
        this.powerPoints = powerPoints;
    }
    
    public int getPowerPoints(){
        return this.powerPoints;
    }

    public boolean shouldDisappear(){
        return this.shouldDisappear;
    }

    public void resetStatus(){
        this.shouldDisappear = false;
        this.directions.clear();
        this.setDirectionCount(0);
        this.setCollided(false);
    }


    @Override
    public void reactToEvent(String event, Object others){
        if (event.equals("eaten")){
            this.shouldDisappear = true;
            this.setX(-1 -this.getWidth());
            this.setY(-1 - this.getHeight());
            return;
        }
        super.reactToEvent(event, others);
    }

}
