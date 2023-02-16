package com.mygdx.game.Characters;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Interfaces.iCollidable;
import com.mygdx.game.Utils.Controls;
import com.mygdx.game.Utils.Direction;

public abstract class CollidableActor extends MovingImageActor implements iCollidable{
    private boolean collided = false;

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
        // do forecasting
        Vector2 forecastedPosition = getForecastedPosition();
        Rectangle forecast = new Rectangle(forecastedPosition.x, forecastedPosition.y, this.getWidth(), this.getHeight());
        return forecast.overlaps(collidable.getBounds());
    }

    @Override
    public void reactToEvent(String event, Object others){
        if (event.equals("collision")){
            iCollidable temp = (iCollidable) others;
            handleCollision(temp);
        }
    }
    
    @Override
    public void act(float delta){
        if(!collided){
            super.act(delta);
        }
        collided = false;
    }

    @Override
    public void handleCollision(iCollidable collidable){
        this.collided = true;
    }

    public void setCollided(boolean collided){
        this.collided = collided;
    }
    
    public boolean isCollided(){
        return this.collided;
    }
}
