package discordia.robo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Dalud on 29.1.2017.
 */

public class Controllable {
    Body body;
    Vector2 position, velocity;
    Sprite sprite;
    private float animSpeed, stateTime;
    protected float walkSpeed, actionSpeed, spriteXfix, spriteYfix;
    private TextureRegion[] animFrames;
    protected TextureRegion currentFrame;
    int frame_cols, hangTime;
    Animation anim;
    Texture basicTex, walkRight, action, animSheet;

    enum State  {   IDLE,
                    RIGHT,
                    LEFT,
                    ACTION  }
    State state;

    enum Facing {   EAST,
                    WEST    }
    Facing facing;

    boolean midAir;

    public Controllable(){}

    public void move(int direction) {
        //0 = STOP
        //1 = LEFT
        //2 = RIGHT
        //3 = ACTION

        if(!midAir) {
            switch (direction) {
                case 0:
                    body.setLinearVelocity(0, velocity.y); //X = DIMINISHING RETURN
                    state = State.IDLE;
                    break;
                case 1:
                    if (velocity.x > -2) body.applyLinearImpulse(-2, 0, position.x, position.y, true);
                    state = State.LEFT;
                    facing = Facing.WEST;
                    break;
                case 2:
                    if (velocity.x < 2) body.applyLinearImpulse(2, 0, position.x, position.y, true);
                    state = State.RIGHT;
                    facing = Facing.EAST;
                    break;
                case 3:
                    action();
                    state = State.ACTION;
                    break;
            }
        }
    }

    public void action(){}

    public void draw(SpriteBatch batch){
        position = body.getPosition();
        sprite.setPosition(position.x-spriteXfix, position.y-spriteYfix);
        if(facing == Facing.WEST && !currentFrame.isFlipX()) currentFrame.flip(true, false); //KATO! TÄSSÄHÄN OPPII UUTTA!
        sprite.setRegion(currentFrame); //HUOM! AIKA TÄRKEÄ FUNKTIO, JOTA EI LÖYDY GDX-JAVADOCISTA
        sprite.draw(batch);
    }

    public void anim() {
        stateTime += Gdx.graphics.getDeltaTime();
        velocity = body.getLinearVelocity();

        //LASKETAAN ILMASSA OLO AIKAA, KOSKA EI HALUTA NOLLATA LAKIPISTEESSÄ
        if(velocity.y == 0 && midAir) hangTime++;
        if(hangTime > 1) midAir = false;

        if(state == State.IDLE) {
            frame_cols = 1;
            animSheet = basicTex;
        }
        else if(state == State.RIGHT || state == State.LEFT){
            frame_cols = 8;
            animSheet = walkRight;
            animSpeed = walkSpeed;
        }
        else if(state == State.ACTION) {
            frame_cols = 8;
            animSpeed = actionSpeed;
            animSheet = action;

            if(facing == Facing.EAST) {
                if(velocity.x < .1f) body.applyLinearImpulse(.1f, 0, position.x, position.y, true);
            }
            else {
                if(velocity.x > -.1f) body.applyLinearImpulse(-.1f, 0 , position.x, position.y, true);
            }
        }

        animFrames = new TextureRegion[frame_cols];
        int index = 0;
        for (int i = 0; i < animSheet.getWidth(); i+=animSheet.getWidth()/frame_cols) {
            animFrames[index++] = new TextureRegion(animSheet, i, 0, animSheet.getWidth()/frame_cols, animSheet.getHeight()); //SAATIINPAS LYHENNETTYÄ TÄTÄ OPERAATIOTA, KOSKA EI OLLA VIELÄ TÄHÄN PÄIVÄÄN MENNESSÄ KÄYTETTY RIVEJÄ
        }
        anim = new Animation(animSpeed, animFrames);
        currentFrame = anim.getKeyFrame(stateTime, true);
    }
}