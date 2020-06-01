package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class PantallaMenu extends Pantalla {
    // Juego: allows to create another screen when the listener is activated.
    private final Juego juego;

    // Texturas: The sprites initialized.
    private Texture texturaFondo;

    // Preferencias
    private Preferences prefsMusic;
    private Preferences prefsSoundFX;

    private Preferences pref;

    // MENU: The values of the class are generated.
    private Stage escenaMenu;

    // Botones
    private Boton btnJugar;
    private Boton btnConfig;
    private Boton btnAcercaDe;

    // Screen: This creates a new screen to be displayed.
    public PantallaMenu(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        // Background: Initialize the sprite for the background.
        texturaFondo = new Texture("Fondos/fondoMenu.png");
        crearMenu();
        Gdx.input.setCatchKey(Input.Keys.BACK,false);
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);
        prefsMusic = Gdx.app.getPreferences("TheDunes.settings.music");
        prefsMusic.getBoolean("musicOn", true);
        prefsSoundFX = Gdx.app.getPreferences("TheDunes.settings.soundFX");
        prefsSoundFX.getBoolean("soundFXOn",true);


        btnJugar = new Boton("Botones/botonJugar.png", "Botones/botonJugarPres.png");
        btnJugar.posicionarBoton(ANCHO/2 - btnJugar.getWidth()/2,ALTO/2);
        btnJugar.presionar(juego, 8);

        btnConfig = new Boton("Botones/botonConfiguracion.png", "Botones/botonConfiguracionPres.png");
        btnConfig.posicionarBoton(ANCHO/2 - btnConfig.getWidth()/2, (ALTO/2)-100);
        btnConfig.presionar(juego, 2);

        btnAcercaDe = new Boton("Botones/botonAcercade2.png", "Botones/botonAcercadePres.png");
        btnAcercaDe.posicionarBoton(ANCHO/2 - btnAcercaDe.getWidth()/2, (ALTO/2)-200);
        btnAcercaDe.presionar(juego, 1);

        // Display images: This attributes draw the images in screen.
        btnJugar.agregar(escenaMenu);
        btnConfig.agregar(escenaMenu);
        btnAcercaDe.agregar(escenaMenu);

        // Action: This attributes allow the buttons to have interaction with the user.
        Gdx.input.setInputProcessor(escenaMenu);
    }

    @Override
    public void render(float delta) {
        // Init: Default initializers.
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        // Draw game: Everything in here is drawn into the game.
        batch.begin();
        // Background: The image of the background is displayed.
        batch.draw(texturaFondo,0,0);
        batch.end();

        // Visibility: When this is activated everything is visible from show().
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
        texturaFondo.dispose();
    }
}
