package ru.itlab.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public final class Constants {
    public static final float PPM = 32;
    public static final float C_SPEED = 300;
    public static final float SIZE = 32f;
    public static final int LIVES = 9;
    public static final float E_SPEED = C_SPEED * 1.2f;
    public static final float B_SPEED = C_SPEED * 2;
    public static final Vector2 C_VISION = new Vector2(2*Gdx.graphics.getWidth(), 2*Gdx.graphics.getHeight());
}
