package com.mygdx.game.Interfaces;

import java.io.Serializable;

public interface iSettings extends Serializable {
    public void initDefault();
    public Serializable getSerializableValue();
}
