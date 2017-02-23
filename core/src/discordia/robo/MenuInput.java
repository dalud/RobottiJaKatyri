package discordia.robo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;


/**
 * Created by Dalud on 23.2.2017.
 */

public class MenuInput implements InputProcessor {
    RobottiMain main;
    Music bardic;

    public MenuInput(RobottiMain robottiMain, Music bardic) {
        this.main = robottiMain;
        this.bardic = bardic;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ESCAPE) {
            bardic.stop();
            main.create();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println(pointer);

        //IHAN HIRVEÄÄ PURKKAA, HÄPEÄ!
        if(screenX > Gdx.graphics.getWidth()/16*14 && screenY < Gdx.graphics.getHeight()/9*2) {
            bardic.stop();
            main.create();
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}