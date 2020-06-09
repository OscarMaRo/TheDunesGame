package mx.equipotres.thedunes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PantallaCargandoSegundoNivel extends Pantalla {

    private final Juego juego;
    private Texture texturaFondo;
    private long startTime = System.currentTimeMillis();
    private long elapsedTime;
    private Stage escenaPantallaCargandoSegundoNivel;

    public PantallaCargandoSegundoNivel(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoCargando.png");

        crearPantalla();
    }

    private void crearPantalla() {
        escenaPantallaCargandoSegundoNivel = new Stage(vista);
    }

    @Override
    public void render(float delta) {
        elapsedTime = (System.currentTimeMillis() - startTime)/1000;
        if (elapsedTime>=2){
            juego.setScreen(new PantallaSegundoNivel(juego));
        }

        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);

        batch.end();

        escenaPantallaCargandoSegundoNivel.draw();
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