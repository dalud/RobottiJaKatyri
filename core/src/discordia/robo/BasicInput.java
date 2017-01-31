package discordia.robo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Dalud on 29.1.2017.
 */

public class BasicInput implements InputProcessor {
    Robo robo;
    int move, meridian, finger;


    public BasicInput(Robo robo){
        this.robo = robo;
        meridian = Gdx.graphics.getWidth()/2;
    }

    public void poll() {
        //KEYS
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) robo.move(0);
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) robo.move(1);
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) robo.move(2);

        //TOUCH
        else robo.move(move);

        //TÄÄLLÄ KOSKA MOVE() ANIMOISI VAIN NAPPI ALHAALLA
        robo.anim();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.SPACE) robo.move(3);

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT) robo.move(0);

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        finger++;

        if(finger == 2) robo.move(3);
        if (screenX < meridian) move = 1;
        else move = 2;

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        finger--;

        if(finger == 0) move = 0;
        else if(screenX < meridian) move = 2;
        else move = 1;

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