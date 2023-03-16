package com.mygdx.game.Factory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;

public class FontLoaderFactory {
    private String fontName;
    
    public FontLoaderFactory(String fontName){
        this.fontName = String.valueOf(fontName);
    };
    
    public FreeTypeFontLoaderParameter generateFont(int size, Color color){
        FreeTypeFontLoaderParameter font = new FreeTypeFontLoaderParameter();
        font.fontFileName = fontName;
        font.fontParameters.size = size;
        font.fontParameters.color = color;
        return font;
    }
    
}
