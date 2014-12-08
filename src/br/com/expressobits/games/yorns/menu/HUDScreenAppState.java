/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.menu;

import br.com.expressobits.games.yorns.GameAppState;
import br.com.expressobits.games.yorns.Main;
import br.com.expressobits.games.yorns.StateManager;
import br.com.expressobits.games.yorns.entidades.EntidadeAppState;
import br.com.expressobits.games.yorns.entidades.LevelAppState;
import br.com.expressobits.games.yorns.gj.GameJoltAppState;
import br.com.expressobits.games.yorns.hud.HUDAppStateGame;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.loaderv2.types.LayerType;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import java.util.ArrayList;

/**
 *
 * @author Rafael
 */
public class HUDScreenAppState extends AbstractAppState implements ScreenController {

  public SimpleApplication app;
  static String s;
  long multiplicarTime;
  private static final long TIMEDELAY = 2000;
  Label labelScore;
  Label labelLevel;
  Label labelCombo;
  Label labelPopupPoints;
  
  private Element layerBubble;
  private long bubbleInitTime = 1000;
  private long bubbleTimeExpiry = 2000;
  
  
  Element popupElementGameOverLogged;
  Element popupElementGameOverNotLogged;
  Element popupElementInfo;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (SimpleApplication) app;
    this.app.getGuiViewPort().addProcessor(Main.niftyJmeDisplay);
    Main.nifty.registerScreenController(this);
    //Ler seu XML e inicializar o ScreenController customizado
    Main.nifty.fromXml("Interface/hudScreen.xml", "GScreenHudScreen", this);
    this.app.getFlyByCamera().setDragToRotate(true);
    
    
  }

  @Override
  public void update(float tpf) {
    super.update(tpf); //To change body of generated methods, choose Tools | Templates.
    if (app.getStateManager().getState(EntidadeAppState.class) != null
            && labelLevel != null && labelCombo != null && labelScore != null) {
      labelScore.setColor(new Color("#0f0a"));
      labelScore.setText(String.format("%8d", (Integer) app.getRootNode().getChild(EntidadeAppState.NODEPLAYER).getUserData("score")));
      labelLevel.setColor(new Color("#f0fa"));
      labelLevel.setText("Level " + ((Integer) app.getStateManager().getState(LevelAppState.class).getLevel()));
     

      multiplicarTime = System.currentTimeMillis();
      long timeMult = System.currentTimeMillis() - multiplicarTime;
      if (timeMult > TIMEDELAY) {
        labelCombo.setColor(new Color("#ff00"));
        labelCombo.setEnabled(false);
      } else {
        labelCombo.setEnabled(true);
        int m = app.getStateManager().getState(EntidadeAppState.class).multiplicar;

        if (m < 9) {
          labelCombo.setColor(new Color(1, 1, 0, m-1 - ((m-1) * 25f)));
        } else {
          labelCombo.setColor(new Color(1, 1 - ((m - 10) * 0.1f), 0, 1));
        }
        labelCombo.setText("X " + m);
      }
    }
    
    //Update of bubble
    if(bubbleTimeExpiry < System.currentTimeMillis() - bubbleInitTime){
      closeBubble();
    }
  }

  @Override
  public void stateDetached(AppStateManager stateManager) {
    super.stateDetached(stateManager);
    this.app.getGuiViewPort().removeProcessor(Main.niftyJmeDisplay);
    Main.nifty.removeScreen("GScreenHudScreen");
  }

  @Override
  public void stateAttached(AppStateManager stateManager) {
    super.stateAttached(stateManager); //To change body of generated methods, choose Tools | Templates.

  }

  public void bind(Nifty nifty, Screen screen) {
    System.out.println("bind( " + screen.getScreenId() + ")");
    labelScore = nifty.getCurrentScreen().findNiftyControl("GLabelScore", Label.class);
    labelLevel = nifty.getCurrentScreen().findNiftyControl("GLabelLevel", Label.class);
    labelCombo = nifty.getCurrentScreen().findNiftyControl("GLabelCombo", Label.class);
    
    
    
    popupElementGameOverLogged = Main.nifty.createPopup("popupGameOverLogged");
    
    labelPopupPoints = popupElementGameOverLogged.findNiftyControl("GLabelPoints", Label.class);
    popupElementInfo = Main.nifty.createPopup("popupInfo");
    
    for (int i = 0; i < 3; i++) {
      if(!GameJoltAppState.connect){
        String s = "Login to view highscore!";
      System.out.println(s);
      Main.nifty.getCurrentScreen().findNiftyControl("GLabel" + (i + 1), Label.class).setText(s);
      Main.nifty.getCurrentScreen().findNiftyControl("GLabel" + (i + 1), Label.class).setColor(new Color("#fff8"));
      Main.nifty.getCurrentScreen().findElementByName("Image" + (i + 1)).getRenderer(ImageRenderer.class)
              .setImage(app.getStateManager().getState(GameJoltAppState.class).imagesScores.get(i));
      }else{
      String s = 
              app.getStateManager().getState(GameJoltAppState.class).highScores.get(i).getUsername()+" - "+
              app.getStateManager().getState(GameJoltAppState.class).highScores.get(i).getScoreValue();
      System.out.println(s);
      Main.nifty.getCurrentScreen().findNiftyControl("GLabel" + (i + 1), Label.class).setText(s);
      Main.nifty.getCurrentScreen().findNiftyControl("GLabel" + (i + 1), Label.class).setColor(new Color("#fff8"));
      Main.nifty.getCurrentScreen().findElementByName("Image" + (i + 1)).getRenderer(ImageRenderer.class)
              .setImage(app.getStateManager().getState(GameJoltAppState.class).imagesScores.get(i));
    }
    }
    
    layerBubble = nifty.getCurrentScreen().findElementByName("GLayerBubble");
    layerBubble.hideWithoutEffect();
  }
  
  public void gameOver(){
    app.getStateManager().getState(StateManager.class).changeCursor(false);
      Main.nifty.showPopup(Main.nifty.getCurrentScreen(), popupElementGameOverLogged.getId(), null);
    
    
    labelPopupPoints.setText("Your score\n"+
            labelScore.getText()
            );
  }
  
  public void post(){
    if (app.getStateManager().getState(GameJoltAppState.class).publicYourPoints(
            (Integer)
            (app.getRootNode().getChild(EntidadeAppState.NODEPLAYER).getUserData("score"))
            )){
          app.getStateManager().getState(HUDAppStateGame.class)
                  .addGameOver(ColorRGBA.Green, "Public your score in GameJolt");
        } else {
          app.getStateManager().getState(HUDAppStateGame.class)
                  .addGameOver(ColorRGBA.Red, "Public your score in GameJolt without login");
        }
    
    cancel();
  }
  
  public void cancel(){
    Main.nifty.closePopup(popupElementGameOverLogged.getId());
    app.getStateManager().getState(StateManager.class).changeGameAppState();
    
  }
  
  /**
   * Exibe um balão de informação na tela por tempo determinado.
   * @param text Texto ao ser exibido(Compativel com quebra de linha)
   * @param time Tempo que será exibido.
   */
  public void showBubble(String text,long time){
    showBubble(text, time,"Textures/Default_bubble.png");
  }
  
  public void showBubble(String text,long time,String image){
    layerBubble.findNiftyControl("GLabelBubble",Label.class).setText(text);
    
    Main.nifty.getCurrentScreen().findElementByName("GLabelBubbleImage").getRenderer(ImageRenderer.class)
              .setImage(Main.nifty.createImage(image,false));
    
    bubbleTimeExpiry = time;
    bubbleInitTime = System.currentTimeMillis();
    layerBubble.show();
  }
  
  private void closeBubble(){
    layerBubble.hide();
  }

  public void onStartScreen() {
  }

  public void onEndScreen() {
  }

}
