/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Rafael
 */
public class LevelCreator extends AbstractAppState{
  
  SimpleApplication app;
  
  public Node levelNode;
  
  public static String INIMIGOS="Inimigos";
  public static String PLAYER = "Player";
  public static String BULLETS = "Bullets";
  public static String POWERUPS = "Powerups";

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (SimpleApplication)app;
  }
  
  
  
  public void createLevel(){
    //createPlayer();
    createEnemies();
    createBullets();
    createPowerUps();
  }
  
  
  public void createEnemies(){
    
  }
  
  public void createPowerUps(){
    
  }
  
  public void createBullets(){
    
  }
  
  /**
   * 
   * @param name
   * @param radius Raio de tamanho
   * @param color
   * @return 
   */
  public Geometry createGeometry(String name,float radius,ColorRGBA color){
    Sphere sphere = new Sphere(12, 10,radius);
    Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setColor("Color", color);
    Geometry geo = new Geometry(name, sphere);
    geo.setMaterial(mat);
    geo.setUserData("active",false);
    geo.setUserData("radius",radius);
    return geo;
  }
}
