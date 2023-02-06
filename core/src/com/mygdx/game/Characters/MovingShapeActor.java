package com.mygdx.game.Characters;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Utils.Controls;
import com.mygdx.game.Utils.Direction;

public class MovingShapeActor extends Actor{
    private String shape;
    private Color color;
    private float movementSpeed;
    private Controls control;
    private ArrayList<Direction> directions = new ArrayList<>();
    private ShapeRenderer renderer;

    public MovingShapeActor(ShapeRenderer renderer, String shape, float width, float height, Color color){
        this(renderer, shape, width, height, 0, 0, color, 100, Controls.Presets.DEFAULT);
    }

    public MovingShapeActor(ShapeRenderer renderer, String shape, float width, float height, Color color, Controls control){
        this(renderer, shape, width, height, 0, 0, color, 100, control);
    }

    public MovingShapeActor(ShapeRenderer renderer, String shape, float width, float height, float x, float y, Color color){
        this(renderer, shape, width, height, x, y, color, 100, Controls.Presets.DEFAULT);
    }

    public MovingShapeActor(ShapeRenderer renderer, String shape, float width, float height, float x, float y, Color color, Controls control){
        this(renderer, shape, width, height, x, y, color, 100, control);
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
        if (this instanceof Ball){
            drop(delta);
            return;
        }
        super.act(delta);
    }

    public void drop(float delta){
        if(this.getY() <= 0){
            return;
        }
        this.setY(this.getY() - delta*(movementSpeed/2));
    }

    public void moveUp(){
        this.setY(this.getY() + this.movementSpeed*Gdx.graphics.getDeltaTime());
    }

    public void moveDown(){
        this.setY(this.getY() - this.movementSpeed*Gdx.graphics.getDeltaTime());
    }

    public void moveLeft(){
        this.setX(this.getX() - this.movementSpeed*Gdx.graphics.getDeltaTime());
    }

    public void moveRight(){
        this.setX(this.getX() + this.movementSpeed*Gdx.graphics.getDeltaTime());
    }

    public void processKeyStrokes() {
        if (Gdx.input.isKeyPressed(control.getUp())) {
            this.moveUp();
            this.directions.add(Direction.UP);
        }

        if (Gdx.input.isKeyPressed(control.getDown())) {
            this.moveDown();
            this.directions.add(Direction.DOWN);
        }

        if (Gdx.input.isKeyPressed(control.getLeft())) {
            this.moveLeft();
            this.directions.add(Direction.LEFT);
        }

        if (Gdx.input.isKeyPressed(control.getRight())) {
            this.moveRight();
            this.directions.add(Direction.RIGHT);
        }
    }
}
