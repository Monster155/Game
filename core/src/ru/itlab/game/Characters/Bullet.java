package ru.itlab.game.Characters;

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
    World world;
    Texture texture;
    public boolean inGame = true;

    public Bullet(Vector2 rot, World world, Vector2 pos){
        this.world = world;
        //Конструктор класса, вызывается при выстреле - создает пулю
        this.rot = rot;
        body = Utils.createBox(world, check(pos), B_SIZE.x, B_SIZE.y,
                false, "bullet", (short)-2);
        texture = new Texture("bullets/bulletVer.png");
    }

    public void update(float delta){
        //Ну update как update, думаю и так всё понятно
        body.getBody().setLinearVelocity(delta*B_SPEED*rot.x, delta*B_SPEED*rot.y);
        if(!inGame){
            Gdx.app.log("Bullet", "deleted");
            world.destroyBody(body.getBody());            ;
            body = null;
        }
    }

    public void render(SpriteBatch batch){
        Gdx.app.log("Render", body.getBody().getPosition().x+" "+body.getBody().getPosition().y);
        //Ну render как render, думаю и так всё понятно
        batch.draw(texture,
                body.getBody().getPosition().x,
                body.getBody().getPosition().y,
                B_SIZE.x,
                B_SIZE.y);
    }
    public Vector2 check(Vector2 pos){
        //При генерации откуда будет вылетать пуля относительно игрока
        /*float x = pos.x + SIZE.x/2, y = pos.y + SIZE.y/2;
        x += rot.x*SIZE.x/2;
        y += rot.y*SIZE.y/2;
        Gdx.app.log("Player position", "\nX: "+pos.x+"\nY: "+pos.y);
        Gdx.app.log("Position", "\nX: "+x+"\nY: "+y);
        return new Vector2(x, y);*/
        return new Vector2(pos.x+SIZE.x/2f-B_SIZE.x/2f, pos.y+SIZE.y/2f-B_SIZE.y/2f);
    }
}
