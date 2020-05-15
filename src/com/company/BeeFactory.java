package com.company;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class BeeFactory implements EntityFactory {

    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        Entity wall = FXGL.entityBuilder()
                .type(BeeType.WALL)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
        return wall;
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        Entity player = FXGL.entityBuilder()
                .from(data)
                .type(BeeType.PLAYER)
                .viewWithBBox(new Rectangle(30,30 , Color.YELLOW))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new PlayerComponent())
                .build();
        return player;
    }

    @Spawns("coin")
    public Entity newCoin(SpawnData data) {
        Entity coin = FXGL.entityBuilder()
                .from(data)
                .type(BeeType.COIN)
                .viewWithBBox(new Circle(data.<Integer>get("width")/2, Color.GOLD))
                .with(new CollidableComponent(true))
                .build();
        return coin;
    }
}
