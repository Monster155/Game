package ru.itlab.game.Utils;

import com.badlogic.gdx.math.Vector2;

public final class Constants {
    public static final int PPM = 32;
    public static final float C_SPEED = 300;
    public static final float SHOOT_RATE = 3f; //кол-во выстрелов в 1 секунду
    public static long SCORE = 0;
    public static final Vector2 SIZE = new Vector2(32 / PPM, 32 / PPM);
    public static final Vector2 B_SIZE = new Vector2(SIZE.x / 2, SIZE.y / 2);
    public static final int MAXLIVES = 10; // Всегда должно быть чётным числом!!! И его нужно обновлять в GameScreen
    public static int LIVES = MAXLIVES;
    public static final float E_SPEED = C_SPEED * 1.1f;
    public static final float B_SPEED = C_SPEED * 3f;
    public static final float PM = PPM * 2;
    public static boolean isAndroid = true;
    public static Vector2 stick = new Vector2(0,0);
    public static Vector2 gun = new Vector2(0,0);
    public static float DrawX;
    public static float Scale;
    public static final float MaxEnemies = 6;
}
