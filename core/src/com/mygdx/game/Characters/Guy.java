package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Utils.Controls;
import com.mygdx.game.Utils.Globals;

public class Guy extends Player {
    private static final TextureAtlas atlas = Globals.getAssetManager().get("characters.atlas", TextureAtlas.class);
    private static final TextureRegionDrawable drawableObese = new TextureRegionDrawable(atlas.findRegion("player1-obese"));
    private static final TextureRegionDrawable drawableFat = new TextureRegionDrawable(atlas.findRegion("player1-fat"));
    private static final TextureRegionDrawable drawable = new TextureRegionDrawable(atlas.findRegion("player1-base"));
    private static final TextureRegionDrawable drawableStrong = new TextureRegionDrawable(atlas.findRegion("player1-strong"));
    private static final TextureRegionDrawable drawableBuff = new TextureRegionDrawable(atlas.findRegion("player1-buff"));

    public Guy() {
        this(30, 60);
    }

    public Guy(float width, float height) {
        this(drawable, width, height, 0, 0, 150, Controls.Presets.DEFAULT);
    }

    public Guy(float width, float height, Controls control) {
        this(drawable, width, height, 0, 0, 150, control);
    }

    public Guy(float width, float height, float movementSpeed, Controls control) {
        this(drawable, width, height, 0, 0, movementSpeed, control);
    }

    public Guy(float width, float height, float x, float y) {
        this(drawable, width, height, x, y, 150, Controls.Presets.DEFAULT);
    }

    public Guy(float width, float height, float x, float y, Controls control) {
        this(drawable, width, height, x, y, 150, control);
    }

    public Guy(float width, float height, float x, float y, float movementSpeed, Controls control) {
        this(drawable, width, height, x, y, movementSpeed, control);
    }

    public Guy(TextureRegionDrawable drawable, float width, float height, float x, float y, float movementSpeed,
               Controls control) {
        super(drawable, width, height, x, y, movementSpeed, control);
    }

    @Override
    protected TextureRegionDrawable resolveImage() {
        TextureRegionDrawable[] selection = {drawableObese, drawableFat, drawable, drawableStrong, drawableBuff};
        return selection[this.getLevel()];
    }

}
