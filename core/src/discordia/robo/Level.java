package discordia.robo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    public Body ground, platform1, elevator;
    Sprite grund, platformI, sky, apparatuS, elevatorS, doorS;
    Texture down, up;
    Music theme1;
    Rectangle apparatus, door;
    enum ElevState {    UP,
                        DOWN    }
    ElevState elevState;
    Sound button;

    public Level(World world){

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
        groundDef.position.set(12, 5); //PYSTYY NÄKÖJÄÄN KÄYTTÄÄN SAMAA DEFFIÄ (y)
        platform1 = world.createBody(groundDef);
        box.setAsBox(6, 1);
        platform1.createFixture(box, 0);

        //ELEVATOR
        groundDef.position.set(12, 7);
        groundDef.type = BodyDef.BodyType.KinematicBody;
        elevator = world.createBody(groundDef);
        box.setAsBox(4, 0);
        elevator.createFixture(box, 0);
        elevState = ElevState.DOWN;

        box.dispose();

        //APPARATUS
        apparatus = new Rectangle(22, 1, 3, 3);

        //DOOR
        //door = new Rectangle(-15, 1, 6, 12);


        //================================================
        // GRAFIIKAT (täytyy skaalaa x2 fysiikoihin nähen)
        //================================================

        grund = new Sprite(new Texture("environs/grund.png"));
        grund.setSize(66, 3);
        grund.setPosition(ground.getPosition().x-33, ground.getPosition().y-1);

        platformI = new Sprite(grund);
        platformI.setSize(13, 3);
        platformI.setPosition(platform1.getPosition().x-6.5f, platform1.getPosition().y-1);

        sky = new Sprite(new Texture("environs/sky.png"));
        sky.setSize(92, 46);
        sky.setPosition(-46, 1);

        apparatuS = new Sprite();
        apparatuS.setSize(apparatus.width, apparatus.height);
        apparatuS.setPosition(apparatus.x, apparatus.y);

        down = new Texture("environs/apparatusDown.png");
        up = new Texture("environs/apparatusUp.png");
        apparatuS.setRegion(down);

        /*elevatorS = new Sprite(new Texture("environs/elevator.png"));
        elevatorS.setSize(8, 3);
        elevatorS.setPosition(-4, 1);

        doorS = new Sprite(new Texture("environs/door.png"));
        doorS.setSize(door.width, door.height);
        doorS.setPosition(door.x, door.y);*/


        //========
        // SOUNDIT
        //========

        theme1 = Gdx.audio.newMusic(Gdx.files.internal("sounds/level1.mp3"));
        theme1.setLooping(true);
        theme1.play();

        button = Gdx.audio.newSound(Gdx.files.internal("sounds/button.mp3"));
    }

    public void draw(SpriteBatch batch){
        sky.draw(batch);
        grund.draw(batch);
        platformI.draw(batch);
        apparatuS.draw(batch);
        //doorS.draw(batch);

        //elevatorS.draw(batch); //tämä täytyy siirtää drawFront();
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
        }
    }
}