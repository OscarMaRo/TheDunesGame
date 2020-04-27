package mx.equipotres.thedunes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BarraVida {

    Texture textura;

    public BarraVida() {
        textura = new Texture("Sprites/blank.jpeg");
    }

    public void render(SpriteBatch batch, Torre torre, float x, float y) {

        if (torre.vida > 0.6f) {
            batch.setColor(Color.GREEN);
        } else if (torre.vida > 0.3f) {
            batch.setColor(Color.ORANGE);
        } else if (torre.vida >= 0.0f) {
            batch.setColor(Color.RED);
        }

        //sprite.draw(batch);
        batch.draw(textura, x, y, 100 * torre.vida, 5);

    }
}
