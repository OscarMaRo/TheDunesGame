package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
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

    // Estrellas
    private Texture texturaEstrella;
    // Nivel 1
    private Image imgEstrella1;
    private Image imgEstrella2;
    private Image imgEstrella3;
    // Nivel 2
    private Image imgEstrella4;
    private Image imgEstrella5;
    private Image imgEstrella6;
    // Nivel 3
    private Image imgEstrella7;
    private Image imgEstrella8;
    private Image imgEstrella9;
    // Estrellas vacías
    private Texture texturaEstrellaVacia;
    private Image imgEstrellaVacia1;
    private Image imgEstrellaVacia2;
    private Image imgEstrellaVacia3;

    // Archivos
    private Preferences pref;
    private Preferences block;

    // MENU: The values of the class are generated.
    private Stage escenaMenu;

    // Screen: This creates a new screen to be displayed.
    public PantallaMenuSeleccionNivel(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        // Background: Initialize the sprite for the background.
        cargarTexturas();
        cargarPreferencias();
        crearMenu();
        crearCandado2();
        crearCandado3();
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void cargarPreferencias() {
        pref = Gdx.app.getPreferences("preferencias-estrellas");
        block = Gdx.app.getPreferences("preferencias-niveles");
    }

    private void cargarTexturas() {
        texturaFondoNiveles = new Texture("Fondos/fondoNiveles.jpg");
        //Estrellas
        texturaEstrella = new Texture("Sprites/estrella.png");
        texturaEstrellaVacia = new Texture("Sprites/estrellaVacia.png");
        // Candado
        texturaCandado = new Texture("Sprites/locked.png");
    }

    private void crearCandado2() {
        imgCandadoNivel2 = new Image(texturaCandado);
        imgCandadoNivel2.setPosition(ANCHO*0.45f, ALTO*0.9f);
        if (btnSegundoNivel.getBloqueado()) {
            escenaMenu.addActor(imgCandadoNivel2);
        }
    }

    private void crearCandado3() {
        imgCandadoNivel3 = new Image(texturaCandado);
        imgCandadoNivel3.setPosition(ANCHO*0.8f, ALTO*0.9f);
        if (btnTercerNivel.getBloqueado()) {
            escenaMenu.addActor(imgCandadoNivel3);
        }
    }

    private void crearEstrella1() {
        imgEstrella1 = new Image(texturaEstrella);
        imgEstrella1.setPosition(ANCHO*0.02f+texturaEstrella.getWidth()/2,ALTO*0.1f);
    }

    private void crearEstrella2() {
        imgEstrella2 = new Image(texturaEstrella);
        imgEstrella2.setPosition(ANCHO*0.06f+texturaEstrella.getWidth()/2,ALTO*0.1f);
    }

    private void crearEstrella3() {
        imgEstrella3 = new Image(texturaEstrella);
        imgEstrella3.setPosition(ANCHO*0.1f+texturaEstrella.getWidth()/2,ALTO*0.1f);
    }

    private void crearEstrella4() {
        imgEstrella4 = new Image(texturaEstrella);
        imgEstrella4.setPosition(ANCHO*0.33f+texturaEstrella.getWidth()/2,ALTO*0.1f);
    }

    private void crearEstrella5() {
        imgEstrella5 = new Image(texturaEstrella);
        imgEstrella5.setPosition(ANCHO*0.37f+texturaEstrella.getWidth()/2,ALTO*0.1f);
    }

    private void crearEstrella6() {
        imgEstrella6 = new Image(texturaEstrella);
        imgEstrella6.setPosition(ANCHO*0.41f+texturaEstrella.getWidth()/2,ALTO*0.1f);
    }

    private void crearEstrella7() {
        imgEstrella7 = new Image(texturaEstrella);
        imgEstrella7.setPosition(ANCHO*0.66f+texturaEstrella.getWidth()/2,ALTO*0.1f);
    }

    private void crearEstrella8() {
        imgEstrella8 = new Image(texturaEstrella);
        imgEstrella8.setPosition(ANCHO*0.70f+texturaEstrella.getWidth()/2,ALTO*0.1f);
    }

    private void crearEstrella9() {
        imgEstrella9 = new Image(texturaEstrella);
        imgEstrella9.setPosition(ANCHO*0.74f+texturaEstrella.getWidth()/2,ALTO*0.1f);
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);

        // Primer Nivel
        btnPrimerNivel = new BotonNiveles("Botones/botonPrimerNivel.png", "Botones/botonPrimerNivelPress.png", false);
        btnPrimerNivel.posicionarBoton(0,0);
        // Segundo Nivel
        btnSegundoNivel = new BotonNiveles("Botones/botonSegundoNivel.png", "Botones/botonSegundoNivelPress.png", true);
        btnSegundoNivel.posicionarBoton(btnPrimerNivel.getWidth(),0);
        // Tercer Nivel
        btnTercerNivel = new BotonNiveles("Botones/botonTercerNivel.png", "Botones/botonTercerNivelPress.png", true);
        btnTercerNivel.posicionarBoton(btnSegundoNivel.getX() + btnSegundoNivel.getWidth(),0);
        // Regresar al Menú Principal
        btnRegresar = new Boton("Botones/botonRegresar.png");
        btnRegresar.posicionarBoton(escenaMenu.getWidth()-150, escenaMenu.getHeight()-150);

        if (block.getBoolean("segundo-nivel", false)) {
            btnSegundoNivel.desbloquear();
            System.out.println(btnSegundoNivel.getBloqueado());
        }

        if (block.getBoolean("tercer-nivel", false)) {
            btnTercerNivel.desbloquear();
            System.out.println(btnTercerNivel.getBloqueado());
        }

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

        // Estrellas
        // Nivel 1
        if (pref.getBoolean("estrella-1", false)) {
            crearEstrella1();
            escenaMenu.addActor(imgEstrella1);
        }
        if (pref.getBoolean("estrella-2", false)) {
            crearEstrella2();
            escenaMenu.addActor(imgEstrella2);
        }
        if (pref.getBoolean("estrella-3", false)) {
            crearEstrella3();
            escenaMenu.addActor(imgEstrella3);
        }
        if (pref.getBoolean("estrella-4", false)) {
            crearEstrella4();
            escenaMenu.addActor(imgEstrella4);
        }
        if (pref.getBoolean("estrella-5", false)) {
            crearEstrella5();
            escenaMenu.addActor(imgEstrella5);
        }
        if (pref.getBoolean("estrella-6", false)) {
            crearEstrella6();
            escenaMenu.addActor(imgEstrella6);
        }
        if (pref.getBoolean("estrella-7", false)) {
            crearEstrella7();
            escenaMenu.addActor(imgEstrella7);
        }
        if (pref.getBoolean("estrella-8", false)) {
            crearEstrella8();
            escenaMenu.addActor(imgEstrella8);
        }
        if (pref.getBoolean("estrella-9", false)) {
            crearEstrella9();
            escenaMenu.addActor(imgEstrella9);
        }



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
