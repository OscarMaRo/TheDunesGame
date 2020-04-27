package mx.equipotres.thedunes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Boogie extends Objeto {

    // Variables.
    private final int ROTACION = 360 / 8;
    private final int PASO = 1;
    public int vidas;

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
        //out_of_bounds();
        if (sprite.getX()+sprite.getHeight() < 1280 &&
                (sprite.getRotation() == -90 || sprite.getRotation() == 270)) {
            sprite.setX(sprite.getX() + PASO);
        } else if (sprite.getY()+sprite.getHeight() < 720 &&
                sprite.getRotation() == 0) {
            sprite.setX(sprite.getX());
            sprite.setY(sprite.getY() + PASO);
        } else if (sprite.getY() > 0 &&
                Math.abs(sprite.getRotation()) == 180) {
            sprite.setX(sprite.getX());
            sprite.setY(sprite.getY() - PASO);
        } else if (sprite.getX() > 0 &&
                (sprite.getRotation() == 90 || sprite.getRotation() == -270)) {
            sprite.setX(sprite.getX() - PASO);
            sprite.setY(sprite.getY());
        } else if (sprite.getY()+sprite.getHeight()<720 && sprite.getX()+sprite.getHeight()<1280 &&
                (sprite.getRotation() == -45 || sprite.getRotation() == 315)) {
            sprite.setX(sprite.getX() + PASO);
            sprite.setY(sprite.getY() + PASO);
        } else if (sprite.getY()>0 && sprite.getX()+sprite.getHeight()<1280 &&
                (sprite.getRotation() == -135 || sprite.getRotation() == 225)) {
            sprite.setX(sprite.getX() + PASO);
            sprite.setY(sprite.getY() - PASO);
        } else if (sprite.getY()+sprite.getHeight()<720 && sprite.getX()>0 &&
                (sprite.getRotation() == 45 || sprite.getRotation() == -315)) {
            sprite.setX(sprite.getX() - PASO);
            sprite.setY(sprite.getY() + PASO);
        } else if (sprite.getY()>0 && sprite.getX()>0 &&
                (sprite.getRotation() == 135 || sprite.getRotation() == -225)) {
            sprite.setX(sprite.getX() - PASO);
            sprite.setY(sprite.getY() - PASO);
        }

    }

    public void rotacion(float x, float y){
        if (x <= 0.35 && x >= -0.35 && y > 0){
            sprite.setRotation(0);
            this.mover();
        } else if (x < 0 && y > 0){
            sprite.setRotation(45);
            this.mover();
        } else if (x  < 0 && y <= 0.35 && y >= -0.35){
            sprite.setRotation(90);
            this.mover();
        } else if (x < 0 && y < 0){
            sprite.setRotation(135);
            this.mover();
        } else if (x <= 0.35 && x> -0.35 && y < 0){
            sprite.setRotation(180);
            this.mover();
        } else if (x > 0 && y < 0){
            sprite.setRotation(225);
            this.mover();
        } else if (x > 0 && y <= 0.35 && y >= -0.35){
            sprite.setRotation(270);
            this.mover();
        } else if (x > 0 && y > 0){
            sprite.setRotation(315);
            this.mover();
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
