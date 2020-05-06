package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
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

    private Texture texturaBotonCerrar;

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
    }

    private void crearPantalla() {
        escenaPantallaDesarrolladores = new Stage(vista);
        texturaBotonCerrar = new Texture("Botones/BotonCerrar.png");
        TextureRegionDrawable trdCerrar = new TextureRegionDrawable(new TextureRegion(texturaBotonCerrar));
        ImageButton btnCerrar = new ImageButton(trdCerrar);
        btnCerrar.setPosition(ANCHO-280, ALTO-100);
        escenaPantallaDesarrolladores.addActor(btnCerrar);

        btnCerrar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAcercaDe(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaPantallaDesarrolladores);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.draw(texturaRectangulo,ANCHO/2-texturaRectangulo.getWidth()/2,ALTO/2-texturaRectangulo.getHeight()/2);
        String desarrolladoPor = "Desarrollado\n               por:";
        desarrolladores.render(batch, desarrolladoPor, ANCHO/2, ALTO-60);
        String zoe = "Zoe  Caballero  Dominguez";
        desarrolladores.render(batch, zoe, ANCHO/2, ALTO-225);
        String rodrigo = "Rodrigo  Cravioto  Caballero";
        desarrolladores.render(batch, rodrigo, ANCHO/2, ALTO-300);
        String oscar = "Oscar  Macias  Rodriguez";
        desarrolladores.render(batch, oscar, ANCHO/2, ALTO-375);
        String alan = "Alan  Diaz  Carrera";
        desarrolladores.render(batch, alan, ANCHO/2, ALTO-450);
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