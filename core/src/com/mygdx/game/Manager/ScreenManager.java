package com.mygdx.game.Manager;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.game.Screen.AbstractScreen;

public class ScreenManager {
    private HashMap<Class, AbstractScreen> screensMap;
    private Game game;
    private AbstractScreen previousScreen;

    public ScreenManager(Game game) {
        this.screensMap = new HashMap<>();
        this.game = game;
    }

    public void addScreen(AbstractScreen screen){
        screensMap.put(screen.getClass(), screen);
    }

    public Screen getScreen(Class screenClass){
        return screensMap.get(screenClass);
    }

    public void setScreen(Class screen){
        this.previousScreen = (AbstractScreen) game.getScreen();
        game.setScreen(getScreen(screen));
    }
    
    public AbstractScreen getPreviousScreen(){
        return this.previousScreen;
    }

    public void disposeScreen(AbstractScreen screen){
        Screen temp = (Screen) screen;
        temp.dispose();
        this.screensMap.remove(screen.getClass());
    }

    public void disposeAll(){
        for(Screen screen: screensMap.values()){
            screen.dispose();
        }
        screensMap.clear();
    }

}
