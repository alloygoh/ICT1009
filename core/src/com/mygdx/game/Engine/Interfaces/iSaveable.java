package com.mygdx.game.Engine.Interfaces;

import java.util.HashMap;

public interface iSaveable {
    void populate(HashMap<String, Object> options);

    HashMap<String, Object> stashOptions();
}