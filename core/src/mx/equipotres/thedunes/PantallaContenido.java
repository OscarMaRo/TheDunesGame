package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaContenido extends Pantalla
{
    private final Juego juego;
    private Texto contenido;

    // Texturas
    Texture texturaFondo;
    Texture texturaRectangulo;
    Texture texturaBoogie;
    Texture texturaEnemigo;

    private Texture texturaBotonCerrar;

    private Stage escenaPantallaCont;

    public PantallaContenido(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoMenu.png");
        texturaRectangulo = new Texture("Fondos/Fondopausa.jpeg");
        texturaBoogie = new Texture("Sprites/boogie1_frente.png");
        texturaEnemigo = new Texture("Sprites/enemigo1.png");
        contenido = new Texto("Fuentes/fuente.fnt");
        crearPantalla();
    }

    private void crearPantalla() {
        escenaPantallaCont = new Stage(vista);
        texturaBotonCerrar = new Texture("Botones/BotonCerrar.png");
        TextureRegionDrawable trdCerrar = new TextureRegionDrawable(new TextureRegion(texturaBotonCerrar));
        ImageButton btnCerrar = new ImageButton(trdCerrar);
        btnCerrar.setPosition(ANCHO-280, ALTO-100);
        escenaPantallaCont.addActor(btnCerrar);

        btnCerrar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // TO OTHER SCREEN: when clicked it displays the Menu Level Screen.
                juego.setScreen(new PantallaAcercaDe(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaPantallaCont);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.draw(texturaRectangulo,ANCHO/2-texturaRectangulo.getWidth()/2,ALTO/2-texturaRectangulo.getHeight()/2);
        batch.draw(texturaBoogie, 100, 100);
        batch.draw(texturaEnemigo,ANCHO-175,ALTO-175);
        String contT = "En un mundo post apocaliptico,\n un piloto conduce un boogie\nmodificado, donde para " +
                "poder\n sobrevivir, debe atravesar el\ndesierto con el objetivo de\ndestruir la " +
                "fortaleza enemiga,\nPero es obstaculizado por\nun ejercito de esqueletos,\n" +
                "comandado por el rey Skull.";
        contenido.render(batch, contT, ANCHO/2+20, ALTO-120);
        batch.end();

        escenaPantallaCont.draw();
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
