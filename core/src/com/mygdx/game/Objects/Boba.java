package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Utils.Globals;

public class Boba extends BaseObject{
    private static TextureAtlas atlas = Globals.getAssetManager().get("objects.atlas", TextureAtlas.class);
    private static TextureRegionDrawable drawable = new TextureRegionDrawable(atlas.findRegion("boba"));

    public Boba() {
        this(40,50);
    }

    public Boba(float width, float height) {
        this(drawable, width, height, 0, 0, 100);
    }

    public Boba(float width, float height, float x, float y) {
        this(drawable, width, height, x, y, 100);
    }

    public Boba(TextureRegionDrawable drawable, float width, float height, float x, float y, float movementSpeed) {
        super(drawable, width, height, x, y, movementSpeed, -20);
    }
    
}
