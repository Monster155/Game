package ru.itlab.game.Utils;

import com.badlogic.gdx.math.Vector2;

public final class Constants {
    public static final int PPM = 4;
    public static final float C_SPEED = 1500;
    public static final int SHOOT_RATE = 30; // выстрелов в минуту
    public static long SCORE = 0;
    public static final Vector2 SIZE = new Vector2(32 / PPM, 32 / PPM);
    public static final Vector2 B_SIZE = new Vector2(SIZE.x / 2, SIZE.y / 2);
    public static final int LIVES = 9;
    public static final float E_SPEED = C_SPEED * 1.2f;
    public static final float B_SPEED = C_SPEED * 2;
}
