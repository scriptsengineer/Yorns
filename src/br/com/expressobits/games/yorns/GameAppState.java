/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns;

import br.com.expressobits.games.yorns.audio.AudioAppState;
import br.com.expressobits.games.yorns.entidades.EntidadeAppState;
import br.com.expressobits.games.yorns.hud.HUDAppStateGame;
import br.com.expressobits.games.yorns.menu.HUDScreenAppState;
import br.com.expressobits.games.yorns.settings.SettingAppState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

/**
 *
 * @author Rafael
 */
public class GameAppState extends AbstractAppState{
    
    private SimpleApplication app;
    public EntidadeAppState entidadeAppState;
    public HUDAppStateGame hUDAppState;
    public HUDScreenAppState hUDScreenAppState;
    
    public static boolean gameOver=false;
    public static boolean gameWarp=false;
    public static boolean audioMusic=true;
    public static boolean audioEffect=true;
    
    public static int width=800;
    public static int height=600;
    
    public AudioAppState audioAppState;
    

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
        this.app = (SimpleApplication)app;
        
        app.getStateManager().getState(StateManager.class).changeCursor(true);
        
        
        
        gameOver = false;
        gameWarp = app.getContext().getSettings().getBoolean(SettingAppState.GAMEWARP);
        audioMusic = app.getContext().getSettings().getBoolean(SettingAppState.AUDIOMUSIC);
        audioEffect = app.getContext().getSettings().getBoolean(SettingAppState.AUDIOEFFECT);
                
        entidadeAppState = new EntidadeAppState(this.app);
        hUDAppState = new HUDAppStateGame();
        audioAppState = new AudioAppState();
        hUDScreenAppState = new HUDScreenAppState();
        
        app.getStateManager().attach(entidadeAppState);
        app.getStateManager().attach(hUDAppState);
        app.getStateManager().attach(audioAppState);
        app.getStateManager().attach(hUDScreenAppState);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf); //To change body of generated methods, choose Tools | Templates.
        
    }
    
    

    @Override
    public void stateAttached(AppStateManager stateManager) {
        super.stateAttached(stateManager); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        super.stateDetached(stateManager); //To change body of generated methods, choose Tools | Templates.
        
        app.getStateManager().detach(entidadeAppState);
        app.getStateManager().detach(hUDAppState);
        app.getStateManager().detach(audioAppState);
        app.getStateManager().detach(hUDScreenAppState);
    }

    
    
    public static float getAngleFromVector(Vector3f vec) {
        Vector2f vec2 = new Vector2f(vec.x, vec.y);
        return vec2.getAngle();
    }

    public static Vector3f getVectorFromAngle(float angle) {
        return new Vector3f(FastMath.cos(angle), FastMath.sin(angle), 0);
    }
    
    public static Vector2f getMouseCorrectAim(Vector2f mouse){
      return new Vector2f(mouse.getX()+16,mouse.getY()-16);
    }
    
}
