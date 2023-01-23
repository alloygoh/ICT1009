package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Utils.Controls;

public class Brain extends Image {
    float movementSpeed;
    Controls control;

    public Brain(TextureRegionDrawable texture, float width, float height){
        this(texture, width, height, 0, 0, 1, Controls.Presets.DEFAULT);
    }

    public Brain(TextureRegionDrawable texture, float width, float height, Controls control){
        this(texture, width, height, 0, 0, 1, control);
    }

    public Brain(TextureRegionDrawable texture, float width, float height, float x, float y){
        this(texture, width, height, x, y, 1, Controls.Presets.DEFAULT);
    }

    public Brain(TextureRegionDrawable texture, float width, float height, float x, float y, Controls control){
        this(texture, width, height, x, y, 1, control);
    }

    public Brain(TextureRegionDrawable texture, float width, float height, float x, float y, float movementSpeed, Controls control){
        super(texture);
        this.movementSpeed = movementSpeed;
        this.control = control;
    }

    public void moveUp(){
        this.setY(this.getY() + this.movementSpeed);
    }

    public void moveDown(){
        this.setY(this.getY() - this.movementSpeed);
    }

    public void moveLeft(){
        this.setX(this.getX() - this.movementSpeed);
    }

    public void moveRight(){
        this.setX(this.getX() + this.movementSpeed);
    }

    public void processKeyStrokes(){
        if (Gdx.input.isKeyPressed(control.getUp())){
            this.moveUp();
        }

        if (Gdx.input.isKeyPressed(control.getDown())){
            this.moveDown();
        }

        if (Gdx.input.isKeyPressed(control.getLeft())){
            this.moveLeft();
        }

        if (Gdx.input.isKeyPressed(control.getRight())){
            this.moveRight();
        }
        
        // detects if collided with border
        if (exceedingBorder()){
            correctMovement();
        }

    }
    
    public boolean exceedingBorder(){
        float maxX = Gdx.graphics.getWidth() - this.getWidth();
        float maxY = Gdx.graphics.getHeight() - this.getHeight();
        if(this.getX() > maxX || this.getY() > maxY || this.getX() < 0 || this.getY() < 0){
            return true;
        }
        return false;
    }

    public void correctMovement(){
        float maxX = Gdx.graphics.getWidth() - this.getWidth();
        float maxY = Gdx.graphics.getHeight() - this.getHeight();
        this.setX(Math.min(maxX, Math.max(this.getX(), 0)));
        this.setY(Math.min(maxY, Math.max(this.getY(), 0)));
    }
}
