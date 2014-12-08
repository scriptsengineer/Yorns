/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades;

import br.com.expressobits.games.yorns.GameAppState;
import br.com.expressobits.games.yorns.particles.ParticleAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.math.FastMath;
import com.jme3.math.Plane;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Rafael
 */
public class PlayerControl extends AbstractControl {

  private int screenWidth, screenHeight;
  //o player está se mechendo??
  public boolean up, down, left, right;
  //speed of the player
  private float speed = 500f;
  //lastRotation of the player
  private float lastRotation = 0;
  private SimpleApplication app;
  public Vector3f localRotation;

  public PlayerControl(SimpleApplication app, int width, int height, float speed, ParticleAppState particleAppState) {
    this.screenWidth = width;
    this.screenHeight = height;
    this.speed = speed;
    this.app = app;
  }

  @Override
  protected void controlUpdate(float tpf) {

    Vector2f mousePositionScreen = app.getInputManager().getCursorPosition();
    mousePositionScreen = GameAppState.getMouseCorrectAim(mousePositionScreen);
    Vector3f mousePosition3d = app.getCamera().getWorldCoordinates(mousePositionScreen, 0).clone();
    Vector3f dir = app.getCamera().getWorldCoordinates(mousePositionScreen, 1f).subtractLocal(mousePosition3d).normalizeLocal();
    Ray ray = new Ray(mousePosition3d, dir);
    Plane plane = new Plane(Vector3f.UNIT_Z, 0);
    Vector3f mousePositionWorld = new Vector3f();
    ray.intersectsWherePlane(plane, mousePositionWorld);
    mousePositionWorld.z = 0;

    Quaternion rotation = new Quaternion();
    rotation.lookAt(mousePositionWorld.subtract(spatial.getLocalTranslation()), Vector3f.UNIT_Z);
    spatial.setLocalRotation(rotation);
    localRotation =mousePositionWorld.subtract(spatial.getLocalTranslation());
    spatial.setUserData("angle",localRotation.normalizeLocal());
    //Move o jogador em certa direção
    //Se ele não está fora da tela
    if (up) {
      if (spatial.getLocalTranslation().y < screenHeight - (Float) spatial.getUserData("radius")) {
         spatial.move(0, tpf * +speed, 0);
      }
      //spatial.rotate(0, 0, -lastRotation + FastMath.PI / 2);
      lastRotation = FastMath.PI / 2;
    }
    if (down) {
      if (spatial.getLocalTranslation().y > (Float) spatial.getUserData("radius")) {
        spatial.move(0, tpf * -speed, 0);
      }
      //spatial.rotate(0, 0, -lastRotation + FastMath.PI * 1.5f);
      lastRotation = FastMath.PI * 1.5f;
    }
    if (left) {
      if (spatial.getLocalTranslation().x > (Float) spatial.getUserData("radius")) {
        spatial.move(tpf * -speed, 0, 0);
      }
      //spatial.rotate(0, 0, -lastRotation + FastMath.PI);
      lastRotation = FastMath.PI;
    }
    if (right) {
      if (spatial.getLocalTranslation().x < screenWidth - (Float) spatial.getUserData("radius")) {
        spatial.move(tpf * speed, 0, 0);
      }
      //spatial.rotate(0, 0, -lastRotation + 0);
      lastRotation = 0;
    }
    try {
      if (up || down || left || right) {
      }
    } catch (NullPointerException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  protected void controlRender(RenderManager rm, ViewPort vp) {
  }

  /**
   * Reseta valores de movimento
   */
  public void reset() {
    up = false;
    down = false;
    left = false;
    right = false;
  }
}
