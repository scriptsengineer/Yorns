/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades.warp;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import java.awt.Rectangle;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import java.util.ArrayList;

/**
 *
 * @author Rafael
 */
public class Grid {

  private Node gridNode;
  private Spring[] springs;
  private PointMass[][] points;
  private Geometry defaultLine;
  private Geometry thickLine;
  private ColorRGBA color;

  public Grid(Rectangle size, Vector2f spacing, Node guiNode, AssetManager assetManager) {
    gridNode = new Node();
    guiNode.attachChild(gridNode);
    defaultLine = createLine(1f, assetManager);
    thickLine = createLine(3f, assetManager);
    color = ColorRGBA.Magenta;

    ArrayList<Spring> springList = new ArrayList<Spring>();
    float stiffness = 0.28f;
    float damping = 0.06f;

    int numColumns = (int) (size.width / spacing.x) + 2;
    int numRows = (int) (size.height / spacing.y) + 2;
    points = new PointMass[numColumns][numRows];

    PointMass[][] fixedPoints = new PointMass[numColumns][numRows];

    /*
     * A primeira para laço cria duas massas regulares
     * e imóveis massas em cada interseção da grade.
     * Nós realmente não vai usar todas as massas imóveis,
     * e as massas não utilizados serão simplesmente lixo
     * coletado em algum momento após o construtor termina.
     * Poderíamos otimizar, evitando a criação de objetos
     * desnecessários, mas desde que a grade é geralmente
     * criado apenas uma vez, ele não vai fazer muita diferença.
     */
    // create the point masses
    float xCoord = 0, yCoord = 0;
    for (int row = 0; row < numRows; row++) {
      for (int column = 0; column < numColumns; column++) {
        points[column][row] = new PointMass(new Vector3f(xCoord, yCoord, 0), 1);
        fixedPoints[column][row] = new PointMass(new Vector3f(xCoord, yCoord, 0), 0);
        xCoord += spacing.x;
      }
      yCoord += spacing.y;
      xCoord = 0;
    }

    // link the point masses with springs
    Geometry line;
    for (int y = 0; y < numRows; y++) {
      for (int x = 0; x < numColumns; x++) {
        if (x == 0 || y == 0 || x == numColumns - 1 || y == numRows - 1) {
          springList.add(new Spring(fixedPoints[x][y], points[x][y], 0.5f, 0.1f, gridNode, false, null));
        } else if (x % 3 == 0 && y % 3 == 0) {
          springList.add(new Spring(fixedPoints[x][y], points[x][y], 0.005f, 0.02f, gridNode, false, null));
        }
        if (x > 0) {
          if (y % 2 == 0) {
            //ALTERADO
            line = thickLine;
          } else {
            line = thickLine;
          }
          springList.add(new Spring(points[x - 1][y], points[x][y], stiffness, damping, gridNode, true, line.clone()));
        }
        if (x > 0 && y > 0) {
          Geometry additionalLine = thickLine.clone();
          additionalLine.addControl(new AdditionalLineControl(points[x - 1][y], points[x][y], points[x - 1][y - 1], points[x][y - 1],0.25f));
          gridNode.attachChild(additionalLine);

          Geometry additionalLine2 = thickLine.clone();
          additionalLine2.addControl(new AdditionalLineControl(points[x][y - 1], points[x][y], points[x - 1][y - 1], points[x - 1][y],0.25f));
          gridNode.attachChild(additionalLine2);
        }
        if (x > 0 && y > 0) {
          Geometry additionalLine = thickLine.clone();
          additionalLine.addControl(new AdditionalLineControl(points[x - 1][y], points[x][y], points[x - 1][y - 1], points[x][y - 1],0.75f));
          gridNode.attachChild(additionalLine);

          Geometry additionalLine2 = thickLine.clone();
          additionalLine2.addControl(new AdditionalLineControl(points[x][y - 1], points[x][y], points[x - 1][y - 1], points[x - 1][y],0.75f));
          gridNode.attachChild(additionalLine2);
        }
        if (x > 0 && y > 0) {
          Geometry additionalLine = thickLine.clone();
          additionalLine.addControl(new AdditionalLineControl(points[x - 1][y], points[x][y], points[x - 1][y - 1], points[x][y - 1],0.5f));
          gridNode.attachChild(additionalLine);

          Geometry additionalLine2 = thickLine.clone();
          additionalLine2.addControl(new AdditionalLineControl(points[x][y - 1], points[x][y], points[x - 1][y - 1], points[x - 1][y],0.5f));
          gridNode.attachChild(additionalLine2);
        }
        if (y > 0) {
          if (x % 2 == 0) {
            //ALTERADO
            line = thickLine;
          } else {
            line = thickLine;
          }
          springList.add(new Spring(points[x][y - 1], points[x][y], stiffness, damping, gridNode, true, line.clone()));
        }
      }
    }
    springs = new Spring[springList.size()];
    for (int i = 0; i < springList.size(); i++) {
      springs[i] = springList.get(i);
    }
  }

  private Geometry createLine(float thickness, AssetManager assetManager) {
    Vector3f[] vertices = {new Vector3f(0, 0, 0), new Vector3f(0, 0, 1)};
    int[] indices = {0, 1};

    Mesh lineMesh = new Mesh();
    lineMesh.setMode(Mesh.Mode.Lines);
    lineMesh.setLineWidth(thickness);
    lineMesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
    lineMesh.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createIntBuffer(indices));
    lineMesh.updateBound();

    Geometry lineGeom = new Geometry("lineMesh", lineMesh);
    Material matWireframe = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    matWireframe.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
    matWireframe.setColor("Color", new ColorRGBA(0.545f, 0f, 0.545f, 0.05f));
    matWireframe.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.AlphaAdditive);
    lineGeom.setMaterial(matWireframe);

    return lineGeom;
  }

  public void update(float tpf) {
    for (int i = 0; i < springs.length; i++) {
      springs[i].update(tpf);
    }

    for (int x = 0; x < points.length; x++) {
      for (int y = 0; y < points[0].length; y++) {
        points[x][y].update(tpf);
      }
    }
  }

  /**
   * 
   * @param force Força
   * @param position Posição de origem
   * @param radius raio total
   */
 public void applyDirectedForce(Vector3f force, Vector3f position, float radius) {
    for (int x = 0; x < points.length; x++) {
      for (int y = 0; y < points[0].length; y++) {
        if (position.distanceSquared(points[x][y].getPosition()) < radius * radius) {
          float forceFactor = 10 / (10 + position.distance(points[x][y].getPosition()));
          points[x][y].applyForce(force.mult(forceFactor));
        }
      }
    }
  }

  public void applyImplosiveForce(float force, Vector3f position, float radius) {
    for (int x = 0; x < points.length; x++) {
      for (int y = 0; y < points[0].length; y++) {
        float dist = position.distanceSquared(points[x][y].getPosition());
        if (dist < radius * radius) {
          Vector3f forceVec = position.subtract(points[x][y].getPosition());
          forceVec.multLocal(10f * force / (100 + dist));
          points[x][y].applyForce(forceVec);
          points[x][y].increaseDamping(0.6f);
        }
      }
    }
  }

  public void applyExplosiveForce(float force, Vector3f position, float radius) {
    for (int x = 0; x < points.length; x++) {
      for (int y = 0; y < points[0].length; y++) {
        float dist = position.distanceSquared(points[x][y].getPosition());
        if (dist < radius * radius) {
          Vector3f forceVec = position.subtract(points[x][y].getPosition());
          forceVec.multLocal(-100f * force / (10000 + dist));
          points[x][y].applyForce(forceVec);
          points[x][y].increaseDamping(0.6f);
        }
      }
    }
  }
}
