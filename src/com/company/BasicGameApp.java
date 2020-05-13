package com.company;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.*;

public class BasicGameApp extends GameApplication {

    @Override
    protected void initSettings (GameSettings settings) {
        settings.setWidth(8*30);
        settings.setHeight(8*30);
        settings.setTitle("Project B");
        settings.setVersion("Θ");
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Entity player;
    private Entity money;
    public enum EntityType {
        PLAYER, MONEY
    }

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder()
                .type(EntityType.PLAYER)
                .at(300,300)
                //.view(new Rectangle(25,25,Color.YELLOW))
                .viewWithBBox("bee-player.png")
                .with(new CollidableComponent(true))
                .buildAndAttach();

        player.setScaleX(0.5);
        player.setScaleY(0.5);
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                player.translateX(5);
                //FXGL.getWorldProperties().increment("pixelsMoved", +5);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                player.translateX(-5);
                //FXGL.getWorldProperties().increment("pixelsMoved", +5);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                player.translateY(-5);
                //FXGL.getWorldProperties().increment("pixelsMoved", +5);
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                player.translateY(5);
                //FXGL.getWorldProperties().increment("pixelsMoved", +5);
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
        textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("moneyCollected").asString());

        var honeycombTexture = FXGL.getAssetLoader().loadTexture("honeycomb.png");
        honeycombTexture.setTranslateX(50);
        honeycombTexture.setTranslateY(250);
        FXGL.getGameScene().addUINode(honeycombTexture);

        honeycombTexture.setScaleX(0.2);
        honeycombTexture.setScaleY(0.2);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        //vars.put("pixelsMoved", 0);
        vars.put("moneyCollected", 0);
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.MONEY) {

            @Override
            protected void onCollisionBegin(Entity player, Entity money) {
                FXGL.play("money.wav");
                FXGL.getWorldProperties().increment("moneyCollected", +1);
            }
        });

    }
}