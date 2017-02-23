package discordia.robo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Dalud on 25.1.2017.
 */

public class Level {
    public Body ground, platform1, platform2, elevator;
    Sprite grund, platformI, platformII, sky, apparatuS, elevatorS, doorS, theVoid, credits, resetS;
    Texture down, up;
    Music theme1, elevS, bardic, sucked;
    Rectangle apparatus, door, reset;
    enum ElevState {    UP,
                        DOWN    }
    ElevState elevState;
    boolean elevOperational, doorOpened, rollCredits;
    Sound button;
    Robotti robo;
    Vector2 doorPos;
    MenuInput menuInput;
    OrthographicCamera camera;

    public Level(World world, Robotti robo, OrthographicCamera camera, RobottiMain robottiMain){
        this.robo = robo;
        doorPos = new Vector2();
        this.camera = camera;
        reset = new Rectangle();

        //=====================
        // FYSIIKAT ===========
        //=====================

        //LATTIA
        BodyDef groundDef = new BodyDef();
        groundDef.position.set(0, 0);
        ground = world.createBody(groundDef);
        PolygonShape box = new PolygonShape();
        box.setAsBox(32, 1);
        ground.createFixture(box, 0);

        //PLATFORM1
        groundDef.position.set(12, 4.5f); //PYSTYY NÄKÖJÄÄN KÄYTTÄÄN SAMAA DEFFIÄ (y)
        platform1 = world.createBody(groundDef);
        box.setAsBox(6, 1);
        platform1.createFixture(box, 0);
        //2
        groundDef.position.set(-2, 20);
        platform2 = world.createBody(groundDef);
        box.setAsBox(10, 1);
        platform2.createFixture(box, 0);

        //ELEVATOR
        groundDef.position.set(12, 5.5f);
        groundDef.type = BodyDef.BodyType.KinematicBody;
        elevator = world.createBody(groundDef);
        box.setAsBox(4, 0);
        elevator.createFixture(box, 0);
        elevState = ElevState.DOWN;

        box.dispose();

        //APPARATUS
        apparatus = new Rectangle(22, 1, 3, 3);

        //DOOR
        door = new Rectangle(platform2.getPosition().x-4, platform2.getPosition().y+1, 6, 12);


        //================================================
        // GRAFIIKAT (täytyy skaalaa x2 fysiikoihin nähen)
        //================================================

        grund = new Sprite(new Texture("environs/grund.png"));
        grund.setSize(66, 3);
        grund.setPosition(ground.getPosition().x-33, ground.getPosition().y-1);

        platformI = new Sprite(grund);
        platformI.setSize(13, 3);
        platformI.setPosition(platform1.getPosition().x-6.5f, platform1.getPosition().y-1);

        platformII = new Sprite(grund);
        platformII.setSize(20, 3);
        platformII.setPosition(platform2.getPosition().x-10, platform2.getPosition().y-1);

        sky = new Sprite(new Texture("environs/sky.png"));
        sky.setSize(92, 46);
        sky.setPosition(-46, 1);

        apparatuS = new Sprite();
        apparatuS.setSize(apparatus.width, apparatus.height);
        apparatuS.setPosition(apparatus.x, apparatus.y);

        down = new Texture("environs/apparatusDown.png");
        up = new Texture("environs/apparatusUp.png");
        apparatuS.setRegion(down);

        elevatorS = new Sprite(new Texture("environs/elevator.png"));
        elevatorS.setSize(8, 3);
        elevatorS.setPosition(elevator.getPosition().x-4, elevator.getPosition().y-.5f);

        doorS = new Sprite(new Texture("environs/door.png"));
        doorS.setSize(door.width, door.height);
        doorS.setPosition(door.x, door.y);

        theVoid = new Sprite(new Texture("environs/theVoid.png"));
        theVoid.setSize(0, door.height);
        theVoid.setAlpha(.74f);
        theVoid.setPosition(door.x, door.y);

        credits = new Sprite(new Texture("environs/credits.png"));
        credits.setSize(6.5f, 26);

        resetS = new Sprite(new Texture("environs/reset.png"));


        //========
        // SOUNDIT
        //========

        theme1 = Gdx.audio.newMusic(Gdx.files.internal("sounds/level1.mp3"));
        theme1.setLooping(true);
        theme1.play();

        button = Gdx.audio.newSound(Gdx.files.internal("sounds/button.mp3"));

        elevS = Gdx.audio.newMusic(Gdx.files.internal("sounds/elevator.mp3"));
        elevS.setLooping(true);

        bardic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Bardic.mp3"));
        bardic.setVolume(.5f);

        sucked = Gdx.audio.newMusic(Gdx.files.internal("sounds/sucked.mp3"));


        //========
        // JA MUUT
        //========

        menuInput = new MenuInput(robottiMain, bardic);
    }

    public void poll() {
        //HISSI
        if(elevOperational){
            switch(elevState){
                case UP:
                    if(elevator.getPosition().y < 21) elevator.setLinearVelocity(0, 1);
                    else elevStop();
                    break;
                case DOWN:
                    if(elevator.getPosition().y > 7) elevator.setLinearVelocity(0, -1);
                    else elevStop();
                    break;
            }
            elevatorS.setPosition(elevator.getPosition().x-4, elevator.getPosition().y-.5f);
        }

        //EXIT
        float distance = door.getCenter(doorPos).dst2(robo.position);
        if(distance < 8.97f) {
            trancendent();

        }
    }

    public void draw(SpriteBatch batch){
        if(rollCredits) {
            credits.draw(batch);
            theme1.stop();
            float distance = camera.position.y - credits.getY();
            resetS.setAlpha(distance/50);
            if(distance > 12) resetS.draw(batch);
            if(distance < 19.5f) credits.translate(0, -.005f);
        }
        else{
            sky.draw(batch);
            grund.draw(batch);
            platformI.draw(batch);
            platformII.draw(batch);
            apparatuS.draw(batch);
            doorS.draw(batch);
            if(doorOpened) {
                theVoid.draw(batch);
                theVoid.setCenterX(door.x + door.width/2);
                if(theVoid.getWidth() < door.width) {
                    theVoid.setSize(theVoid.getWidth()+.05f, door.height);
                    camera.zoom -= .00835f;
                    camera.translate(0, -.05f);
                    robo.sprite.setSize(robo.sprite.getWidth()-.014f, robo.sprite.getHeight()-.014f);
                }
                if(camera.zoom < 0) theEnd();
            }
        }
    }



    public void drawFront(SpriteBatch batch) {
        elevatorS.draw(batch);
    }

    public void apparatusOperate(Vector2 oCoords){
        Rectangle touch = new Rectangle(oCoords.x-1.5f, oCoords.y-1.5f, 3, 3);
        if(touch.overlaps(apparatus)){
            button.play();

            switch (elevState){
                case DOWN:
                    elevState = ElevState.UP;
                    apparatuS.setRegion(up);
                    break;
                case UP:
                    elevState = ElevState.DOWN;
                    apparatuS.setRegion(down);
                    break;
            }
            elevOperational = true;
            if(!elevS.isPlaying()) elevS.play();
        }
    }

    public void elevStop(){
        elevOperational = false;
        elevator.setLinearVelocity(0, 0);
        button.play();
        elevS.stop();
    }

    private void trancendent() {
        if(!doorOpened) sucked.play();
        doorOpened = true;
        robo.body.setActive(false);
        Gdx.input.setInputProcessor(menuInput);
    }

    private void theEnd() {
        robo.sprite = new Sprite();
        credits.setPosition(camera.position.x-3.3f, camera.position.y-1.7f);
        reset = new Rectangle(credits.getX()+5.5f, credits.getY()+5, .5f, .5f);
        resetS.setSize(reset.width, reset.height);
        resetS.setPosition(reset.x, reset.y);

        camera.zoom = .2f;
        bardic.play();
        rollCredits = true;
    }
}