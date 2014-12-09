/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.particles;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Rafael
 */
public class ParticleAppState extends AbstractAppState {

  float time;
  private SimpleApplication app;
  private Node particlesExplosion;
  private Node particlesExaust;
  ParticleEmitter particleExplosion;

  public ParticleAppState() {
    particlesExplosion = new Node("particlesExplosion");
    particlesExaust = new Node("particlesExaust");
  }

  @Override
  public void stateDetached(AppStateManager stateManager) {
    super.stateDetached(stateManager); //To change body of generated methods, choose Tools | Templates.
    this.app.getRootNode().detachChild(particlesExplosion);
  }

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (SimpleApplication) app;
    this.app.getRootNode().attachChild(particlesExplosion);
  }

  @Override
  public void update(float tpf) {
    super.update(tpf); //To change body of generated methods, choose Tools | Templates.
    time += tpf;
    for (int i = 0; i < particlesExplosion.getQuantity(); i++) {
      if (particlesExplosion.getChild(i).getUserData("initTime") == null) {
        particlesExplosion.getChild(i).setUserData("initTime", time);
      }
      if (((Float) particlesExplosion.getChild(i).getUserData("lifeTime"))
              < (time - ((Float) particlesExplosion.getChild(i).getUserData("initTime")))) {
        particlesExplosion.detachChild(particlesExplosion.getChild(i));
      } else {
        if (((Float) particlesExplosion.getChild(i).getUserData("lifeTime")) / 9
                < (time - ((Float) particlesExplosion.getChild(i).getUserData("initTime")))) {
          ((ParticleEmitter) particlesExplosion.getChild(i)).setParticlesPerSec(0);
        }
      }
    }


  }

  public void enemyExplosion(ColorRGBA color, Vector3f position, float lifespan, boolean isDied) {

    particlesExplosion.attachChild(createParticleExplosion(color, position, lifespan));
  }

  public void bulletsXbulletsExplosion(Vector3f position, float lifespan) {
    //createParticle(position);
  }

  public void bulletExplosion(Vector3f position) {
    //createParticle(position);
  }

  /**
   * We can get more control over our colors by specifying them in the HSV (hue, saturation, and
   * value) color space. We'd like to pick colors with a random hue but a fixed saturation and
   * value, to make them all look bright and shiny, so we need a helper function that can produce a
   * color from HSV values.
   *
   * @param h
   * @param s
   * @param v
   * @return
   */
  public ColorRGBA hsvToColor(float h, float s, float v) {
    if (h == 0 && s == 0) {
      return new ColorRGBA(v, v, v, 1);
    }

    float c = s * v;
    float x = c * (1 - Math.abs(h % 2 - 1));
    float m = v - c;

    if (h < 1) {
      return new ColorRGBA(c + m, x + m, m, 1);
    } else if (h < 2) {
      return new ColorRGBA(x + m, c + m, m, 1);
    } else if (h < 3) {
      return new ColorRGBA(m, c + m, x + m, 1);
    } else if (h < 4) {
      return new ColorRGBA(m, x + m, c + m, 1);
    } else if (h < 5) {
      return new ColorRGBA(x + m, m, c + m, 1);
    } else {
      return new ColorRGBA(c + m, m, x + m, 1);
    }
  }

  public void playerExplosion(Vector3f position) {
    //createParticle(position);
  }

  public void makeExhaustFire(Vector3f position, float rotation) {
    //createParticle(position);
  }

  public void makeExhaustBullet(Vector3f position, float rotation, ColorRGBA color) {
    //createParticle(position);
  }

  private ParticleEmitter createParticleExplosion(Vector3f position, float life) {
    /**
     * Explosion effect. Uses Texture from jme3-test-data library!
     */
    ParticleEmitter emitter = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 100);
    Material debrisMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
    //debrisMat.setTexture("Texture", app.getAssetManager().loadTexture("Effects/Explosion/Debris.png"));
    emitter.setMaterial(debrisMat);
    emitter.setImagesX(1);
    emitter.setUserData("lifeTime", life);
    emitter.setImagesY(1); // 3x3 texture animation
    emitter.setRotateSpeed(4);
    emitter.setSelectRandomImage(true);
    emitter.getParticleInfluencer().setInitialVelocity(new Vector3f(Math.round(Math.random() * 30) + 5, Math.round(Math.random() * 30) + 5, 0f));
    emitter.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f));   // red
    emitter.setStartColor(new ColorRGBA(1f, 1f, 0f, 1f)); // yellow
    emitter.setStartSize(1f);
    emitter.setEndSize(0.1f);
    emitter.setGravity(0f, 0f, 0f);
    emitter.setLowLife(1f);
    emitter.setParticlesPerSec(1000);
    emitter.setHighLife(2f);
    emitter.getParticleInfluencer().setVelocityVariation(2f);
    emitter.setLocalTranslation(position);
    emitter.setFacingVelocity(true);
    return emitter;
  }

  private ParticleEmitter createParticleExplosion(ColorRGBA color, Vector3f position, float life) {
    /**
     * Explosion effect. Uses Texture from jme3-test-data library!
     */
    ParticleEmitter emitter = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 100);
    Material debrisMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
    //debrisMat.setTexture("Texture", app.getAssetManager().loadTexture("Effects/Explosion/Debris.png"));
    emitter.setMaterial(debrisMat);
    emitter.setImagesX(1);
    emitter.setUserData("lifeTime", life);
    emitter.setImagesY(1); // 3x3 texture animation
    emitter.setRotateSpeed(4);
    emitter.setSelectRandomImage(true);
    emitter.getParticleInfluencer().setInitialVelocity(new Vector3f(Math.round(Math.random() * 30) + 5, Math.round(Math.random() * 30) + 5, 0f));
    emitter.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f));   // red
    emitter.setStartColor(color); // yellow
    emitter.setStartSize(1f);
    emitter.setEndSize(0.1f);
    emitter.setGravity(0f, 0f, 0f);
    emitter.setLowLife(1f);
    emitter.setParticlesPerSec(1000);
    emitter.setHighLife(2f);
    emitter.getParticleInfluencer().setVelocityVariation(2f);
    emitter.setLocalTranslation(position);
    emitter.setFacingVelocity(true);
    return emitter;
  }
}
