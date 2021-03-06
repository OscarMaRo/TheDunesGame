package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaConfiguracion extends Pantalla
{
    private final Juego juego;
    private Texto configuracion;
    private Texto musica;
    private Texto efectosDeSonido;

    // Texturas
    private Texture texturaFondo;
    private Texture texturaRectangulo;

    private Texture texturaBotonMusicaActivado;
    private Texture texturaBotonMusicaDesactivado;
    private Texture texturaBotonSonidoActivado;
    private Texture texturaBotonSonidoDesactivado;

    private Preferences prefsBotonMusic;
    private Preferences prefsMusic;
    private Preferences prefsBotonSoundFX;
    private Preferences prefsSoundFX;

    /* MENU: The values of the class are generated. */
    // Botones
    private Boton btnRegresar;

    // Escena
    private Stage escenaMenuConfig;

    public PantallaConfiguracion(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoMenu.png");
        texturaRectangulo = new Texture("Fondos/Fondopausa.jpeg");
        configuracion = new Texto("Fuentes/fuente.fnt");
        musica = new Texto("Fuentes/fuente.fnt");
        efectosDeSonido = new Texto("Fuentes/fuente.fnt");
        crearMenu();

        Gdx.input.setCatchKey(Input.Keys.BACK,true);

    }

    private void crearMenu() {
        escenaMenuConfig = new Stage(vista);

        prefsMusic = Gdx.app.getPreferences("TheDunes.settings.music");
        prefsBotonMusic = Gdx.app.getPreferences("TheDunes.settings.BotonMusic");
        prefsBotonMusic.getBoolean("BotonMusicOn", true);
        prefsSoundFX = Gdx.app.getPreferences("TheDunes.settings.soundFX");
        prefsBotonSoundFX = Gdx.app.getPreferences("TheDunes.settings.BotonSoundFX");
        prefsBotonSoundFX.getBoolean("BotonSoundFXOn", true);

        // Boton Musica
        if (prefsBotonMusic.getBoolean("BotonMusicOn")==true) {
            texturaBotonMusicaActivado = new Texture("Botones/BotonActivado.png");
            TextureRegionDrawable trdMusicaActivado = new TextureRegionDrawable(new TextureRegion(texturaBotonMusicaActivado));
            ImageButton btnMusicaActivado = new ImageButton(trdMusicaActivado);
            btnMusicaActivado.setPosition(ANCHO*.65f- btnMusicaActivado.getWidth()/2, ALTO/2+80);
            escenaMenuConfig.addActor(btnMusicaActivado);
            btnMusicaActivado.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    activarMusica(false);
                    juego.setScreen(new PantallaConfiguracion(juego));
                }
            });
        } else if (prefsBotonMusic.getBoolean("BotonMusicOn")==false) {
            texturaBotonMusicaDesactivado = new Texture("Botones/BotonDesactivado.png");
            TextureRegionDrawable trdMusicaDesactivado = new TextureRegionDrawable(new TextureRegion(texturaBotonMusicaDesactivado));
            ImageButton btnMusicaDesactivado = new ImageButton(trdMusicaDesactivado);
            btnMusicaDesactivado.setPosition(ANCHO*.65f- btnMusicaDesactivado.getWidth()/2, ALTO/2+80);
            escenaMenuConfig.addActor(btnMusicaDesactivado);
            btnMusicaDesactivado.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    activarMusica(true);
                    juego.setScreen(new PantallaConfiguracion(juego));
                }
            });
        }

        // Boton Efectos de Sonido
        if (prefsBotonSoundFX.getBoolean("BotonSoundFXOn")==true) {
            texturaBotonSonidoActivado = new Texture("Botones/BotonActivado.png");
            TextureRegionDrawable trdSonidoActivado = new TextureRegionDrawable(new TextureRegion(texturaBotonSonidoActivado));
            ImageButton btnSonidoActivado = new ImageButton(trdSonidoActivado);
            btnSonidoActivado.setPosition(ANCHO*.65f - btnSonidoActivado.getWidth()/2, ALTO/3+40);
            escenaMenuConfig.addActor(btnSonidoActivado);
            btnSonidoActivado.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    activarSonido(false);
                    juego.setScreen(new PantallaConfiguracion(juego));
                }
            });
        } else if (prefsBotonSoundFX.getBoolean("BotonSoundFXOn")==false) {
            texturaBotonSonidoDesactivado = new Texture("Botones/BotonDesactivado.png");
            TextureRegionDrawable trdSonidoDesactivado = new TextureRegionDrawable(new TextureRegion(texturaBotonSonidoDesactivado));
            ImageButton btnSonidoDesactivado = new ImageButton(trdSonidoDesactivado);
            btnSonidoDesactivado.setPosition(ANCHO*.65f - btnSonidoDesactivado.getWidth()/2, ALTO/3+40);
            escenaMenuConfig.addActor(btnSonidoDesactivado);
            btnSonidoDesactivado.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    activarSonido(true);
                    juego.setScreen(new PantallaConfiguracion(juego));
                }
            });
        }

        // Boton Regresar
        btnRegresar = new Boton("Botones/botonRegresar.png");
        btnRegresar.posicionarBoton(escenaMenuConfig.getWidth() - 150,escenaMenuConfig.getHeight() - 150);
        btnRegresar.presionar(juego, 7);

        btnRegresar.agregar(escenaMenuConfig);

        Gdx.input.setInputProcessor(escenaMenuConfig);
    }

    private void activarMusica(boolean activado) {
        prefsMusic.putBoolean("musicOn",activado);
        prefsBotonMusic.putBoolean("BotonMusicOn", activado);
        prefsMusic.flush();
        prefsBotonMusic.flush();
    }

    private void activarSonido(boolean activado) {
        prefsSoundFX.putBoolean("soundFXOn", activado);
        prefsBotonSoundFX.putBoolean("BotonSoundFXOn", activado);
        prefsSoundFX.flush();
        prefsBotonSoundFX.flush();
    }

    @Override
    public void render(float delta) {

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            juego.setScreen(new PantallaMenu(juego));
        }

        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.draw(texturaRectangulo,ANCHO/2-texturaRectangulo.getWidth()/2,ALTO/2-texturaRectangulo.getHeight()/2);
        String configT = "   Configuracion";
        configuracion.render(batch, configT, ANCHO/2 - 20, ALTO-100);
        String musicaT = "Musica";
        musica.render(batch, musicaT, ANCHO*.4f, ALTO/2+125);
        String efectosT = "Efectos de\n     Sonido";
        efectosDeSonido.render(batch, efectosT, ANCHO*.4f, ALTO/3+110);
        batch.end();

        escenaMenuConfig.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
