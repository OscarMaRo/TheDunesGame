package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaInstrucciones2 extends Pantalla
{
    private final Juego juego;
    private Texto instrucciones;

    // Texturas
    Texture texturaFondo;
    Texture texturaRectangulo;
    Texture texturaEnemigo1;
    Texture texturaTorre;
    Texture texturaBotonRegresar;

    private Texture texturaBotonCerrar;

    private Stage escenaPantallaInstrucciones2;

    public PantallaInstrucciones2(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoMenu.png");
        texturaRectangulo = new Texture("Fondos/Fondopausa.jpeg");
        texturaEnemigo1 = new Texture("Sprites/enemigo1.png");
        texturaBotonRegresar = new Texture("Botones/BotonRegresar2.png");
        texturaTorre = new Texture("Sprites/torre.png");
        instrucciones = new Texto("Fuentes/fuente.fnt");
        crearPantalla();
    }

    private void crearPantalla() {
        escenaPantallaInstrucciones2 = new Stage(vista);
        texturaBotonCerrar = new Texture("Botones/BotonCerrar.png");
        TextureRegionDrawable trdCerrar = new TextureRegionDrawable(new TextureRegion(texturaBotonCerrar));
        ImageButton btnCerrar = new ImageButton(trdCerrar);
        btnCerrar.setPosition(ANCHO-280, ALTO-100);
        escenaPantallaInstrucciones2.addActor(btnCerrar);
        btnCerrar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAcercaDe(juego));
            }
        });

        TextureRegionDrawable trdRegresar = new TextureRegionDrawable(new TextureRegion(texturaBotonRegresar));
        ImageButton btnRegresar = new ImageButton(trdRegresar);
        btnRegresar.setPosition(ANCHO/3-185, texturaRectangulo.getHeight()*.1f);
        escenaPantallaInstrucciones2.addActor(btnRegresar);
        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaInstrucciones(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaPantallaInstrucciones2);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.draw(texturaRectangulo,ANCHO/2-texturaRectangulo.getWidth()/2,ALTO/2-texturaRectangulo.getHeight()/2);
        batch.draw(texturaEnemigo1, 2*ANCHO/3+140 - texturaEnemigo1.getWidth()/2,2*ALTO/3 - texturaEnemigo1.getHeight()/2-35);
        batch.draw(texturaEnemigo1, 2*ANCHO/3+110 - texturaEnemigo1.getWidth()/2,2*ALTO/3 - texturaEnemigo1.getHeight()/2 + 36);
        batch.draw(texturaTorre, 2*ANCHO/3+130 - texturaTorre.getWidth()/2, ALTO/3 - texturaTorre.getHeight()/2);
        batch.draw(texturaTorre, 2*ANCHO/3+100 - texturaTorre.getWidth()/2, ALTO/3 - texturaTorre.getHeight()/2+85);
        String instruccionesT = "Instrucciones";
        instrucciones.render(batch, instruccionesT, ANCHO/2, ALTO-100);
        String instEnemigos = "     Cuidado con los enemigos!!\nson chiquitos pero mortales";
        instrucciones.render(batch, instEnemigos,ANCHO/3+175,2*ALTO/3+50);
        String instTorres = "      Destruye las torres sin\n        escudo para quitarselo\n                       a la siguiente.\n   destruye todas para ganar";
        instrucciones.render(batch, instTorres,ANCHO/3+175,ALTO/3+150);
        batch.end();

        escenaPantallaInstrucciones2.draw();
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

