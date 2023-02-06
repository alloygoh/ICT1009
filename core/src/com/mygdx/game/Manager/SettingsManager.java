package com.mygdx.game.Manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.print.attribute.HashAttributeSet;

import com.badlogic.gdx.Game;
import com.mygdx.game.Interfaces.iSettings;
import com.mygdx.game.Settings.ControlSettings;
import com.mygdx.game.Utils.Controls;

public class SettingsManager{

    private ControlSettings controlSettings;
    private ArrayList<iSettings> masterSettings;

    public SettingsManager(Game game) {
        masterSettings = new ArrayList<iSettings>();

        this.controlSettings = new ControlSettings();

        masterSettings.add(controlSettings);
    }

    public ControlSettings getControlSettings() {
        return controlSettings;
    }

    private void populateSettings(){
        this.controlSettings = new ControlSettings((HashMap<Integer, Controls>)masterSettings.get(0));

        // re-add into masterSettings with proper types to allow for proper serialization
        masterSettings.clear();
        masterSettings.add(controlSettings);
    }

    private ArrayList serializeAllSettings(){
        ArrayList serializedData = new ArrayList<>();
        for(iSettings data: masterSettings){
            serializedData.add(data.getSerializableValue());
        }
        return serializedData;
    }

    public void readFromConfig(){
        FileInputStream inputStream;
        try{
            inputStream = new FileInputStream("settings.config");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            masterSettings = (ArrayList)objectInputStream.readObject();
            objectInputStream.close();
            populateSettings();
        } catch(ClassNotFoundException| IOException e){
            // no previous config or error reading config
            // populate with default
            controlSettings.initDefaultControls();
        }
        return;
    }

    public void writeToConfig(){
        FileOutputStream outputStream;
        try{
            outputStream = new FileOutputStream("settings.config");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(serializeAllSettings());
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch(IOException e){
            // unable to write config, terminate
            return;
        }
    }

    
}
