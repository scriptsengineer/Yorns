/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades;

import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.ui.Picture;

/**
 *
 * @author Rafael
 */
public class SeekerMoveControl extends AbstractControl{
    private Spatial player;
    private Vector3f velocity;
    private long spawnTime;
    
    public SeekerMoveControl(Spatial player) {
        this.player = player;
        velocity = new Vector3f(0,0,0);
        spawnTime = System.currentTimeMillis();
    }

    @Override
    protected void controlUpdate(float tpf) {
        if((Boolean)spatial.getUserData("active")){
            //Translate the Seeker
            Vector3f playerDirection = player.getLocalTranslation().subtract(spatial.getLocalTranslation());
            playerDirection.normalizeLocal();
            playerDirection.multLocal(1000f);
            velocity.addLocal(playerDirection);
            velocity.multLocal(0.2f);
            spatial.move(velocity.mult(tpf/20* EntidadeAppState.speed));
            
            //rotate seeker
            if(velocity != Vector3f.ZERO){
                spatial.rotateUpTo(velocity.normalize());
                spatial.rotate(0, 0, FastMath.PI/2);
            }
        }else{
            //no active
            long dif = System.currentTimeMillis() - spawnTime;
            if(dif >= 2000f){
                
            }
            spatial.setUserData("active",true);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
}
