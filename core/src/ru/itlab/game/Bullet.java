package ru.itlab.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.itlab.game.Utils.Enums;

public class Bullet {

    public Vector2 position;
    Enums.Rotation rotation;

    public Bullet(Vector2 position, Enums.Rotation rotation){
        this.position = position;
        this.rotation = rotation;
    }
    public void update(){

    }

    public void render(SpriteBatch batch){

    }
}
