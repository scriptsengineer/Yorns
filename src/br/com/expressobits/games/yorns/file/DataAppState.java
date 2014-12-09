/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.file;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Carrega e armazena
 *
 * @author Rafael
 */
public class DataAppState extends AbstractAppState {

  private static final Logger logger = Logger.getLogger(DataAppState.class.getName());
  /**
   * Entidades a serem carregadas são anexadas para este Nó.
   */
  private Node entities;
  private SimpleApplication app;
  private String fileName = "entities";
  public static String STRINGNAME = "name";
  public static String STRINGMODELTYPE = "modeltype";
  public static String STRINGGEOM = "geom";
  public static String STRINGSPEED = "speed";
  public static String STRINGRADIUS = "radius";
  public static String STRINGLIFE = "life";
  public static String STRINGTYPE = "type";
  public static String STRINGSUBTYPE = "subtype";
  public static String STRINGCOLOR = "color";
  public static String STRINGACTIVE = "active";
  public static String STRINGCONTROL = "control";
  public static String[] ENEMYNAMES = {"GreenEnemy", "RedEnemy", "YellowEnemy", "BlueEnemy"};
  public static String[] POWERUPSNAMES = {"BulletExtraPowerUp", "BulletSpeedPowerUp", "FreezePowerUp", "ProtectPowerUp"};
  private static int TYPE = 0;
  private static int ID = 1;
  private static int NAME = 2;
  private static int GEOM = 3;
  private static int COLOR = 4;
  private static int SPEED = 5;
  private static int RADIUS = 6;
  private static int LIFE = 7;
  private static int CONTROL = 8;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.entities = new Node("data");
    this.app = (SimpleApplication) app;
    this.loadEntities();
  }

  public void loadEntities() {

    ArrayList<String> lines = app.getStateManager().getState(FileAppState.class).loadFile(fileName);

    ArrayList<String> linesSpatial = new ArrayList<String>();
    boolean intoSpatial = false;
    for (int i = 0; i < lines.size(); i++) {
      if (intoSpatial) {
        if (lines.get(i).equals("}")) {
          intoSpatial = false;
          entities.attachChild(loadEntity(linesSpatial));
          linesSpatial = new ArrayList<String>();
        } else {
          linesSpatial.add(lines.get(i));
        }
      } else {

        if (lines.get(i).equals("{")) {
          intoSpatial = true;
        }
      }
    }
  }

  public Spatial loadEntity(ArrayList<String> lines) {
    Spatial sp;
    if (getValue(lines, STRINGMODELTYPE).equals("node")) {
      sp = new Node(getValue(lines,STRINGNAME));

      Spatial spatial = createModel(lines);
      ((Node) sp).attachChild(spatial);
    } else {
      sp = createModel(lines);
    }

    for (int i = 0; i < lines.size(); i++) {
      String key = lines.get(i).split(":")[0];
      String value = lines.get(i).split(":")[1];
      if (key.equals(STRINGSPEED)) {
        sp.setUserData(key, (Float.valueOf(value)));
      }
      if (key.equals(STRINGRADIUS)) {
        sp.setUserData(key, (Float.valueOf(value)));
      }
      if (key.equals(STRINGLIFE)) {
        sp.setUserData(key, (Integer.valueOf(value)));
      }
      if (key.equals(STRINGTYPE)) {
        sp.setUserData(key, value);
      }
      if (key.equals(STRINGSUBTYPE)) {
        sp.setUserData(key, (Integer.valueOf(value)));
      }
      if (key.equals(STRINGCOLOR)) {
        sp.setUserData(key, stringToColorRGBA(value));
      }
      if (key.equals(STRINGACTIVE)) {
        sp.setUserData(key, true);
      }
      if (key.equals(STRINGCONTROL)) {
        sp.setUserData(key + value, value);
      }
    }
    logger.log(Level.INFO,"Loaded Spatial {0} with sucess!",sp.getName());
    return sp;
  }

  public String getValue(String s) {
    return s.split(":")[1];
  }

  public String getValue(ArrayList<String> lines, String key) {
    String value = "no value";
    for (int i = 0; i < lines.size(); i++) {
      if (lines.get(i).split(":")[0].equals(key)) {
        value = lines.get(i).split(":")[1];
        logger.log(Level.FINEST,"Loaded key-{0}({1})",lines.get(i).split(":"));
        break;
      }

    }
    return value;
  }

  public Geometry createGeometry(String name, float radius, ColorRGBA color) {
    Sphere sphere = new Sphere(12, 10, radius);
    Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setColor("Color", color);
    Geometry geo = new Geometry(name, sphere);
    geo.setMaterial(mat);
    return geo;
  }

  private ColorRGBA stringToColorRGBA(String s) {
    String[] scolor = s.split("-");
    if (scolor[0].equals("#")) {
      return new ColorRGBA(
              Float.parseFloat(scolor[1]),
              Float.parseFloat(scolor[2]),
              Float.parseFloat(scolor[3]),
              Float.parseFloat(scolor[4]));
    } else {
      logger.log(Level.WARNING,"Parameter invalid of color - {0}",s);
      return ColorRGBA.White;
    }
  }

  public Spatial loadModelSpatial(int size, String name) {
    logger.log(Level.INFO,"Loaded spatial {0}",name);
    Spatial sp = entities.getChild(name).clone();
    sp.setLocalScale(size);
    sp.setUserData("radius", ((Float) sp.getUserData("radius")) * size);
    sp.setUserData("size", size);
    return sp;
  }

  public Node loadModelNode(int size, String name) {
    logger.log(Level.INFO,"Loaded spatial {0}",name);
    Node sp = (Node) entities.getChild(name).clone();
    sp.setLocalScale(size);
    sp.setUserData("radius", ((Float) sp.getUserData("radius")) * size);
    sp.setUserData("size", size);
    return sp;
  }

  private Spatial createModel(ArrayList<String> lines) {
    Spatial sp;
    if (getValue(lines, STRINGGEOM).equals("sphere")) {
      Sphere sphere = new Sphere(12, 10, Integer.parseInt(getValue(lines, STRINGRADIUS)));
      Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
      mat.setColor("Color", stringToColorRGBA(getValue(lines, STRINGCOLOR)));
      if (getValue(lines, STRINGMODELTYPE).equals("node")) {
        sp = new Geometry(getValue(lines, STRINGNAME+"Model"), sphere);
      } else {
        sp = new Geometry(getValue(lines, STRINGNAME), sphere);
      }
      sp.setMaterial(mat);
    } else if (getValue(lines, STRINGGEOM).equals("box")) {
      Box box = new Box((float) Integer.parseInt(getValue(lines, STRINGRADIUS)), (float) Integer.parseInt(getValue(lines, STRINGRADIUS)), (float) Integer.parseInt(getValue(lines, STRINGRADIUS)));
      Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
      mat.setColor("Color", stringToColorRGBA(getValue(lines, STRINGCOLOR)));
      if (getValue(lines, STRINGMODELTYPE).equals("node")) {
        sp = new Geometry(getValue(lines, STRINGNAME+"Model"), box);
      } else {
        sp = new Geometry(getValue(lines, STRINGNAME), box);
      }
      sp.setMaterial(mat);
    } else {
      sp = app.getAssetManager().loadModel("Models/" + getValue(lines, STRINGGEOM) + ".obj");
      Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
      mat.setColor("Color", stringToColorRGBA(getValue(lines, STRINGCOLOR)));
      sp.setMaterial(mat);
      if (getValue(lines, STRINGMODELTYPE).equals("node")) {
        sp.setName(getValue(lines, STRINGNAME+"Model"));
      } else {
        sp.setName(getValue(lines, STRINGNAME));
      }
    }


    return sp;
  }
}
