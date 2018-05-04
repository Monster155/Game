package ru.itlab.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import ru.itlab.game.Utils.Utils;

import static ru.itlab.game.Utils.Constants.B_SIZE;
import static ru.itlab.game.Utils.Constants.B_SPEED;
import static ru.itlab.game.Utils.Constants.SIZE;

public class Bullet {

    Vector2 rot;
    public Fixture body;
    Texture texture;
    public boolean inGame = true;

    public Bullet(Vector2 rot, World world, Vector2 pos){
        //Конструктор класса, вызывается при выстреле - создает пулю
        this.rot = rot;
        body = Utils.createBox(world, check(pos), B_SIZE.x, B_SIZE.y,
                false, "bullet", (short)0);
        texture = new Texture("bullet.png");
    }

    public void update(float delta){ // ", Vector2 pos" - убрал, т.к. нижняя часть в комментах
        //Ну update как update, думаю и так всё понятно
        body.getBody().setLinearVelocity(delta*B_SPEED*rot.x, delta*B_SPEED*rot.y);
        /*if(body.getBody().getPosition().x > pos.x+C_VISION.x*1.5
                || body.getBody().getPosition().y > pos.y+C_VISION.y*1.5
                || body.getBody().getPosition().x < pos.x-C_VISION.x*1.5
                || body.getBody().getPosition().y < pos.y-C_VISION.y*1.5)
            inGame = false; Обработка также в Левел*/
        if(!inGame) Gdx.app.log("Bullet", "deleted");
    }

    public void render(SpriteBatch batch){
        //Ну render как render, думаю и так всё понятно
        batch.draw(texture,
                body.getBody().getPosition().x-B_SIZE.x/2,
                body.getBody().getPosition().y-B_SIZE.y/2,
                B_SIZE.x,
                B_SIZE.y);
    }
    public Vector2 check(Vector2 pos){
        //При генерации откуда будет вылетать пуля относительно игрока
        float x = pos.x + SIZE.x/2, y = pos.y + SIZE.y/2;
        x += rot.x*SIZE.x;
        y += rot.y*SIZE.y;
        Gdx.app.log("Position", "\nX: "+x+"\nY: "+y);
        return new Vector2(x, y);
    }
}
