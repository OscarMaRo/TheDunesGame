package mx.equipotres.thedunes;

import com.badlogic.gdx.graphics.Texture;

import mx.equipotres.thedunes.Objeto;

public class Torre extends Objeto {

    private int vida;

    public Torre(Texture textura, float x, float y) {
        super(textura, x, y);
        vida = 1000;
    }

    private void restarVida(int cantidad) {
        vida -= cantidad;
    }
}
