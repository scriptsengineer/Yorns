/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades.warp;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author Rafael
 */
public class Spring {
    private PointMass end1;
    private PointMass end2;
    private float targetLength;
    private float stiffness;
    private float damping;
 
    /**
     * 
     * @param end1
     * @param end2
     * @param stiffness
     * @param damping
     * @param gridNode
     * @param visible
     * @param defaultLine 
     */
    public Spring(PointMass end1, PointMass end2, float stiffness, float damping, Node gridNode, boolean visible, Geometry defaultLine) {
        /**
         * Quando se criar uma mola, que defina o comprimento natural da mola
         * a ser apenas ligeiramente menor do que a distância entre os dois pontos finais.
         * Isso mantém a grade esticada, mesmo quando em repouso, e melhora a aparência um pouco.
         */
      
        this.end1 = end1;
        this.end2 = end2;
        this.stiffness = stiffness;
        this.damping = damping;
        targetLength = end1.getPosition().distance(end2.getPosition()) * 0.95f;
 
        if (visible) {
            defaultLine.addControl(new LineControl(end1,end2));
            gridNode.attachChild(defaultLine);
        }
    }
 
    /**
     * método primeiro verifica se a mola é esticada além
     * do seu comprimento natural. Se ele não estiver esticado,
     * nada acontece. Se for, nós usamos a Lei de Hooke modificada
     * para encontrar a força da primavera e aplicá-lo para as duas
     * massas conectadas.
     * @param tpf 
     */
    public void update(float tpf) {
        Vector3f x = end1.getPosition().subtract(end2.getPosition());
 
        float length = x.length();
        if (length > targetLength) {
            x.normalizeLocal();
            x.multLocal(length - targetLength);
 
            Vector3f dv = end2.getVelocity().subtract(end1.getVelocity());
            Vector3f force = x.mult(stiffness);
            force.subtract(dv.mult(damping/10f));
 
            end1.applyForce(force.negate());
            end2.applyForce(force);
        }
    }
}