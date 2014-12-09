/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.menu;

import br.com.expressobits.games.yorns.Main;
import br.com.expressobits.games.yorns.settings.SettingAppState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael
 */
public class SettingScreenAppState extends AbstractAppState implements ScreenController {

  private static final Logger logger = Logger.getLogger(SettingScreenAppState.class.getName());
  SimpleApplication app;
  CheckBox checkAudioEffect;
  CheckBox checkAudioMusic;
  CheckBox checkGameWarp;
  
  Element popupElement;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app);
    this.app = (SimpleApplication) app;
    this.app.getGuiViewPort().addProcessor(Main.niftyJmeDisplay);
    Main.nifty.registerScreenController(this);
    Main.nifty.fromXml("Interface/SettingScreen.xml", "GScreenSetting");
  }

  @Override
  public void stateDetached(AppStateManager stateManager) {
    super.stateDetached(stateManager);
    this.app.getGuiViewPort().removeProcessor(Main.niftyJmeDisplay);
  }

  public void cancel() {
    app.getStateManager().getState(MenuAppState.class).retornaMenu();
  }
  
  public void save(){
    //AUDIO
    app.getStateManager().getState(SettingAppState.class).settings.put(
            SettingAppState.AUDIOEFFECT,checkAudioEffect.isChecked());
    app.getStateManager().getState(SettingAppState.class).settings.put(
            SettingAppState.AUDIOMUSIC,checkAudioMusic.isChecked());
    //GAME
    app.getStateManager().getState(SettingAppState.class).settings.put(
            SettingAppState.GAMEWARP,checkGameWarp.isChecked());
    
    app.getStateManager().getState(SettingAppState.class).onSave();
    Main.nifty.showPopup(Main.nifty.getCurrentScreen(), popupElement.getId(), null);
  }

  public void bind(Nifty nifty, Screen screen) {
    logger.log(Level.INFO,"bind( {0} )",screen.getScreenId());
    
    popupElement = Main.nifty.createPopup("popupExit");
    
    checkAudioEffect = Main.nifty.getCurrentScreen().findNiftyControl("checkAudioEffect",CheckBox.class);
    checkAudioMusic = Main.nifty.getCurrentScreen().findNiftyControl("checkAudioMusic",CheckBox.class);
    checkGameWarp = Main.nifty.getCurrentScreen().findNiftyControl("checkGameWarp",CheckBox.class);
    
    //Carregar configurações atuais para tela
    //AUDIO
    checkAudioEffect.setChecked(app.getStateManager().
            getState(SettingAppState.class).settings.getBoolean(SettingAppState.AUDIOEFFECT));
    checkAudioMusic.setChecked(app.getStateManager().
            getState(SettingAppState.class).settings.getBoolean(SettingAppState.AUDIOMUSIC));
    //GAME
    checkGameWarp.setChecked(app.getStateManager().
            getState(SettingAppState.class).settings.getBoolean(SettingAppState.GAMEWARP));
  }

  public void onStartScreen() {
  }

  public void onEndScreen() {
  }
}
