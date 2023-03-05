package com.mygdx.game.Objects;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Characters.MovingAI;

public class BaseObject extends MovingAI {
    private int powerPoints;

    public BaseObject(TextureRegionDrawable drawable, float width, float height, float x, float y, float movementSpeed, int powerPoints) {
        super(drawable, width, height, x, y, movementSpeed);
        this.powerPoints = powerPoints;
    }
    
    public int getPowerPoints(){
        return this.powerPoints;
    }
}
