/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.expressobits.games.yorns.entidades.warp;

import com.jme3.math.Vector3f;

/**
 *
 * @author Rafael
 */
public class PointMass {
    private Vector3f position;
    private Vector3f velocity = Vector3f.ZERO;
    
    /**
     * Inverso da massa - Infinitamente inverso a ZERO
     */
    private float inversoDaMassa;
 
    private Vector3f acceleration = Vector3f.ZERO;
    
    
      
    /**
     *  A classe também contém um amortecimento variável,
     * que actua para abrandar a massa gradualmente para baixo
     */
    private float amortecimento = 0.98f;
 
    public PointMass(Vector3f position, float inverseMass) {
        this.position = position;
        this.inversoDaMassa = inverseMass;
    }
 
    public void applyForce(Vector3f force) {
        acceleration.addLocal(force.mult(inversoDaMassa));
    }
 
   
    /**
     *  Use para aumentar temporariamente a quantidade de amortecimento.
     * @param factor 
     */
    public void increaseDamping(float factor) {
        amortecimento *= factor;
    }
 
    /**
     * O método faz o trabalho de deslocar o ponto de massa
     * cada quadro. Começa por fazer integração Euler simplética,
     * o que significa apenas que adicionar a aceleração à velocidade e,
     * em seguida, adicionar a velocidade atualizado para a posição.
     * Isso difere do padrão de integração Euler em que atualizaria
     * a velocidade depois de atualizar a posição.
     * @param tpf 
     */
    public void update(float tpf) {
        velocity.addLocal(acceleration.mult(1f));
        position.addLocal(velocity.mult(0.6f));
        acceleration = Vector3f.ZERO.clone();
 
        /*
         * Depois de atualizar a velocidade e posição, vamos verificar se a velocidade é muito pequena e, se for, vamos defini-lo para zero. Isso pode ser importante para o desempenho, devido à natureza de números de ponto flutuante desnormalizada .
         */
        if (velocity.lengthSquared() < 0.0001f) {
            velocity = Vector3f.ZERO.clone();
        }
 
        velocity.multLocal(amortecimento);
        amortecimento = 0.98f;
        amortecimento = 0.8f;
 
        position.z *= 0.9f;
        if (position.z < 0.01) {position.z = 0;}
    }
 
    public Vector3f getPosition() {
        return position;
    }
 
    public Vector3f getVelocity() {
        return velocity;
    }
}