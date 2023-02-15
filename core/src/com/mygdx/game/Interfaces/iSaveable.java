package com.mygdx.game.Interfaces;

import java.io.Serializable;
import java.util.HashMap;

public interface iSaveable<T>{
    T createInstanceOf(HashMap<String, Object>options);
    HashMap<String, Object> stashOptions();
}