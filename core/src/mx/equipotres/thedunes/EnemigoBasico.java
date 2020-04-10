package mx.equipotres.thedunes;

import com.badlogic.gdx.graphics.Texture;

public class EnemigoBasico extends Enemigo{
    
     private  int vida;

    public EnemigoBasico(Texture textura, float x, float y) {
        super(textura, x, y);
        vida = 80;
    }
}
