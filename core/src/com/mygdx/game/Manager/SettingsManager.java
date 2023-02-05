package com.mygdx.game.Manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.mygdx.game.Utils.Controls;

public class SettingsManager{

    private HashMap<Integer, Controls> settingsMap;

    public SettingsManager(Game game) {
        this.settingsMap = new HashMap<>();
    }

    public void addControlSetting(int index, Controls control){
        settingsMap.put(index, control);
    }

    public void changeControlSetting(int index, Controls controls){
        settingsMap.remove(index);
        settingsMap.put(index, controls);
    }

    public Controls getControlOf(int index){
        return settingsMap.get(index);
    }

    public void readFromConfig(){
        FileInputStream inputStream;
        try{
            inputStream = new FileInputStream("settings.config");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            settingsMap = (HashMap<Integer,Controls>) objectInputStream.readObject();
            objectInputStream.close();
        } catch(ClassNotFoundException| IOException e){
            // no previous config or error reading config
            // populate with default
            Controls player1Controls = new Controls(Input.Keys.UP,Input.Keys.DOWN ,Input.Keys.LEFT ,Input.Keys.RIGHT);
            Controls player2Controls = new Controls(Input.Keys.W,Input.Keys.S ,Input.Keys.A ,Input.Keys.D);
            settingsMap.put(1, player1Controls);
            settingsMap.put(2, player2Controls);
        }
        return;
    }

    public void writeToConfig(){
        FileOutputStream outputStream;
        try{
            outputStream = new FileOutputStream("settings.config");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(settingsMap);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch(IOException e){
            // unable to write config, terminate
            return;
        }
    }

    
}
