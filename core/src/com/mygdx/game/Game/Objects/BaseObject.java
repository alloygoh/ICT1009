package com.mygdx.game.Game.Objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Engine.Characters.MovingAI;
import com.mygdx.game.Game.Utils.Globals;

public class BaseObject extends MovingAI {
    private static final Sound sfxGoodConsume = Globals.getAssetManager().get("sound/good-food.mp3");
    private static final Sound sfxBadConsume = Globals.getAssetManager().get("sound/bad-food.mp3");
    private final int powerPoints;
    private boolean shouldDisappear = false;

    public BaseObject(TextureRegionDrawable drawable, float width, float height, float x, float y, float movementSpeed, int powerPoints) {
        super(drawable, width, height, x, y, movementSpeed);
        this.powerPoints = powerPoints;
    }

    public int getPowerPoints() {
        return this.powerPoints;
    }

    public boolean shouldDisappear() {
        return this.shouldDisappear;
    }

    public void resetStatus() {
        this.shouldDisappear = false;
        this.directions.clear();
        this.setDirectionCount(0);
        this.setCollided(false);
    }

    private void playSound() {
        if (powerPoints > 0) {
            sfxGoodConsume.play(1.0f);
        } else {
            sfxBadConsume.play(1.0f);
        }
    }


    @Override
    public void reactToEvent(String event, Object others) {
        if (event.equals("eaten")) {
            this.shouldDisappear = true;
            this.setX(-1 - this.getWidth());
            this.setY(-1 - this.getHeight());
            playSound();
            return;
        }
        super.reactToEvent(event, others);
    }

}
