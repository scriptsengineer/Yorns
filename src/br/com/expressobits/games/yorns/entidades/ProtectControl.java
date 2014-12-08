/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades;

import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.ui.Picture;

/**
 *
 * @author Rafael
 */
public class ProtectControl extends AbstractControl {

    long spawnTime;
    long time = 4000;
    String resource;

    public ProtectControl(String resource) {

        spawnTime = System.currentTimeMillis();
        this.resource = resource;
    }

    @Override
    protected void controlUpdate(float tpf) {
        long timePassed = System.currentTimeMillis() - spawnTime;
        if (timePassed % 4 == 0) {
            if ((timePassed / 4) % 2==0) {
                ColorRGBA color = new ColorRGBA(1, 1, 1,0.1f);
                //Node spatialNode = (Node) spatial;
               // Picture pic = (Picture) spatialNode.getChild(resource);
               // pic.getMaterial().setColor("Color", color);
            } else {
                ColorRGBA color = new ColorRGBA(1, 1, 1,0.9f);
                //Node spatialNode = (Node) spatial;
                //Picture pic = (Picture) spatialNode.getChild(resource);
               // pic.getMaterial().setColor("Color", color);
            }
        }

        if (timePassed > time) {
            ColorRGBA color = new ColorRGBA(1, 1, 1,1f);
                //Node spatialNode = (Node) spatial;
               // Picture pic = (Picture) spatialNode.getChild(resource);
                //pic.getMaterial().setColor("Color", color);
            spatial.removeControl(ProtectControl.class);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
