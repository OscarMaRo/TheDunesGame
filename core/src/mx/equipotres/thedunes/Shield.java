package mx.equipotres.thedunes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Shield extends Stage {
    protected Sprite sprite;
    private int ANCHO = 1280;
    private int ALTO = 720;
    protected Image imgEscudo;

    public Shield(Viewport vista, SpriteBatch batch) {
        super(vista, batch);

        Pixmap pixmap = new Pixmap((int)(ANCHO * 0.7f), (int)(ALTO * 0.8f), Pixmap.Format.RGBA8888);
        pixmap.setColor(0,0,0.7f,0.5f);
        pixmap.fillCircle((int)(ANCHO * 0.5),(int)(ALTO * 0.3),70);
        Texture texturaEscudo = new Texture(pixmap);

        imgEscudo = new Image(texturaEscudo);
        //imgEscudo.setPosition(ANCHO/2 , ALTO/2);
        this.addActor(imgEscudo);
    }

    public void posicionarEscudo(float x, float y) {
        imgEscudo.setPosition(x, y);
    }

    public Rectangle getBoundaries(float x, float y) {
        float size = 100;
        return new Rectangle(x, y, size, size);
    }

    public Circle getCircumference(float x, float y) {
        float radius = 30;
        return new Circle(x, y, radius);
    }

}
