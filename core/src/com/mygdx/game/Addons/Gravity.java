package com.mygdx.game.Addons;

import com.mygdx.game.Characters.AbstractActor;

public class Gravity {
    // default gravity level
    public static final int GRAVITY_LIGHT = 3;
    public static final int GRAVITY_MEDIUM = 2;
    public static final int GRAVITY_HEAVY = 1;
    // default to light gravity
    private static int gravityLevel = GRAVITY_LIGHT;

    public void setGravityLevel(int gravityLevel){
        Gravity.gravityLevel = gravityLevel;
    }
    
    // handle default gravity
    public static void invokeGravity(AbstractActor actor, float delta){
       invokeGravity(gravityLevel, actor, delta); 
    }
    
    public static void invokeGravity(int gravityLevel, AbstractActor actor, float delta){
        if(actor.getY() <= 0){
            return;
        }
        actor.setY(actor.getY() - delta*(actor.getMovementSpeed()/gravityLevel));
    }

}
