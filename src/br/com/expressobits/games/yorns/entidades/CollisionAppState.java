/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades;

import br.com.expressobits.games.yorns.GameAppState;
import br.com.expressobits.games.yorns.Main;
import br.com.expressobits.games.yorns.audio.AudioAppState;
import br.com.expressobits.games.yorns.file.DataAppState;
import br.com.expressobits.games.yorns.gj.GameJoltAppState;
import br.com.expressobits.games.yorns.menu.HUDScreenAppState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Random;

/**
 *
 * @author Rafael
 */
public class CollisionAppState extends AbstractAppState {

  EntidadeAppState entidadeAppState;
  SimpleApplication app;
  //ACHIEVED MANAGER
  protected int killsGreen = 0;
  protected int killsRed = 0;
  protected int killsYellow = 0;
  protected int killsBlue = 0;
  
  public long lastEnemyDown = System.currentTimeMillis();
  public static final long ENEMY_LIMIT_COOL_DOWN = 2000;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (SimpleApplication)app;
    this.entidadeAppState = this.app.getStateManager().getState(EntidadeAppState.class);
  }

  @Override
  public void update(float tpf) {
    super.update(tpf); //To change body of generated methods, choose Tools | Templates.
    handleCollisions();
  }

  private void handleCollisions() {
    handleCollisionsEnemyPlayer();
    handleCollisionsEnemyBullet();
    handleCollisionsPlayerSpecial();
    handleCollisionsPlayerBulletenemy();
    handleCollisionsBulletPlayerBulletenemy();
  }

  private void handleCollisionsEnemyPlayer() {
    // should the player die?
    for (int i = 0; i < ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getQuantity(); i++) {
      if ((Boolean)((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getUserData("active")) {
        if (checkCollision(((Node)app.getRootNode().getChild(EntidadeAppState.NODEPLAYER)), ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i))) {
          ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).detachChildAt(i);
          app.getStateManager().getState(EntidadeAppState.class).killPlayer();
        }
      }
    }
  }

  private boolean checkCollision(Spatial a, Spatial b) {
    
    float distance = a.getWorldTranslation().distance(b.getWorldTranslation());
    float maxDistance = (Float)a.getUserData("radius") + (Float)b.getUserData("radius");
    return distance <= maxDistance;
    
    
  }

  private void handleCollisionsEnemyBullet() {
    //should an enemy die?
    int i = 0;
    while (i < ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getQuantity()) {
      int j = 0;
      while (j < ((Node)app.getRootNode().getChild(EntidadeAppState.NODEBULLETSPLAYER)).getQuantity()) {
        if (checkCollision(((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i), ((Node)app.getRootNode().getChild(EntidadeAppState.NODEBULLETSPLAYER)).getChild(j))) {
          ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).setUserData("life",
                  (Integer) ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getUserData("life") - 1);

          if ((Integer) ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getUserData("life") == 0) {
            if (((Integer) ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getUserData("size")) > 1) {
              entidadeAppState.createEnemy(
                      DataAppState.ENEMYNAMES[((Integer) ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getUserData("subtype"))],
                      ((Integer) ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getUserData("size")) - 1,
                      ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getWorldTranslation());
              entidadeAppState.createEnemy(
                      DataAppState.ENEMYNAMES[((Integer) ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getUserData("subtype"))],
                      ((Integer) ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getUserData("size")) - 1,
                      ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getWorldTranslation());
            }
            int random = new Random().nextInt(300);
            if (random < 1) {
              entidadeAppState.createSpecial(1, ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getWorldTranslation(), 1);
            } else if (random < 3) {
              entidadeAppState.createSpecial(1, ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getWorldTranslation(), 2);
            } else if (random < 6) {
              entidadeAppState.createSpecial(1, ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getWorldTranslation(), 3);
            } else if (random < 7) {
              entidadeAppState.createSpecial(1, ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getWorldTranslation(), 4);
            }
            if (System.currentTimeMillis() - lastEnemyDown < ENEMY_LIMIT_COOL_DOWN) {

              if (entidadeAppState.multiplicar < EntidadeAppState.multLIMIT) {
                entidadeAppState.multiplicar++;
              }
            } else {
              entidadeAppState.multiplicar = 1;
            }
            int p = (Integer) ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getUserData("size") * entidadeAppState.multiplicar;
            app.getRootNode().getChild(EntidadeAppState.NODEPLAYER).setUserData("score", (Integer) app.getRootNode().getChild(EntidadeAppState.NODEPLAYER).getUserData("score") + p);
            entidadeAppState.particleAppState.enemyExplosion(
                    (ColorRGBA) ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getUserData("color"),
                    ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getWorldTranslation(),
                    1,
                    true);
            switch ((Integer) ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getUserData("subtype")) {
              case 1:
                killsGreen++;
                break;
              case 2:
                killsRed++;
                break;
              case 3:
                killsYellow++;
                break;
              case 4:
                killsBlue++;
                break;
            }

            if (GameJoltAppState.logged || Main.debug) {
              //ACHIEVED

              GameJoltAppState api = entidadeAppState.app.getStateManager().getState(GameJoltAppState.class);

              if (killsGreen == 25) {
                
                  /*app.app.getStateManager().getState(HUDAppStateGame.class)
                          .addTrophyAchieviment(api.getTrophyString(
                          GameJoltAppState.TROPHY_BRONZE_GREEN_KILL).getTitle(),
                          "TROPHY_BRONZE_GREE
                          N_KILL_25",
                          ColorRGBA.Green);*/
                  
                  entidadeAppState.app.getStateManager().getState(HUDScreenAppState.class).showBubble(
                          api.getTrophyString(
                          GameJoltAppState.TROPHY_BRONZE_GREEN_KILL).getTitle(),
                          2000,
                          "Textures/Trophy/TROPHY_BRONZE_GREEN_KILL.png");
                
              }
              if (killsGreen == 75) {
                
                entidadeAppState.app.getStateManager().getState(HUDScreenAppState.class).showBubble(
                          api.getTrophyString(
                          GameJoltAppState.TROPHY_SILVER_GREEN_KILL).getTitle(),
                          2000,
                          "Textures/Trophy/TROPHY_SILVER_GREEN_KILL.png");
              }


              if (killsRed == 25) {
                entidadeAppState.app.getStateManager().getState(HUDScreenAppState.class).showBubble(
                          api.getTrophyString(
                          GameJoltAppState.TROPHY_BRONZE_RED_KILL).getTitle(),
                          2000,
                          "Textures/Trophy/TROPHY_BRONZE_RED_KILL.png");
              }
              if (killsRed == 75) {
                entidadeAppState.app.getStateManager().getState(HUDScreenAppState.class).showBubble(
                          api.getTrophyString(
                          GameJoltAppState.TROPHY_SILVER_RED_KILL).getTitle(),
                          2000,
                          "Textures/Trophy/TROPHY_SILVER_RED_KILL.png");
              }
              
              if (killsYellow == 25) {
                entidadeAppState.app.getStateManager().getState(HUDScreenAppState.class).showBubble(
                          api.getTrophyString(
                          GameJoltAppState.TROPHY_BRONZE_YELLOW_KILL).getTitle(),
                          2000,
                          "Textures/Trophy/TROPHY_BRONZE_YELLOW_KILL.png");
              }
              if (killsYellow == 75) {
                entidadeAppState.app.getStateManager().getState(HUDScreenAppState.class).showBubble(
                          api.getTrophyString(
                          GameJoltAppState.TROPHY_SILVER_YELLOW_KILL).getTitle(),
                          2000,
                          "Textures/Trophy/TROPHY_SILVER_YELLOW_KILL.png");
              }
              
              if (killsBlue == 25) {
                entidadeAppState.app.getStateManager().getState(HUDScreenAppState.class).showBubble(
                          api.getTrophyString(
                          GameJoltAppState.TROPHY_BRONZE_BLUE_KILL).getTitle(),
                          2000,
                          "Textures/Trophy/TROPHY_BRONZE_BLUE_KILL.png");
              }
              if (killsBlue == 75) {
                entidadeAppState.app.getStateManager().getState(HUDScreenAppState.class).showBubble(
                          api.getTrophyString(
                          GameJoltAppState.TROPHY_SILVER_BLUE_KILL).getTitle(),
                          2000,
                          "Textures/Trophy/TROPHY_SILVER_BLUE_KILL.png");
              }
            }

            ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).detachChildAt(i);
            lastEnemyDown = System.currentTimeMillis();


            entidadeAppState.app.getStateManager().getState(AudioAppState.class).explosion();
          } else {
            
            entidadeAppState.particleAppState.enemyExplosion(
                    (ColorRGBA) ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getUserData("color"),
                    ((Node)app.getRootNode().getChild(EntidadeAppState.NODEENEMIES)).getChild(i).getWorldTranslation(),
                    1,
                    false);
            entidadeAppState.app.getStateManager().getState(AudioAppState.class).metal();
          }
          ((Node)app.getRootNode().getChild(EntidadeAppState.NODEBULLETSPLAYER)).detachChildAt(j);
          break;

        }

        j++;
      }

      i++;
    }
  }

  private void handleCollisionsPlayerSpecial() {
    for (int q = 0; q < ((Node)app.getRootNode().getChild(EntidadeAppState.NODEPOWERUPS)).getQuantity(); q++) {
      if ((Boolean) ((Node)app.getRootNode().getChild(EntidadeAppState.NODEPOWERUPS)).getChild(q).getUserData("active")) {
        if (checkCollision(app.getRootNode().getChild(EntidadeAppState.NODEPLAYER), ((Node)app.getRootNode().getChild(EntidadeAppState.NODEPOWERUPS)).getChild(q))) {
          switch ((Integer) ((Node)app.getRootNode().getChild(EntidadeAppState.NODEPOWERUPS)).getChild(q).getUserData("tipo")) {
            case 1:
              app.getRootNode().getChild(EntidadeAppState.NODEPLAYER).setUserData("protect", true);
              ((Node)app.getRootNode().getChild(EntidadeAppState.NODEPLAYER)).attachChild(app.getStateManager().getState(EntidadeAppState.class).protect);
              entidadeAppState.app.getStateManager().getState(HUDScreenAppState.class).showBubble(
                      "Protection",2000);
              break;
            case 2:
              app.getRootNode().getChild(EntidadeAppState.NODEPLAYER).addControl(
                      new FrezzeControl(entidadeAppState));
              entidadeAppState.app.getStateManager().getState(HUDScreenAppState.class).showBubble(
                      "Frezze time!",2000);
              break;
            case 3:
              app.getRootNode().getChild(EntidadeAppState.NODEPLAYER).setUserData(
                      "bullet_speed", (Float) app.getRootNode().getChild(EntidadeAppState.NODEPLAYER).getUserData("bullet_speed") + 100);
               entidadeAppState.app.getStateManager().getState(HUDScreenAppState.class).showBubble(
                      "Bullet Speed +100!",2000);
              break;
            case 4:
              if ((Integer) app.getRootNode().getChild(EntidadeAppState.NODEPLAYER).getUserData(
                      "bullet_n") < 4) {
                app.getRootNode().getChild(EntidadeAppState.NODEPLAYER).setUserData("bullet_n", ((Integer) (app.getRootNode().getChild(EntidadeAppState.NODEPLAYER).getUserData("bullet_n"))) + 1);
              }

               entidadeAppState.app.getStateManager().getState(HUDScreenAppState.class).showBubble(
                      "Extra bullet!",2000);
              break;




            default:

          }
          ((Node)app.getRootNode().getChild(EntidadeAppState.NODEPOWERUPS)).detachChildAt(q);
        }
      }
    }
  }

  private void handleCollisionsPlayerBulletenemy() {
    // should the player die?
    for (int i = 0; i < ((Node)app.getRootNode().getChild(EntidadeAppState.NODEBULLETSENEMIES)).getQuantity(); i++) {
      if (checkCollision(app.getRootNode().getChild(EntidadeAppState.NODEPLAYER), ((Node)app.getRootNode().getChild(EntidadeAppState.NODEBULLETSENEMIES)).getChild(i))) {
        ((Node)app.getRootNode().getChild(EntidadeAppState.NODEBULLETSENEMIES)).detachChildAt(i);
        entidadeAppState.killPlayer();
      }

    }
  }

  private void handleCollisionsBulletPlayerBulletenemy() {
    // should the player die?
    for (int i = 0; i < ((Node)app.getRootNode().getChild(EntidadeAppState.NODEBULLETSENEMIES)).getQuantity(); i++) {
      for (int j = 0; j < ((Node)app.getRootNode().getChild(EntidadeAppState.NODEBULLETSPLAYER)).getQuantity(); j++) {
        if (checkCollision(((Node)app.getRootNode().getChild(EntidadeAppState.NODEBULLETSPLAYER)).getChild(j), ((Node)app.getRootNode().getChild(EntidadeAppState.NODEBULLETSENEMIES)).getChild(i))) {
          entidadeAppState.particleAppState.bulletsXbulletsExplosion(
                  ((Node)app.getRootNode().getChild(EntidadeAppState.NODEBULLETSENEMIES)).getChild(i).getWorldTranslation(),
                  500);
          ((Node)app.getRootNode().getChild(EntidadeAppState.NODEBULLETSENEMIES)).detachChildAt(i);
          ((Node)app.getRootNode().getChild(EntidadeAppState.NODEBULLETSPLAYER)).detachChildAt(j);
          break;
        }
      }
    }
  }
}
