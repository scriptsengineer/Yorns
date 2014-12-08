/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades.warp;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Rafael
 */
public class AdditionalLineControl extends AbstractControl {
    private PointMass end11, end12, end21, end22;
    float var;
 
    public AdditionalLineControl(PointMass end11, PointMass end12, PointMass end21, PointMass end22,float var) {
        this.end11 = end11;
        this.end12 = end12;
        this.end21 = end21;
        this.end22 = end22;
        this.var = var;
    }
 
    @Override
    protected void controlUpdate(float tpf) {
        //movement
        spatial.setLocalTranslation(position1());
 
        //scale
        Vector3f dif = position2().subtract(position1());
        spatial.setLocalScale(dif.length());
 
        //rotation
        spatial.lookAt(position2(),new Vector3f(1,0,0));
 
    }
 
    private Vector3f position1() {
        return new Vector3f().interpolate(end11.getPosition(),end12.getPosition(),var);
    }
 
    private Vector3f position2() {
        return new Vector3f().interpolate(end21.getPosition(),end22.getPosition(),var);
    }
 
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
 
    }
}