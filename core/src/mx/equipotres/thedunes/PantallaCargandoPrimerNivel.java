package mx.equipotres.thedunes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PantallaCargandoPrimerNivel extends Pantalla {

    private final Juego juego;
    private Texture texturaFondo;
    private long startTime = System.currentTimeMillis();
    private long elapsedTime;
    private Stage escenaPantallaCargandoPrimerNivel;

    public PantallaCargandoPrimerNivel(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoCargando.png");

        crearPantalla();
    }

    private void crearPantalla() {
        escenaPantallaCargandoPrimerNivel = new Stage(vista);
    }

    @Override
    public void render(float delta) {
        elapsedTime = (System.currentTimeMillis() - startTime)/1000;
        if (elapsedTime>=2){
            juego.setScreen(new PantallaPrimerNivel(juego));
        }

        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);

        batch.end();

        escenaPantallaCargandoPrimerNivel.draw();
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