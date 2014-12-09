/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.menu;

import br.com.expressobits.games.yorns.Main;
import br.com.expressobits.games.yorns.gj.GameJoltAppState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael
 */
public class ScoreScreenAppState extends AbstractAppState implements ScreenController {

  private static final Logger logger = Logger.getLogger(ScoreScreenAppState.class.getName());
  public SimpleApplication app;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app);
    this.app = (SimpleApplication) app;
    this.app.getGuiViewPort().addProcessor(Main.niftyJmeDisplay);
    Main.nifty.registerScreenController(this);
    Main.nifty.fromXml("Interface/ScoreScreen.xml", "GScreenScore");
    this.app.getFlyByCamera().setDragToRotate(true);
  }

  @Override
  public void stateDetached(AppStateManager stateManager) {
    super.stateDetached(stateManager);
    Main.nifty.removeScreen("GScreenScore");
    this.app.getGuiViewPort().removeProcessor(Main.niftyJmeDisplay);
  }

  public void cancel() {
    app.getStateManager().getState(MenuAppState.class).retornaMenu();
  }

  public void bind(Nifty nifty, Screen screen) {
    logger.log(Level.INFO,"bind ( {0} )",screen.getScreenId());
    if(GameJoltAppState.connect){
      for (int i = 0; i < 5; i++) {
      String s;
      if(app.getStateManager().getState(GameJoltAppState.class).highScores.get(i).getUsername().isEmpty()){
        s = app.getStateManager().getState(GameJoltAppState.class).highScores.get(i).getProperty("guest")+" \n "+
              app.getStateManager().getState(GameJoltAppState.class).highScores.get(i).getScoreValue();
      }else{
      s = 
              app.getStateManager().getState(GameJoltAppState.class).highScores.get(i).getUsername()+" \n "+
              app.getStateManager().getState(GameJoltAppState.class).highScores.get(i).getScoreValue();
      }
      System.out.println(s);
      Main.nifty.getCurrentScreen().findNiftyControl("GLabel" + (i + 1), Label.class).setText(s);
      Main.nifty.getCurrentScreen().findElementByName("Image" + (i + 1)).getRenderer(ImageRenderer.class)
              .setImage(app.getStateManager().getState(GameJoltAppState.class).imagesScores.get(i));
    }
    }
    
  }

  public void onStartScreen() {
  }

  public void onEndScreen() {
  }
}
