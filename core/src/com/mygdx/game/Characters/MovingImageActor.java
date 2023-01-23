package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Utils.Controls;

public class MovingImageActor extends Brain{
    float width;
    float height;
    float x;
    float y;

    TextureRegionDrawable texture;

    public MovingImageActor(TextureRegionDrawable texture, float width, float height){
        this(texture, width, height, 0, 0, 1, Controls.Presets.DEFAULT);
    }

    public MovingImageActor(TextureRegionDrawable texture, float width, float height, Controls control){
        this(texture, width, height, 0, 0, 1, control);
    }

    public MovingImageActor(TextureRegionDrawable texture, float width, float height, float x, float y){
        this(texture, width, height, x, y, 1, Controls.Presets.DEFAULT);
    }

    public MovingImageActor(TextureRegionDrawable texture, float width, float height, float x, float y, Controls control){
        this(texture, width, height, x, y, 1, control);
    }

    public MovingImageActor(TextureRegionDrawable texture, float width, float height, float x, float y, float movementSpeed, Controls control){
        super(texture, width, height, x, y, movementSpeed, control);
        this.texture = texture;
        this.setWidth(width);
        this.setHeight(height);
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        texture.draw(batch, this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}


}
