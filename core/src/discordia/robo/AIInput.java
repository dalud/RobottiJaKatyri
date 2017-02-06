package discordia.robo;

/**
 * Created by Dalud on 4.2.2017.
 */

public class AIInput {
    Controllable slave, master;

    public AIInput(Controllable slave, Controllable master){
        this.slave = slave;
        this.master = master;
    }

    public void poll() {

        if (master instanceof Robotti) {
            float distance = master.body.getPosition().x - slave.body.getPosition().x;
            if (distance > 5) {
                slave.body.setActive(true);
                slave.move(2);

            } else if (distance < -5) {
                slave.body.setActive(true);
                slave.move(1);
            }
            else slave.move(0);

            //ANNETAAN KÄVELLÄ LÄPI
            if(distance < 2.1 && distance > -2.1) slave.body.setActive(false);
        }
        slave.anim();
    }
}