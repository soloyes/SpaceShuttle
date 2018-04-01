package xyz.shuttle.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import xyz.shuttle.game.screens.GameScreen;
import xyz.shuttle.game.screens.MenuScreen;
import xyz.shuttle.game.screens.LoadingScreen;

/**
 * Created by FlameXander on 12.03.2018.
 */
public class ScreenManager {
    private static final ScreenManager ourInstance = new ScreenManager();
    private CoreGame game;
    private SpriteBatch batch;
    private Viewport viewport;
    private Camera camera;
    private GameScreen gameScreen;
    private LoadingScreen loadingScreen;
    private MenuScreen menuScreen;
    private Screen targetScreen;
    private ScreenManager() {
    }

    public void dispose() {
        Assets.getInstance().getAssetManager().dispose();
    }

    public void goToTarget() {
        game.setScreen(targetScreen);
    }

    public void init(CoreGame game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
        this.camera = new OrthographicCamera(1080, 1920);
        this.viewport = new FitViewport(1080, 1920, camera);
        this.viewport.apply();
        this.loadingScreen = new LoadingScreen(batch);
        this.gameScreen = new GameScreen(batch, camera);
        this.menuScreen = new MenuScreen(batch);
    }

    public void onResize(int width, int height) {
        viewport.update(width, height, true);
        viewport.apply();
    }

    public void switchScreen(ScreenType type) {
        Screen screen = game.getScreen();
        Assets.getInstance().clear();
        if (screen != null) {
            screen.dispose();
        }
        //resetCamera();
        switch (type) {
            case MENU:
                game.setScreen(loadingScreen);
                targetScreen = menuScreen;
                Assets.getInstance().loadAssets(ScreenType.MENU);
                break;
            case GAME:
                game.setScreen(loadingScreen);
                targetScreen = gameScreen;
                Assets.getInstance().loadAssets(ScreenType.GAME);
                break;
        }
    }

    public void resetCamera() {
        camera.position.set(480, 640, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public static ScreenManager getInstance() {
        return ourInstance;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public Camera getCamera() {
        return camera;
    }

    public enum ScreenType {
        MENU, GAME
    }
}
