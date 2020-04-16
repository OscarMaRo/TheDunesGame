package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaConfiguracion extends Pantalla
{
    // Juego: allows to create another screen when the listener is activated.
    private final Juego juego;
    private Texto configuracion;
    private Texto musica;
    private Texto efectosDeSonido;

    // Texturas
    Texture texturaFondo;
    Texture texturaRectangulo;

    Texture texturaBotonMusicaActivado;
    Texture texturaBotonMusicaDesactivado;
    Texture texturaBotonSonidoActivado;
    Texture texturaBotonSonidoDesactivado;
    Texture texturaBotonAcercaDe;
    Texture texturaBotonContenido;
    Texture texturaBotonRegresar;

    Preferences prefsBotonMusic;
    Preferences prefsMusic;
    Preferences prefsBotonSoundFX;
    Preferences prefsSoundFX;

    /* MENU: The values of the class are generated. */

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
            btnMusicaActivado.setPosition(ANCHO * .4f - btnMusicaActivado.getWidth() / 2, ALTO * .55f);
            escenaMenuConfig.addActor(btnMusicaActivado);
            btnMusicaActivado.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    prefsMusic.putBoolean("musicOn",false);
                    prefsBotonMusic.putBoolean("BotonMusicOn", false);
                    prefsMusic.flush();
                    prefsBotonMusic.flush();
                    juego.setScreen(new PantallaConfiguracion(juego));
                }
            });
        } else if (prefsBotonMusic.getBoolean("BotonMusicOn")==false) {
            texturaBotonMusicaDesactivado = new Texture("Botones/BotonDesactivado.png");
            TextureRegionDrawable trdMusicaDesactivado = new TextureRegionDrawable(new TextureRegion(texturaBotonMusicaDesactivado));
            ImageButton btnMusicaDesactivado = new ImageButton(trdMusicaDesactivado);
            btnMusicaDesactivado.setPosition(ANCHO * .4f - btnMusicaDesactivado.getWidth() / 2, ALTO * .55f);
            escenaMenuConfig.addActor(btnMusicaDesactivado);
            btnMusicaDesactivado.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    prefsMusic.putBoolean("musicOn",true);
                    prefsBotonMusic.putBoolean("BotonMusicOn", true);
                    prefsMusic.flush();
                    prefsBotonMusic.flush();
                    juego.setScreen(new PantallaConfiguracion(juego));
                }
            });
        }

        // Boton Efectos de Sonido
        if (prefsBotonSoundFX.getBoolean("BotonSoundFXOn")==true) {
            texturaBotonSonidoActivado = new Texture("Botones/BotonActivado.png");
            TextureRegionDrawable trdSonidoActivado = new TextureRegionDrawable(new TextureRegion(texturaBotonSonidoActivado));
            ImageButton btnSonidoActivado = new ImageButton(trdSonidoActivado);
            btnSonidoActivado.setPosition(ANCHO * .4f - btnSonidoActivado.getWidth() / 2, ALTO / 3 - 35);
            escenaMenuConfig.addActor(btnSonidoActivado);
            btnSonidoActivado.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    prefsSoundFX.putBoolean("soundFXOn", false);
                    prefsBotonSoundFX.putBoolean("BotonSoundFXOn", false);
                    prefsSoundFX.flush();
                    prefsBotonSoundFX.flush();
                    juego.setScreen(new PantallaConfiguracion(juego));
                }
            });
        } else if (prefsBotonSoundFX.getBoolean("BotonSoundFXOn")==false) {
            texturaBotonSonidoDesactivado = new Texture("Botones/BotonDesactivado.png");
            TextureRegionDrawable trdSonidoDesactivado = new TextureRegionDrawable(new TextureRegion(texturaBotonSonidoDesactivado));
            ImageButton btnSonidoDesactivado = new ImageButton(trdSonidoDesactivado);
            btnSonidoDesactivado.setPosition(ANCHO * .4f - btnSonidoDesactivado.getWidth() / 2, ALTO / 3 - 35);
            escenaMenuConfig.addActor(btnSonidoDesactivado);
            btnSonidoDesactivado.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    prefsSoundFX.putBoolean("soundFXOn", true);
                    prefsBotonSoundFX.putBoolean("BotonSoundFXOn", true);
                    prefsSoundFX.flush();
                    prefsBotonSoundFX.flush();
                    juego.setScreen(new PantallaConfiguracion(juego));
                }
            });
        }

        // Boton Acerca De
        texturaBotonAcercaDe = new Texture("Botones/BotonAcercaDe.png");
        TextureRegionDrawable trdBotonAcercaDe= new TextureRegionDrawable(new TextureRegion(texturaBotonAcercaDe));
        Texture texturaBotonAcercaDeP = new Texture("Botones/BotonAcercaDeP.png");
        TextureRegionDrawable trdBotonAcercaDeP = new TextureRegionDrawable(new TextureRegion(texturaBotonAcercaDeP));
        ImageButton btnAcercaDe = new ImageButton(trdBotonAcercaDe,trdBotonAcercaDeP);
        btnAcercaDe.setPosition(2*ANCHO/3 - btnAcercaDe.getWidth()/2-75,ALTO/3-35);
        escenaMenuConfig.addActor(btnAcercaDe);
        btnAcercaDe.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // TO OTHER SCREEN: when clicked it displays the Menu Level Screen.
                juego.setScreen(new PantallaAcercaDe(juego));
            }
        });

        // Boton Contenido
        texturaBotonContenido = new Texture("Botones/BotonContenido.png");
        TextureRegionDrawable trdBotonContenido= new TextureRegionDrawable(new TextureRegion(texturaBotonContenido));
        Texture texturaBotonContenidoP = new Texture("Botones/BotonContenidoP.png");
        TextureRegionDrawable trdBotonContenidoP = new TextureRegionDrawable(new TextureRegion(texturaBotonContenidoP));
        ImageButton btnContenido = new ImageButton(trdBotonContenido,trdBotonContenidoP);
        btnContenido.setPosition(2*ANCHO/3 - btnContenido.getWidth()/2-75,ALTO*.55f);
        escenaMenuConfig.addActor(btnContenido);
        btnContenido.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // TO OTHER SCREEN: when clicked it displays the Menu Level Screen.
                juego.setScreen(new PantallaContenido(juego));
            }
        });

        // Boton Regresar
        texturaBotonRegresar = new Texture("Botones/botonRegresar.png");
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable(new TextureRegion(texturaBotonRegresar));
        ImageButton btnRegresar = new ImageButton(trdRegresar);
        btnRegresar.setPosition(escenaMenuConfig.getWidth() - 150,escenaMenuConfig.getHeight() - 150);
        escenaMenuConfig.addActor(btnRegresar);
        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // TO OTHER SCREEN: when clicked it displays the Menu Level Screen.
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaMenuConfig);
    }

    @Override
    public void render(float delta) {
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
