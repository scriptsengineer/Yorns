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
import br.com.expressobits.games.yorns.particles.ParticleAppState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * APPSTATE DE ENTIDADES DO JOGO
 *
 * @author Rafael
 */
public class EntidadeAppState extends AbstractAppState {

  //APPSTATES
  SimpleApplication app;
  ParticleAppState particleAppState;
  CollisionAppState collisionAppState;
  LevelAppState levelAppState;
  //STRINGS COM NOME DOS NODES
  public static String NODEENEMIES = "Enemies";
  public static String NODEPLAYER = "Player";
  public static String NODEBULLETSPLAYER = "BulletsPlayer";
  public static String NODEBULLETSENEMIES = "BulletsEnemies";
  public static String NODEPOWERUPS = "Powerups";
  //NODES
  private Node nodePlayer = new Node(NODEPLAYER);
  private Node nodeEnemies = new Node(NODEENEMIES);
  private Node nodeBulletsPlayer = new Node(NODEBULLETSPLAYER);
  private Node nodeBulletsEnemies = new Node(NODEBULLETSENEMIES);
  private Node nodePowerups = new Node(NODEPOWERUPS);
  public Spatial protect = new Node("Protect");
  //FATORES DE MULTPLICAR
  public int multiplicar = 1;
  public final static int multLIMIT = 20;
  public final static long ENEMY_COOL_DOWN = 1000;
  //VELOCIDADE
  public static float speed = 4f;
  public static final float SPEED_NORMAL = 4f;
  long bulletCooldown = System.currentTimeMillis();
  public static final float BULLETTIMENORMAL = 250f;
  public static final float BULLETTIME = 250f;
  //3D IMPLEMENTION
  private Node node3delements = new Node("Elements 3D");

  public EntidadeAppState(SimpleApplication app) {
    this.app = app;
  }

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (SimpleApplication) app;
    //APPSTATE INIT
    collisionAppState = new CollisionAppState();
    particleAppState = new ParticleAppState();
    levelAppState = new LevelAppState();
    app.getStateManager().attach(collisionAppState);
    app.getStateManager().attach(particleAppState);
    app.getStateManager().attach(levelAppState);

    createPlayer();
    node3delements.attachChild(nodePlayer);
    node3delements.attachChild(nodeEnemies);
    node3delements.attachChild(nodePowerups);
    node3delements.attachChild(nodeBulletsPlayer);
    node3delements.attachChild(nodeBulletsEnemies);
    speed = SPEED_NORMAL;
    if (GameJoltAppState.logged) {
      app.getStateManager().getState(GameJoltAppState.class).updateInternet();
    }
  }

  @Override
  public void update(float tpf) {
    super.update(tpf); //To change body of generated methods, choose Tools | Templates.
    if ((Boolean) nodePlayer.getUserData("alive")) {
      app.getCamera().setLocation(new Vector3f(nodePlayer.getLocalTranslation().x, nodePlayer.getLocalTranslation().y, Main.camDistance));
      app.getCamera().lookAt(nodePlayer.getLocalTranslation(), Vector3f.ZERO);
    } else {
      GameAppState.gameOver = true;
    }
  }

  @Override
  public void stateAttached(AppStateManager stateManager) {
    super.stateAttached(stateManager);
    this.app.getRootNode().attachChild(node3delements);
  }

  @Override
  public void stateDetached(AppStateManager stateManager) {
    super.stateDetached(stateManager); //To change body of generated methods, choose Tools | Templates.
    this.app.getRootNode().detachChild(node3delements);
    this.app.getStateManager().detach(levelAppState);
    this.app.getStateManager().detach(particleAppState);
  }

  private void createPlayer() {
    nodePlayer = app.getStateManager().getState(DataAppState.class).loadModelNode(3, NODEPLAYER);

    //protect = createGeometry("Protect", 1, ColorRGBA.Magenta);
    nodePlayer.setUserData("alive", true);
    nodePlayer.setUserData("protect", false);
    nodePlayer.setUserData("bullet_speed", 1000f);
    nodePlayer.setUserData("bullet_n", 1);
    nodePlayer.setUserData("score", 0);
    nodePlayer.addControl(new ProtectControl(NODEPLAYER));
    nodePlayer.move(GameAppState.width / 2, GameAppState.height / 2, 0);
    nodePlayer.addControl(new PlayerControl(
            this.app,
            GameAppState.width,
            GameAppState.height,
            (Float) nodePlayer.getUserData("speed"),
            particleAppState));
  }

  public void createEnemy(String name, int size, Vector3f loc) {
    Spatial sp = app.getStateManager().getState(DataAppState.class).loadModelSpatial(size, name);
    sp.setLocalTranslation(loc);
    String ctrlname;
    ctrlname = sp.getUserData("controlwanderer");
    if (ctrlname != null) {
      if (ctrlname.equals("wanderer")) {
        sp.addControl(new WandererMoveControl(
                GameAppState.width,
                GameAppState.height,
                1000));
      }
    }
    
    ctrlname = sp.getUserData("controlseeker");
    if (ctrlname != null) {
      if (ctrlname.equals("seeker")) {
        sp.addControl(new SeekerMoveControl(nodePlayer));
      }
    }
    
    ctrlname = sp.getUserData("controlshooter");
    if (ctrlname != null) {
      if (ctrlname.equals("shooter")) {
        sp.addControl(new ShooterControl(nodePlayer, 1, this));
      }
      
    }
    
    ctrlname = sp.getUserData("controlrotate");
    if (ctrlname != null) {
      if (ctrlname.equals("rotate")) {
        sp.addControl(new RotateControl(200f));
      }
    }
    
    nodeEnemies.attachChild(sp);
  }

  protected void createSpecial(int size, Vector3f loc, int tipo) {

    Spatial sp = app.getStateManager().getState(DataAppState.class).loadModelSpatial(1, DataAppState.POWERUPSNAMES[tipo - 1]);

    sp.setUserData(
            "tipo", tipo);
    sp.setLocalTranslation(loc);
    for (int i = 0;
            i < 5; i++) {
      String ctrlname = sp.getUserData("ctrls" + i);
      if (ctrlname != null) {
        if (ctrlname.equals("wanderer")) {
          sp.addControl(new WandererMoveControl(
                  GameAppState.width,
                  GameAppState.height,
                  1000));
        }
        if (ctrlname.equals("seeker")) {
          sp.addControl(new SeekerMoveControl(nodePlayer));
        }
      }
    }

    nodePowerups.attachChild(sp);
  }

  private Vector3f getAimDirection() {
    Vector2f mousePositionScreen = app.getInputManager().getCursorPosition();
    mousePositionScreen = GameAppState.getMouseCorrectAim(mousePositionScreen);
    Vector3f directionNormal;

    Vector3f click3d = app.getCamera().getWorldCoordinates(new Vector2f(mousePositionScreen.x, mousePositionScreen.y), 0f).clone();
    Vector3f direction = app.getCamera().getWorldCoordinates(new Vector2f(mousePositionScreen.x, mousePositionScreen.y), 0f).subtractLocal(click3d);
    directionNormal = direction.normalizeLocal();


    Vector3f playerPos = nodePlayer.getLocalTranslation();
    Vector3f directionDiferencial = new Vector3f(click3d.x - playerPos.x, click3d.y - playerPos.y, 0);


//      Vector3f click3d = app.getCamera().getWorldCoordinates(click2d,0).clone();
//      
//      Vector3f direction = nodePlayer.getLocalTranslation().subtract(click3d);
//      directionNormal = direction.normalize();
//      Ray ray = new Ray(click3d,directionNormal);
//      Plane plane = new Plane(Vector3f.UNIT_Z, 0);
//      Vector3f mousePositionWorld = new Vector3f();
//      ray.intersectsWherePlane(plane, mousePositionWorld);
//      mousePositionWorld.z = 0;

//      System.out.println("-------------\nCAM DEBUG");
//      System.out.println("click2d \t"+click2d);
//      System.out.println("click3d \t"+click3d);
//      System.out.println("diretion \t"+direction);
//      System.out.println("directionNormal "+directionNormal);
    return directionDiferencial.normalizeLocal();


  }

  protected void killPlayer() {
    if (nodePlayer.getControl(ProtectControl.class) == null) {
      if ((Boolean) nodePlayer.getUserData(
              "protect")) {
        //nodePlayer.detachChild(protect);
        nodePlayer.setUserData("protect", false);
        nodePlayer.addControl(new ProtectControl("Player"));

      } else {
        //particleAppState.playerExplosion(player.getLocalTranslation());
        //System.out.println(player.getUserData("points"));
        //nodePlayer.removeFromParent();
        nodePlayer.getControl(PlayerControl.class).reset();
        nodePlayer.setUserData("alive", false);
        //player.setUserData("dieTime", System.currentTimeMillis());
        app.getStateManager().detach(collisionAppState);

        GameJoltAppState api = app.getStateManager().getState(GameJoltAppState.class);
        if (GameJoltAppState.logged) {
          if (collisionAppState.killsGreen >= 25) {
            api.achievedTrophy(api.getTrophyString(GameJoltAppState.TROPHY_BRONZE_GREEN_KILL).getId());
          }
          if (collisionAppState.killsRed >= 25) {
            api.achievedTrophy(api.getTrophyString(GameJoltAppState.TROPHY_BRONZE_RED_KILL).getId());
          }
          if (collisionAppState.killsYellow >= 25) {
            api.achievedTrophy(api.getTrophyString(GameJoltAppState.TROPHY_BRONZE_YELLOW_KILL).getId());
          }
          if (collisionAppState.killsBlue >= 25) {
            api.achievedTrophy(api.getTrophyString(GameJoltAppState.TROPHY_BRONZE_BLUE_KILL).getId());
          }

          if (collisionAppState.killsGreen >= 75) {
            api.achievedTrophy(api.getTrophyString(GameJoltAppState.TROPHY_SILVER_GREEN_KILL).getId());
          }
          if (collisionAppState.killsRed >= 75) {
            api.achievedTrophy(api.getTrophyString(GameJoltAppState.TROPHY_SILVER_RED_KILL).getId());
          }
          if (collisionAppState.killsYellow >= 75) {
            api.achievedTrophy(api.getTrophyString(GameJoltAppState.TROPHY_SILVER_YELLOW_KILL).getId());
          }
          if (collisionAppState.killsBlue >= 75) {
            api.achievedTrophy(api.getTrophyString(GameJoltAppState.TROPHY_SILVER_BLUE_KILL).getId());
          }
        }

        app.getStateManager().getState(HUDScreenAppState.class).gameOver();
      }
    }
  }

  /**
   * Player atira
   */
  public void shootBulletPlayer() {
    //shoot Bullet
    if (System.currentTimeMillis() - bulletCooldown > BULLETTIME / (EntidadeAppState.speed / 4)) {
      bulletCooldown = System.currentTimeMillis();

      Vector3f aim = (Vector3f) nodePlayer.getUserData("angle");
      Vector3f offset = new Vector3f(aim.y / 3, -aim.x / 3, 0);
      shootBulletsPlayer(aim, offset, (Integer) nodePlayer.getUserData("bullet_n"), 1);
      app
              .getStateManager().getState(AudioAppState.class).shoot();
    }
  }

  private void shootBulletsPlayer(Vector3f aim, Vector3f offset, int n, int power) {


    for (int i = 0; i < n; i++) {
      Vector3f finalOffset;
      Vector3f trans;
      Spatial bullet = app.getStateManager().getState(DataAppState.class).loadModelSpatial(1, "Bullet");
      if (i
              == 0) {
        finalOffset = aim.add(offset).mult((n - 1) * 0);
        trans = nodePlayer.getLocalTranslation().add(finalOffset);
      } else if (i
              == 1) {
        finalOffset = aim.add(offset.negate()).mult((n - 1) * 15);
        trans = nodePlayer.getLocalTranslation().add(finalOffset);
      } else if (i
              == 2) {
        finalOffset = aim.add(offset).mult((n - 1) * 15);
        trans = nodePlayer.getLocalTranslation().add(finalOffset);
      } else if (i
              == 3) {
        finalOffset = aim.add(offset.negate()).mult((n - 1) * 30);
        trans = nodePlayer.getLocalTranslation().add(finalOffset);
      } else {
        finalOffset = aim.add(offset).mult((n - 1) * 30);
        trans = nodePlayer.getLocalTranslation().add(finalOffset);

      }

      bullet.setUserData(
              "speed", (Float) nodePlayer.getUserData("bullet_speed"));
      bullet.setLocalTranslation(trans);

      bullet.addControl(
              new BulletControl(aim,
              GameAppState.width,
              GameAppState.height,
              particleAppState));
      nodeBulletsPlayer.attachChild(bullet);
    }
  }

  public void shootBulletEnemy(Spatial spatial) {

    Vector3f aim = nodePlayer.getLocalTranslation().subtract(spatial.getLocalTranslation()).normalize();
    Vector3f offset = new Vector3f(aim.y / 3, -aim.x / 3, 0);
    shootBulletsEnemy(spatial, aim, offset);
    app
            .getStateManager().getState(AudioAppState.class).shoot_enemy();

  }

  private void shootBulletsEnemy(Spatial spatial, Vector3f aim, Vector3f offset) {
    Vector3f finalOffset;
    Vector3f trans;
    Spatial bullet = app.getStateManager().getState(DataAppState.class).loadModelSpatial(1, "BulletEnemy");
    finalOffset = aim.add(offset).mult(0);
    trans = spatial.getLocalTranslation().add(finalOffset);

    bullet.setLocalTranslation(trans);

    bullet.addControl(
            new BulletControl(aim,
            GameAppState.width,
            GameAppState.height,
            particleAppState));
    nodeBulletsEnemies.attachChild(bullet);
  }
}
