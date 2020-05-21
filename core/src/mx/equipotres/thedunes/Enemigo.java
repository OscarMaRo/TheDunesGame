package mx.equipotres.thedunes;

import com.badlogic.gdx.graphics.Texture;

public class Enemigo extends Objeto {

    public Estado estado;
    private int vida = 2;

    public Enemigo(Texture textura, float x, float y) {
        super(textura, x, y);
        estado = Estado.DESACTIVADO;
    }

    public void moverH1Nvl1(float dx, float dy) {
        if (sprite.getY() >= 400){
            sprite.setY(sprite.getY() + dy);
        } else if (sprite.getY() < 400 && sprite.getX() > 160){
            sprite.setX(sprite.getX() - dx);
        } else if (sprite.getY() > 180){
            sprite.setY(sprite.getY() + dy);
        } else {
            sprite.setX(sprite.getX() - dx);
        }
    }

    public void moverH2Nvl1(float dx, float dy) {
        if (sprite.getX() >= 850) {
            sprite.setX(sprite.getX() - dx);
        } else {
            sprite.setX(sprite.getX() - dx);
            sprite.setY(sprite.getY() - dy);
        }
    }

    public void moverH1y2Nvl1(float dx, float dy) {
        sprite.setX(sprite.getX() + dx);
        if (sprite.getX() <= 1000){
            sprite.setY(sprite.getY() + dy);
        }
    }

    //Dependiendo del bloque en que está el boggie, ajusta la posición del sprite para estár más cerca del boogie 
    /*public void seguirBoggie(float dy, int bloque) {
        float posY = sprite.getY();
        if (bloque == 1) {
            sprite.setY(posY - dy);
        }else if (bloque == 3){
            sprite.setY(posY + dy);
        }else{
            sprite.setY(posY);
        }
    }*/

    //Perseguir al Boogie por toda la pantalla
    /*public void perseguirBoggie(float PASO, float boogieY, float boogieX) {
        float posY = sprite.getY();
        float posX = sprite.getX();

        if (boogieY < posY){
            sprite.setY(posY - PASO);
        }else if (boogieY > posY){
            sprite.setY(posY + PASO);
        }
        if (boogieX < posX){
            sprite.setX(posX - PASO);
        }else if (boogieX > posX){
            sprite.setX(posX + PASO);
        }
    }*/

    public boolean recibirDaño(){
        vida--;
        if (vida<=0){
            return true;
        }
        return false;
    }
    
    //Estado del sprite 
    public enum Estado{
        ACTIVADO, //En pantalla
        DESACTIVADO //Oculto
    }



}
