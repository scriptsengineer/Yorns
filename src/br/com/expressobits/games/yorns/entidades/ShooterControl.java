/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Rafael
 */
public class ShooterControl extends AbstractControl{

    Spatial player;
    long lastTimeShoot;
    long delayTimeShoot;
    EntidadeAppState app;

    public ShooterControl(Spatial player,int nivel,EntidadeAppState app) {
        this.player = player;
        delayTimeShoot = (long)10000f/nivel;
        lastTimeShoot = System.currentTimeMillis();
        this.app = app;
    }
    
    
    @Override
    protected void controlUpdate(float tpf) {
        if(System.currentTimeMillis()-lastTimeShoot>delayTimeShoot/(EntidadeAppState.speed/2)){
            app.shootBulletEnemy(spatial);
            lastTimeShoot = System.currentTimeMillis();
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
}
