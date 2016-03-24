package com.shiping.mariobros.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.shiping.mariobros.MarioBros;
import com.shiping.mariobros.Scenes.Hud;
import com.shiping.mariobros.Screens.PlayScreen;
import com.shiping.mariobros.Sprites.Mario;

/**
 * Created by shiping on 29/2/16.
 */
public class Brick extends com.shiping.mariobros.Sprites.TileObjects.InteractiveTileObject {
    public Brick(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Mario mario) {
        if (mario.isBig()) {
            setCategoryFilter(MarioBros.DESTROYED_BIT);
            // DESTROYED_BIT is not one of the masks that Mario can collide with
            getCell().setTile(null);    // remove the brick from the map
            Hud.addScore(200);
            MarioBros.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
        } else {
            MarioBros.manager.get("audio/sounds/bump.wav", Sound.class).play();
        }
        Gdx.app.log("Brick", "Collision");

    }
}
