package ru.itlab.game.Characters;

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
import static ru.itlab.game.Utils.Constants.LIVES;
import static ru.itlab.game.Utils.Constants.MAXLIVES;
import static ru.itlab.game.Utils.Constants.SIZE;
import static ru.itlab.game.Utils.Constants.isAndroid;

public class Player {
    public Fixture body;
    public Texture texture;
    public Vector2 bulletRot;
    String path = "PNG/blue";


    public Player(World world) {
        body = Utils.createBox(world, 50, 50, SIZE.x, SIZE.y,
                false, "player", (short)-2);
        texture = new Texture(path+"1.png");
    }

    public void render(SpriteBatch batch){
        batch.draw(texture,
                body.getBody().getPosition().x - SIZE.x/2,
                body.getBody().getPosition().y - SIZE.y/2,
                SIZE.x,
                SIZE.y);
    }

    public void update(float delta){
        if(isAndroid) moveA(delta);
        else move(delta);
        bulletRot = new Vector2(Constants.gun.x, Constants.gun.y);
    }

    public void moveA(float delta){
        float x = Constants.stick.x, y = Constants.stick.y;
        if(y != 0 && x != 0)
            body.getBody().setLinearVelocity(delta*C_SPEED*x/(float)Math.sqrt(2), delta*C_SPEED*y/(float)Math.sqrt(2));
        else
            body.getBody().setLinearVelocity(delta*C_SPEED*x, delta*C_SPEED*y);
    }

    public void damaged(){
        LIVES--;
        if(LIVES  > MAXLIVES*3f/4f){ //Damage level 1
            texture = new Texture(path+"1.png");
        } else if(LIVES > MAXLIVES/2f){ //Damage level 2
            texture = new Texture(path+"2.png");
        } else if(LIVES > 0){ //Damage level 3
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




    public void dispose(){
        texture.dispose();
    }
}
