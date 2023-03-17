package com.mygdx.game.Interfaces;

import java.io.Serializable;

public interface iSettings extends Serializable {
    void initDefault();

    Serializable getSerializableValue();
}
