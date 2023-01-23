package com.mygdx.game.Characters;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Utils.Controls;

public class MovingShapeActor extends Actor{
    String shape;
    float x;
    float y;
    Color color;
    float movementSpeed;
    Controls control;

    ShapeRenderer renderer;

    public MovingShapeActor(ShapeRenderer renderer, String shape, float width, float height, Color color){
        this(renderer, shape, width, height, 0, 0, color, 1, Controls.Presets.DEFAULT);
    }

    public MovingShapeActor(ShapeRenderer renderer, String shape, float width, float height, Color color, Controls control){
        this(renderer, shape, width, height, 0, 0, color, 1, control);
    }

    public MovingShapeActor(ShapeRenderer renderer, String shape, float width, float height, float x, float y, Color color){
        this(renderer, shape, width, height, x, y, color, 1, Controls.Presets.DEFAULT);
    }

    public MovingShapeActor(ShapeRenderer renderer, String shape, float width, float height, float x, float y, Color color, Controls control){
        this(renderer, shape, width, height, x, y, color, 1, control);
    }

    public MovingShapeActor(ShapeRenderer renderer, String shape, float width, float height, float x, float y, Color color, float movementSpeed, Controls control){
        super();
        this.renderer = renderer;
        this.shape = shape;
        this.setWidth(width);
        this.setHeight(height);
        this.setX(x);
        this.setY(y);
        this.color = color;
        this.movementSpeed = movementSpeed;
        this.control = control;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.end();

        // Vector2 coords = new Vector2(getX(),getY());
        // renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setAutoShapeType(true);
        renderer.begin(ShapeType.Filled);

        renderer.setColor(color);
        float width = this.getWidth();
        float height = this.getHeight();
        if (shape == "circle"){
            renderer.circle(this.getX() + width, this.getY() + width, width);
        } else{
            // default to square
            renderer.rect(this.getX(), this.getY(), width, height);
        }
        renderer.end();

        batch.begin();
	}

    @Override
    public void act(float delta){
        if (this.getClass() == (Class)Ball.class){
            drop(delta);
            return;
        }
        super.act(delta);
    }

    public void drop(float delta){
        this.setY(this.getY() - delta*movementSpeed*10);
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
