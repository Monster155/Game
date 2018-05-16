package ru.itlab.game.Utils;

import com.badlogic.gdx.math.Vector2;

public final class Constants {
    public static final int PPM = 32;
    public static final float C_SPEED = 300;
    public static final int SHOOT_RATE = 60; // выстрелов в минуту
    public static long SCORE = 0;
    public static final Vector2 SIZE = new Vector2(32 / PPM, 32 / PPM);
    public static final Vector2 B_SIZE = new Vector2(SIZE.x / 2, SIZE.y / 2);
    public static final int LIVES = 6; // Всегда должно быть чётным числом!!!
    public static final float E_SPEED = C_SPEED * 0.5f;
    public static final float B_SPEED = C_SPEED * 1.5f;
    public static final float PM = PPM * 2;
    public static boolean isAndroid = true;
    public static Vector2 stick = new Vector2(0,0);
    public static Vector2 gun = new Vector2(0,0);
}
