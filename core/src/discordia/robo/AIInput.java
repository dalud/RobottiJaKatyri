package discordia.robo;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Dalud on 4.2.2017.
 */

public class AIInput {
    Controllable slave, master, robo, katyri;
    OrthographicCamera camera;
    boolean active;

    public AIInput(Controllable slave, Controllable master, OrthographicCamera camera){
        this.slave = slave;
        this.master = master;
        robo = master;
        katyri = slave;

        this.camera = camera;
    }

    public void poll() {
        slave.anim();

        slave.body.setActive(active);
        float distanceX = master.body.getPosition().x - slave.body.getPosition().x;
        float distanceY = master.body.getPosition().y - slave.body.getPosition().y;

        //System.out.println("X: " +distanceX +", Y: " +distanceY);

        if (master instanceof Robotti) {
            if (distanceX > 5) {
                //active = true;
                slave.move(2);

            } else if (distanceX < -5) {
                //active = true;
                slave.move(1);
            }
            else slave.move(0);
        }
        else if(master instanceof Katyri) slave.move(0);

        //ANNETAAN KÄVELLÄ LÄPI
        if(distanceY < 4 && (distanceX < 2.1 && distanceX > -2.1)) active = false;
        else active = true;



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