package com.mygdx.game.Engine.Interfaces;

import java.io.Serializable;

public interface iSettings extends Serializable {
    void initDefault();

    Serializable getSerializableValue();
}
