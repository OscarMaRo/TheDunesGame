package mx.equipotres.thedunes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BotonNiveles extends Boton {

    private boolean bloqueado;
    private Image imgCandado;

    public BotonNiveles(String btn, String btnPresionado, boolean bloqueado) {
        super(btn, btnPresionado);
        this.bloqueado = bloqueado;
        bloquear(bloqueado);
    }

    public void bloquear(boolean bloqueado) {
        if (bloqueado) {
            System.out.println("created");
            Texture texturaCandado = new Texture("Sprites/locked.png");
            imgCandado = new Image(texturaCandado);
            posicionarCandado(super.getX() + super.getWidth() - imgCandado.getImageX(),
                                 super.getY() + super.getHeight() - imgCandado.getImageY());
        }
    }

    private void posicionarCandado(float x, float y) {
        imgCandado.setPosition(x, y);
    }

    public void agregarCandado(Stage escena) {
        escena.addActor(imgCandado);
    }

    public boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Image getImgCandado() {
        return imgCandado;
    }
}
