package com.mygdx.game.Interfaces;

import com.badlogic.gdx.math.Rectangle;

public interface iCollidable {
    public Rectangle getBounds();
    public boolean isIdle();
    public boolean collidesWith(iCollidable collidable);
    public void handleCollision(iCollidable collidable);
}
