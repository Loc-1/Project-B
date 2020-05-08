package com.company;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
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

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(600);
        settings.setHeight(600);
        settings.setTitle("Project B");
        settings.setVersion("Θ");
    }

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder().at(300, 300)
                .view(new Rectangle(25,25,Color.YELLOW))
                .buildAndAttach();

    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                player.translateX(5);
                FXGL.getWorldProperties().increment("pixelsMoved", +5);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                player.translateX(-5);
                FXGL.getWorldProperties().increment("pixelsMoved", +5);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                player.translateY(-5);
                FXGL.getWorldProperties().increment("pixelsMoved", +5);
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                player.translateY(5);
                FXGL.getWorldProperties().increment("pixelsMoved", +5);
            }
        }, KeyCode.S);

    }

    @Override
    protected void initUI() {
        Text textPixels = new Text("test lol");
        textPixels.setTranslateX(50);
        textPixels.setTranslateY(100);
        textPixels.textProperty().bind(FXGL.getWorldProperties()
                .intProperty("pixelsMoved").asString());

        FXGL.getGameScene().addUINode(textPixels);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
    }

}