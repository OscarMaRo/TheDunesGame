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

public class PantallaDesarrolladores extends Pantalla
{
    private final Juego juego;
    private Texto desarrolladores;

    // Texturas
    private Texture texturaFondo;
    private Texture texturaRectangulo;

    // Botones
    private Boton btnRegresar;
    private Boton btnInfo;

    // Escena
    private Stage escenaPantallaDesarrolladores;

    public PantallaDesarrolladores(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoMenu.png");
        texturaRectangulo = new Texture("Fondos/Fondopausa.jpeg");
        desarrolladores= new Texto("Fuentes/fuente.fnt");
        crearPantalla();

        Gdx.input.setCatchKey(Input.Keys.BACK,true);

    }

    private void crearPantalla() {
        escenaPantallaDesarrolladores = new Stage(vista);

        btnRegresar = new Boton("Botones/botonRegresar.png");
        btnRegresar.posicionarBoton(escenaPantallaDesarrolladores.getWidth() - 150,escenaPantallaDesarrolladores.getHeight() - 150);
        btnRegresar.presionar(juego, 1);

        //btnInfo = new Boton("Botones/info.png");
        //btnInfo.posicionarBoton(ANCHO/2-btnInfo.getWidth()/2, 130);

        btnRegresar.agregar(escenaPantallaDesarrolladores);
        //btnInfo.agregar(escenaPantallaDesarrolladores);

        Gdx.input.setInputProcessor(escenaPantallaDesarrolladores);
    }

    @Override
    public void render(float delta) {

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            juego.setScreen(new PantallaAcercaDe(juego));
        }

        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.draw(texturaRectangulo,ANCHO/2-texturaRectangulo.getWidth()/2,ALTO/2-texturaRectangulo.getHeight()/2);
        String desarrolladoPor = "Desarrollado\n               por:";
        desarrolladores.render(batch, desarrolladoPor, ANCHO/2, ALTO-60);
        String zoe = "Zoe  Caballero  Dominguez";
        desarrolladores.render(batch, zoe, ANCHO/2, ALTO-225);
        String oscar = "Oscar  Macias  Rodriguez";
        desarrolladores.render(batch, oscar, ANCHO/2, ALTO-300);
        String rodrigo = "Rodrigo  Cravioto  Caballero";
        desarrolladores.render(batch, rodrigo, ANCHO/2, ALTO-375);
        String alan = "Alan  Diaz  Carrera";
        desarrolladores.render(batch, alan, ANCHO/2, ALTO-450);

        desarrolladores.render(batch, "zora.itesm@gmail.com", ANCHO/2, 120);

        batch.end();

        escenaPantallaDesarrolladores.draw();
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