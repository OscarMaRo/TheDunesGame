package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaAcercaDe extends Pantalla
{
    private final Juego juego;
    private Texto acercaDe;

    // Texturas
    private Texture texturaFondo;
    private Texture texturaRectangulo;
    private Texture texturaBoogie;

    private Texture texturaBotonRegresar;
    private Texture texturaBotonContenido;
    private Texture texturaBotonContenidoP;
    private Texture texturaBotonDesarrolladores;
    private Texture texturaBotonDesarrolladoresP;
    private Texture texturaBotonInstrucciones;
    private Texture texturaBotonInstruccionesP;

    private Stage escenaPantallaAcercaDe;

    public PantallaAcercaDe(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoMenu.png");
        texturaRectangulo = new Texture("Fondos/Fondopausa.jpeg");
        texturaBoogie = new Texture("Sprites/boogie3_frente.png");
        acercaDe = new Texto("Fuentes/fuente.fnt");
        crearPantalla();
    }

    private void crearPantalla() {
        escenaPantallaAcercaDe = new Stage(vista);


        // Boton Contenido
        texturaBotonContenido = new Texture("Botones/BotonContenido.png");
        TextureRegionDrawable trdBotonContenido= new TextureRegionDrawable(new TextureRegion(texturaBotonContenido));
        texturaBotonContenidoP = new Texture("Botones/BotonContenidoP.png");
        TextureRegionDrawable trdBotonContenidoP = new TextureRegionDrawable(new TextureRegion(texturaBotonContenidoP));
        ImageButton btnContenido = new ImageButton(trdBotonContenido,trdBotonContenidoP);
        btnContenido.setPosition(ANCHO/2 - btnContenido.getWidth()/2,ALTO/2-25);
        escenaPantallaAcercaDe.addActor(btnContenido);
        btnContenido.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaContenido(juego));
            }
        });

        // Boton Desarrolladores
        texturaBotonDesarrolladores = new Texture("Botones/BotonDesarrolladores.png");
        TextureRegionDrawable trdBotonDesarrolladores= new TextureRegionDrawable(new TextureRegion(texturaBotonDesarrolladores));
        texturaBotonDesarrolladoresP = new Texture("Botones/BotonDesarrolladoresP.png");
        TextureRegionDrawable trdBotonDesarrolladoresP = new TextureRegionDrawable(new TextureRegion(texturaBotonDesarrolladoresP));
        ImageButton btnDesarrolladores = new ImageButton(trdBotonDesarrolladores,trdBotonDesarrolladoresP);
        btnDesarrolladores.setPosition(ANCHO/2 - btnContenido.getWidth()/2,ALTO/2-125);
        escenaPantallaAcercaDe.addActor(btnDesarrolladores);
        btnDesarrolladores.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaDesarrolladores(juego));
            }
        });

        // Boton Instrucciones
        texturaBotonInstrucciones = new Texture("Botones/BotonInstrucciones.png");
        TextureRegionDrawable trdBotonInstrucciones= new TextureRegionDrawable(new TextureRegion(texturaBotonInstrucciones));
        texturaBotonInstruccionesP = new Texture("Botones/BotonInstruccionesP.png");
        TextureRegionDrawable trdBotonInstruccionesP = new TextureRegionDrawable(new TextureRegion(texturaBotonInstruccionesP));
        ImageButton btnInstrucciones = new ImageButton(trdBotonInstrucciones,trdBotonInstruccionesP);
        btnInstrucciones.setPosition(ANCHO/2 - btnContenido.getWidth()/2,ALTO/2+75);
        escenaPantallaAcercaDe.addActor(btnInstrucciones);
        btnInstrucciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaInstrucciones(juego));
            }
        });

        // Boton Regresar
        texturaBotonRegresar = new Texture("Botones/botonRegresar.png");
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable(new TextureRegion(texturaBotonRegresar));
        ImageButton btnRegresar = new ImageButton(trdRegresar);
        btnRegresar.setPosition(escenaPantallaAcercaDe.getWidth() - 150,escenaPantallaAcercaDe.getHeight() - 150);
        escenaPantallaAcercaDe.addActor(btnRegresar);
        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaPantallaAcercaDe);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.draw(texturaRectangulo,ANCHO/2-texturaRectangulo.getWidth()/2,ALTO/2-texturaRectangulo.getHeight()/2);
        batch.draw(texturaBoogie,ANCHO-175,100);
        String acercaDeT = "Acerca De";
        acercaDe.render(batch, acercaDeT, ANCHO/2, ALTO-100);
        batch.end();

        escenaPantallaAcercaDe.draw();
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
