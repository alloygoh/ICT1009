package com.mygdx.game.Characters;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Utils.Controls;

public class Car extends MovingImageActor{

    static TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("car.png"))));

    public Car(float width, float height){
        this(drawable, width, height, 0, 0, 1, Controls.Presets.DEFAULT);
    }

    public Car(float width, float height, Controls control){
        this(drawable, width, height, 0, 0, 1, control);
    }

    public Car(float width, float height, float movementSpeed, Controls control){
        this(drawable, width, height, 0, 0, movementSpeed, control);
    }

    public Car(float width, float height, float x, float y){
        this(drawable, width, height, x, y, 1, Controls.Presets.DEFAULT);
    }

    public Car(float width, float height, float x, float y, Controls control){
        this(drawable, width, height, x, y, 1, control);
    }

    public Car(float width, float height, float x, float y, float movementSpeed, Controls control){
        super(drawable, width, height, x, y, movementSpeed, control);
    }

    // public Car(TextureRegionDrawable texture, float width, float height){
    //     this(texture, width, height, 0, 0, 1, Controls.Presets.DEFAULT);
    // }

    // public Car(TextureRegionDrawable texture, float width, float height, Controls control){
    //     this(texture, width, height, 0, 0, 1, control);
    // }

    // public Car(TextureRegionDrawable texture, float width, float height, float x, float y){
    //     this(texture, width, height, x, y, 1, Controls.Presets.DEFAULT);
    // }

    // public Car(TextureRegionDrawable texture, float width, float height, float x, float y, Controls control){
    //     this(texture, width, height, x, y, 1, control);
    // }

    public Car(TextureRegionDrawable drawable, float width, float height, float x, float y, float movementSpeed, Controls control){
        super(drawable, width, height, x, y, movementSpeed, control);
    }

}
