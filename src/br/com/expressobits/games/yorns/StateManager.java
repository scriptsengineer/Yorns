/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns;

import static br.com.expressobits.games.yorns.Main.bloomIntensity;
import static br.com.expressobits.games.yorns.Main.configureBloom;
import br.com.expressobits.games.yorns.file.DataAppState;
import br.com.expressobits.games.yorns.file.FileAppState;
import br.com.expressobits.games.yorns.gj.GameJoltAppState;
import br.com.expressobits.games.yorns.menu.MenuAppState;
import br.com.expressobits.games.yorns.settings.SettingAppState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.cursors.plugins.JmeCursor;

/**
 *
 * @author Rafael
 */
public class StateManager extends AbstractAppState{

  SimpleApplication app;
  
  public InputAppState inputAppState;
  public MenuAppState menuAppState;
  public GameAppState gameAppState;
  public GameJoltAppState gameJoltAppState;
  public SettingAppState settingAppState;
  public FileAppState fileAppState;
  public DataAppState dataAppState;

  public StateManager(SimpleApplication app) {
    this.app = (SimpleApplication)app;
  }
  
  
  
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    
  }
  
  /**
   * Define os estados principais do jogo!
   */
  public void changeGameAppState() {
    if (app.getStateManager().hasState(gameAppState)) {
      app.getStateManager().attach(menuAppState);
      app.getStateManager().detach(gameAppState);
    } else if (app.getStateManager().hasState(menuAppState)) {
      app.getStateManager().attach(gameAppState);
      app.getStateManager().detach(menuAppState);
    }
  }
  
  /**
   * Inicializa os AppStates.
   */
  public void initAppStates() {
    changeCursor(false);
    gameJoltAppState = new GameJoltAppState();
    menuAppState = new MenuAppState();
    gameAppState = new GameAppState();
    inputAppState = new InputAppState();
    fileAppState = new FileAppState();
    dataAppState = new DataAppState();

    app.getStateManager().attach(gameJoltAppState);
    app.getStateManager().attach(menuAppState);
    app.getStateManager().attach(inputAppState);
    app.getStateManager().attach(fileAppState);
    app.getStateManager().attach(dataAppState);
    gameAppState.setEnabled(true);


  }
  
  public void changeCursor(boolean inGame){
      if(inGame){
        this.app.getInputManager().setMouseCursor((JmeCursor)this.app.getAssetManager().loadAsset("Textures/Pointer.ico"));
      }else{
        this.app.getInputManager().setMouseCursor((JmeCursor)this.app.getAssetManager().loadAsset("Textures/PointerMenu.ico"));
      }
    }
  
}
