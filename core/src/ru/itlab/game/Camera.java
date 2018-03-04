package ru.itlab.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import static ru.itlab.game.Utils.Constants.C_VISION;
import static ru.itlab.game.Utils.Constants.PPM;

public class Camera {
    public OrthographicCamera camera;
    Player player;

    public Camera(Player player){
        this.player = player;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, C_VISION.x/4, C_VISION.y/4);
    }

    public void update(float delta){
        Vector3 position = camera.position;
        position.x = player.player.getPosition().x * PPM;
        position.y = player.player.getPosition().y * PPM;
        camera.position.set(position);

        camera.update();
    }
}
