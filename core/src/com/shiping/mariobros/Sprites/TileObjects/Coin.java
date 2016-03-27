package com.shiping.mariobros.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.shiping.mariobros.MarioBros;
import com.shiping.mariobros.Scenes.Hud;
import com.shiping.mariobros.Screens.PlayScreen;
import com.shiping.mariobros.Sprites.Items.ItemDef;
import com.shiping.mariobros.Sprites.Items.Mushroom;
import com.shiping.mariobros.Sprites.Mario;

/**
 * Created by shiping on 29/2/16.
 */
public class Coin extends com.shiping.mariobros.Sprites.TileObjects.InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28; // Blank coin ID is 27. but for libgdx, add 1

    public Coin (PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.COIN_BIT);
    }

    @Override
    public void onHeadHit(Mario mario) {
        Gdx.app.log("Coin", "Collision");
        if (getCell().getTile().getId() == BLANK_COIN) {
            MarioBros.manager.get("audio/sounds/bump.wav", Sound.class).play();
        } else {
            getCell().setTile(tileSet.getTile(BLANK_COIN));
            // Some tiles contain the key "mushroom" so mushrooms will be spawned when hit
            if (object.getProperties().containsKey("mushroom")) {
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / MarioBros.PPM),
                        Mushroom.class));
                MarioBros.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            } else {
                MarioBros.manager.get("audio/sounds/coin.wav", Sound.class).play();
            }

            Hud.addScore(100);
        }
    }
}
