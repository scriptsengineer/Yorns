/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.menu;

import br.com.expressobits.games.yorns.Main;
import br.com.expressobits.games.yorns.gj.GameJoltAppState;
import static br.com.expressobits.games.yorns.menu.LoginScreenAppState.s;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.plugins.UrlLocator;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.batch.spi.BatchRenderBackend;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.dynamic.PanelCreator;
import de.lessvoid.nifty.controls.dynamic.TextCreator;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class ScoreScreenAppState extends AbstractAppState implements ScreenController {

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
    System.out.println("bind( " + screen.getScreenId() + ")");

    
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
