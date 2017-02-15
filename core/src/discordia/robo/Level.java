package discordia.robo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Dalud on 25.1.2017.
 */

public class Level {
    public Body ground, platform1;
    Sprite grund, platformI, sky, apparatuS;
    Music theme1;
    Rectangle apparatus;

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

        box.dispose();

        //APPARATUS
        apparatus = new Rectangle(22, 1, 4, 4);


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

        apparatuS = new Sprite(new Texture("environs/apparatus.png"));
        apparatuS.setSize(apparatus.width, apparatus.height);
        apparatuS.setPosition(apparatus.x, apparatus.y);


        //========
        // SOUNDIT
        //========

        theme1 = Gdx.audio.newMusic(Gdx.files.internal("sounds/level1.mp3"));
        theme1.setLooping(true);
        theme1.play();
    }

    public void draw(SpriteBatch batch, ShapeRenderer shaper){
        sky.draw(batch);
        grund.draw(batch);
        platformI.draw(batch);
        apparatuS.draw(batch);

        //DEBUG-renderöintiä
        /*shaper.begin(ShapeRenderer.ShapeType.Filled);
        shaper.setColor(Color.BLACK);
        shaper.rect(apparatus.x, apparatus.y, apparatus.width, apparatus.height);
        shaper.end();*/
    }
}