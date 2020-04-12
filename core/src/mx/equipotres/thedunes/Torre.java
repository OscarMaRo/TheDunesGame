package mx.equipotres.thedunes;

import com.badlogic.gdx.graphics.Texture;

import java.awt.Rectangle;

import mx.equipotres.thedunes.Objeto;

public class Torre extends Objeto {

    public float vida;

    public Torre(Texture textura, float x, float y) {
        super(textura, x, y);
        vida = 1.0f;
    }

    public void restarVida() {
        vida -= 0.1f;
    }

}