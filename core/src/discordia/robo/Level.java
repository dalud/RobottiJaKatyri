package discordia.robo;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Dalud on 25.1.2017.
 */

public class Level {
    public Body ground;

    public Level(World world){
        //LATTIA
        BodyDef groundDef = new BodyDef();
        groundDef.position.set(0, 0);
        ground = world.createBody(groundDef);
        PolygonShape box = new PolygonShape();
        box.setAsBox(16, 1);
        ground.createFixture(box, 0);
        box.dispose();
    }
}
