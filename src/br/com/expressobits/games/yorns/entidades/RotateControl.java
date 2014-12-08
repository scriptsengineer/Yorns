/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Rafael
 */
public class RotateControl extends AbstractControl{

  private float rotatez;
  
  public RotateControl(float rotate) {
    this.rotatez = rotate;
  }

  
  
  @Override
  protected void controlUpdate(float tpf) {
    spatial.rotate(0f, 0f, tpf/80*rotatez*(EntidadeAppState.speed));
  }

  @Override
  protected void controlRender(RenderManager rm, ViewPort vp) {
  }
  
}
