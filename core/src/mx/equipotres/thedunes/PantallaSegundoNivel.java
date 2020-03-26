package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

class PantallaSegundoNivel extends Pantalla {
    // Juego: allows to create another screen when the listener is activated.
    private final Juego juego;

    // Texturas: The sprites initialized.

    // MENU: The values of the class are generated.
    private Stage escenaMenu;

    public PantallaSegundoNivel(Juego juego) { this.juego = juego; }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,0,1);
    }

    @Override
    public void render(float delta) {
        // Init: Default initializers.
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        // Draw game: Everything in here is drawn into the game.
        batch.begin();
        // Background: The image of the background is displayed.
        Gdx.gl.glClearColor(0,0,0,1);
        batch.end();

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
