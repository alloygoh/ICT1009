package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Utils.Controls;

public class Ball extends MovingShapeActor{
    float radius;
    ShapeRenderer renderer;

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
        this.radius = radius;
        this.renderer = renderer;
    }

    // @Override
    // public void draw(Batch batch, float parentAlpha){
    //     batch.end();
        
    //     Vector2 coords = new Vector2(getX(),getY());
    //     renderer.setProjectionMatrix(batch.getProjectionMatrix());
    //     renderer.setColor(color);

    //     renderer.setAutoShapeType(true);
    //     renderer.begin(ShapeType.Filled);

	// 	renderer.circle(coords.x+radius, coords.y + radius, radius);
    //     renderer.end();
    //     batch.begin();
    // }

}
