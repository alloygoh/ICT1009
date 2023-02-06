package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Utils.Controls;

public class Ball extends MovingShapeActor{

    public Ball(ShapeRenderer renderer, float radius, Color color){
        this(renderer, radius, 0, 0, color, 100, Controls.Presets.DEFAULT);
    }

    public Ball(ShapeRenderer renderer, float radius, Color color, Controls control){
        this(renderer, radius, 0, 0, color, 100, control);
    }

    public Ball(ShapeRenderer renderer, float radius, Color color, float movementSpeed, Controls control){
        this(renderer, radius, 0, 0, color, movementSpeed, control);
    }

    public Ball(ShapeRenderer renderer, float radius, float x, float y, Color color){
        this(renderer, radius, x, y, color, 100, Controls.Presets.DEFAULT);
    }

    public Ball(ShapeRenderer renderer, float radius, float x, float y, Color color, Controls control){
        this(renderer, radius, x, y, color, 100, control);
    }

    public Ball(ShapeRenderer renderer, float radius, float x, float y, Color color, float movementSpeed){
        this(renderer, radius, x, y, color, movementSpeed, Controls.Presets.DEFAULT);
    }

    public Ball(ShapeRenderer renderer, float radius, float x, float y, Color color, float movementSpeed, Controls control){
        super(renderer, "circle", radius*2, radius*2, x, y, color, movementSpeed, control);
    }

    @Override
    public void act(float delta){
        drop(delta);
        super.act(delta);
    }

    public void drop(float delta){
        if(this.getY() <= 0){
            return;
        }
        this.setY(this.getY() - delta*(movementSpeed/2));
    }
}
