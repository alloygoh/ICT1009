package com.mygdx.game.Interfaces;

import com.badlogic.gdx.math.Rectangle;

public interface iCollidable {
    Rectangle getBounds();

    boolean isIdle();

    boolean collidesWith(iCollidable collidable);

    void handleCollision(iCollidable collidable);
}
