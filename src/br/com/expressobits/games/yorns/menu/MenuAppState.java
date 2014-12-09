/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.menu;

import br.com.expressobits.games.yorns.Main;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 *
 * @author Rafael
 */
public class MenuAppState extends AbstractAppState{

    public Main app;
    public MainMenuScreenAppState mainMenuScreenAppState;
    public LoginScreenAppState loginScreenAppState;
    public AboutGameScreenAppState aboutGameScreenAppState;
    public SettingScreenAppState settingScreenAppState;
    public ScoreScreenAppState scoreScreenAppState;
    public AbstractAppState currentAppState;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
        this.app = (Main)app;
        
        loginScreenAppState = new LoginScreenAppState();
        mainMenuScreenAppState = new MainMenuScreenAppState();
        aboutGameScreenAppState = new AboutGameScreenAppState();
        settingScreenAppState = new SettingScreenAppState();
        scoreScreenAppState = new ScoreScreenAppState();
        app.getStateManager().attach(mainMenuScreenAppState);
        currentAppState = mainMenuScreenAppState;
        
        
    }
    
    @Override
    public void update(float tpf) {
        super.update(tpf); //To change body of generated methods, choose Tools | Templates.
        
    }
    
    public void retornaMenu(){
        app.getStateManager().detach(currentAppState);
        app.getStateManager().attach(mainMenuScreenAppState);
        currentAppState = mainMenuScreenAppState;
    }
    
    public void loginScreen(){
        app.getStateManager().detach(currentAppState);
        app.getStateManager().attach(loginScreenAppState);
        currentAppState = loginScreenAppState;
    }
    
    public void aboutGameScreen(){
        app.getStateManager().detach(currentAppState);
        app.getStateManager().attach(aboutGameScreenAppState);
        currentAppState = aboutGameScreenAppState;
    }
    
    public void settingScreen(){
        app.getStateManager().detach(currentAppState);
        app.getStateManager().attach(settingScreenAppState);
        currentAppState = settingScreenAppState;
    }
    
    public void scoreScreen(){
        app.getStateManager().detach(currentAppState);
        app.getStateManager().attach(scoreScreenAppState);
        currentAppState = scoreScreenAppState;
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        super.stateDetached(stateManager); //To change body of generated methods, choose Tools | Templates.
        app.getStateManager().detach(mainMenuScreenAppState);
    }
    
    
    
    
    
    
    
    
    
    
}
