/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.hud;

import br.com.expressobits.games.yorns.Main;
import br.com.expressobits.games.yorns.entidades.EntidadeAppState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Rafael
 */
public class HUDAppStateGame extends AbstractAppState {

  SimpleApplication app;
  private final int fontSize = 50;
  public int lives;
  public int score;
  public int multiplier;
  private long specialActivationTime;
  private final int specialExpiryTime = 2000;
  private long trophyTime;
  private final int TROPHYTIMELIMIT = 3000;
  private Node imageTrophy;
  private BitmapFont guiFont;
  private BitmapText specialText;
  private BitmapText gameOverText;
  private BitmapText spaceButtonText;
  private BitmapText postHighScore;
  private BitmapText achieviedTrophyText;
  private Node HUDgame;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (SimpleApplication) app;
    initGui();
  }

  @Override
  public void update(float tpf) {
    super.update(tpf);
    long timeSpecial = System.currentTimeMillis() - specialActivationTime;
    long timeTrophy = System.currentTimeMillis() - trophyTime;

    if (timeSpecial > specialExpiryTime) {

      removeBitmap(specialText);

    } else if (timeSpecial < specialExpiryTime) {
    }

    if (timeTrophy > TROPHYTIMELIMIT) {
      removeBitmap(achieviedTrophyText);
      if (imageTrophy != null) {
        HUDgame.detachChild(imageTrophy);
      }

    } else if (timeTrophy < TROPHYTIMELIMIT) {
    }
    updateHUD();
  }

  @Override
  public void stateAttached(AppStateManager stateManager) {
    super.stateAttached(stateManager); //To change body of generated methods, choose Tools | Templates.

  }

  @Override
  public void stateDetached(AppStateManager stateManager) {
    super.stateDetached(stateManager); //To change body of generated methods, choose Tools | Templates.
    app.getGuiNode().detachChild(HUDgame);
    this.app.getGuiViewPort().removeProcessor(Main.niftyJmeDisplay);
  }

  public void addSpecial(String name, ColorRGBA color, Vector3f loc) {
    if (HUDgame.hasChild(specialText)) {
      HUDgame.detachChild(specialText);
    }
    specialText = new BitmapText(guiFont, false);
    specialText.setColor(color);
    specialText.setText(name);
    //specialText.setLocalTranslation(
    //10,
    //app.getContext().getSettings().getHeight() - 30,
    //1);
    specialText.setLocalTranslation(loc.x - specialText.getLineWidth() / 4, loc.y, loc.z);
    specialText.setSize(fontSize / 2);

    HUDgame.attachChild(specialText);
    specialActivationTime = System.currentTimeMillis();
  }

  public void removeBitmap(BitmapText bmpt) {
    if (bmpt != null) {
      HUDgame.detachChild(bmpt);
    }

  }

  private void initGui() {
    HUDgame = new Node();

    guiFont = app.getAssetManager().loadFont("Interface/Fonts/PocketCalculatorMaior.fnt");



    app.getGuiNode().attachChild(HUDgame);
  }


  private void updateHUD() {
    if (app.getStateManager().getState(EntidadeAppState.class) != null) {
      
     
      //levelText.setText("Level " + ((Integer) app.getStateManager().getState(EntidadeAppState.class).level + 1));


    }
  }


  private int loadHighscore() {
    try {
      FileReader fileReader = new FileReader(new File("highscore.txt"));
      BufferedReader reader = new BufferedReader(fileReader);
      String line = reader.readLine();
      return Integer.valueOf(line);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  private void saveHighscore() {
    try {
      FileWriter writer = new FileWriter(new File("highscore.txt"), false);
      writer.write(score + System.getProperty("line.separator"));
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void addGameOver(ColorRGBA color, String text) {
    gameOverText = new BitmapText(guiFont, true);
    gameOverText.setText("GAME OVER");
    gameOverText.setLocalTranslation(
            (app.getContext().getSettings().getWidth() / 2)
            - (gameOverText.getLineWidth()/2),
            app.getContext().getSettings().getHeight() / 2, 1);
    gameOverText.setColor(ColorRGBA.DarkGray);
    gameOverText.setSize(fontSize);

    spaceButtonText = new BitmapText(guiFont, true);
    spaceButtonText.setText("Press space button to menu");
    spaceButtonText.setLocalTranslation(
            (app.getContext().getSettings().getWidth() / 2)
            - (spaceButtonText.getLineWidth() / 4),
            (app.getContext().getSettings().getHeight() / 2 - 60), 1);
    spaceButtonText.setColor(ColorRGBA.White);
    spaceButtonText.setSize(fontSize / 2);


    postHighScore = new BitmapText(guiFont, true);
    postHighScore.setText(text);
    postHighScore.setLocalTranslation(
            (app.getContext().getSettings().getWidth() / 2)
            - (postHighScore.getLineWidth() / 4),
            (app.getContext().getSettings().getHeight() / 2 - 100), 1);
    postHighScore.setColor(color);
    postHighScore.setSize(fontSize / 2);

    HUDgame.attachChild(spaceButtonText);
    HUDgame.attachChild(postHighScore);

    HUDgame.attachChild(gameOverText);
  }

  public void addTrophyAchieviment(String name, String image,ColorRGBA color) {

    if (HUDgame.hasChild(achieviedTrophyText)) {
      HUDgame.detachChild(achieviedTrophyText);
    }if (HUDgame.hasChild(imageTrophy)) {
      HUDgame.detachChild(imageTrophy);
    }
    
    imageTrophy = new Node();
    //Carrega imagem
    Picture pic = new Picture(name);
    Texture2D tex = (Texture2D) app.getAssetManager()
            .loadTexture("Textures/Trophy/" + image + ".png");
    pic.setTexture(app.getAssetManager(), tex, true);

    //Ajuste de imagem
    float width = 64;
    float height = 64;
    pic.setWidth(width);
    pic.setHeight(height);
    pic.move(-width / 2, -height / 2, 0);

    //Adiciona um material para imagem
    Material picMat = new Material(app.getAssetManager(),
            "Common/MatDefs/Gui/Gui.j3md");
    //picMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.AlphaAdditive);
    imageTrophy.setMaterial(picMat);

    //Anexa o picture para o no e retorna
    imageTrophy.attachChild(pic);
    imageTrophy.setLocalTranslation(
            (app.getContext().getSettings().getWidth() / 2),
            (app.getContext().getSettings().getHeight() / 2)
            - 100, 1);

    achieviedTrophyText = new BitmapText(guiFont, true);
    achieviedTrophyText.setText(name);
    achieviedTrophyText.setLocalTranslation(
            (app.getContext().getSettings().getWidth() / 2)
            - (achieviedTrophyText.getLineWidth()/2),
            app.getContext().getSettings().getHeight() / 2, 1);
    color.set(color.r, color.g, color.b, 1f);
    achieviedTrophyText.setColor(color);
    achieviedTrophyText.setSize(fontSize);

    HUDgame.attachChild(imageTrophy);
    HUDgame.attachChild(achieviedTrophyText);
    trophyTime = System.currentTimeMillis();

  }
}
