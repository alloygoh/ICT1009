package com.mygdx.game.Game.Objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Game.Utils.Globals;

public class Carrot extends BaseObject {
    private static final TextureAtlas atlas = Globals.getAssetManager().get("objects.atlas", TextureAtlas.class);
    private static final TextureRegionDrawable drawable = new TextureRegionDrawable(atlas.findRegion("carrot"));

    public Carrot() {
        this(40, 40);
    }

    public Carrot(float width, float height) {
        this(drawable, width, height, 0, 0, 100);
    }

    public Carrot(float width, float height, float x, float y) {
        this(drawable, width, height, x, y, 100);
    }

    public Carrot(TextureRegionDrawable drawable, float width, float height, float x, float y, float movementSpeed) {
        super(drawable, width, height, x, y, movementSpeed, 15);
    }

}
