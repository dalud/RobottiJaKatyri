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
    private TextureRegion currentFrame;
    int frame_cols;
    Animation anim;
    Texture basicTex, walkRight, action, animSheet;

    enum State  {   RIGHT,
        LEFT    }
    State state;
    boolean actionInProgress;

    public Controllable(){}

    public void move(int direction) {
        //0 = STOP
        //1 = LEFT
        //2 = RIGHT
        //3 = ACTION

        if (!actionInProgress) {
            switch (direction) {
                case 0:
                    body.setLinearVelocity(0, body.getLinearVelocity().y);
                    frame_cols = 1;
                    animSheet = basicTex;
                    break;
                case 1:
                    if (velocity.x > -2) body.applyLinearImpulse(-2, 0, position.x, position.y, true);
                    frame_cols = 8;
                    animSheet = walkRight;
                    state = State.LEFT;
                    break;
                case 2:
                    if (velocity.x < 2)
                        body.applyLinearImpulse(2, 0, position.x, position.y, true);
                    frame_cols = 8;
                    animSheet = walkRight;
                    state = State.RIGHT;
                    break;
                case 3:
                    actionInProgress = true;
                    action();
                    frame_cols = 8;
                    animSheet = action;
                    break;
                default:
                    body.setLinearVelocity(0, 0);
                    break;
            }
        }
    }

    public void action(){}

    public void draw(SpriteBatch batch){
        position = body.getPosition();
        sprite.setPosition(position.x-spriteXfix, position.y-spriteYfix);
        if(state == State.LEFT && !currentFrame.isFlipX()) currentFrame.flip(true, false); //KATO! TÄSSÄHÄN OPPII UUTTA!
        sprite.setRegion(currentFrame); //HUOM! AIKA TÄRKEÄ FUNKTIO, JOTA EI LÖYDY GDX-JAVADOCISTA
        sprite.draw(batch);
    }

    public void anim() {
        stateTime += Gdx.graphics.getDeltaTime();

        //TÄMÄ TÄÄLLÄ, KOSKA HALUTAAN ANIMAATION JATKUVAN, VAIKKEI INPUTISTA TULISIKAAN MOVE()-KÄSKYÄ
        velocity = body.getLinearVelocity();
        if(actionInProgress && velocity.y == 0) {
            actionInProgress = false;
            frame_cols = 1;
            animSheet = basicTex;
        }
        if(actionInProgress) animSpeed = actionSpeed;
        else animSpeed = walkSpeed;

        animFrames = new TextureRegion[frame_cols];
        int index = 0;
        for (int i = 0; i < animSheet.getWidth(); i+=animSheet.getWidth()/frame_cols) {
            animFrames[index++] = new TextureRegion(animSheet, i, 0, animSheet.getWidth()/frame_cols, animSheet.getHeight()); //SAATIINPAS LYHENNETTYÄ TÄTÄ OPERAATIOTA, KOSKA EI OLLA VIELÄ TÄHÄN PÄIVÄÄN MENNESSÄ KÄYTETTY RIVEJÄ
        }
        anim = new Animation(animSpeed, animFrames);
        currentFrame = anim.getKeyFrame(stateTime, true);
    }
}