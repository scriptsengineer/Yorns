/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns;

import br.com.expressobits.games.yorns.entidades.PlayerControl;
import br.com.expressobits.games.yorns.gj.GameJoltAppState;
import br.com.expressobits.games.yorns.menu.HUDScreenAppState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Rafael
 */
public class InputAppState extends AbstractAppState implements ActionListener, AnalogListener {

  private Main app;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (Main) app;


    this.app.getInputManager().addMapping("up", new KeyTrigger(KeyInput.KEY_W));
    this.app.getInputManager().addListener(this, "up");
    this.app.getInputManager().addMapping("down", new KeyTrigger(KeyInput.KEY_S));
    this.app.getInputManager().addListener(this, "down");
    this.app.getInputManager().addMapping("left", new KeyTrigger(KeyInput.KEY_A));
    this.app.getInputManager().addListener(this, "left");
    this.app.getInputManager().addMapping("right", new KeyTrigger(KeyInput.KEY_D));
    this.app.getInputManager().addListener(this, "right");
    this.app.getInputManager().addMapping("mousePick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    this.app.getInputManager().addListener(this, "mousePick");

    this.app.getInputManager().addMapping("retornaMenu", new KeyTrigger(KeyInput.KEY_SPACE));
    this.app.getInputManager().addListener(this, "retornaMenu");

  }

  public void onAction(String name, boolean isPressed, float tpf) {
    if (name.equals("Game") && !isPressed) {
      app.getStateManager().getState(StateManager.class).changeGameAppState();
    }
    if (app.getStateManager().hasState(app.getStateManager().getState(GameAppState.class))) {
      if (app.getRootNode().getChild("Player") != null && (Boolean) app.getRootNode().getChild("Player").getUserData("alive")) {

        if (name.equals("up")) {
          app.getRootNode().getChild("Player").getControl(PlayerControl.class).up = isPressed;
        }
        if (name.equals("down")) {
          app.getRootNode().getChild("Player").getControl(PlayerControl.class).down = isPressed;
        }
        if (name.equals("left")) {
          app.getRootNode().getChild("Player").getControl(PlayerControl.class).left = isPressed;
        }
        if (name.equals("right")) {
          app.getRootNode().getChild("Player").getControl(PlayerControl.class).right = isPressed;
        }
        if (name.equals("retornaMenu") && !isPressed && Main.debug) {
          System.out.println("ACTION DEBUG");
          app.getStateManager().getState(HUDScreenAppState.class).showBubble(
                  "sdjkasjkd",
                          2000);
        }
      } else {
        
      }

    }
  }

  public void onAnalog(String name, float value, float tpf) {
    if (app.getRootNode().getChild("Player") != null && (Boolean) app.getRootNode().getChild("Player").getUserData("alive")) {
      if (name.equals("mousePick")) {
        app.getStateManager().getState(StateManager.class).gameAppState.entidadeAppState.shootBulletPlayer();
      }
    }
  }
}
