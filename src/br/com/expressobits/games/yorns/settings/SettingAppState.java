/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.settings;

import static br.com.expressobits.games.yorns.Main.NAME;
import br.com.expressobits.games.yorns.gj.GameJoltAppState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import org.gamejolt.GameJoltAPI;

/**
 *
 * @author Rafael
 */
public class SettingAppState extends AbstractAppState{

  private static final Logger logger = Logger.getLogger(SettingAppState.class.getName());
  public static final String AUDIOMUSIC = "audiomusic";
  public static final String AUDIOEFFECT = "audioeffect";
  public static final String GAMEWARP = "warpgrid";
  
  public AppSettings settings;
  
  public SimpleApplication app;

  public SettingAppState(SimpleApplication app) {
    settings = new AppSettings(true);
    this.app=(SimpleApplication)app;
    onLoad();
    settings.setTitle(NAME);
  }
  
  
  
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    
    
    
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    
    
    
  }
  
  public void onSave(){
    try{
      settings.save("br.com.expressobits.games.yorns");
      logger.log(Level.INFO,"Sucess on save your settings!");
    }catch(BackingStoreException ex){
      logger.log(Level.WARNING,"Error on save your settings!");
    }
    app.getContext().setSettings(settings);
  }

  @Override
  public void stateDetached(AppStateManager stateManager) {
    super.stateDetached(stateManager); //To change body of generated methods, choose Tools | Templates.
    
  }
  
  
  public void onLoad(){
    try{
      settings.load("br.com.expressobits.games.yorns");
      if(settings.containsKey(GAMEWARP)){
        logger.log(Level.INFO,"Sucess on load your settings!");
      }else{
         logger.log(Level.INFO,"Not found settings, load default configuration!");
        loadDefaults();
      }
    }catch(BackingStoreException ex){
      logger.log(Level.WARNING,"Error on load your settings! - {0}",ex);
    }
    
  }
  
  public void loadDefaults(){
    
    settings.setResolution(800,600);
    settings.put(AUDIOMUSIC,true);
    settings.put(AUDIOEFFECT,true);
    settings.put(GAMEWARP,true);
  }
  
  
}
