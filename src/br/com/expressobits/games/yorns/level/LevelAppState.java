/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.level;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 *
 * @author Rafael
 */
public class LevelAppState extends AbstractAppState{
  
  SimpleApplication app;
  public static final float WORLD_SIZE_X = 1600;
  public static final float WORLD_SIZE_Z = 1200;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (SimpleApplication)app;
  }
  
  
  
  
  
}
