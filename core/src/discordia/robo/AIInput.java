package discordia.robo;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Dalud on 4.2.2017.
 */

public class AIInput {
    Controllable slave, master, robo, katyri;
    OrthographicCamera camera;

    public AIInput(Controllable slave, Controllable master, OrthographicCamera camera){
        this.slave = slave;
        this.master = master;
        robo = master;
        katyri = slave;

        this.camera = camera;
    }

    public void poll() {
        float distance = master.body.getPosition().x - slave.body.getPosition().x;

        if (master instanceof Robotti) {
            if (distance > 5) {
                slave.body.setActive(true);
                slave.move(2);

            } else if (distance < -5) {
                slave.body.setActive(true);
                slave.move(1);
            }
            else slave.move(0);
        }
        else if(master instanceof Katyri) slave.move(0);

        //ANNETAAN KÄVELLÄ LÄPI
        if(distance < 2.1 && distance > -2.1) slave.body.setActive(false);

        slave.anim();

        //KAMERAN PÄIVITYS
        float focusX = master.position.x - camera.position.x;
        float focusY = master.position.y - camera.position.y;
        camera.translate(focusX/30, (focusY+3)/50, 0);
    }

    public void swap() {
        if(master instanceof Robotti){
            slave = robo;
            master = katyri;
        }
        else if(master instanceof Katyri){
            slave = katyri;
            master = robo;
        }
        master.body.setActive(true);
    }
}