package com.mygdx.game.Manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.GlobalSetter;
import com.mygdx.game.Characters.Ball;
import com.mygdx.game.Characters.Car;
import com.mygdx.game.Characters.Pen;
import com.mygdx.game.Interfaces.iSaveable;
import com.mygdx.game.Utils.Globals;

public class GameStateManager {
    private ArrayList<iSaveable> saveables;
    private HashMap<Class, ArrayList<HashMap>> stash;

    public GameStateManager(){
       this(new ArrayList<iSaveable>());
    }
    
    public GameStateManager(ArrayList<iSaveable>saveables){
        this.saveables = saveables;
        this.stash = new HashMap<>();
    }
    
    private void buildStash(){
        for(iSaveable saveable: saveables){
            ArrayList<HashMap> temp = stash.get(saveable.getClass());
            if (temp == null){
                temp = new ArrayList<>();
            } 
            temp.add(saveable.stashOptions());
            stash.put(saveable.getClass(),temp);
        }
    }

    public void updateSaveables(ArrayList<iSaveable> saveables){
        this.saveables = saveables;
        buildStash();
    } 
    
    public boolean hasSavedState(){
        File stateFile = new File("game.state");
        return stateFile.exists();
    }

    public void saveState(){
        FileOutputStream outputStream;
        try{
            outputStream = new FileOutputStream("game.state", false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(saveables.size());
            for (Map.Entry<Class, ArrayList<HashMap>> set: stash.entrySet()){
                for(HashMap options : set.getValue()){
                    objectOutputStream.writeObject(set.getKey());
                    objectOutputStream.writeObject(options);
                }
            }
            objectOutputStream.writeObject(Globals.getScore()); 
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch(IOException e){
            // unable to write config, terminate
            return;
        }
    }
    
    private void initSaveable(Class saveableClass, HashMap<String,Object>options){
        if(saveableClass == Ball.class){
           Ball tempBall = new Ball(null,0,Color.BLACK);
           Ball trueBall = tempBall.createInstanceOf(options);
           saveables.add(trueBall);
        } else if(saveableClass == Pen.class){
           Pen tempPen = new Pen(0, 0);
           Pen truePen = tempPen.createInstanceOf(options);
           saveables.add(truePen);
        }else if(saveableClass == Car.class){
           Car tempCar = new Car(0, 0);
           Car trueCar = tempCar.createInstanceOf(options);
           saveables.add(trueCar);
        }
    }
    
    public ArrayList<iSaveable> loadState(){
        FileInputStream inputStream;
        try{
            inputStream = new FileInputStream("game.state");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            int size = (int)objectInputStream.readObject();
            for(int i = 0; i < size; i++){
                Class saveableClass = (Class)objectInputStream.readObject();
                HashMap<String, Object> options = (HashMap<String, Object>)objectInputStream.readObject();
                initSaveable(saveableClass, options);
            }
            Globals.setScore((int)objectInputStream.readObject());
            objectInputStream.close();
        } catch(ClassNotFoundException| IOException e){
            // unable to load config
            return new ArrayList<>();
        }
        return this.saveables;
    }
}
