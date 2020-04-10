package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.LinkedList;

// Primer Nivel del Juego.
class PantallaPrimerNivel extends Pantalla {
    // Juego: allows to create another screen when the listener is activated.
    private final Juego juego;

    // Texturas: The sprites initialized.
    private Texture texturaFondo;
    private Texture texturaBotonAcelerar;

    // Torre enemiga
    private Texture texturaTorre;
    private Objeto torre;
    private float vidaTorre = 1000f;

    // Boogie
    private Boogie boogie;
    private Texture texturaBoogie;

    // Balas: Las que sean.
    private Texture texturaBala;
    private LinkedList<Bala> b = new LinkedList<>();
    private float direccionBala;
    
    //ENEMIGOS
    private Array<EnemigoBasico> arrEnemigos;
    private Texture texturaEnemigos;
    private int MAX_PASOS = 1100;
    private  int numeroPasos = 0;
    private int cantidadEnemigos = 20;
    private int horda = 1;

    // Mover automático... soon mover con flechas
    private float TIEMPO_PASO = 0.5f;
    private float tiempoMoverBoogie = 0;

    // MENU: The values of the class are generated.
    private Stage escenaMenu;

    public PantallaPrimerNivel(Juego juego) { this.juego = juego; }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,0,1);
        cargarTexturas();
        crearBotones();
        crearBoogie();
        crearEnemigos();
        crearTorre();
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearTorre() {
        texturaTorre = new Texture("Sprites/torre.png");
        torre = new Objeto(texturaTorre, ANCHO/2, ALTO/2);
    }

    private void crearBotones() {
        escenaMenu = new Stage(vista);

        // Botón: Acelerar.
        // Steps to create a fully functional button.
        // 1.1 Texturize: Creates the image in the game. Idle.
        Texture texturaBotonAcelerar = new Texture("Botones/move.jpg");
        TextureRegionDrawable trdAcelerar = new TextureRegionDrawable(new TextureRegion(texturaBotonAcelerar));
        // 1.2 Texturize: Creates the image in the game. Clicked.
        //Texture texturaBotonJugarPress = new Texture("Botones/button_jugarP.png");
        //TextureRegionDrawable trdJugarP = new TextureRegionDrawable(new TextureRegion(texturaBotonJugarPress));
        // 2. Creation: Creates the button to be used.
        ImageButton btnAcelerar = new ImageButton(trdAcelerar);
        // 3. Position: Sets the position of the button.
        btnAcelerar.setPosition(ANCHO - btnAcelerar.getWidth(),0);

        // Listener: This calls the functionality of the buttons.
        btnAcelerar.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // TO ACTIVATE EVENT: Click in order to accelerate character.
                boogie.mover();
                //System.out.println("Move");
            }
        });

        // Display images: This attributes draw the images in screen.
        escenaMenu.addActor(btnAcelerar);
        // Action: This attributes allow the buttons to have interaction with the user.
        Gdx.input.setInputProcessor(escenaMenu);
    }

    //TO-DO: Creo que es mejor que pongamos todas las texturas aquí, pero no quise mover código sin antes saber. 
    private void cargarTexturas() {
        texturaEnemigos = new Texture("Sprites/enemigo1.png");
        texturaFondo = new Texture(("Fondos/fondoNivel1.jpeg"));
    }
    
    private void crearEnemigos() {
        arrEnemigos = new Array<>(cantidadEnemigos);
        float dy = ALTO * 0.8f / cantidadEnemigos;
        //Para primera horda
        for (int y = 0; y < 10; y++) {
            EnemigoBasico enemigo = new EnemigoBasico(texturaEnemigos, ANCHO-texturaEnemigos.getWidth(), y*dy + ALTO * 0.45f);
            arrEnemigos.add(enemigo);
        }
        //Para horda = 2
        for (int y = 0; y < cantidadEnemigos-1; y++) {
            EnemigoBasico enemigo = new EnemigoBasico(texturaEnemigos, ANCHO-texturaEnemigos.getWidth(), y*dy + ALTO * 0.35f);
            arrEnemigos.add(enemigo);
        }
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
        
        //Actualizaciones
        moverEnemigos(delta);
        llamarHorda();

        probarColisiones();

        // Init: Default initializers.
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        // Draw game: Everything in here is drawn into the game.
        batch.begin();

        // Background: The image of the background is displayed.
        batch.draw(texturaFondo,0,0);
        // Boogie: The image of the boogie is displayed.
        boogie.render(batch);
        // Torre: The image of the torre is displayed.
        torre.render(batch);

        // Balas: Mover al ser creadas.
        for (int i = 0; i < b.size(); i++) {
            if (b.get(i) != null) {
                b.get(i).render(batch);
                moverBala(b.get(i), delta);
            }
        }
        
        // Enemigos
        for (EnemigoBasico enemigo: arrEnemigos) {
            if(enemigo.estado == Enemigo.Estado.ACTIVADO){
                enemigo.render(batch);
            }
        }
        // Visibility: When this is activated everything is visible from show().
        //escenaMenu.draw();
        // Finaliza el batch.
        batch.end();



    }
    
    /* Cambia el número de horda de acuerdo al número de pasos que los enemigos han dado y activa los enemigos correspondientes
    a la horda */
    private void llamarHorda() {
        if(horda ==1){
            for(int i = 0; i < 10; i++ ){
                EnemigoBasico enemigoBasico = arrEnemigos.get(i);
                enemigoBasico.estado = Enemigo.Estado.ACTIVADO;
            }
        }
        if (numeroPasos == 500){
            horda = 2;
            for(int i = 10; i < cantidadEnemigos-1; i++ ){
                EnemigoBasico enemigoBasico = arrEnemigos.get(i);
                enemigoBasico.estado = Enemigo.Estado.ACTIVADO;
            }
        }
    }
    
    //TO-DO: falta poner los límites de la pantalla para que los enemigos no se salgan
    private void moverEnemigos(float delta) {
        float posYBoogie = boogie.sprite.getY();

        if(numeroPasos< MAX_PASOS) {
            for (EnemigoBasico enemigoBasico : arrEnemigos) {
                if (enemigoBasico.estado == Enemigo.Estado.ACTIVADO)
                    enemigoBasico.mover(-1);
            }
            numeroPasos ++;
            if(numeroPasos%5 == 0){  //A partir de aquí la posición en Y cambia de acuerdo a la posY del Boogie
                for (EnemigoBasico enemigoBasico:arrEnemigos) {
                    if(posYBoogie > 0 && posYBoogie <= ALTO*.33){
                        enemigoBasico.seguirBoggie(5,1);
                    }else if(posYBoogie < ALTO*.33 && posYBoogie <= ALTO*.66){
                        enemigoBasico.seguirBoggie(5,2);
                    }else{
                        enemigoBasico.seguirBoggie(5,3);
                    }
                }
            }
        }
    }

    // Colisiones: Probar las colisisones.
    private void probarColisiones() {
        if (b.size() > 0) {
            for (int i = arrEnemigos.size - 1; i >= 0; i--) {
                for (int j = 0; j < b.size(); j++) {
                    EnemigoBasico enemigoBasico = arrEnemigos.get(i);
                    Rectangle rectEnemigoBasico = enemigoBasico.sprite.getBoundingRectangle();
                    Rectangle rectBala = b.get(j).sprite.getBoundingRectangle();
                    if (rectEnemigoBasico.overlaps(rectBala)) {
                        arrEnemigos.removeIndex(i);
                        //b.get(j).sprite.;
                        break;
                    }
                }
            }
        }
    }

    //Metodo para los enemigos que sigan al boogie
    /*private void moverEnemigosSeguidor() {
        float posYBoogie = boogie.sprite.getY();
        float posXBoogie = boogie.sprite.getX();

        for (EnemigoBasico enemigoBasico : arrEnemigos) {
            if (enemigoBasico.estado == Enemigo.Estado.ACTIVADO)
                enemigoBasico.perseguirBoggie(0.2f, posYBoogie, posXBoogie);
        }
        numeroPasos ++;
    }*/

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
            } else if (bala.sprite.getRotation() == 45 || bala.sprite.getRotation() == -315) {  // works
                bala.moverDiagonalArrIzq(delta);
            } else if (bala.sprite.getRotation() == -45 || bala.sprite.getRotation() == 315) {  // works
                bala.moverDiagonalArrDer(delta);
            } else if (bala.sprite.getRotation() == 135 || bala.sprite.getRotation() == -225) {  // works
                bala.moverDiagonalAbjIzq(delta);
            } else if (bala.sprite.getRotation() == -135 || bala.sprite.getRotation() == 225) {  // works
                bala.moverDiagonalAbjDer(delta);
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
