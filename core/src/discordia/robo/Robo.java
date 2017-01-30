package discordia.robo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Dalud on 29.1.2017.
 */

public class Robo {
    Body body;
    Vector2 position, velocity;
    Sprite robo;
    private float animSpeed, stateTime;
    private TextureRegion[] animFrames;
    private TextureRegion currentFrame;
    int frame_cols;
    Animation anim;
    Texture roboTex, roboWalkRight, roboAir, animSheet;

    enum State  {   RIGHT,
                    LEFT    }
    State state;
    boolean midair;

    public Robo(World world){
        //FYSIIKKA
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 7);

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
        roboTex = new Texture("robotti/ropotti.png");
        robo = new Sprite(roboTex);
        robo.setSize(4, 4); //SPRITET JOUTUU 2xTAMAAN, KOSKA FYSIIKAT LAITETTU KOKONAISIKSI
        roboWalkRight = new Texture("robotti/robotti_walkRight.png");
        roboAir = new Texture("robotti/ropotti_air.png");
        //currentFrame = new TextureRegion(roboTex);
        animSheet = roboTex;


        frame_cols = 1;
    }

    public void move(int direction) {
        //0 = STOP
        //1 = RIGHT
        //2 = LEFT
        //3 = JUMP



        if (!midair) {
            switch (direction) {
                case 0:
                    body.setLinearVelocity(0, 0);
                    frame_cols = 1;
                    //anim(roboTex);
                    animSheet = roboTex;
                    break;
                case 1:
                    if (velocity.x < 2) body.applyLinearImpulse(2, 0, position.x, position.y, true);
                    frame_cols = 8;
                    //anim(roboWalkRight);
                    animSheet = roboWalkRight;
                    state = State.RIGHT;
                    break;
                case 2:
                    if (velocity.x > -2)
                        body.applyLinearImpulse(-2, 0, position.x, position.y, true);
                    frame_cols = 8;
                    animSheet = roboWalkRight;
                    state = State.LEFT;
                    break;
                case 3:
                    midair = true;
                    body.applyLinearImpulse(0, 8, position.x, position.y, true);
                    frame_cols = 8;
                    animSheet = roboAir;
                    break;
                default:
                    body.setLinearVelocity(0, 0);
                    break;
            }
        }
    }

    public void draw(SpriteBatch batch){
        position = body.getPosition();
        robo.setPosition(position.x-2, position.y-2);
        if(state == State.LEFT && !currentFrame.isFlipX()) currentFrame.flip(true, false); //KATO! TÄSSÄHÄN OPPII UUTTA!
        robo.setRegion(currentFrame); //HUOM! AIKA TÄRKEÄ FUNKTIO, JOTA EI LÖYDY GDX-JAVADOCISTA
        robo.draw(batch);
    }

    public void anim() {
        stateTime += Gdx.graphics.getDeltaTime();

        //TÄMÄ TÄÄLLÄ, KOSKA HALUTAAN ANIMAATION JATKUVAN, VAIKKEI INPUTISTA TULISIKAAN MOVE()-KÄSKYÄ
        velocity = body.getLinearVelocity();
        if(midair && velocity.y == 0) {
            midair = false;
            frame_cols = 1;
            animSheet = roboTex;
        }
        if(midair) animSpeed = .09f;
        else animSpeed = .05f;

        animFrames = new TextureRegion[frame_cols];
        int index = 0;
        for (int i = 0; i < animSheet.getWidth(); i+=animSheet.getWidth()/frame_cols) {
                animFrames[index++] = new TextureRegion(animSheet, i, 0, animSheet.getWidth()/frame_cols, animSheet.getHeight()); //SAATIINPAS LYHENNETTYÄ TÄTÄ OPERAATIOTA, KOSKA EI OLLA VIELÄ TÄHÄN PÄIVÄÄN MENNESSÄ KÄYTETTY RIVEJÄ
            }
        anim = new Animation(animSpeed, animFrames);
        currentFrame = anim.getKeyFrame(stateTime, true);
    }
}