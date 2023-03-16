package com.mygdx.game.Utils;

import com.badlogic.gdx.Input;

import java.io.Serializable;

// allow custom controls
public class Controls implements Serializable {
    private int up;
    private int down;
    private int left;
    private int right;
    private int specialKey;

    public Controls() {
        this.up = -1;
        this.down = -1;
        this.left = -1;
        this.right = -1;
        this.specialKey = -1;
    }

    public Controls(int up, int down, int left, int right, int specialKey) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.specialKey = specialKey;
    }

    // presets
    public static final class Presets {
        public static final Controls DEFAULT = new Controls(Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.SPACE);
        public static final Controls NONE = new Controls();
    }

    public int getSpecialKey() {
        return specialKey;
    }

    public void setSpecialKey(int specialKey) {
        this.specialKey = specialKey;
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

}
