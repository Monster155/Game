package ru.itlab.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;

import static ru.itlab.game.Utils.Constants.SCORE;

public class ResultsScreen implements Screen{

    Preferences prefs;
    String name = "I am the best";
    String nums[] = new String[]{"First", "Second", "Third", "Fourth", "Fifth",
            "Sixth", "Seventh", "Eighth", "Ninth", "Tenth"};
    //prefs.getBoolean("tutorial", false)
    //prefs.putBoolean("tutorial", true);
    //prefs.flush(); //сохранениеx

    public ResultsScreen(){
        prefs = Gdx.app.getPreferences("Preferences");
        if((prefs.getLong("First"+"s", 0)) == 0)
            generatePrefs();
    }

    @Override
    public void show() {
        prefs = Gdx.app.getPreferences("Preferences");
        if(prefs.getLong("First"+"s", 0) == 0)
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

    public void saveResults() {
        int newPlace = 9;
        for(int i = 0; i < nums.length; i++) {
            if(prefs.getLong(nums[i]+"s", 0) < SCORE){
                newPlace = i;
                break;
            }
        }
        for(int i = nums.length-1; i > newPlace; i--) {
            prefs.putLong(nums[i]+"s", prefs.getLong(nums[i-1]+"s")).flush();
            prefs.putString(nums[i], prefs.getString(nums[i-1])).flush();
        }
        prefs.putLong(nums[newPlace]+"s", SCORE).flush();
        prefs.putString(nums[newPlace], name).flush();
        for(int i = 0; i < nums.length; i++)
            Gdx.app.log("Prefs", prefs.getLong(nums[i]+"s")+" "+prefs.getString(nums[i]));
    }

    public void generatePrefs(){
        prefs.putLong(nums[0]+"s", 10).flush();
        prefs.putString(nums[0], "Drake").flush();
        prefs.putLong(nums[1]+"s", 10).flush();
        prefs.putString(nums[1], "James").flush();
        prefs.putLong(nums[2]+"s", 10).flush();
        prefs.putString(nums[2], "Sam").flush();
        prefs.putLong(nums[3]+"s", 10).flush();
        prefs.putString(nums[3], "Ashley").flush();
        prefs.putLong(nums[4]+"s", 10).flush();
        prefs.putString(nums[4], "Johny").flush();
        prefs.putLong(nums[5]+"s", 10).flush();
        prefs.putString(nums[5], "Lucy").flush();
        prefs.putLong(nums[6]+"s", 10).flush();
        prefs.putString(nums[6], "Bob").flush();
        prefs.putLong(nums[7]+"s", 10).flush();
        prefs.putString(nums[7], "Andy").flush();
        prefs.putLong(nums[8]+"s", 10).flush();
        prefs.putString(nums[8], "Samantha").flush();
        prefs.putLong(nums[9]+"s", 0).flush();
        prefs.putString(nums[9], name+"").flush();
    }
}
