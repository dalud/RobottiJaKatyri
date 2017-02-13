package discordia.robo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Dalud on 25.1.2017.
 */

public class Level {
    public Body ground, platform1;
    Sprite grund, platformI;

    public Level(World world){
        //LATTIA
        BodyDef groundDef = new BodyDef();
        groundDef.position.set(0, 0);
        ground = world.createBody(groundDef);
        PolygonShape box = new PolygonShape();
        box.setAsBox(16, 1);
        ground.createFixture(box, 0);

        //PLATFORM1
        //platformDef
        groundDef.position.set(4, 6);
        platform1 = world.createBody(groundDef);
        box.setAsBox(6, 1);
        platform1.createFixture(box, 0);

        box.dispose();

        //GRAFIIKKA
        grund = new Sprite(new Texture("environs/grund.png"));
        grund.setSize(32, 3);
        grund.setPosition(ground.getPosition().x-16, ground.getPosition().y-1);

        platformI = new Sprite(grund);
        platformI.setSize(13, 3);
        platformI.setPosition(platform1.getPosition().x-6.5f, platform1.getPosition().y-1);
    }

    public void draw(SpriteBatch batch){
        grund.draw(batch);
        platformI.draw(batch);
    }
}