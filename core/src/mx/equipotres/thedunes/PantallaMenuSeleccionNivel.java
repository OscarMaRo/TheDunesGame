package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class PantallaMenuSeleccionNivel extends Pantalla {
    // Juego: allows to create another screen when the listener is activated.
    private final Juego juego;

    // Texturas: The sprites initialized.
    private Texture texturaFondoNiveles;
    // Primer Nivel
    private Texture texturaBotonPrimerNivel;
    private Texture texturaBotonPrimerNivelPress;
    // Segundo Nivel
    private Texture texturaBotonSegundoNivel;
    private Texture texturaBotonSegundoNivelPress;
    // Tercer Nivel
    private Texture texturaBotonTercerNivel;
    private Texture texturaBotonTercerNivelPress;
    // Botón: Regresar
    private Texture texturaBotonRegresar;
    // Candado
    private Texture texturaCandado;
    private Image imgCandadoNivel2;
    private Image imgCandadoNivel3;

    // Scroll Pane... pending checar
    private ScrollPane scrollPane;
    private List list;
    //private Skin skin;
    private Table table;
    private Table container;

    // Botones
    private BotonNiveles btnPrimerNivel;
    private BotonNiveles btnSegundoNivel;
    private BotonNiveles btnTercerNivel;
    private Boton btnRegresar;

    // MENU: The values of the class are generated.
    private Stage escenaMenu;

    // Screen: This creates a new screen to be displayed.
    public PantallaMenuSeleccionNivel(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        // Background: Initialize the sprite for the background.
        texturaFondoNiveles = new Texture("Fondos/fondoNiveles.jpg");
        crearMenu();

        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearCandado() {
        if (btnSegundoNivel.getBloqueado()) {
            System.out.println("agregado");
            btnSegundoNivel.agregarCandado(escenaMenu);
        }
        if (btnTercerNivel.getBloqueado()) {
            System.out.println("agregado");
            btnTercerNivel.agregarCandado(escenaMenu);
        }

    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);

        // Primer Nivel
        btnPrimerNivel = new BotonNiveles("Botones/botonPrimerNivel.png", "Botones/botonPrimerNivelPress.png", false);
        btnPrimerNivel.posicionarBoton(0,0);
        // Segundo Nivel
        btnSegundoNivel = new BotonNiveles("Botones/botonSegundoNivel.png", "Botones/botonSegundoNivelPress.png", false);
        btnSegundoNivel.posicionarBoton(btnPrimerNivel.getWidth(),0);
        // Tercer Nivel
        btnTercerNivel = new BotonNiveles("Botones/botonTercerNivel.png", "Botones/botonTercerNivelPress.png", false);
        btnTercerNivel.posicionarBoton(btnSegundoNivel.getX() + btnSegundoNivel.getWidth(),0);
        // Regresar al Menú Principal
        btnRegresar = new Boton("Botones/botonRegresar.png");
        btnRegresar.posicionarBoton(escenaMenu.getWidth()-150, escenaMenu.getHeight()-150);

        // Listeners
        btnPrimerNivel.presionar(juego, 9);
        if (!btnSegundoNivel.getBloqueado()) { btnSegundoNivel.presionar(juego, 10);}
        if (!btnTercerNivel.getBloqueado()) { btnTercerNivel.presionar(juego, 11);}
        btnRegresar.presionar(juego, 7);

        // Despliega las imagenes
        btnPrimerNivel.agregar(escenaMenu);
        btnSegundoNivel.agregar(escenaMenu);
        btnTercerNivel.agregar(escenaMenu);
        btnRegresar.agregar(escenaMenu);
        crearCandado();

        // Acciones
        Gdx.input.setInputProcessor(escenaMenu);
    }


    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            juego.setScreen(new PantallaMenu(juego));
        }

        // Init: Default initializers.
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        // Dibuja el juego
        batch.begin();
        // Fondo
        batch.draw(texturaFondoNiveles,0,0);
        batch.end();

        // Visibilidad
        escenaMenu.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaFondoNiveles.dispose();
    }
}
