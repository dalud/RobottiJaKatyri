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

public class Robotti extends Controllable {

    public Robotti(World world){
        //FYSIIKKA
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 7);

        body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(1, 3);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;
        Fixture fixture = body.createFixture(fixtureDef);

        box.dispose();
        position = body.getPosition();

        //GRAFIIKKA
        basicTex = new Texture("robotti/ropotti.png");
        sprite = new Sprite(basicTex);
        sprite.setSize(6, 6); //SPRITET JOUTUU 2xTAMAAN, KOSKA FYSIIKAT LAITETTU KOKONAISIKSI
        spriteXfix = 3;
        spriteYfix = 3;
        walkRight = new Texture("robotti/robotti_walkRight.png");
        action = new Texture("robotti/ropotti_air.png");
        animSheet = basicTex;
        frame_cols = 1;
        walkSpeed = .05f;
        actionSpeed = .09f;
    }

    //HYPPY
    @Override public void action(){
        body.applyLinearImpulse(0, 8, position.x, position.y, true);
    }
}
