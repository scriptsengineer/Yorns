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
public class LineControl extends AbstractControl {
    private PointMass end1, end2;
 
    public LineControl(PointMass end1, PointMass end2) {
        this.end1 = end1;
        this.end2 = end2;
    }
 
    @Override
    protected void controlUpdate(float tpf) {
        //movement
        spatial.setLocalTranslation(end1.getPosition());
 
        //scale
        Vector3f dif = end2.getPosition().subtract(end1.getPosition());
        spatial.setLocalScale(dif.length());
 
        //rotation
        spatial.lookAt(end2.getPosition(),new Vector3f(1,0,0));
 
    }
 
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
 
    }
}