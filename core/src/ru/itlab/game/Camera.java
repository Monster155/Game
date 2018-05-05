package ru.itlab.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import static ru.itlab.game.Utils.Constants.PPM;


public class Camera {
    public OrthographicCamera camera;
    Player player;

    public Camera(Player player){
        this.player = player;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() * PPM, Gdx.graphics.getHeight() * PPM);
    }

    public void update(float delta){
        Vector3 position = camera.position;
        position.x = player.body.getBody().getPosition().x;
        position.y = player.body.getBody().getPosition().y;
        camera.position.set(position);

        camera.update();
    }
}
