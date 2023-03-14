package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Utils.Controls;
import com.mygdx.game.Utils.Globals;

public class Girl extends Player {
    private static TextureAtlas atlas = Globals.getAssetManager().get("characters.atlas", TextureAtlas.class);
    private static TextureRegionDrawable drawable = new TextureRegionDrawable(atlas.findRegion("player2-base"));

    public Girl() {
        this(40, 60);
    }

    public Girl(float width, float height) {
        this(drawable, width, height, 0, 0, 100, Controls.Presets.DEFAULT);
    }

    public Girl(float width, float height, Controls control) {
        this(drawable, width, height, 0, 0, 100, control);
    }

    public Girl(float width, float height, float movementSpeed, Controls control) {
        this(drawable, width, height, 0, 0, movementSpeed, control);
    }

    public Girl(float width, float height, float x, float y) {
        this(drawable, width, height, x, y, 100, Controls.Presets.DEFAULT);
    }

    public Girl(float width, float height, float x, float y, Controls control) {
        this(drawable, width, height, x, y, 100, control);
    }

    public Girl(float width, float height, float x, float y, float movementSpeed, Controls control) {
        this(drawable, width, height, x, y, movementSpeed, control);
    }

    public Girl(TextureRegionDrawable drawable, float width, float height, float x, float y, float movementSpeed,
            Controls control) {
        super(drawable, width, height, x, y, movementSpeed, control);
    }
        
}
