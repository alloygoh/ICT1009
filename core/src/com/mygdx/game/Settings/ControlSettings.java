package com.mygdx.game.Settings;

import java.io.Serializable;
import java.util.HashMap;

import com.badlogic.gdx.Input;
import com.mygdx.game.Interfaces.iSettings;
import com.mygdx.game.Utils.Controls;

public class ControlSettings implements iSettings{

    private HashMap<Integer, Controls> controlSettingsMap;

    public ControlSettings() {
        this.controlSettingsMap = new HashMap<>();
    }

    public ControlSettings(HashMap<Integer, Controls> controlSetting){
        this.controlSettingsMap = controlSetting;
    }

    public void addControlSetting(int index, Controls control) {
        controlSettingsMap.put(index, control);
    }

    public void changeControlSetting(int index, Controls controls) {
        controlSettingsMap.remove(index);
        controlSettingsMap.put(index, controls);
    }

    public Controls getControlOf(int index) {
        return controlSettingsMap.get(index);
    }

    public void initDefaultControls() {
        Controls player1Controls = new Controls(Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT);
        Controls player2Controls = new Controls(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D);
        controlSettingsMap.put(1, player1Controls);
        controlSettingsMap.put(2, player2Controls);
    }

    @Override
    public void initDefault() {
        initDefaultControls();
    }

    @Override
    public Serializable getSerializableValue() {
        return this.controlSettingsMap;
    }

}
