package ru.itlab.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ru.itlab.game.Utils.Constants;
import ru.itlab.game.Utils.Utils;

import static ru.itlab.game.Utils.Constants.C_SPEED;
import static ru.itlab.game.Utils.Constants.SIZE;
import static ru.itlab.game.Utils.Constants.isAndroid;

public class Player {
    public Fixture body;
    public Texture texture, lifeTexture[];
    public int lives = Constants.LIVES*100;
    public Vector2 bulletRot;
    String path = "PNG/blue", lifePath = "lifes/hudHeart_";


    public Player(World world) {
        body = Utils.createBox(world, 50, 50, SIZE.x, SIZE.y,
                false, "player", (short)1);
        texture = new Texture(path+"1.png");
        lifeTexture = new Texture[Constants.LIVES/2];
        for(int i = 0; i < lifeTexture.length; i++){
            lifeTexture[i] = new Texture(lifePath+"full.png");
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(texture,
                body.getBody().getPosition().x - SIZE.x/2,
                body.getBody().getPosition().y - SIZE.y/2,
                SIZE.x,
                SIZE.y);
        for(int i = 0; i < lifeTexture.length; i++) {
            batch.draw(lifeTexture[i],
                    body.getBody().getPosition().x + i * lifeTexture[i].getWidth(),
                    body.getBody().getPosition().y + Gdx.graphics.getHeight() - lifeTexture[i].getHeight(),
                    SIZE.x, SIZE.y);
            //Gdx.app.log("Lives", "draw "+i);
        }
    }

    public void update(float delta){
        if(isAndroid) moveA(delta);
        else move(delta);
        fire();
    }

    public void moveA(float delta){
        float x = Constants.stick.x, y = Constants.stick.y;
        if(y != 0 && x != 0)
            body.getBody().setLinearVelocity(delta*C_SPEED*x/(float)Math.sqrt(2), delta*C_SPEED*y/(float)Math.sqrt(2));
        else
            body.getBody().setLinearVelocity(delta*C_SPEED*x, delta*C_SPEED*y);
    }

    public void fire(){
        if(isAndroid){
            bulletRot = new Vector2(Constants.gun.x, Constants.gun.y);
        } else {
            bulletRot = new Vector2(0,0);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                bulletRot.x--;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                bulletRot.x++;

            if (Gdx.input.isKeyPressed(Input.Keys.UP))
                bulletRot.y++;
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
                bulletRot.y--;
        }
    }

    public void damaged(){
        lives--;
        if(lives  > 4){ //Damage level 1
            texture = new Texture(path+"1.png");
        } else if(lives > 2){ //Damage level 2
            texture = new Texture(path+"2.png");
        } else if(lives > 0){ //Damage level 3
            texture = new Texture(path+"3.png");
        } else{ //Game Over
            Gdx.app.log("Game", "Game Over");
        }
    }

    public void move(float delta){
        float x = 0, y = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            x--;
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            x++;

        if(Gdx.input.isKeyPressed(Input.Keys.W))
            y++;
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            y--;

        if(y != 0 && x != 0)
            body.getBody().setLinearVelocity(delta*C_SPEED*x/(float)Math.sqrt(2), delta*C_SPEED*y/(float)Math.sqrt(2));
        else
            body.getBody().setLinearVelocity(delta*C_SPEED*x, delta*C_SPEED*y);
    }
}
