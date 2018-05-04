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

import ru.itlab.game.Utils.Constants;
import ru.itlab.game.Utils.Utils;

import static ru.itlab.game.Utils.Constants.E_SPEED;
import static ru.itlab.game.Utils.Constants.SIZE;

public class Enemy {

    public Fixture body;
    Rectangle rect;
    Texture texture;
    Vector2 rot = new Vector2(0,0);
    public boolean inGame = true;
    public int live = 9;
    String path;
    long change = TimeUtils.nanoTime();

    public Enemy(World world, Vector2 pos, Rectangle rect){
        this.rect = rect;
        body = Utils.createBox(world, rand(pos), SIZE.x, SIZE.y,
                false, "enemy", (short) -1);
        switch((int)Math.random()*4+1){
            case 1:path = "PNG/green";break;
            case 2:path = "PNG/pink";break;
            case 3:path = "PNG/sand";break;
            case 4:path = "PNG/yellow";break;
        }
        texture = new Texture(path + "1.png");
    }

    public void update(float delta, Vector2 pos){
        if(MathUtils.nanoToSec*(TimeUtils.nanoTime()-change) > 3) {
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
                body.getBody().getPosition().x,
                body.getBody().getPosition().y,
                SIZE.x,
                SIZE.y);
    }

    public Vector2 rand(Vector2 pos){
        float x,y;
        float ax = rect.x, bx = rect.width, ay = rect.y, by = rect.height;
        do{
            x = ax + (float)(Math.random() * bx);
            y = ay + (float)(Math.random() * by);
        }while(x < pos.x + Gdx.graphics.getWidth()/2 && x > pos.x - Gdx.graphics.getWidth()/2
                && y < pos.y + Gdx.graphics.getHeight()/2 && y > pos.y - Gdx.graphics.getHeight());
        return new Vector2(x, y);
    }

    public void calcRot(Vector2 pos){
        float x = body.getBody().getPosition().x, y = body.getBody().getPosition().y;
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
