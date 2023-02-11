package com.mygdx.game.Characters;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.Utils.Controls;

public class MovingShapeActor extends AbstractActor{
    private String shape;
    private Color color;
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
        super(width, height, x, y, movementSpeed, control);
        this.renderer = renderer;
        this.shape = shape;
        this.color = color;
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
        super.act(delta);
    }

    public void reactToEvent(String event, Object others){
        return;
    }

}
