package com.mygdx.game.Characters;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Addons.Gravity;
import com.mygdx.game.Interfaces.iSaveable;
import com.mygdx.game.Utils.Controls;

public class Ball extends MovingShapeActor implements iSaveable{
    
    public Ball(){
        this(null,0,Color.CLEAR);
    }

    public Ball(ShapeRenderer renderer, float radius, Color color){
        this(renderer, radius, 0, 0, color, 100, Controls.Presets.DEFAULT);
    }

    public Ball(ShapeRenderer renderer, float radius, Color color, Controls control){
        this(renderer, radius, 0, 0, color, 100, control);
    }

    public Ball(ShapeRenderer renderer, float radius, Color color, float movementSpeed, Controls control){
        this(renderer, radius, 0, 0, color, movementSpeed, control);
    }

    public Ball(ShapeRenderer renderer, float radius, float x, float y, Color color){
        this(renderer, radius, x, y, color, 100, Controls.Presets.DEFAULT);
    }

    public Ball(ShapeRenderer renderer, float radius, float x, float y, Color color, Controls control){
        this(renderer, radius, x, y, color, 100, control);
    }

    public Ball(ShapeRenderer renderer, float radius, float x, float y, Color color, float movementSpeed){
        this(renderer, radius, x, y, color, movementSpeed, Controls.Presets.DEFAULT);
    }

    public Ball(ShapeRenderer renderer, float radius, float x, float y, Color color, float movementSpeed, Controls control){
        super(renderer, "circle", radius*2, radius*2, x, y, color, movementSpeed, control);
    }

    @Override
    public void act(float delta){
        Gravity.invokeGravity(this, delta);
        super.act(delta);
    }
    
    @Override
    public HashMap<String, Object> stashOptions(){
        HashMap<String, Object> options = new HashMap<>();
        options.put("x", this.getX());
        options.put("y", this.getY());
        options.put("radius", this.getWidth()/2);
        options.put("color", this.getColor().toString());
        options.put("speed", this.getMovementSpeed());
        options.put("controls", this.getControl());
        return options;

    }

    @Override
    public void populate(HashMap<String, Object>options) {
        float x = (float)options.get("x");
        float y = (float)options.get("y");
        float radius = (float)options.get("radius");
        Color color = Color.valueOf((String)options.get("color"));
        float movementSpeed = (float)options.get("speed");
        ShapeRenderer renderer = (ShapeRenderer)options.get("renderer");
        Controls controls = (Controls)options.get("controls");
        this.setX(x);    
        this.setY(y);    
        this.setWidth(radius*2);
        this.setHeight(radius*2);
        this.setMovementSpeed(movementSpeed);
        this.setColor(color);
        this.setRenderer(renderer);
        this.setControl(controls);
    }
}
