package net.superkat.happy.particle.defaults;

import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import org.joml.Vector3f;

public abstract class AbstractColorfulParticle extends SpriteBillboardParticle {
    protected final SpriteProvider spriteProvider;

    public boolean colorTransitionMode = false;
    protected Vector3f startColor;
    protected Vector3f endColor;

    public AbstractColorfulParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, velX, velY, velZ);
        this.spriteProvider = spriteProvider;
//        this.setSpriteForAge(this.spriteProvider);
    }

    public void setColorFromColor(Vector3f color) {
        this.setColor(color.x, color.y, color.z);
    }

    public void setTransitionColors(Vector3f start, Vector3f end) {
        this.colorTransitionMode = true;
        this.startColor = start;
        this.endColor = end;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        if(this.colorTransitionMode) this.updateTransitionColor(tickDelta);
        super.buildGeometry(vertexConsumer, camera, tickDelta);
    }

    public void updateTransitionColor(float tickDelta) {
        float f = (this.age + tickDelta) / (this.maxAge + 1.0F);
        Vector3f vector3f = new Vector3f(this.startColor).lerp(this.endColor, f);
        this.red = vector3f.x();
        this.green = vector3f.y();
        this.blue = vector3f.z();
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
}
