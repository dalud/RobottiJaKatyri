package discordia.robo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Dalud on 29.1.2017.
 */

public class BasicInput implements InputProcessor {
    Robotti robo;
    Katyri katyri;
    Controllable slave;
    AIInput ai;
    int move, meridian, finger;


    public BasicInput(Robotti robo, Katyri katyri, AIInput ai){
        this.robo = robo;
        this.katyri = katyri;
        slave = robo;
        this.ai = ai;
        meridian = Gdx.graphics.getWidth()/2;
    }

    public void poll() {
        //KEYS
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) slave.move(0);
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) slave.move(1);
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) slave.move(2);

        //TOUCH
        else slave.move(move);

        //TÄÄLLÄ KOSKA MOVE() ANIMOISI VAIN NAPPI ALHAALLA
        slave.anim();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.SPACE) slave.move(3);
        else if(keycode == Input.Keys.DOWN) swap();

        return false;
    }

    public void swap() {
        if(slave instanceof Robotti) slave = katyri;
        else if(slave instanceof Katyri) slave = robo;
        ai.swap();
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT) slave.move(0);

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        finger++;

        if(finger == 2) slave.move(3);
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