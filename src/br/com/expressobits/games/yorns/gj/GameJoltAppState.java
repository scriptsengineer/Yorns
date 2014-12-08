/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.gj;

import br.com.expressobits.games.yorns.Main;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.plugins.UrlLocator;
import de.lessvoid.nifty.render.NiftyImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.gamejolt.GameJoltAPI;
import org.gamejolt.Highscore;
import org.gamejolt.Trophy;
import org.gamejolt.User;

/**
 *
 * @author Rafael
 */
public class GameJoltAppState extends AbstractAppState {

  SimpleApplication app;
  public static boolean logged;
  final static int GAME_ID = 37955;
  final static String GAME_PRIVATE_KEY = "20eac15bfbaa5893383e7310a6f2c078";
  static String USER_NAME = "NONE";
  static String USER_TOKEN = "NONE";
  public ArrayList<Trophy> trophys;
  public ArrayList<Trophy> trophysGet;
  public ArrayList<Trophy> trophysNotGet;
  public final static String TROPHY_BRONZE_BLUE_KILL = "12696";
  public final static String TROPHY_BRONZE_YELLOW_KILL = "12695";
  public final static String TROPHY_BRONZE_RED_KILL = "12694";
  public final static String TROPHY_BRONZE_GREEN_KILL = "12693";
  public final static String TROPHY_SILVER_BLUE_KILL = "12717";
  public final static String TROPHY_SILVER_YELLOW_KILL = "12716";
  public final static String TROPHY_SILVER_RED_KILL = "12715";
  public final static String TROPHY_SILVER_GREEN_KILL = "12714";
  public final static String TROPHY_GOLD_BLUE_KILL = "1";
  public final static String TROPHY_GOLD_YELLOW_KILL = "1";
  public final static String TROPHY_GOLD_RED_KILL = "1";
  public final static String TROPHY_GOLD_GREEN_KILL = "1";
  public GameJoltAPI api;
  public static boolean connect = false;
  public ArrayList<Highscore> highScores;
  public ArrayList<String> imagesURLScores;
  public ArrayList<NiftyImage> imagesScores = new ArrayList<NiftyImage>();

  public GameJoltAppState() {
    connect = isInternetReachable();
  }

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (SimpleApplication) app;
    
    System.out.println("Connection with internet status:"+connect);
    if (connect) {
      api = new GameJoltAPI(GAME_ID, GAME_PRIVATE_KEY);
      api.setVerbose(Main.debug);
      Main.online = connect;

      highScores = api.getHighscores(true, 5);
      imagesURLScores = getHighScoresURLImages();
      for (int i = 0; i < 5; i++) {
        if (imagesURLScores.get(i).contains("http:")) {
          app.getAssetManager().registerLocator(imagesURLScores.get(i), UrlLocator.class);
          imagesScores.add(Main.nifty.createImage(imagesURLScores.get(i), false));
          app.getAssetManager().unregisterLocator(imagesURLScores.get(i), UrlLocator.class);
        } else {
          imagesScores.add(Main.nifty.createImage(imagesURLScores.get(i), false));
        }

      }
      if (api.hasQuickplay()) {
        USER_NAME = api.getQuickplayUser().getName();
        USER_TOKEN = api.getQuickplayUserCredientals().getToken();
        logged = true;
        updateInternet();
      }
    } else {
      imagesURLScores = getHighScoresURLImages();
      for (int i = 0; i < 5; i++) {


        imagesScores.add(Main.nifty.createImage("Textures/noImage.png", false));


      }
    }




  }

  public boolean login(String name, String token) {
    if (connect) {
      if (api.verifyUser(name, token)) {
        USER_NAME = name;
        USER_TOKEN = token;
        System.out.println("LOGIN SUCESS NAME " + name + " TOKEN " + token);
        updateInternet();
        logged = true;
        return true;
      }
      System.out.println("LOGIN FAILED NAME " + name + " TOKEN " + token);
      logged = false;
    }

    return false;
  }

  public void updateInternet() {
    trophys = api.getTrophies();
    trophysGet = api.getTrophies(Trophy.Achieved.TRUE);
    trophysNotGet = api.getTrophies(Trophy.Achieved.FALSE);
  }

  public boolean isLogged() {
    return api.isVerified();
  }

  public ArrayList<String> getHighScoresURLImages() {
    ArrayList<String> s = new ArrayList<String>();
    if (connect) {
      ArrayList<Highscore> h = highScores;
      for (int i = 0; i < h.size(); i++) {
        int id = h.get(i).getUserId();
        User user = api.getUser(id);
        if (user != null) {
          s.add(user.getAvatarURL());
        } else {
          s.add("Textures/noImage.png");
        }
      }
    } else {
      for (int i = 0; i < 5; i++) {
        s.add("Textures/noImage.png");
      }
    }


    return s;
  }

  public boolean publicYourPoints(int points) {
    if (Main.debug && !connect) {
      return false;
    } else {
      if (verifyUser() && !USER_NAME.equals("TheOtherRof")) {
        api.addHighscore("Score " + points, points);
        System.out.println("Public your score " + points);
        return true;
      } else {
        String anonymous = "anonymous" + System.currentTimeMillis();
        api.addHighscore(anonymous, "Score " + points, points);
        System.out.println("Public your score " + points);
        return false;
      }
    }
  }

  public static int getTrophiesSize() {
    GameJoltAPI api = new GameJoltAPI(GAME_ID, GAME_PRIVATE_KEY);
    if (api.verifyUser(USER_NAME, USER_TOKEN)) {
      ArrayList<Trophy> trophies = api.getTrophies(Trophy.Achieved.TRUE);
      api.achieveTrophy(trophies.get(0));


      return trophies.size();
    }
    return 0;
  }

  public boolean verifyUser() {
    if (api.verifyUser(USER_NAME, USER_TOKEN)) {
      return true;
    } else {
      return false;
    }
  }

  public String getNameUser() {
    return USER_NAME;
  }

  public String getNameTrophy(int i) {
    //return api.getTrophy(i).getTitle();
    return "TROPHY NAME";
  }

  public String getImageURLTrophy(int i) {
    //return api.getTrophy(i).getImageURL();
    return "TROPHY URL";
  }

  public void achievedTrophy(String s) {
    if (verifyUser()) {
      //return api.getTrophy(i).getImageURL();
      api.achieveTrophy(getTrophyString(s));
    }
  }

  public Trophy getTrophyString(String id) {

    for (int i = 0; i < trophys.size(); i++) {
      if (trophys.get(i).getId().equals(id)) {
        return trophys.get(i);
      }
    }

    return null;
  }

  //checks for connection to the internet through dummy request
  public static boolean isInternetReachable() {
    try {
      //make a URL to a known source
      URL url = new URL("http://gamejolt.com");

      //open a connection to that source
      HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();

      //trying to retrieve data from the source. If there
      //is no connection, this line will fail
      Object objData = urlConnect.getContent();

    } catch (UnknownHostException  e) {
      // TODO Auto-generated catch block
      System.err.println("No connection!");
      //e.printStackTrace();
      return false;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
