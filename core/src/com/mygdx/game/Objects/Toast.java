package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Utils.Globals;

public class Toast extends BaseObject{
    private static TextureAtlas atlas = Globals.getAssetManager().get("objects.atlas", TextureAtlas.class);
    private static TextureRegionDrawable drawable = new TextureRegionDrawable(atlas.findRegion("toast"));

    public Toast() {
        this(40,40);
    }

    public Toast(float width, float height) {
        this(drawable, width, height, 0, 0, 100);
    }

    public Toast(float width, float height, float x, float y) {
        this(drawable, width, height, x, y, 100);
    }

    public Toast(TextureRegionDrawable drawable, float width, float height, float x, float y, float movementSpeed) {
        super(drawable, width, height, x, y, movementSpeed, 10);
    }
    
}
