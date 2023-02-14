package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class StaticActor extends Actor {
    private TextureRegionDrawable drawable;
    private ShapeRenderer renderer;
    private Color color;
    private String shape;

    public StaticActor(ShapeRenderer renderer, String shape, float width, float height, float x, float y, Color color) {
        super();
        this.renderer = renderer;
        this.drawable = null;
        this.color = color;
        this.shape = shape;
        this.setX(x);
        this.setY(y);
        this.setHeight(height);
        this.setWidth(width);
    }

    public StaticActor(TextureRegionDrawable drawable, float width, float height, float x, float y) {
        super();
        this.drawable = drawable;
        this.renderer = null;
        this.setX(x);
        this.setY(y);
        this.setHeight(height);
        this.setWidth(width);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // is image based actor
        if (drawable != null) {
            drawable.draw(batch, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            // is shape based actor
            batch.end();

            renderer.setAutoShapeType(true);
            renderer.begin(ShapeType.Filled);

            renderer.setColor(color);
            float width = this.getWidth();
            float height = this.getHeight();
            if (shape == "circle") {
                renderer.circle(this.getX() + width, this.getY() + width, width);
            } else {
                // default to square
                renderer.rect(this.getX(), this.getY(), width, height);
            }
            renderer.end();

            batch.begin();
        }
    }
}
