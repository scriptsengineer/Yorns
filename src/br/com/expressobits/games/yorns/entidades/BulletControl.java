/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades;

import br.com.expressobits.games.yorns.GameAppState;
import br.com.expressobits.games.yorns.entidades.warp.Grid;
import br.com.expressobits.games.yorns.particles.ParticleAppState;
import br.com.expressobits.games.yorns.settings.SettingAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Rafael
 */
public class BulletControl extends AbstractControl{
    private int screenWidth, screenHeight;
    private float speed;
    public Vector3f direction;
    private float rotation;
    private ParticleAppState particleAppState;
    private ColorRGBA exaustColor;
    private Grid grid;

    public BulletControl(Vector3f direction, int screenWidth, int screenHeight, ParticleAppState particleAppState) {
        this.direction = direction;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.particleAppState = particleAppState;
    }

  @Override
  public void setSpatial(Spatial spatial) {
    this.speed = spatial.getUserData("speed");
    this.exaustColor = spatial.getUserData("color");
    super.setSpatial(spatial); //To change body of generated methods, choose Tools | Templates.
  }
    
    

    @Override
    protected void controlUpdate(float tpf) {
//        movement
        spatial.move(direction.mult(speed*(EntidadeAppState.speed) * tpf/20));
        
//        rotation
        float actualRotation = GameAppState.getAngleFromVector(direction);
        if (actualRotation != rotation) {
            spatial.rotate(0, 0, actualRotation - rotation);
            rotation = actualRotation;
        }
        //IMPLEMENTAR
        //particleAppState.makeExhaustBullet(spatial.getLocalTranslation(), rotation,exaustColor);
//        check boundaries
        Vector3f loc = spatial.getLocalTranslation();
        if (loc.x > screenWidth
                || loc.y > screenHeight
                || loc.x < 0
                || loc.y < 0) {
            spatial.removeFromParent();
        }
        if(GameAppState.gameWarp){
          grid.applyExplosiveForce(direction.length()*(18f), spatial.getLocalTranslation(), 200);
        }
        

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
}
