package mx.equipotres.thedunes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Boton {

    private ImageButton boton;
    private Pantalla pantalla;

    // Botón normal
    public Boton(String btn) {
        // Texturas
        Texture texturaBtn = new Texture(btn);
        // Texturización
        TextureRegionDrawable trd = new TextureRegionDrawable(new TextureRegion(texturaBtn));
        // Creación del Botón
        boton = new ImageButton(trd, trd);
    }

    // Botón con efecto al presionarlo
    public Boton(String btn, String btnPresionado) {
        // Texturas
        Texture texturaBtn = new Texture(btn);
        Texture texturaBtnPress = new Texture(btnPresionado);
        // Texturización
        TextureRegionDrawable trd = new TextureRegionDrawable(new TextureRegion(texturaBtn));
        TextureRegionDrawable trdPresionado = new TextureRegionDrawable(new TextureRegion(texturaBtnPress));
        // Creación del Botón
        boton = new ImageButton(trd, trdPresionado);
    }

    public void posicionarBoton(float x, float y) {
        boton.setPosition(x, y);
    }

    // Selecciona la pantalla
    private Pantalla getPantalla(Juego juego, int opcion) {
        switch (opcion) {
            case 1:
                pantalla = new PantallaAcercaDe(juego);
                break;
            case 2:
                pantalla = new PantallaConfiguracion(juego);
                break;
            case 3:
                pantalla = new PantallaContenido(juego);
                break;
            case 4:
                pantalla = new PantallaDesarrolladores(juego);
                break;
            case 5:
                pantalla = new PantallaInstrucciones(juego);
                break;
            case 6:
                pantalla = new PantallaInstrucciones2(juego);
                break;
            case 7:
                pantalla = new PantallaMenu(juego);
                break;
            case 8:
                pantalla = new PantallaMenuSeleccionNivel(juego);
                break;
            case 9:
                pantalla = new PantallaPrimerNivel(juego);
                break;
            case 10:
                pantalla = new PantallaSegundoNivel(juego);
                break;
            case 11:
                pantalla = new PantallaTercerNivel(juego);
                break;
            default:
                System.out.println("Screen is not available");
                break;
        }
        return pantalla;
    }

    // Función al presionar el botón.
    public void presionar(final Juego juego, final int opcion) {
        boton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(getPantalla(juego, opcion));
            }
        });
    }

    public void agregar(Stage escena) {
        escena.addActor(boton);
    }

    public float getWidth() {
        return boton.getWidth();
    }

    public float getHeight() {
        return boton.getHeight();
    }

    public float getX() {
        return boton.getX();
    }

    public float getY() {
        return boton.getY();
    }
}
