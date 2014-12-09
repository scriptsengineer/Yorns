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
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.Random;

/**
 * Appstate do level do jogo.
 * @author Rafael
 */
public class LevelAppState extends AbstractAppState{

  SimpleApplication app;
  
  private long enemySpawnCooldown;
  private float enemySpawnChance = 300;
  private int level = 0;
  private static int levelQuantidadeMaxEnemies = 40;
  
  private int ENEMY_LIMIT_COOL_DOWN = 100;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (SimpleApplication)app;
  }

  @Override
  public void update(float tpf) {
    super.update(tpf); //To change body of generated methods, choose Tools | Templates.
    if((Boolean)app.getRootNode().getChild(EntidadeAppState.NODEPLAYER).getUserData("alive")){
      spawnEnemies();
    }
  }
  
  private void spawnEnemies() {
    if (System.currentTimeMillis() - enemySpawnCooldown >= ENEMY_LIMIT_COOL_DOWN) {
      enemySpawnCooldown = System.currentTimeMillis();
      if (((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getQuantity() < levelQuantidadeMaxEnemies) {
        int random = new Random().nextInt((int) enemySpawnChance);
        if (level == 0 || level > 3) {
          if (random <= 1) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[0],4,getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).createEnemy(4, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 3) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[0],3,getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).createEnemy(3, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 6) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
                    createEnemy(DataAppState.ENEMYNAMES[0],2,getSpawnpointForEnemy());
            //app.getStateManager().getState(EntidadeAppState.class).createEnemy(2, getSpawnpointForEnemy(), 1, 1000);
          } else if (random <= 12) {
            app.getStateManager().getState(EntidadeAppState.class).app.getStateManager().getState(EntidadeAppState.class).
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
      if (enemySpawnChance <= 301) {
        if (enemySpawnChance <= ENEMY_LIMIT_COOL_DOWN) {
          enemySpawnChance = 300;
          level++;
          ENEMY_LIMIT_COOL_DOWN = ENEMY_LIMIT_COOL_DOWN - 10;
        }
        enemySpawnChance -= 0.5;
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
