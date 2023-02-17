package com.mygdx.game.Interfaces;

import java.util.HashMap;

public interface iSaveable {
    void populate(HashMap<String, Object> options);

    HashMap<String, Object> stashOptions();
}