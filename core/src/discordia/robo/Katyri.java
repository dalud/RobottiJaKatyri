package discordia.robo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Dalud on 4.2.2017.
 */

public class Katyri extends Controllable{

    public Katyri(World world){
        //FYSIIKKA
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(-4, 7);

        body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(1, 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;
        Fixture fixture = body.createFixture(fixtureDef);

        box.dispose();
        position = body.getPosition();

        //GRAFIIKKA
        basicTex = new Texture("katyri/katyri.png");
        sprite = new Sprite(basicTex);
        sprite.setSize(4, 4); //SPRITET JOUTUU 2xTAMAAN, KOSKA FYSIIKAT LAITETTU KOKONAISIKSI
        spriteXfix = 2;
        spriteYfix = 2;
        walkRight = new Texture("katyri/katyriWalk.png");
        action = new Texture("katyri/katyriWalk.png");
        animSheet = basicTex;
        frame_cols = 1;
        walkSpeed = .05f;
        actionSpeed = .09f;
    }

    @Override public void action(){

    }
}