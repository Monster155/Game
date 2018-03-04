package ru.itlab.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import ru.itlab.game.Utils.Constants;
import ru.itlab.game.Utils.Enums;
import ru.itlab.game.Utils.Enums.Rotation;
import ru.itlab.game.Utils.Utils;

import static ru.itlab.game.Utils.Constants.C_SPEED;
import static ru.itlab.game.Utils.Constants.PPM;
import static ru.itlab.game.Utils.Constants.SIZE;

public class Player {
    public Body player;
    Texture texture;
    public Rotation rotation;
    int lives = Constants.LIVES;

    public Player(World world) {
        player = Utils.createBox(world, 500, 500, SIZE, SIZE, false);
        texture = new Texture("PNG/blue1.png");
    }

    public void render(SpriteBatch batch){
        batch.draw(texture,
                player.getPosition().x * PPM - SIZE/2,
                player.getPosition().y * PPM - SIZE/2,
                SIZE,
                SIZE);
    }

    public void update(float delta){
        move(delta);
        health();
    }

    public void health(){
        if(lives  > 6){ //Damage level 1

        } else if(lives > 3){ //Damage level 2

        } else if(lives > 0){ //Damage level 3

        } else{ //Game Over

        }
    }

    public void move(float delta){
        boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean up = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        float x = 0, y = 0;

        if(left && up && !down && !right){
            rotation = Rotation.LEFTandUP;
            x = (float) (-1*delta*C_SPEED/Math.sqrt(2));
            y = -1*x;
        } else if(left && !up && !down && !right){
            rotation = Rotation.LEFT;
            x = (float) (-1*delta*C_SPEED);
        } else if(left && !up && down && !right){
            rotation = Rotation.LEFTandDOWN;
            x = (float) (-1*delta*C_SPEED/Math.sqrt(2));
            y = x;
        } else if(!left && !up && down && !right){
            rotation = Rotation.DOWN;
            y = (float) (-1*delta*C_SPEED);
        } else if(!left && !up && down && right){
            rotation = Rotation.RIGHTandDOWN;
            x = (float) (delta*C_SPEED/Math.sqrt(2));
            y = x*-1;
        } else if(!left && !up && !down && right){
            rotation = Rotation.RIGHT;
            x = (float) (delta*C_SPEED);
        } else if(!left && up && !down && right){
            rotation = Rotation.RIGHTandUP;
            x = (float) (delta*C_SPEED/Math.sqrt(2));
            y = x;
        } else if(!left && up && !down && !right){
            rotation = Rotation.UP;
            y = (float) (delta*C_SPEED);
        }
        player.setLinearVelocity(x, y);
    }
}
