package com.company;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class PlayerComponent extends Component {

    private PhysicsComponent physics;

    @Override
    public void onUpdate(double tpf) {

    }

    public void left() {
        physics.setVelocityX(-32);
    }

    public void right() {
        physics.setVelocityX(32);
    }

    public void down() {
        physics.setVelocityY(32);
    }

    public void up() {
        physics.setVelocityY(-32);
    }

    public void stop() {
        physics.setVelocityX(0);
        physics.setVelocityY(0);
    }
}
