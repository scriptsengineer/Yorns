/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.audio;

import br.com.expressobits.games.yorns.GameAppState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import java.util.Random;

/**
 *
 * @author Rafael
 */
public class AudioAppState extends AbstractAppState {

  SimpleApplication app;
  private AudioNode music;
  private AudioNode[] shots;
  private AudioNode[] explosions;
  private AudioNode[] spawns;
  private AudioNode[] metal;
  private AudioNode[] shots_enemies;
  static final String MUSIC_SOUND = "Sounds/Electric_Exodus_v1_0_0.ogg";
  static final String EFEITO_SHOOT = "Sounds/Effects/Shoot/shoot-0";
  static final String EFEITO_EXPLOSAO = "Sounds/Effects/explosion-0";
  static final String EFEITO_SPAWN = "Sounds/Effects/spawn-0";
  static final String EFEITA_METAL = "Sounds/Effects/Metal/Metal-0";
  static final String EFEITO_SHOOT_ENEMY = "Sounds/Effects/Shoot/shoot_enemy-0";
  private float volume_efeito = 0.6f;
  private float volume_music = 0.6f;

  public AudioAppState() {
    shots = new AudioNode[1];
    explosions = new AudioNode[1];
    spawns = new AudioNode[8];
    metal = new AudioNode[3];
    shots_enemies = new AudioNode[1];

  }

  private void loadSounds() {
    music = new AudioNode(app.getAssetManager(), MUSIC_SOUND);
    music.setPositional(false);
    music.setReverbEnabled(false);
    music.setLooping(true);
    music.setVolume(volume_music);

    for (int i = 0; i < shots.length; i++) {
      shots[i] = new AudioNode(app.getAssetManager(), EFEITO_SHOOT + (i + 1) + ".wav");
      shots[i].setVolume(volume_efeito);
      shots[i].setPositional(false);
      shots[i].setReverbEnabled(false);
      shots[i].setLooping(false);
    }

    for (int i = 0; i < explosions.length; i++) {
      explosions[i] = new AudioNode(app.getAssetManager(), EFEITO_EXPLOSAO + (i + 1) + ".wav");
      explosions[i].setVolume(volume_efeito);
      explosions[i].setPositional(false);
      explosions[i].setReverbEnabled(false);
      explosions[i].setLooping(false);
    }

    for (int i = 0; i < spawns.length; i++) {
      spawns[i] = new AudioNode(app.getAssetManager(), EFEITO_SPAWN + (i + 1) + ".wav");
      spawns[i].setVolume(volume_efeito);
      spawns[i].setPositional(false);
      spawns[i].setReverbEnabled(false);
      spawns[i].setLooping(false);
    }

    for (int i = 0; i < metal.length; i++) {
      metal[i] = new AudioNode(app.getAssetManager(), EFEITA_METAL + (i + 1) + ".wav");
      metal[i].setVolume(volume_efeito * 2);
      metal[i].setPositional(false);
      metal[i].setReverbEnabled(false);
      metal[i].setLooping(false);
    }

    for (int i = 0; i < shots_enemies.length; i++) {
      shots_enemies[i] = new AudioNode(app.getAssetManager(), EFEITO_SHOOT_ENEMY + (i + 1) + ".wav");
      shots_enemies[i].setVolume(volume_efeito);
      shots_enemies[i].setPositional(false);
      shots_enemies[i].setReverbEnabled(false);
      shots_enemies[i].setLooping(false);
    }
  }

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (SimpleApplication) app;
    loadSounds();
    startMusic();


  }

  @Override
  public void update(float tpf) {
    super.update(tpf); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void stateDetached(AppStateManager stateManager) {
    super.stateDetached(stateManager); //To change body of generated methods, choose Tools | Templates.
    stopMusic();
  }

  public void startMusic() {
    if (GameAppState.audioMusic) {
      music.play();
    }
  }

  public void stopMusic() {
    if (GameAppState.audioMusic) {
      music.stop();
    }
  }

  public void shoot() {
    if (GameAppState.audioEffect) {
      shots[new Random().nextInt(shots.length)].playInstance();
    }
  }

  public void explosion() {
    if (GameAppState.audioEffect) {
      explosions[new Random().nextInt(explosions.length)].playInstance();
    }
  }

  public void spawn() {
    if (GameAppState.audioEffect) {
      spawns[new Random().nextInt(spawns.length)].playInstance();
    }
  }

  public void metal() {
    if (GameAppState.audioEffect) {
      metal[new Random().nextInt(metal.length)].playInstance();
    }
  }

  public void shoot_enemy() {
    if (GameAppState.audioEffect) {
      shots_enemies[new Random().nextInt(shots_enemies.length)].playInstance();
    }

  }
}
