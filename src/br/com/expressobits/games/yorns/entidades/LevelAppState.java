/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades;

import br.com.expressobits.games.yorns.GameAppState;
import br.com.expressobits.games.yorns.file.DataAppState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.Random;

/**
 * Appstate do level do jogo.
 * @author Rafael
 */
public class LevelAppState extends AbstractAppState{

  SimpleApplication app;
  
  /**
   * Tempo entre spawn de inimigos
   */
  private static final int ENEMY_LIMIT_COOL_DOWN = 25;
  /**
   * Último tempo que foi spawn inimigos
   */
  private long lastEnemySpawnTime;
  /**
   * Chance <b>inicial</b> de spawnar novos inimigos.
   */
  private static final int CHANCESPAWN_INICIAL = 50;
  /**
   * Chance atual de spawnar inimigos.
   */
  private float spawnChanceEnemy = 50;
  /**
   * Level atual.
   */
  private int level = 0;
  /**
   * Número <b>máximo</b> de inimigos que podem ter na tela.
   */
  private static final int NUMERO_MAXIMO_DE_INIMIGOS = 50;
  
  
  private static int SIZEBLOCK = 10;
  private static int SIZEX = 80;
  private static int SIZEY = 60;
  
  Node nodeLevel = new Node();
  
  
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (SimpleApplication)app;
        /** A white ambient light source. */ 
    AmbientLight ambient = new AmbientLight();
    ambient.setColor(ColorRGBA.White);
    this.app.getRootNode().addLight(ambient);
    
    
  }

  @Override
  public void update(float tpf) {
    super.update(tpf); //To change body of generated methods, choose Tools | Templates.
    if((Boolean)app.getRootNode().getChild(EntidadeAppState.NODEPLAYER).getUserData("alive")){
      spawnEnemies();
    }
  }
  
  private void createLevel(){
    for (int i = 0; i <= SIZEX; i++) {
      for (int j = 0; j <= SIZEY; j++) {
        if(i==0){
          Spatial sp = createBasicBlock();
          sp.setLocalTranslation((i-1)*SIZEBLOCK, j*SIZEBLOCK, 0);
          nodeLevel.attachChild(sp);
        }
        if(i==SIZEX){
          Spatial sp = createBasicBlock();
          sp.setLocalTranslation((i+1)*SIZEBLOCK, j*SIZEBLOCK, 0);
          nodeLevel.attachChild(sp);
        }
        if(j==0){
          Spatial sp = createBasicBlock();
          sp.setLocalTranslation(i*SIZEBLOCK, (j-1)*SIZEBLOCK, 0);
          nodeLevel.attachChild(sp);
        }
        if(j==SIZEY){
          Spatial sp = createBasicBlock();
          sp.setLocalTranslation(i*SIZEBLOCK, (j+1)*SIZEBLOCK, 0);
          nodeLevel.attachChild(sp);
        }
      }
    }
  }
  
  private Spatial createBasicBlock(){
    
    Box boxMesh = new Box(SIZEBLOCK-6,SIZEBLOCK-6,SIZEBLOCK-6); 
    Geometry boxGeo = new Geometry("Colored Box", boxMesh); 
    Material boxMat = new Material(app.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md"); 
    boxMat.setBoolean("UseMaterialColors", true); 
    boxMat.setColor("Ambient", ColorRGBA.Blue); 
    boxMat.setColor("Diffuse", ColorRGBA.Blue); 
    boxGeo.setMaterial(boxMat); 
    return boxGeo;
  }
  
  private void spawnEnemies() {
    
    
    if (System.currentTimeMillis() - lastEnemySpawnTime >= ENEMY_LIMIT_COOL_DOWN) {
      lastEnemySpawnTime = System.currentTimeMillis();
      
      if (((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getQuantity() < NUMERO_MAXIMO_DE_INIMIGOS) {
        int random = new Random().nextInt((int) spawnChanceEnemy);
        
        if (nodeLevel.getChild(0).getUserData("GreenEnemy")!=null) {
          if((Integer)nodeLevel.getChild(0).getUserData("GreenEnemy")<=1){
            app.getStateManager().getState(EntidadeAppState.class).
                    app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[0],4,getSpawnpointForEnemy());
          }
          if (random <= 1) {
            
            //app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).createEnemy(4, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 3) {
            app.getStateManager().getState(EntidadeAppState.class).
                    app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[0],3,getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).createEnemy(3, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 6) {
            app.getStateManager().getState(EntidadeAppState.class).
                    app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[0],2,getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).createEnemy(2, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 12) {
            app.getStateManager().getState(EntidadeAppState.class).
                    app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[0],1,getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).createEnemy(1, getSpawnpointForEnemy(), 1, 1000);
          }
        }
        if (level == 1 || level > 4) {
          if (random <= 1) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[1],4,getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).createEnemy(4, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 3) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[1],3,getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).createEnemy(3, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 6) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[1],2,getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).createEnemy(2, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 12) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[1],1,getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).createEnemy(1, getSpawnpointForEnemy(), 1, 1000);
          }
        }

        if (level == 2 || level > 5) {
          if (random <= 1) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[2],4,getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).createEnemy(4, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 3) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[2],3,getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).createEnemy(3, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 6) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[2],2,getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).createEnemy(2, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 12) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[2],1,getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).createEnemy(1, getSpawnpointForEnemy(), 1, 1000);
          }
        }
        if (level == 3 || level > 6) {
          if (random <= 1) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[3], 4, getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).createEnemy(4, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 3) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[3], 3, getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).createEnemy(3, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 6) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[3], 2, getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).createEnemy(2, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 12) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[3], 1, getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).createEnemy(1, getSpawnpointForEnemy(), 1, 1000);
          }
        }
      }
      //Increase Spawn Time
      if (spawnChanceEnemy <= 51) {
        if (spawnChanceEnemy <= ENEMY_LIMIT_COOL_DOWN) {
          spawnChanceEnemy = 51;
          level++;
          nodeLevel = app.getStateManager().getState(DataAppState.class).getLevel(level);
          createLevel();
          
        }
        spawnChanceEnemy -= 0.05;
      }
    }
  }
  
  /**
   * Retorna um lugar aleatório para inimigo nascer, sambendo que 
   * nasce longe do inimigo.
   * @return Localização para nascer o inimigo aleatória.
   */
  private Vector3f getSpawnpointForEnemy() {
    Vector3f pos;
    do {
      pos = new Vector3f(new Random().nextInt(GameAppState.width),
              new Random().nextInt(GameAppState.height)
              ,0);
    } while (pos.distanceSquared(app.getRootNode().getChild(EntidadeAppState.NODEPLAYER).getLocalTranslation()) < 32000);
    return pos;
  }

  public int getLevel() {
    return level+1;
  }
  
  
  
}
