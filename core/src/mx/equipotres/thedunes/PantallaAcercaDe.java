package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

    // Escena
    private Stage escenaPantallaAcercaDe;

    // Botones
    private Boton btnContenido;
    private Boton btnDesarrolladores;
    private Boton btnInstrucciones;
    private Boton btnRegresar;

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

        Gdx.input.setCatchKey(Input.Keys.BACK,true);

    }

    private void crearPantalla() {
        escenaPantallaAcercaDe = new Stage(vista);

        btnContenido = new Boton("Botones/BotonContenido.png", "Botones/BotonContenidoP.png");
        btnContenido.posicionarBoton(ANCHO/2 - btnContenido.getWidth()/2,ALTO/2-25);
        btnContenido.presionar(juego, 3);

        btnDesarrolladores = new Boton("Botones/BotonDesarrolladores.png", "Botones/BotonDesarrolladoresP.png");
        btnDesarrolladores.posicionarBoton(ANCHO/2 - btnContenido.getWidth()/2,ALTO/2-125);
        btnDesarrolladores.presionar(juego, 4);

        btnInstrucciones = new Boton("Botones/BotonInstrucciones.png", "Botones/BotonInstruccionesP.png");
        btnInstrucciones.posicionarBoton(ANCHO/2 - btnContenido.getWidth()/2,ALTO/2+75);
        btnInstrucciones.presionar(juego, 5);

        btnRegresar = new Boton("Botones/botonRegresar.png");
        btnRegresar.posicionarBoton(escenaPantallaAcercaDe.getWidth() - 150,escenaPantallaAcercaDe.getHeight() - 150);
        btnRegresar.presionar(juego, 7);

        btnContenido.agregar(escenaPantallaAcercaDe);
        btnDesarrolladores.agregar(escenaPantallaAcercaDe);
        btnInstrucciones.agregar(escenaPantallaAcercaDe);
        btnRegresar.agregar(escenaPantallaAcercaDe);

        Gdx.input.setInputProcessor(escenaPantallaAcercaDe);
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
        batch.draw(texturaBoogie,ANCHO-175,100);
        String acercaDeTexto = "Acerca De";
        acercaDe.render(batch, acercaDeTexto, ANCHO/2, ALTO-100);
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
