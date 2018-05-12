package ru.itlab.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Objects;

import ru.itlab.game.Utils.Constants;
import ru.itlab.game.Utils.Utils;

import static ru.itlab.game.Utils.Constants.E_SPEED;
import static ru.itlab.game.Utils.Constants.SIZE;

public class Enemy {

    public Fixture body;
    Texture texture;
    public Vector2 rot = new Vector2(0,0);
    public boolean inGame = true;
    public int live = 9;
    String path;
    public long change = TimeUtils.nanoTime();

    public Enemy(World world, Vector2 pos){
        body = Utils.createBox(world, rand(pos), SIZE.x, SIZE.y,
                false, "enemy", (short) -1);
        int abb = (int)(Math.random()*4+1);
        Gdx.app.log("Random", abb+"");
        switch(abb){
            case 1:path = "PNG/green";break;
            case 2:path = "PNG/pink";break;
            case 3:path = "PNG/sand";break;
            case 4:path = "PNG/yellow";break;
        }
        texture = new Texture(path + "1.png");
    }

    public void update(float delta, Vector2 pos){
        if(MathUtils.nanoToSec*(TimeUtils.nanoTime()-change) > (int)(Math.random()*3+2)) {
            calcRot(pos);
            change = TimeUtils.nanoTime();
        }
        body.getBody().setLinearVelocity(delta*E_SPEED*rot.x, delta*E_SPEED*rot.y);
        /*if(body.getBody().getPosition().x > pos.x+C_VISION.x*1.5
                || body.getBody().getPosition().y > pos.y+C_VISION.y*1.5
                || body.getBody().getPosition().x < pos.x-C_VISION.x*1.5
                || body.getBody().getPosition().y < pos.y-C_VISION.y*1.5)
                должна работать обработка в Левел
            inGame = false; */
        if(!inGame) Gdx.app.log("Enemy", "deleted");
    }

    public void render(SpriteBatch batch){
        batch.draw(texture,
                body.getBody().getPosition().x - SIZE.x/2,
                body.getBody().getPosition().y - SIZE.y/2,
                SIZE.x,
                SIZE.y);
    }

    public Vector2 rand(Vector2 pos){
        float x,y;
        //Размеры смотреть в логах - самый первые строки, они описываны в TiledObjectUtil
        float ax = 5, bx = 45, ay = 5, by = 45;
        x = ax + (float)(Math.random() * (bx-ax));
        y = ay + (float)(Math.random() * (by-ay));
        return new Vector2(x, y);
    }

    public void calcRot(Vector2 pos){
        float x = body.getBody().getPosition().x, y = body.getBody().getPosition().y;
        if(x > pos.x+SIZE.x)
            rot.x = -1;
        else if(x < pos.x-SIZE.x)
            rot.x = 1;
        else rot.x = 0;

        if(y > pos.y+SIZE.y)
            rot.y = -1;
        else if(y < pos.y-SIZE.y)
            rot.y = 1;
        else rot.y = 0;

        if(rot.x == 0 && rot.y == 0){
            if(x > pos.x)
                rot.x = -1;
            else if(x < pos.x)
                rot.x = 1;
            else rot.x = 0;

            if(y > pos.y)
                rot.y = -1;
            else if(y < pos.y)
                rot.y = 1;
            else rot.y = 0;
        }
    }

    public void damaged(){
        live--;
        if(live > 6)
            texture = new Texture(path+"1.png");
        else if(live > 3)
            texture = new Texture(path+"2.png");
        else if(live > 0)
            texture = new Texture(path+"3.png");
        else inGame = false;
    }
}
