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
        //bloquear(bloqueado);
    }

    public void desbloquear() {
        if (bloqueado) {
            this.bloqueado = false;
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
