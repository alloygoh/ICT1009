package com.mygdx.game.Characters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Interfaces.iCollidable;
import com.mygdx.game.Utils.Controls;
import com.mygdx.game.Utils.Direction;

public abstract class CollidableActor extends MovingImageActor implements iCollidable{

    public CollidableActor(TextureRegionDrawable texture, float width, float height, float x, float y, float movementSpeed, Controls control){
        super(texture, width, height, x, y, movementSpeed, control);
    }

    @Override
    public boolean isIdle(){
        return this.getDirections().contains(Direction.IDLE);
    }

    @Override
    public Rectangle getBounds(){
        return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public boolean collidesWith(iCollidable collidable) {
        if (this == collidable)
            return false;
        return getBounds().overlaps(collidable.getBounds());
    }
}
