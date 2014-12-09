/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.ui.Picture;

/**
 *
 * @author Rafael
 */
public class SpecialControl extends AbstractControl{
    
    public SpecialControl(int tipo) {
        spatial.setUserData("tipo",tipo);
    }

    @Override
    protected void controlUpdate(float tpf) {
           Node spatialNode = (Node) spatial;
            Picture pic = (Picture) spatialNode.getChild("SpecialBullet"+spatial.getUserData("tipo"));
            
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
