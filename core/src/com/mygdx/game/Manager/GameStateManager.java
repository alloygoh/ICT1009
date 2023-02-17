package com.mygdx.game.Manager;

import com.mygdx.game.Interfaces.iSaveable;
import com.mygdx.game.Utils.Globals;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameStateManager {
    private ArrayList<iSaveable> saveables;
    private HashMap<Class, ArrayList<HashMap>> stash;

    public GameStateManager() {
        this(new ArrayList<iSaveable>());
    }

    public GameStateManager(ArrayList<iSaveable> saveables) {
        this.saveables = saveables;
        this.stash = new HashMap<>();
    }

    private void buildStash() {
        stash.clear();
        for (iSaveable saveable : saveables) {
            ArrayList<HashMap> temp = stash.get(saveable.getClass());
            if (temp == null) {
                temp = new ArrayList<>();
            }
            temp.add(saveable.stashOptions());
            stash.put(saveable.getClass(), temp);
        }
    }

    public void updateSaveables(ArrayList<iSaveable> saveables) {
        this.saveables = saveables;
        buildStash();
    }

    public boolean hasSavedState() {
        File stateFile = new File("game.state");
        return stateFile.exists();
    }

    public void saveState() {
        FileOutputStream outputStream;
        try {
            File file = new File("game.state");
            if (file.exists()) {
                file.delete();
            }
            outputStream = new FileOutputStream("game.state", false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(saveables.size());
            for (Map.Entry<Class, ArrayList<HashMap>> set : stash.entrySet()) {
                for (HashMap options : set.getValue()) {
                    objectOutputStream.writeObject(set.getKey());
                    objectOutputStream.writeObject(options);
                }
            }
            objectOutputStream.writeObject(Globals.getScore());
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            // unable to write config, terminate
            return;
        }
    }

    private void initSaveable(Class<? extends iSaveable> saveableClass, HashMap<String, Object> options) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        saveables.add((iSaveable) saveableClass.getConstructor().newInstance());
        saveables.get(saveables.size() - 1).populate(options);
    }

    public ArrayList<iSaveable> loadState() {
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream("game.state");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            int size = (int) objectInputStream.readObject();
            for (int i = 0; i < size; i++) {
                Class saveableClass = (Class) objectInputStream.readObject();
                HashMap<String, Object> options = (HashMap<String, Object>) objectInputStream.readObject();
                initSaveable(saveableClass, options);
            }
            Globals.setScore((int) objectInputStream.readObject());
            objectInputStream.close();
        } catch (Exception e) {
            // unable to load config
            return new ArrayList<>();
        }
        return this.saveables;
    }
}
