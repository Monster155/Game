package ru.itlab.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.itlab.game.Utils.Constants;

public class Joystick extends Actor {

    Camera camera;
    Vector3 touchPos, touchPos2;
    Vector2 ciSize, cuSize;

    BitmapFont font;

    private Texture circleTex, cursorTex;
    private Sprite circleSp, cursorSp;

    public Joystick(Camera camera) {

        this.camera = camera;
        circleTex = new Texture("Controller/joystick.png");
        cursorTex = new Texture("Controller/button.png");

        circleSp = new Sprite(circleTex);
        cursorSp = new Sprite(cursorTex);

        touchPos = new Vector3(circleTex.getWidth()/2, circleTex.getHeight()/2, 0);
        touchPos2 = new Vector3(Gdx.graphics.getWidth()-circleTex.getWidth()/2,
                Gdx.graphics.getHeight()-circleTex.getHeight()/2, 0);

        ciSize = new Vector2(circleTex.getWidth(), circleTex.getHeight());
        cuSize = new Vector2(cursorTex.getWidth(), cursorTex.getHeight());

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
    }

    @Override
    public void act(float delta) {
        touchPos = new Vector3(ciSize.x/2, ciSize.y/2, 0);
        Constants.stick = new Vector2(0,0);
        touchPos2 = new Vector3(Gdx.graphics.getWidth()-circleTex.getWidth()/2,ciSize.y/2,0);
        Constants.gun = new Vector2(0,0);

        for (int i = 0; i < 10; i++) {
            if (Gdx.input.isTouched(i)) {
                if(Gdx.input.getX(i) < Gdx.graphics.getWidth()/2) {
                    touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                    camera.unproject(touchPos);
                    if (touchPos.x > ciSize.x)
                        touchPos.x = ciSize.x;
                    if (touchPos.y > ciSize.y)
                        touchPos.y = ciSize.y;
                    float x = 0, y = 0;
                    //right
                    if (touchPos.x > ciSize.x*2/3 && touchPos.x <= ciSize.x)
                        x++;
                    //left
                    if (touchPos.x < ciSize.x/3)
                        x--;
                    //up
                    if (touchPos.y > ciSize.y*2/3 && touchPos.y <= ciSize.y)
                        y++;
                    //down
                    if (touchPos.y < ciSize.x/3)
                        y--;

                    Constants.stick = new Vector2(x, y);
                } else {
                    touchPos2.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                    camera.unproject(touchPos2);
                    if(touchPos2.x < Gdx.graphics.getWidth()-ciSize.x)
                        touchPos2.x = Gdx.graphics.getWidth()-ciSize.x;
                    if(touchPos2.y > ciSize.y)
                        touchPos2.y = ciSize.y;
                    float x = 0, y = 0;
                    //right
                    if (touchPos2.x > Gdx.graphics.getWidth()-ciSize.x/3)
                        x++;
                    //left
                    if (touchPos2.x < Gdx.graphics.getWidth()-ciSize.x*2/3
                            && touchPos2.x >= Gdx.graphics.getWidth()-ciSize.x)
                        x--;
                    //up
                    if (touchPos2.y > ciSize.y*2/3 && touchPos2.y <= ciSize.y)
                        y++;
                    //down
                    if (touchPos2.y < ciSize.x/3)
                        y--;

                    Constants.gun = new Vector2(x,y);
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setProjectionMatrix(camera.combined);
        font.draw(batch, "Your lives: "+Constants.LIVES, 0, Gdx.graphics.getHeight());
        batch.draw(circleSp, 0, 0);
        batch.draw(cursorSp, touchPos.x-cuSize.x/2, touchPos.y-cuSize.y/2);
        batch.draw(circleSp, Gdx.graphics.getWidth()-ciSize.x, 0);
        batch.draw(cursorSp, touchPos2.x-cuSize.x/2, touchPos2.y-cuSize.y/2);
    }

    public void dispose() {
        circleTex.dispose();
        cursorTex.dispose();
    }
}
