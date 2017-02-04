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

    public void poll(){
        //System.out.println(master.getClass().toString());
        if(master instanceof Robotti) {
            if ((master.body.getPosition().x - slave.body.getPosition().x) > 6) {
                slave.move(2);
                //System.out.println("nyt seurataan oikealle");
            } else {
                slave.move(0);
                //System.out.println("nyt ei seurata");
            }
        }
    }
}
