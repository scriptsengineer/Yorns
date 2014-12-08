/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.menu;

import br.com.expressobits.games.yorns.Main;
import br.com.expressobits.games.yorns.StateManager;
import br.com.expressobits.games.yorns.gj.GameJoltAppState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Rafael
 */
public class MainMenuScreenAppState extends AbstractAppState implements ScreenController {

  public SimpleApplication app;
  static Label labelMessagePopup;
  static String s;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (SimpleApplication) app;
    this.app.getGuiViewPort().addProcessor(Main.niftyJmeDisplay);
    Main.nifty.registerScreenController(this);
    //Ler seu XML e inicializar o ScreenController customizado
    Main.nifty.fromXml("Interface/MainMenuScreen.xml", "GScreenMainMenu", this);
    //Main.nifty.addXml("Interface/screen2.xml","hud");
    //nifty.fromXML("Interface/screen2.xml","start", new MySettingsScreen(data));

    //disable the fly cam
    this.app.getFlyByCamera().setDragToRotate(true);
  }

  @Override
  public void stateDetached(AppStateManager stateManager) {
    super.stateDetached(stateManager); //To change body of generated methods, choose Tools | Templates.
    //Cria uma nova niftyGui objeto
    Main.nifty.removeScreen("GScreenMainMenu");
    this.app.getGuiViewPort().removeProcessor(Main.niftyJmeDisplay);
  }

  @Override
  public void stateAttached(AppStateManager stateManager) {
    super.stateAttached(stateManager); //To change body of generated methods, choose Tools | Templates.

  }

  public void startGame() {
    app.getStateManager().getState(StateManager.class).changeGameAppState();

  }

  public void loginScreen() {
    //Main.nifty.fromXml("Interface/LoginScreen.xml", "GScreenLogin");
    GameJoltAppState.logged = false;
    app.getStateManager().getState(MenuAppState.class).loginScreen();
  }

  public void aboutGameScreen() {
    //Main.nifty.fromXml("Interface/LoginScreen.xml", "GScreenLogin");
    app.getStateManager().getState(MenuAppState.class).aboutGameScreen();
  }

  public void scoreScreen() {
    //Main.nifty.fromXml("Interface/LoginScreen.xml", "GScreenLogin");
    app.getStateManager().getState(MenuAppState.class).scoreScreen();
  }

  public void settingScreen() {
    //Main.nifty.fromXml("Interface/LoginScreen.xml", "GScreenLogin");
    app.getStateManager().getState(MenuAppState.class).settingScreen();
  }

  public void bind(Nifty nifty, Screen screen) {
    System.out.println("bind( " + screen.getScreenId() + ")");
    if (GameJoltAppState.logged) {
      nifty.getCurrentScreen().findNiftyControl("GLabelStatusLogged", Label.class).setText("Logged with " + app.getStateManager().getState(GameJoltAppState.class).getNameUser());
    } else {
      nifty.getCurrentScreen().findNiftyControl("GLabelStatusLogged", Label.class).setText("Not logged!");
    }
    if(Main.debug){
    nifty.getCurrentScreen().findNiftyControl("GLabelStatusGame",Label.class).setText("DEBUG MODE");
    }else{
      nifty.getCurrentScreen().findNiftyControl("GLabelStatusGame",Label.class).setText("");
    }
      
  }

  public void onStartScreen() {
  }

  public void onEndScreen() {
  }

  public String retornaUser() {
    return s;
  }
}
