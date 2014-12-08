/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades;

import br.com.expressobits.games.yorns.Main;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Rafael
 */
public class FrezzeControl extends AbstractControl{

    long timeStart;
    long time = 5000;
    EntidadeAppState app;
    
    public FrezzeControl(EntidadeAppState app) {
        
        this.app = app;
        app.speed = 1f;
        timeStart = System.currentTimeMillis();
        Main.configureBloom(10f);
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        long timePassed = System.currentTimeMillis() - timeStart;
        if (timePassed > time) {
            app.speed = app.SPEED_NORMAL;
            Main.configureBloom(Main.bloomIntensity);
            spatial.removeControl(FrezzeControl.class);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
}
