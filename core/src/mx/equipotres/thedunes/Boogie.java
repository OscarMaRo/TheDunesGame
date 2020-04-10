package mx.equipotres.thedunes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.Point;

public class Boogie extends Objeto {

    // Variables.
    private final int ROTACION = 360 / 8;
    private final int PASO = 10;
    private int vidas;

    // Constructor principal.
    public Boogie(Texture textura, float x, float y) {
        super(textura, x, y);
         vidas = 5;
    }
    public void restarVida(int cantidad){
        vidas -= cantidad;
    }

    public void agregarVida(int cantidad){
        vidas += cantidad;
    }

    // Mover: Mueve el vehiculo a las coordenadas (x, y).
    public void mover() {
        out_of_bounds();
        if (sprite.getRotation() == -90 || sprite.getRotation() == 270) {
            sprite.setX(sprite.getX() + PASO);
            sprite.setY(sprite.getY());
        } else if (sprite.getRotation() == 0) {
            sprite.setX(sprite.getX());
            sprite.setY(sprite.getY() + PASO);
        } else if (Math.abs(sprite.getRotation()) == 180) {
            sprite.setX(sprite.getX());
            sprite.setY(sprite.getY() - PASO);
        } else if (sprite.getRotation() == 90 || sprite.getRotation() == -270) {
            sprite.setX(sprite.getX() - PASO);
            sprite.setY(sprite.getY());
        } else if (sprite.getRotation() == -45 || sprite.getRotation() == 315) {
            sprite.setX(sprite.getX() + PASO);
            sprite.setY(sprite.getY() + PASO);
        } else if (sprite.getRotation() == -135 || sprite.getRotation() == 225) {
            sprite.setX(sprite.getX() + PASO);
            sprite.setY(sprite.getY() - PASO);
        } else if (sprite.getRotation() == 45 || sprite.getRotation() == -315) {
            sprite.setX(sprite.getX() - PASO);
            sprite.setY(sprite.getY() + PASO);
        } else if (sprite.getRotation() == 135 || sprite.getRotation() == -225) {
            sprite.setX(sprite.getX() - PASO);
            sprite.setY(sprite.getY() - PASO);
        }

    }

    // INFORMATION. (x, y) + vertices + rotación.
    public void info() {
        System.out.println("x: " + sprite.getX() + ", y: " + sprite.getY());
        System.out.println("v1: x: " + sprite.getVertices()[SpriteBatch.X1] + ", y: " +
                sprite.getVertices()[SpriteBatch.Y1]);
        System.out.println("v2: x: " + sprite.getVertices()[SpriteBatch.X2] + ", y: " +
                sprite.getVertices()[SpriteBatch.Y2]);
        System.out.println("v3: x: " + sprite.getVertices()[SpriteBatch.X3] + ", y: " +
                sprite.getVertices()[SpriteBatch.Y3]);
        System.out.println("v4: x: " + sprite.getVertices()[SpriteBatch.X4] + ", y: " +
                sprite.getVertices()[SpriteBatch.Y4]);
        System.out.println("rotation: " + sprite.getRotation());
        System.out.println();
    }

    // Computes the distance between two points.
    private float distancia_entre_puntos(float[] p1, float[] p2) {
        return (float)Math.sqrt(Math.pow(p1[0]-p2[0],2) + Math.pow(p1[1]-p2[1],2));
    }

    // Computes the slope between two points.
    private float pendiente(float[] p1, float[] p2) {
        return (p2[1] - p1[1])/(p2[0] - p1[0]);
    }

    // Computes the y-intercept.
    private float y_intercept(float[] p1, float[] p2) {
        return p1[1] - pendiente(p1,p2)*p1[0];
    }

    // Computes the function of the slope.
    private float punto_pendiente(float[] p1, float[] p2, float x) {
        return pendiente(p1,p2)*x + y_intercept(p1,p2);
    }

    // Out of bounds.
    private void out_of_bounds() {
       check_lower_bounds();

    }

    // Lower bounds.
    private void check_lower_bounds() {
        if (sprite.getX() < 0 || sprite.getY() < 0 && Math.abs(sprite.getRotation()) == 180) {
            sprite.setBounds(sprite.getX(), sprite.getHeight(), sprite.getWidth(), sprite.getHeight());
        } else if (sprite.getX() < 0 || sprite.getY() < 0 && sprite.getRotation() > -180) {
            sprite.setBounds(sprite.getX() - sprite.getWidth(), sprite.getHeight(), sprite.getWidth(), sprite.getHeight());
        } else if (sprite.getX() < 0 || sprite.getY() < 0 && sprite.getRotation() < -180) {
            sprite.setBounds(sprite.getX() + sprite.getWidth(), sprite.getHeight(), sprite.getWidth(), sprite.getHeight());
        }
    }

    // Rotar: Rota el vehiculo en un ángulo de 360°.
    public void rotar(float dr) {
        sprite.setRotation( sprite.getRotation() + ROTACION * dr);
        if (Math.abs(sprite.getRotation()) == 360) {
            sprite.setRotation(0);
        }
    }

}
