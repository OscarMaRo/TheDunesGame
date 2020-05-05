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

    private Texture texturaBotonCerrar;
    private Texture texturaBotonContenido;
    private Texture texturaBotonContenidoP;

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
        texturaBotonCerrar = new Texture("Botones/BotonCerrar.png");
        TextureRegionDrawable trdCerrar = new TextureRegionDrawable(new TextureRegion(texturaBotonCerrar));
        ImageButton btnCerrar = new ImageButton(trdCerrar);
        btnCerrar.setPosition(ANCHO-280, ALTO-100);
        escenaPantallaAcercaDe.addActor(btnCerrar);

        btnCerrar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        // Boton Contenido
        texturaBotonContenido = new Texture("Botones/BotonContenido.png");
        TextureRegionDrawable trdBotonContenido= new TextureRegionDrawable(new TextureRegion(texturaBotonContenido));
        texturaBotonContenidoP = new Texture("Botones/BotonContenidoP.png");
        TextureRegionDrawable trdBotonContenidoP = new TextureRegionDrawable(new TextureRegion(texturaBotonContenidoP));
        ImageButton btnContenido = new ImageButton(trdBotonContenido,trdBotonContenidoP);
        btnContenido.setPosition(ANCHO/2 - btnContenido.getWidth()/2,ALTO/2);
        escenaPantallaAcercaDe.addActor(btnContenido);
        btnContenido.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaContenido(juego));
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
