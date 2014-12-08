/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades;

import br.com.expressobits.games.yorns.GameAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.ui.Picture;
import java.util.Random;

/**
 *
 * @author Rafael
 */
public class WandererMoveControl extends AbstractControl{
    private int screenWidth, screenHeight;
    private Vector3f velocity;
    private float directionAngle;
    private long spawnTime;
    private long timeSpawn;

    public WandererMoveControl(int screenWidth, int screenHeight,long timeSpawn) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        velocity = new Vector3f();
        directionAngle = new Random().nextFloat() * FastMath.PI * 2f;
        spawnTime = System.currentTimeMillis();
        this.timeSpawn = timeSpawn;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if ((Boolean) spatial.getUserData("active")) {
            // translate the wanderer

            // change the directionAngle a bit
            directionAngle += (new Random().nextFloat() * 20f - 10f) * tpf;
            Vector3f directionVector = GameAppState.getVectorFromAngle(directionAngle);
            directionVector.multLocal(1000f);
            velocity.addLocal(directionVector);

            // decrease the velocity a bit and move the wanderer
            velocity.multLocal(0.2f);
            spatial.move(velocity.mult(tpf * ((Float)spatial.getUserData("speed"))/100 * EntidadeAppState.speed));

            // make the wanderer bounce off the screen borders
            Vector3f loc = spatial.getLocalTranslation();
            if (loc.x > screenWidth || loc.y > screenHeight ||
                    loc.x < 0 || loc.y < 0){
                Vector3f newDirectionVector = new Vector3f(screenWidth / 2, screenHeight / 2, 0).subtract(loc);
                directionAngle = GameAppState.getAngleFromVector(newDirectionVector);
            }

            // rotate the wanderer
            //spatial.rotate(0, 0, tpf * 2);
        } else {
            // handle the "active"-status
            long dif = System.currentTimeMillis() - spawnTime;
            if (dif >= timeSpawn) {
                spatial.setUserData("active", true);
            }
            
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
