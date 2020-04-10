package mx.equipotres.thedunes;

import com.badlogic.gdx.graphics.Texture;

public class Bala extends Objeto {

    // Velocidad.
    private float vy = 360;

    public Bala(Texture textura, float x, float y) { super(textura, x, y); }

    public void moverUp(float dt) {
        float d = vy * dt;
        sprite.setY(sprite.getY() + d);
    }

    public void moverDown(float dt) {
        float d = vy * dt;
        sprite.setY(sprite.getY() - d);
    }

    public void moverRight(float dt) {
        float d = vy * dt;
        sprite.setX(sprite.getX() + d);

    }

    public void moverLeft(float dt) {
        float d = vy * dt;
        sprite.setX(sprite.getX() - d);
    }

    public void moverDiagonalArrDer(float dt){
        float d = vy * dt;
        sprite.setX(sprite.getX() + d);
        sprite.setY(sprite.getY() + d);
    }

    public void moverDiagonalArrIzq(float dt){
        float d = vy * dt;
        sprite.setX(sprite.getX() - d);
        sprite.setY(sprite.getY() + d);
    }

    public void moverDiagonalAbjDer(float dt){
        float d = vy * dt;
        sprite.setX(sprite.getX() + d);
        sprite.setY(sprite.getY() - d);
    }

    public void moverDiagonalAbjIzq(float dt){
        float d = vy * dt;
        sprite.setX(sprite.getX() - d);
        sprite.setY(sprite.getY() - d);
    }



}
