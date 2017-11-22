package com.harrynguon.platformer.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.reflect.Constructor;

/**
 * This class stores all of the assets in the game. This includes the images, levels, music,
 * sound fx, etc.
 *
 * Created by Harry on 20/11/2017.
 */

public class Assets implements Disposable, AssetErrorListener {

    private static final Logger log = new Logger(Assets.class.getName(), Logger.DEBUG);
    public static final Assets instance = new Assets();

    public PlayerAssets playerAssets;
    public SoundAssets soundAssets;

    /**
     * The instance of the asset manager to be parsed around (it will be contained inside the
     * Game instance.
     */
    private AssetManager assetManager;


    /**
     * Construct an asset manager instance
     */
    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.load(Constants.PLAYER_ATLAS, TextureAtlas.class);
        assetManager.load(Constants.MAIN_MENU_MUSIC, Music.class);
        assetManager.load(Constants.BUTTON_SOUND, Sound.class);
        assetManager.finishLoading();

        playerAssets = new PlayerAssets((TextureAtlas) assetManager.get(Constants.PLAYER_ATLAS));
        soundAssets = new SoundAssets();
    }

    /**
     * This method unloads the assets for the current screen.
     */
    public void unloadAssets() {
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        log.error("Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {

    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public class PlayerAssets {

        public final TextureRegion stand;
        public final TextureRegion jump;
        public final Animation<TextureRegion> walk;

        public PlayerAssets(TextureAtlas atlas) {
            stand = new TextureRegion(atlas.findRegion("alienGreen_stand"));
            jump = new TextureRegion(atlas.findRegion("alienGreen_jump"));
            Array<TextureRegion> walkAnimations = new Array<TextureRegion>();
            walkAnimations.add(new TextureRegion(atlas.findRegion("alienGreen_walk1")));
            walkAnimations.add(new TextureRegion(atlas.findRegion("alienGreen_walk2")));
            walk = new Animation<TextureRegion>(0.1f, walkAnimations, Animation.PlayMode.LOOP);
        }
    }

    public class SoundAssets {

        public final Music mainMenu;
        public final Sound btnSound;

        public SoundAssets() {
            mainMenu = assetManager.get(Constants.MAIN_MENU_MUSIC);
            mainMenu.setLooping(true);
            btnSound = assetManager.get(Constants.BUTTON_SOUND);
        }
    }
}