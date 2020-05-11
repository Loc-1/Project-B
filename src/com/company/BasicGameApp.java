package com.company;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.handlers.CollectibleHandler;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.Map;

public class BasicGameApp extends GameApplication {

    private Entity player;

    public static void main(String[] args) {
        launch(args);
    }

    public enum EntityType {
        PLAYER, MONEY
    }

    @Override
    protected void initSettings (GameSettings settings) {
        settings.setWidth(600);
        settings.setHeight(600);
        settings.setTitle("Project B");
        settings.setVersion("Θ");
    }

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder()
                .type(EntityType.PLAYER)
                .at(300,300)
                .viewWithBBox("bee-player.png")
                .with(new CollidableComponent(true))
                .buildAndAttach();
        player.setScaleX(0.5);
        player.setScaleY(0.5);

        FXGL.entityBuilder()
                .type(EntityType.MONEY)
                .at(500, 200)
                .viewWithBBox("money.png")
                .with(new CollidableComponent(true))
                .buildAndAttach();
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                player.translateX(5);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                player.translateX(-5);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                player.translateY(-5);
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                player.translateY(5);
            }
        }, KeyCode.S);

        input.addAction(new UserAction("Play Sound") {
            @Override
            protected void onActionBegin() {
                FXGL.play("buzz.wav");
            }
        }, KeyCode.F);

    }

    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50);
        textPixels.setTranslateY(100);

        FXGL.getGameScene().addUINode(textPixels);
        textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("pixelsMoved").asString());

        var honeycomb = FXGL.getAssetLoader().loadTexture("honeycomb.png");
        honeycomb.setTranslateX(50);
        honeycomb.setTranslateY(250);
        honeycomb.setScaleX(0.2);
        honeycomb.setScaleY(0.2);

        FXGL.getGameScene().addUINode(honeycomb);
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.MONEY) {
            @Override
            protected void onCollisionBegin(Entity player, Entity money) {
                FXGL.play("money.wav");
                FXGL.getWorldProperties().increment("pixelsMoved", +1);

                double randX = Math.random();
                double randY = Math.random();
                money.setPosition((randX*566), (randY*566));
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
    }
}
