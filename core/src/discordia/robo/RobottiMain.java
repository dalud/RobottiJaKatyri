package discordia.robo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class RobottiMain extends ApplicationAdapter {
	SpriteBatch batch;
	World world;
	Box2DDebugRenderer debug;
	OrthographicCamera camera;
	Level level;
	Robotti robo;
	Katyri katyri;
	BasicInput input;
	AIInput ai;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		world = new World(new Vector2(0, -10), true);
		debug = new Box2DDebugRenderer();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(32, 18); //KOSKA HALUTAAN KÄYTTÄÄ FYSIIKKABOXIEN KO'OISSA 1siä EIKÄ .5sia
		level = new Level(world);
		robo = new Robotti(world);
		katyri = new Katyri(world);
		input = new BasicInput(robo);
		Gdx.input.setInputProcessor(input);
		ai = new AIInput(katyri, robo);

		camera.position.set(0, 7, 0);
		camera.update();
		Box2D.init();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		input.poll();
		ai.poll();
		camera.position.set(robo.position.x, robo.position.y+3, 0);
		camera.update();

		batch.begin();
		level.draw(batch);
		robo.draw(batch);
		batch.end();

		debug.render(world, camera.combined);
		world.step(1/45f, 6, 2);
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}