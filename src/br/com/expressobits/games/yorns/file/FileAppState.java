/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.file;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Rafael
 */
public class FileAppState extends AbstractAppState {

  SimpleApplication app;
  private static String FORMAT = ".dat";
  private static String LOCAL = "resources/";

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
    this.app = (SimpleApplication) app;
  }

  public ArrayList<String> loadFile(String fileName) {
    ArrayList<String> lines = new ArrayList<String>();

    try {
      FileReader fileReader = new FileReader(new File(LOCAL+fileName+FORMAT));
      BufferedReader reader = new BufferedReader(fileReader);
      String line = reader.readLine();
      while (line != null) {
        if(line.contains("//")){
          System.out.println(line);
        }else{
          lines.add(line);
        
        }
        line = reader.readLine();
        
      }

      return lines;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void saveHighscore(ArrayList<String> lines, String fileName) {
    try {
      FileWriter writer = new FileWriter(new File(LOCAL+fileName+FORMAT), false);
      for (int i = 0; i < lines.size(); i++) {
        writer.write(lines.get(i) + System.getProperty("line.separator"));
      }
      
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
