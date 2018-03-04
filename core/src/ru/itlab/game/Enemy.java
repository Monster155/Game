package ru.itlab.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.itlab.game.Utils.Constants;
import ru.itlab.game.Utils.Enums.Rotation;

import static ru.itlab.game.Utils.Constants.PPM;
import static ru.itlab.game.Utils.Constants.SIZE;

public class Enemy {
    Vector2 position;
    int lives = Constants.LIVES;
    Texture texture;
    String path;

    public Enemy(Vector2 position, Rotation rotation){
        this.position = rand(position);
        switch((int)Math.random()*(4)+1){
            case 1:path = "PNG/green";break;
            case 2:path = "PNG/pink";break;
            case 3:path = "PNG/sand";break;
            case 4:path = "PNG/yellow";break;
        }
        texture = new Texture(path + "1.png");
    }

    public void update(float delta){

    }

    public void render(SpriteBatch batch){
        batch.draw(texture,
                position.x * PPM - SIZE/2,
                position.y * PPM - SIZE/2,
                SIZE,
                SIZE);
    }

    public Vector2 rand(Vector2 pos){

        return pos;
    }
}
