package discordia.robo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Dalud on 4.2.2017.
 */

public class Katyri extends Controllable{
    Sprite ringu;
    boolean buzz;
    int buzzC;
    float buzzT;

    public Katyri(World world){
        ringu = new Sprite(new Texture(Gdx.files.internal("katyri/ringu.png")));
        ringu.setAlpha(.7f);

        //FYSIIKKA
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(-20, 1);

        body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(1, 1.2f);

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
        spriteXfix = 2f;
        spriteYfix = 1.2f;
        walkRight = new Texture("katyri/katyriWalk.png");
        action = new Texture("katyri/katyriWalk.png");
        animSheet = basicTex;
        frame_cols = 1;
        walkSpeed = .04f;
        actionSpeed = .09f;
    }

    @Override
    public void action(){
        buzz = true;
        buzzT = buzzC = 0;
    }

    public void poll(SpriteBatch batch) {
        if(buzz) {
            buzzT += .3f;
            ringu.setSize(buzzT, buzzT);
            ringu.setPosition(position.x-buzzT/2, position.y-buzzT/2);
            ringu.setOriginCenter();
            ringu.rotate(20);
            ringu.draw(batch);
        }
        if(buzzT > 7) {
            buzzC++;
            buzzT = 0;
        }
        if(buzzC == 3) buzz = false;
    }
}