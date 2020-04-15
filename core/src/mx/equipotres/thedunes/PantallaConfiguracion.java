package mx.equipotres.thedunes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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

    Texture texturaBotonActivado;
    Texture texturaBotonDesactivado;
    Texture texturaBotonAcercaDe;
    Texture texturaBotonContenido;

    // MENU: The values of the class are generated.

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

        // Boton Musica
        Texture texturaBotonMusicaActivado = new Texture("Botones/BotonActivado.png");
        TextureRegionDrawable trdMusicaActivado = new TextureRegionDrawable(new TextureRegion(texturaBotonMusicaActivado));
        Texture texturaBotonMusicaDesactivado = new Texture("Botones/BotonDesactivado.png");
        TextureRegionDrawable trdMusicaDesactivado = new TextureRegionDrawable(new TextureRegion(texturaBotonMusicaDesactivado));
        ImageButton btnMusica = new ImageButton(trdMusicaActivado,trdMusicaDesactivado);
        btnMusica.setPosition(ANCHO*.4f - btnMusica.getWidth()/2,ALTO*.55f);
        escenaMenuConfig.addActor(btnMusica);

        // Boton Efectos de Sonido
        Texture texturaBotonSonidoActivado = new Texture("Botones/BotonActivado.png");
        TextureRegionDrawable trdSonidoActivado = new TextureRegionDrawable(new TextureRegion(texturaBotonSonidoActivado));
        Texture texturaBotonSonidoDesactivado = new Texture("Botones/BotonDesactivado.png");
        TextureRegionDrawable trdSonidoDesactivado = new TextureRegionDrawable(new TextureRegion(texturaBotonSonidoDesactivado));
        ImageButton btnSonido = new ImageButton(trdSonidoActivado,trdSonidoDesactivado);
        btnSonido.setPosition(ANCHO*.4f - btnSonido.getWidth()/2,ALTO/3-35);
        escenaMenuConfig.addActor(btnSonido);

        // Boton Acerca De
        Texture texturaBotonAcercaDe = new Texture("Botones/BotonAcercaDe.png");
        TextureRegionDrawable trdBotonAcercaDe= new TextureRegionDrawable(new TextureRegion(texturaBotonAcercaDe));
        Texture texturaBotonAcercaDeP = new Texture("Botones/BotonAcercaDeP.png");
        TextureRegionDrawable trdBotonAcercaDeP = new TextureRegionDrawable(new TextureRegion(texturaBotonAcercaDeP));
        ImageButton btnAcercaDe = new ImageButton(trdBotonAcercaDe,trdBotonAcercaDeP);
        btnAcercaDe.setPosition(2*ANCHO/3 - btnAcercaDe.getWidth()/2-50,ALTO/3-35);
        escenaMenuConfig.addActor(btnAcercaDe);

        // Boton Contenido
        Texture texturaBotonContenido = new Texture("Botones/BotonContenido.png");
        TextureRegionDrawable trdBotonContenido= new TextureRegionDrawable(new TextureRegion(texturaBotonContenido));
        Texture texturaBotonContenidoP = new Texture("Botones/BotonContenidoP.png");
        TextureRegionDrawable trdBotonContenidoP = new TextureRegionDrawable(new TextureRegion(texturaBotonContenidoP));
        ImageButton btnContenido = new ImageButton(trdBotonContenido,trdBotonContenidoP);
        btnContenido.setPosition(2*ANCHO/3 - btnContenido.getWidth()/2-50,ALTO*.55f);
        escenaMenuConfig.addActor(btnContenido);

        // Boton Regresar
        Texture texturaBotonRegresar = new Texture("Botones/botonRegresar.png");
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable(new TextureRegion(texturaBotonRegresar));
        ImageButton btnRegresar = new ImageButton(trdRegresar);
        btnRegresar.setPosition(escenaMenuConfig.getWidth() - 150,escenaMenuConfig.getHeight() - 150);
        escenaMenuConfig.addActor(btnRegresar);
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
