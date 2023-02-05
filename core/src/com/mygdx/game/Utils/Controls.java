package com.mygdx.game.Utils;

import com.badlogic.gdx.Input;

// allow custom controls
public class Controls {
    private int up;
    private int down;
    private int left;
    private int right;

    public Controls(int up, int down, int left, int right){
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    // presets
    public static final class Presets{
        public static final Controls DEFAULT = new Controls(Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT);
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
