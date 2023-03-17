package com.mygdx.game.Engine.Manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Engine.Screen.AbstractScreen;
import com.mygdx.game.Game.Screen.BattleScreen;
import com.mygdx.game.Game.Screen.GameOverScreen;
import com.mygdx.game.Game.Screen.GameScreen;
import com.mygdx.game.Game.Utils.Globals;

import java.util.HashMap;

public class ScreenManager {
    private final HashMap<Class, AbstractScreen> screensMap;
    private final Game game;
    private AbstractScreen previousScreen;

    public ScreenManager(Game game) {
        this.screensMap = new HashMap<>();
        this.game = game;
    }

    public void addScreen(AbstractScreen screen) {
        screensMap.put(screen.getClass(), screen);
    }

    public Screen getScreen(Class screenClass) {
        return screensMap.get(screenClass);
    }

    public void setScreen(Class screen) {
        if (screen != GameScreen.class) {
            ScreenUtils.clear(0, 0, 0, 0);
        }
        if (screen == GameOverScreen.class) {
            // reset game
            disposeScreen((AbstractScreen) game.getScreen());
        }
        // if transition away from battle, destory screen
        if (game.getScreen() instanceof BattleScreen) {
            disposeScreen((AbstractScreen) game.getScreen());
            Globals.setInBattle(false);
        } else if (screen != BattleScreen.class) {
            // if transition to battle screen, do not update previous screen to allow for game pause to continue working
            this.previousScreen = (AbstractScreen) game.getScreen();
        }
        game.setScreen(getScreen(screen));
    }

    public AbstractScreen getPreviousScreen() {
        return this.previousScreen;
    }

    public void disposeScreen(AbstractScreen screen) {
        Screen temp = screen;
        temp.dispose();
        this.screensMap.remove(screen.getClass());
    }

    public void disposeAll() {
        for (Screen screen : screensMap.values()) {
            screen.dispose();
        }
        screensMap.clear();
    }

}
