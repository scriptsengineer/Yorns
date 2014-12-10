/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns;

import br.com.expressobits.games.yorns.settings.SettingAppState;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import de.lessvoid.nifty.Nifty;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe Principal
 *
 * @author Rafael
 */
public class Main extends SimpleApplication {

  public static String NAME = "YORNS BETA 0.9.9";
  public static Main app;
  public StateManager sm;
  public SettingAppState settingAppState;
  public static BloomFilter bloom;
  public static float bloomIntensity = 1f;
  public static FilterPostProcessor fpp;
  public static Nifty nifty;
  public static NiftyJmeDisplay niftyJmeDisplay;
  private static final Logger log = Logger.getLogger(Main.class.getName());
  public static boolean debug = true;
  public static boolean online = true;
  public static float camDistance = 300f;

  public static void main(String[] args) {
    app = new Main();
  }

  public Main() {
    settingAppState = new SettingAppState(this);
    sm = new StateManager(this);
    getStateManager().attach(settingAppState);
    getStateManager().attach(sm);
    setShowSettings(false);
    setSettings(settingAppState.settings);
    start();
    if(debug){
      Logger.getGlobal().setLevel(Level.ALL);
    }
  }

  @Override
  public void simpleInitApp() {

    configureCam(400,300);
    configureDebugMode(false);
    sm.initAppStates();
    bloomInit(bloomIntensity);
    initNifty();
    
   
    
  }

  @Override
  public void simpleUpdate(float tpf) {
    super.simpleUpdate(tpf); //To change body of generated methods, choose Tools | Templates.


  }

  /**
   * Define configuração da camera de 2D
   */
  public void configureCam(float x, float z) {
    //cam.setParallelProjection(true);
    cam.setLocation(new Vector3f(x, z,camDistance));
    getFlyByCamera().setEnabled(false);
  }

  /**
   * Define o debug mode
   *
   * @param debug
   */
  private void configureDebugMode(boolean debug) {
    setDisplayStatView(debug);
    setDisplayFps(debug);
  }


  private void bloomInit(float intensity) {
    fpp = new FilterPostProcessor(assetManager);
    bloom = new BloomFilter();
    bloom.setBloomIntensity(intensity);
    bloom.setExposurePower(1);
    bloom.setExposureCutOff(0f);
    bloom.setBlurScale(1.5f);
    fpp.addFilter(bloom);
    viewPort.addProcessor(fpp);
  }

  public void initGlow() {
    Material mat = new Material(getAssetManager(), "Common/MatDefs/Misc/SolidColor.j3md");
    mat.setColor("Color", ColorRGBA.Green);
    mat.setColor("GlowColor", ColorRGBA.Green);
    fpp = new FilterPostProcessor(assetManager);
    bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
    fpp.addFilter(bloom);
    guiViewPort.addProcessor(fpp);
  }

  public static void configureBloom(float i) {
    bloom.setBloomIntensity(i);
  }

  private void bloomRemove() {
    fpp.removeAllFilters();
    guiViewPort.removeProcessor(fpp);
    guiViewPort.setClearColor(false);

  }

  private void initNifty() {
    niftyJmeDisplay = new NiftyJmeDisplay(
            app.getAssetManager(), app.getInputManager(),
            app.getAudioRenderer(), app.getViewPort());
    //Cria uma nova niftyGui objeto
    nifty = niftyJmeDisplay.getNifty();
    //this.app.getViewPort().addProcessor(niftyJmeDisplay);
  }

  
  
}
