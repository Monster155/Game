package ru.itlab.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import ru.itlab.game.Utils.Constants;

import static ru.itlab.game.Utils.Constants.LIVES;

public class MainActivity extends Game {

    public GameScreen gs;
    public GameOverScreen gos;
    public MenuScreen ms;
    public TutorialScreen ts;
    Music music;
    long time = TimeUtils.nanoTime();
    long tutor = TimeUtils.nanoTime();
    String mainMusic = "party.mp3", GOMusic = "";
    Preferences prefs;


    @Override
    public void create() {
        prefs = Gdx.app.getPreferences("Preferences");
        gs = new GameScreen();
        ms = new MenuScreen();
        gos = new GameOverScreen();
        ts = new TutorialScreen();
        setScreen(ms);
    }

    @Override
    public void render() {
        super.render();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && getScreen() == gs) {
            setScreen(gos);
            gs.dispose();
        }
        if (Gdx.input.isTouched() && getScreen() == ms) {
            if (prefs.getBoolean("tutorial", false)) {
                setScreen(gs);
            }
            else {
                setScreen(ts);
                tutor = TimeUtils.nanoTime();
            }
            ms.dispose();
        }
        if(Gdx.input.isTouched() && getScreen() == ts
                && MathUtils.nanoToSec * (TimeUtils.nanoTime() - tutor) > 3){
            prefs.putBoolean("tutorial", true);
            prefs.flush(); //сохранение
            setScreen(gs);
            ts.dispose();
        }
        if (Gdx.input.isTouched() && getScreen() == gos
                && MathUtils.nanoToSec * (TimeUtils.nanoTime() - time)*2 > 1) {
            Gdx.app.log("MainActivity", "setScreen = ms");
            setScreen(ms);
            gos.dispose();
        }
        if (LIVES <= 0) {
            Gdx.app.log("Screens", "GameOverScreen");
            setScreen(gos);
            gs.dispose();
            time = TimeUtils.nanoTime();
        }
    }

    public void music(boolean begin, String name) {
        if (begin) {
            music = Gdx.audio.newMusic(Gdx.files.internal(name));
            music.setLooping(true);
            music.play();
        } else {
            music.stop();
        }
    }
}
