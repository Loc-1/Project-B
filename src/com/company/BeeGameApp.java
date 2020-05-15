package com.company;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class BeeGameApp extends GameApplication {

    private static final int TILE_SIZE = 32;

    private Entity player;

    @Override
    protected void initSettings (GameSettings settings) {
        settings.setHeight(TILE_SIZE*20);
        settings.setWidth(TILE_SIZE*20);
        settings.setTitle("Project B");
        settings.setVersion("Θ");
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).left();
            }
            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A);
        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).right();
            }
            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D);
        getInput().addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).down();
            }
            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.S);
        getInput().addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).up();
            }
            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.W);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new BeeFactory());
        int lvlNum = 1 + (int)(Math.random() * ((3-1)+1));
        Level level = setLevelFromMap("tmx/lvl_" + lvlNum +".tmx");

        player = getGameWorld().spawn("player", 288,416);

        Viewport viewport = getGameScene().getViewport();

        viewport.setBounds(-1500, -1500, 1500, 1500);
        viewport.bindToEntity(player, getAppWidth()/2, getAppHeight()/2);
        viewport.setLazy(true);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {

    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0,0);

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(BeeType.PLAYER,BeeType.COIN) {
            @Override
            protected void onCollisionBegin (Entity player, Entity coin) {
                coin.removeFromWorld();
            }
        });
    }

    public static void main (String[] args) {
        launch(args);
    }
}
