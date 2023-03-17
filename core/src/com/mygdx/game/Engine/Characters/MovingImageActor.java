package com.mygdx.game.Engine.Characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Engine.Utils.Controls;

public class MovingImageActor extends AbstractActor {
    private TextureRegionDrawable texture;

    public MovingImageActor(TextureRegionDrawable texture, float width, float height) {
        this(texture, width, height, 0, 0, 100, Controls.Presets.DEFAULT);
    }

    public MovingImageActor(TextureRegionDrawable texture, float width, float height, Controls control) {
        this(texture, width, height, 0, 0, 100, control);
    }

    public MovingImageActor(TextureRegionDrawable texture, float width, float height, float x, float y) {
        this(texture, width, height, x, y, 100, Controls.Presets.DEFAULT);
    }

    public MovingImageActor(TextureRegionDrawable texture, float width, float height, float x, float y,
                            Controls control) {
        this(texture, width, height, x, y, 100, control);
    }

    public MovingImageActor(TextureRegionDrawable texture, float width, float height, float x, float y,
                            float movementSpeed, Controls control) {
        super(width, height, x, y, movementSpeed, control);
        this.texture = texture;
    }

    public void setTexture(TextureRegionDrawable texture) {
        this.texture = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        texture.draw(batch, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void reactToEvent(String event, Object others) {
    }

}
