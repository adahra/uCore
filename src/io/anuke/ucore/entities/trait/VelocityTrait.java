package io.anuke.ucore.entities.trait;

import com.badlogic.gdx.math.Vector2;
import io.anuke.ucore.core.Timers;

public interface VelocityTrait extends MutPosTrait{
    Vector2 getVelocity();

    default void updateVelocity(float drag){
        if(this instanceof SolidTrait){
            ((SolidTrait) this).move(getVelocity().x * Timers.delta(), getVelocity().y * Timers.delta());
        }else{
            moveBy(getVelocity().x * Timers.delta(), getVelocity().y * Timers.delta());
        }
        getVelocity().scl(1f - drag * Timers.delta());
    }
}
