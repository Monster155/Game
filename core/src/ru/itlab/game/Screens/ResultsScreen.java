package ru.itlab.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;

import static ru.itlab.game.Utils.Constants.SCORE;

public class ResultsScreen implements Screen{

    Preferences prefs;
    String name = "I am the best";
    //prefs.getBoolean("tutorial", false)
    //prefs.putBoolean("tutorial", true);
    //prefs.flush(); //сохранениеx

    @Override
    public void show() {
        prefs = Gdx.app.getPreferences("Preferences");
        if(prefs.getLong("First", 0) == 0)
            generatePrefs();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public static void saveResults() {
    }

    public void generatePrefs(){
        prefs.putLong("First", 10).flush();
        prefs.putString("First", "Drake").flush();
        prefs.putLong("Second", 10).flush();
        prefs.putString("Second", "James").flush();
        prefs.putLong("Third", 10).flush();
        prefs.putString("Third", "Sam").flush();
        prefs.putLong("Fourth", 10).flush();
        prefs.putString("Fourth", "Ashley").flush();
        prefs.putLong("Fifth", 10).flush();
        prefs.putString("Fifth", "Johny").flush();
        prefs.putLong("Sixth", 10).flush();
        prefs.putString("Sixth", "Lucy").flush();
        prefs.putLong("Seventh", 10).flush();
        prefs.putString("Seventh", "Bob").flush();
        prefs.putLong("Eighth", 10).flush();
        prefs.putString("Eighth", "Andy").flush();
        prefs.putLong("Ninth", 10).flush();
        prefs.putString("Ninth", "Samantha").flush();
        prefs.putLong("Tenth", 10).flush();
        prefs.putString("Tenth", "Jack").flush();
    }
}
