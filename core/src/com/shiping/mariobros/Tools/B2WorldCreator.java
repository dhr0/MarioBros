package com.shiping.mariobros.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.shiping.mariobros.MarioBros;
import com.shiping.mariobros.Screens.PlayScreen;
import com.shiping.mariobros.Sprites.Enemies.Enemy;
import com.shiping.mariobros.Sprites.Enemies.Turtle;
import com.shiping.mariobros.Sprites.TileObjects.Brick;
import com.shiping.mariobros.Sprites.TileObjects.Coin;
import com.shiping.mariobros.Sprites.Enemies.Goomba;

/**
 * Created by shiping on 29/2/16.
 */
public class B2WorldCreator {
    private Array<Goomba> goombas;
    private static Array<Turtle> turtles;

    public B2WorldCreator (PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        // create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // create ground bodies/fixtures
        for (MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MarioBros.PPM, rect.getHeight() / 2 / MarioBros.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // create pipe bodies/fixtures
        for (MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MarioBros.PPM, rect.getHeight() / 2 / MarioBros.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MarioBros.OBJECT_BIT; // so goomba can identify pipe and turn around upon collision
            body.createFixture(fdef);
        }

        // create coins bodies/fixtures
        for (MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){

            new Coin(screen, object);
        }

        // create bricks bodies/fixtures TO FIX
        for (MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {

            new Brick(screen, object);
        }

        // create all goombas
        goombas = new Array<Goomba>();
        for (MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            goombas.add(new Goomba(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }

        // create all turtles
        turtles = new Array<Turtle>();
        for (MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            turtles.add(new Turtle(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }


    }

    public Array<Goomba> getGoombas() {
        return goombas;
    }

    public static void removeTurtle(Turtle turtle) {
        turtles.removeValue(turtle, true);
    }

    public Array<Enemy> getEnemies() {
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(goombas);
        enemies.addAll(turtles);
        return enemies;
    }
}
