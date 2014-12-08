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
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.PopupBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;

/**
 *
 * @author Rafael
 */
public class LoginScreenAppState extends AbstractAppState implements ScreenController {

  public SimpleApplication app;
  Label labelResult;
  static String s;
  Element popupElement;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (SimpleApplication) app;


    this.app.getGuiViewPort().addProcessor(Main.niftyJmeDisplay);
    Main.nifty.registerScreenController(this);
    Main.nifty.fromXml("Interface/LoginScreen.xml", "GScreenLogin");
    //Main.nifty.fromXmlWithoutStartScreen("Interface/DialogScreen.xml");

    this.app.getFlyByCamera().setDragToRotate(true);
  }

  @Override
  public void stateDetached(AppStateManager stateManager) {
    super.stateDetached(stateManager); //To change body of generated methods, choose Tools | Templates.
    this.app.getGuiViewPort().removeProcessor(Main.niftyJmeDisplay);
  }

  public void login() {
    if (app.getStateManager().getState(GameJoltAppState.class)
            .login(Main.nifty.getCurrentScreen().findNiftyControl("GTextfieldNick", TextField.class).getRealText(),
            Main.nifty.getCurrentScreen().findNiftyControl("GTextfieldPassword", TextField.class).getRealText())) {

      s = "Sucess Logged!";
      labelResult.setColor(new Color("#0f0f"));
    } else {
      s = "Failed Logged!";
      labelResult.setColor(new Color("#f00f"));
    }
    labelResult.setText(s);
     
    Main.nifty.showPopup(Main.nifty.getCurrentScreen(), popupElement.getId(), null);
  }

  public void cancel() {
    app.getStateManager().getState(MenuAppState.class).retornaMenu();
  }

  public void bind(Nifty nifty, Screen screen) {
    System.out.println("bind( " + screen.getScreenId() + ")");

    if (screen.getScreenId().equals("GScreenLogin")) {
      popupElement = Main.nifty.createPopup("popupExit");
      labelResult = popupElement.findNiftyControl("GLabelSucess", Label.class);
      
    }


  }

  public void onStartScreen() {
  }

  public void onEndScreen() {
  }
  
  public void close(){
    Main.nifty.closePopup(popupElement.getId());
  }
}
