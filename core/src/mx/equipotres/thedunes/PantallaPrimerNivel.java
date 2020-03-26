package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.LinkedList;

class PantallaPrimerNivel extends Pantalla {
    // Juego: allows to create another screen when the listener is activated.
    private final Juego juego;

    // Texturas: The sprites initialized.

    // Boogie
    private Boogie boogie;
    private Texture texturaBoogie;

    // Balas: Las que sean.
    private Texture texturaBala;
    private LinkedList<Bala> b = new LinkedList<>();
    private float direccionBala;

    // Mover automático... soon mover con flechas
    private float TIEMPO_PASO = 0.5f;
    private float tiempoMoverBoogie = 0;

    // MENU: The values of the class are generated.
    private Stage escenaMenu;

    public PantallaPrimerNivel(Juego juego) { this.juego = juego; }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,0,1);
        crearBoogie();
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }


    // Boogie: Crea el Boogie. Le asigna una textura, y una posición inicial (x, y).
    private void crearBoogie() {
        texturaBoogie = new Texture("Sprites/boogie1_frente.png");
        texturaBala = new Texture("Sprites/bala1.png");
        boogie = new Boogie(texturaBoogie, 10, 10);
    }

    @Override
    public void render(float delta) {
        // Imprime datos en consola.
        //Gdx.app.log("CENTRO", "(" + boogie.sprite.getWidth()/2 + ", " + boogie.sprite.getHeight()/2 + ")");

        // Init: Default initializers.
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        // Draw game: Everything in here is drawn into the game.
        batch.begin();

        // Background: The image of the background is displayed.
        Gdx.gl.glClearColor(0,0,0,1);
        // Boogie: The image of the boogie is displayed.
        boogie.render(batch);

        // Balas: Mover al ser creadas.
        for (int i = 0; i < b.size(); i++) {
            if (b.get(i) != null) {
                b.get(i).render(batch);
                moverBala(b.get(i), delta);
            }
        }

        // Finaliza el batch.
        batch.end();

    }

    // Bala: Mueve la bala cuando no es null.
    private void moverBala(Bala bala, float delta) {
        if (bala != null) {
            if (bala.sprite.getRotation() == -90 || bala.sprite.getRotation() == 270) { // works
                bala.moverRight(delta);
            } else if (bala.sprite.getRotation() == 0) {   // works
                bala.moverUp(delta);
            } else if (Math.abs(bala.sprite.getRotation()) == 180) {  // works
                bala.moverDown(delta);
            } else if (bala.sprite.getRotation() == 90 || bala.sprite.getRotation() == -270) {  // works
                bala.moverLeft(delta);
            }

            // Salió de la pantalla.
            if (bala.sprite.getY() > ALTO || bala.sprite.getX() > ANCHO ||
                    bala.sprite.getY() < 0 || bala.sprite.getX() < 0) {
                // Fuera de la pantalla.
                bala = null;
            }
        }
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

    private class ProcesadorEntrada implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.RIGHT:
                    boogie.rotar(-1);
                    break;
                case Input.Keys.LEFT:
                    boogie.rotar(1);
                    break;
                case Input.Keys.UP:
                    boogie.mover();
                    break;
                case Input.Keys.SPACE:
                    createBala();
                    break;
            }
            return true;
        }

        // Balas: Genera una nueva bala.
        private void createBala() {
            float xBala = boogie.sprite.getX() + boogie.sprite.getWidth()/2;
            float yBala = boogie.sprite.getY() + boogie.sprite.getHeight();
            b.add(new Bala(texturaBala, xBala, yBala));
            direccionBala = b.size() - 1;
            b.get((int) direccionBala).sprite.setRotation(boogie.sprite.getRotation());
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
