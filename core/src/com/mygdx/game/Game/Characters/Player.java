package com.mygdx.game.Game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Engine.Interfaces.iCollidable;
import com.mygdx.game.Engine.Interfaces.iSaveable;
import com.mygdx.game.Game.Objects.BaseObject;
import com.mygdx.game.Game.Objects.Boba;
import com.mygdx.game.Game.Objects.Carrot;
import com.mygdx.game.Game.Objects.Fries;
import com.mygdx.game.Game.Objects.Fruit;
import com.mygdx.game.Game.Objects.Pizza;
import com.mygdx.game.Game.Objects.Toast;
import com.mygdx.game.Engine.Utils.Controls;
import com.mygdx.game.Game.Utils.Globals;
import com.mygdx.game.Engine.Characters.CollidableActor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public abstract class Player extends CollidableActor implements iSaveable {

    private static final Sound sfxLose = Globals.getAssetManager().get("sound/pvp-lose.mp3");
    private static final Sound sfxDefended = Globals.getAssetManager().get("sound/pvp-win.mp3");
    private static final Sound sfxCombo = Globals.getAssetManager().get("sound/combo-sound.mp3");
    private static final Sound sfxLevelUp = Globals.getAssetManager().get("sound/level-up.mp3");
    private final ArrayList<Class> foodsEaten;
    private int lifeCount;
    private boolean isDead;
    private Vector2 originCoordinates;
    private int highScore;
    private int level;
    private float power;

    public Player(TextureRegionDrawable drawable, float width, float height, float x, float y, float movementSpeed,
                  Controls control) {
        super(drawable, width, height, x, y, movementSpeed, control);
        this.highScore = 0;
        this.power = 0;
        this.lifeCount = 2;
        this.level = 2;
        this.isDead = false;
        this.originCoordinates = new Vector2(x, y);
        this.foodsEaten = new ArrayList<Class>();
    }

    public int getLevel() {
        return this.level;
    }

    public int getLifeCount() {
        return this.lifeCount;
    }

    public int getPower() {
        return (int) this.power;
    }

    public ArrayList<Class> getFoodsEaten() {
        return this.foodsEaten;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public boolean isDead() {
        return this.isDead;
    }

    private void reset() {
        this.power = 0;
        this.level = 2;
        this.setMovementSpeed(150);
        handleLevels();
        this.setX(this.originCoordinates.x);
        this.setY(this.originCoordinates.y);
    }

    private void resetPosition() {
        this.setPosition(originCoordinates.x, originCoordinates.y);
    }

    private void loseLife() {
        this.lifeCount -= 1;
        this.isDead = (this.lifeCount <= 0);
        sfxLose.play(1.0f);
    }

    protected abstract TextureRegionDrawable resolveImage();

    protected void levelUp() {
        this.level += 1;
        this.setMovementSpeed(this.getMovementSpeed() + 50);
        sfxLevelUp.play(1.0f);
    }

    protected void levelDown() {
        this.level -= 1;
        this.setMovementSpeed(Math.min(this.getMovementSpeed() - 30, 100));
    }

    protected void handleLevels() {
        // < -60 level 0
        // -59 till -1 level 1
        // 0 - 70 level 2
        // 71-150 level 3
        // > 150 level 4
        int targetLevel = 0;
        if (power < 0) {
            targetLevel = 1;
        } else if (power <= 70) {
            targetLevel = 2;
        } else if (power <= 150) {
            targetLevel = 3;
        } else if (power > 150) {
            targetLevel = 4;
        }


        if (targetLevel > level) {
            levelUp();
        } else if (targetLevel < level) {
            levelDown();
        }
        TextureRegionDrawable targetDrawable = resolveImage();
        this.setTexture(targetDrawable);
    }

    private void exercise() {
        this.power += 0.01;
        handleLevels();
    }

    @Override
    public void processKeyStrokes(float delta) {
        // if exercise key is pressed, do not move
        if (Gdx.input.isKeyPressed(this.getControl().getSpecialKey())) {
            exercise();
            this.directions.clear();
            return;
        }
        super.processKeyStrokes(delta);
    }

    private boolean checkCombo() {
        // combo consists of 2 carrots, 1 fruit and 1 toast
        if (this.foodsEaten.containsAll(Arrays.asList(Carrot.class, Toast.class, Fruit.class)) && Collections.frequency(this.foodsEaten, Carrot.class) == 2) {
            this.foodsEaten.clear();
            sfxCombo.play(1.0f);
            return true;
        }
        if (this.foodsEaten.contains(Boba.class) || this.foodsEaten.contains(Pizza.class) || this.foodsEaten.contains(Fries.class)) {
            this.foodsEaten.clear();
        }
        if (this.foodsEaten.size() > 4) {
            // if more than combo size, remove oldest food
            this.foodsEaten.remove(0);
        }
        return false;
    }

    @Override
    public void handleCollision(iCollidable collidable) {
        if (collidable instanceof BaseObject) {
            BaseObject object = (BaseObject) collidable;
            if (!this.isIdle()) {
                this.power += object.getPowerPoints();
                this.foodsEaten.add(object.getClass());
                object.reactToEvent("eaten", this);
                if (checkCombo()) {
                    // combo bonus
                    this.power += 20;
                }
                handleLevels();
            }
        } else if (collidable instanceof Player && collidable != this) {
            // collided with another player
            // check if battle started
            if (Globals.getCountDown() > 0) {
                super.handleCollision(collidable);
                return;
            }

            Player player = (Player) collidable;
            if (this.power > player.getPower()) {
                // win
                if (this.highScore < this.power) {
                    this.highScore = (int) this.power;
                }
                return;
            } else if (this.power < player.getPower()) {
                // since forecasting is the method for detecting collisions, the other player does not see collision if idle
                // manually trigger collision event
                player.reactToEvent("collision", this);
                return;
            }

            // if same power, nothing happens
            super.handleCollision(collidable);
        }

    }

    @Override
    public void reactToEvent(String event, Object others) {
        if (event.equals("lose life")) {
            loseLife();
            return;
        } else if (event.equals("reset")) {
            reset();
            // reset counter for hunting phase
            Globals.restoreCountDown();
            return;
        } else if (event.equals("defended")) {
            sfxDefended.play(1.0f);
            return;
        } else if (event.equals("reset position")) {
            resetPosition();
            return;
        }

        super.reactToEvent(event, others);
    }

    @Override
    public void populate(HashMap<String, Object> options) {
        float x = (float) options.get("x");
        float y = (float) options.get("y");
        float width = (float) options.get("width");
        float height = (float) options.get("height");
        float movementSpeed = (float) options.get("speed");
        Controls controls = (Controls) options.get("controls");
        Vector2 origin = (Vector2) options.get("origin");
        float power = (float) options.get("power");
        int life = (int) options.get("life");
        int level = (int) options.get("level");
        int highScore = (int) options.get("highscore");
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setMovementSpeed(movementSpeed);
        this.setControl(controls);
        this.originCoordinates = origin;
        this.power = power;
        this.lifeCount = life;
        this.level = level;
        this.highScore = highScore;
        handleLevels();
    }

    @Override
    public HashMap<String, Object> stashOptions() {
        HashMap<String, Object> options = new HashMap<>();
        options.put("x", this.getX());
        options.put("y", this.getY());
        options.put("origin", this.originCoordinates);
        options.put("power", this.power);
        options.put("life", this.lifeCount);
        options.put("level", this.level);
        options.put("highscore", this.highScore);
        options.put("width", this.getWidth());
        options.put("height", this.getHeight());
        options.put("speed", this.getMovementSpeed());
        options.put("controls", this.getControl());
        return options;
    }
}
