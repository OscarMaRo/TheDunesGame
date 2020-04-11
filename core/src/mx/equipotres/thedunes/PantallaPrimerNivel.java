package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.Point;
import java.util.LinkedList;

// Primer Nivel del Juego.
class PantallaPrimerNivel extends Pantalla {
    // Juego: allows to create another screen when the listener is activated.
    private final Juego juego;

    // Texturas: The sprites initialized.
    private Texture texturaFondo;

    // Torre enemiga
    private Texture texturaTorre;
    private Torre torre;
    private float vidaTorre = 1000f;

    // Boogie
    private Boogie boogie;
    private Texture texturaBoogie;

    // Balas: Las que sean.
    private Texture texturaBala;
    private LinkedList<Bala> b = new LinkedList<>();
    private float direccionBala;

    // Marcador
    private Marcador marcador;

    // Health Bars
    private HealthBar healthBarTorre;

    // Texto
    private Texto ganar;

    // Pausa
    private EscenaPausa escenaPausa;
    private Texture texturaBotonPausa;

    // Mover
    private Texture texturaBotonAcelerar;

    // Estado
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO;

    //ENEMIGOS: En movimiento.
    private Array<EnemigoBasico> arrEnemigos;
    private Texture texturaEnemigos;
    private int MAX_PASOS = 1100;
    private  int numeroPasos = 0;
    private int cantidadEnemigos = 20;
    private int horda = 1;

    // ENEMIGOS: En circulo.
    private LinkedList<EnemigoBasico> arrEnemigosCirculo;
    private int cantidadEnemigosCirculo = 10;

    // Mover automático... soon mover con flechas
    private float TIEMPO_PASO = 0.5f;
    private float tiempoMoverEnemigo = 0;
    private float MAX_PASOS_CIRCLE = 50;
    private int counter = 0;
    private boolean trigger = false;

    // MENU: The values of the class are generated.
    private Stage escenaMenu;

    public PantallaPrimerNivel(Juego juego) { this.juego = juego; }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,0,1);
        cargarTexturas();
        crearObjetos();
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    // Creación: Crea todos los objetos.
    private void crearObjetos() {
        crearBoogie();
        crearMarcador();
        crearTorre();
        crearBarraVidaTorre();
        crearBotones();
        crearEnemigos();
        crearEnemigosAlrededorTorre();
    }

    private void crearBarraVidaTorre() {
        healthBarTorre = new HealthBar();//ANCHO/2, ALTO/2 + texturaTorre.getHeight());
    }

    // Texturas: Carga todas las texturas.
    private void cargarTexturas() {
        texturaEnemigos = new Texture("Sprites/enemigo1.png");
        texturaFondo = new Texture(("Fondos/fondoNivel1.jpeg"));
        texturaBoogie = new Texture("Sprites/boogie1_frente.png");
        texturaBala = new Texture("Sprites/bala1.png");
        texturaTorre = new Texture("Sprites/torre.png");
        texturaBotonPausa = new Texture("Botones/pausa.png");
        texturaBotonAcelerar = new Texture("Botones/move.jpg");
        ganar = new Texto("Fuentes/fuente.fnt");
    }

    // Boogie
    private void crearBoogie() {
        boogie = new Boogie(texturaBoogie, 10, 10);
    }

    // Marcador
    private void crearMarcador() {
        marcador = new Marcador(0.2f*ANCHO, 0.9f*ALTO, boogie);
    }

    // Torre
    private void crearTorre() {
        torre = new Torre(texturaTorre, ANCHO/2 - texturaTorre.getWidth()/2, ALTO/2 - texturaTorre.getHeight()/2);
    }

    // Botones: Funcionalidad llevada a la pantalla gráficamente.
    private void crearBotones() {
        escenaMenu = new Stage(vista);

        // Botón: Acelerar.
        TextureRegionDrawable trdAcelerar = new TextureRegionDrawable(new TextureRegion(texturaBotonAcelerar));
        ImageButton btnAcelerar = new ImageButton(trdAcelerar);
        btnAcelerar.setPosition(ANCHO - btnAcelerar.getWidth(),0);

        // Botón pausa
        TextureRegionDrawable trdPausa = new TextureRegionDrawable(new TextureRegion(texturaBotonPausa));
        ImageButton btnPausa = new ImageButton(trdPausa);
        btnPausa.setPosition(ANCHO - btnPausa.getWidth() + 20,ALTO - btnPausa.getHeight() + 10);


        // Listener: This calls the functionality of the buttons.
        btnAcelerar.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // TO ACTIVATE EVENT: Click in order to accelerate character.
                boogie.mover();
                //System.out.println("Move");
            }
        });

        // Listener: This calls the functionality of the buttons.
        btnPausa.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // TO ACTIVATE EVENT: Click in order to accelerate character.
                // Pausar el juego
                estadoJuego = EstadoJuego.PAUSADO;
                if (escenaPausa == null) {
                    escenaPausa = new EscenaPausa(vista, batch);
                }
            }
        });

        // Display images: This attributes draw the images in screen.
        escenaMenu.addActor(btnAcelerar);
        escenaMenu.addActor(btnPausa);
        // Action: This attributes allow the buttons to have interaction with the user.
        Gdx.input.setInputProcessor(escenaMenu);
    }

    // Enemigos
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

    // Enemigos: Círculo alrededor de la torre.
    private void crearEnemigosAlrededorTorre() {
        float x = ANCHO/2;
        float y = ALTO/2;
        float dx = 0, dy = 0;
        float paso = 100;
        arrEnemigosCirculo = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                dx = x + paso; dy = y;
            } else if(i == 1) {
                dx = x + paso/2; dy = y + paso/2;
            } else if (i == 2) {
                dx = x; dy = y + paso;
            } else if (i == 3) {
                dx = x - paso/2; dy = y + paso/2;
            } else if (i == 4) {
                dx = x - paso; dy = y;
            } else if (i == 5) {
                dx = x - paso/2; dy = y - paso/2;
            } else if (i == 6) {
                dx = x; dy = y - paso;
            } else if (i == 7) {
                dx = x + paso/2; dy = y - paso/2;
            }

            EnemigoBasico enemigoBasico = new EnemigoBasico(texturaEnemigos, dx, dy);
            arrEnemigosCirculo.add(enemigoBasico);

        }
    }

    // Dibuja
    @Override
    public void render(float delta) {

        if (estadoJuego == EstadoJuego.JUGANDO) {
            //Actualizaciones
            moverEnemigos(delta);
            llamarHorda();
            moverEnemigosCirculo(delta);
            // Colisones
            probarColisiones();
        }

        // Init: Default initializers.
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        // Draw game: Everything in here is drawn into the game.
        batch.begin();

        // Background: The image of the background is displayed.
        batch.draw(texturaFondo,0,0);
        // Boogie: The image of the boogie is displayed.
        boogie.render(batch);
        // mx.equipotres.thedunes.Torre: The image of the torre is displayed.
        torre.render(batch);
        // Marcador: Lo dibuja en la pantalla.
        marcador.render(batch);
        // Health Bar: Torre.
        healthBarTorre.render(batch, torre, ANCHO/2 - 50, ALTO/2 + 30);
        batch.setColor(Color.WHITE);

        if (torre.vida <= 0.0f) {
            String mensaje = "Enhorabuena, has ganado!";
            ganar.render(batch, mensaje, ANCHO/2 - 20, ALTO/2 + 10);
        }

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

        for (EnemigoBasico enemigoBasico: arrEnemigosCirculo) {
            enemigoBasico.render(batch);
        }

        // Finaliza el batch.
        batch.end();

        // Visibility: When this is activated everything is visible from show().
        escenaMenu.draw();

        // Juego Pausado.
        if (estadoJuego == EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        }

    }

    // Mover Enemigos alrededor del Castillo.
    private void moverEnemigosCirculo(float delta) {
        tiempoMoverEnemigo += delta;
        if (counter < MAX_PASOS_CIRCLE) {
            if (tiempoMoverEnemigo >= TIEMPO_PASO) {
                tiempoMoverEnemigo = 0;
                counter += 5;
                float paso = 10;
                for (int i = 0; i < arrEnemigosCirculo.size(); i++) {
                    EnemigoBasico enemigoBasico = arrEnemigosCirculo.get(i);
                    if (i == 0) {
                        enemigoBasico.sprite.setX(enemigoBasico.sprite.getX() + paso);
                    } else if (i == 2) {
                        enemigoBasico.sprite.setY(enemigoBasico.sprite.getY() + paso);
                    } else if (i == 4) {
                        enemigoBasico.sprite.setX(enemigoBasico.sprite.getX() - paso);
                    } else if (i == 6) {
                        enemigoBasico.sprite.setY(enemigoBasico.sprite.getY() - paso);
                    }
                }

                if (trigger) {
                    for (int i = 0; i < arrEnemigosCirculo.size(); i++) {
                        EnemigoBasico enemigoBasico = arrEnemigosCirculo.get(i);
                        if (i == 1) {
                            enemigoBasico.sprite.setX(enemigoBasico.sprite.getX() - paso);
                            enemigoBasico.sprite.setY(enemigoBasico.sprite.getY() - paso);
                        } else if (i == 3) {
                            enemigoBasico.sprite.setX(enemigoBasico.sprite.getX() + paso);
                            enemigoBasico.sprite.setY(enemigoBasico.sprite.getY() - paso);
                        } else if (i == 5) {
                            enemigoBasico.sprite.setX(enemigoBasico.sprite.getX() + paso);
                            enemigoBasico.sprite.setY(enemigoBasico.sprite.getY() + paso);
                        } else if (i == 7) {
                            enemigoBasico.sprite.setX(enemigoBasico.sprite.getX() - paso);
                            enemigoBasico.sprite.setY(enemigoBasico.sprite.getY() + paso);
                        }
                    }
                }
            }
        } else if (counter >= MAX_PASOS_CIRCLE) {
            if (tiempoMoverEnemigo >= TIEMPO_PASO) {
                trigger = true;
                tiempoMoverEnemigo = 0;
                counter += 5;
                float paso = 10;
                for (int i = 0; i < arrEnemigosCirculo.size(); i++) {
                    EnemigoBasico enemigoBasico = arrEnemigosCirculo.get(i);
                    if (i == 0) {
                        enemigoBasico.sprite.setX(enemigoBasico.sprite.getX() - paso);
                    } else if (i == 1) {
                        enemigoBasico.sprite.setX(enemigoBasico.sprite.getX() + paso);
                        enemigoBasico.sprite.setY(enemigoBasico.sprite.getY() + paso);
                    } else if (i == 2) {
                        enemigoBasico.sprite.setY(enemigoBasico.sprite.getY() - paso);
                    } else if (i == 3) {
                        enemigoBasico.sprite.setX(enemigoBasico.sprite.getX() - paso);
                        enemigoBasico.sprite.setY(enemigoBasico.sprite.getY() + paso);
                    } else if (i == 4) {
                        enemigoBasico.sprite.setX(enemigoBasico.sprite.getX() + paso);
                    } else if (i == 5) {
                        enemigoBasico.sprite.setX(enemigoBasico.sprite.getX() - paso);
                        enemigoBasico.sprite.setY(enemigoBasico.sprite.getY() - paso);
                    } else if (i == 6) {
                        enemigoBasico.sprite.setY(enemigoBasico.sprite.getY() + paso);
                    } else if (i == 7) {
                        enemigoBasico.sprite.setX(enemigoBasico.sprite.getX() + paso);
                        enemigoBasico.sprite.setY(enemigoBasico.sprite.getY() - paso);
                    }
                }
            }
            if (counter > 100) {
                counter = 0;
            }
        }

    }


    /* Cambia el número de horda de acuerdo al número de pasos que los enemigos han dado y activa los enemigos correspondientes
    a la horda */
    private void llamarHorda() {
        if(horda == 1){
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

        if(numeroPasos < MAX_PASOS) {
            for (EnemigoBasico enemigoBasico : arrEnemigos) {
                if (enemigoBasico.estado == Enemigo.Estado.ACTIVADO)
                    enemigoBasico.mover(-1);
            }
            numeroPasos ++;
            if(numeroPasos % 5 == 0){  //A partir de aquí la posición en Y cambia de acuerdo a la posY del Boogie
                for (EnemigoBasico enemigoBasico:arrEnemigos) {
                    if(posYBoogie > 0 && posYBoogie <= ALTO * 0.33){
                        enemigoBasico.seguirBoggie(5,1);
                    }else if(posYBoogie < ALTO * 0.33 && posYBoogie <= ALTO * 0.66){
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
                        b.remove(j);
                        marcador.agregarPuntos(5);
                        break;
                    }
                }
            }

            for (int i = arrEnemigosCirculo.size() - 1; i >= 0; i--) {
                for (int j = 0; j < b.size(); j++) {
                    EnemigoBasico enemigoBasico = arrEnemigosCirculo.get(i);
                    Rectangle rectEnemigoBasico = enemigoBasico.sprite.getBoundingRectangle();
                    Rectangle rectBala = b.get(j).sprite.getBoundingRectangle();
                    if (rectEnemigoBasico.overlaps(rectBala)) {
                        arrEnemigosCirculo.remove(i);
                        b.remove(j);
                        marcador.agregarPuntos(5);
                        break;
                    }
                }
            }
        }

        for (int j = 0; j < b.size(); j++) {
            Rectangle rectTorre = torre.sprite.getBoundingRectangle();
            Rectangle rectBala = b.get(j).sprite.getBoundingRectangle();
            if (rectTorre.overlaps(rectBala)) {
                torre.restarVida();
                b.remove(j);
                break;
            }
        }

        for (int i = arrEnemigos.size - 1; i >= 0; i--) {
            EnemigoBasico enemigoBasico = arrEnemigos.get(i);
            Rectangle rectEnemigoBasico = enemigoBasico.sprite.getBoundingRectangle();
            Rectangle rectBoogie = boogie.sprite.getBoundingRectangle();
            if (rectEnemigoBasico.overlaps(rectBoogie)) {
                boogie.restarVida(1);
                marcador.restarVidas(1);
                boogie.sprite.setPosition(10, 10);
            }
        }

        for (int i = arrEnemigosCirculo.size() - 1; i >= 0; i--) {
            EnemigoBasico enemigoBasico = arrEnemigosCirculo.get(i);
            Rectangle rectEnemigoBasico = enemigoBasico.sprite.getBoundingRectangle();
            Rectangle rectBoogie = boogie.sprite.getBoundingRectangle();
            if (rectEnemigoBasico.overlaps(rectBoogie)) {
                boogie.restarVida(1);
                marcador.restarVidas(1);
                boogie.sprite.setPosition(10, 10);
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
            Vector3 v = new Vector3(screenX, screenY, 0);
            camara.unproject(v);

            if (v.y >= ALTO - texturaBotonPausa.getHeight()) {
                if (v.x >= ANCHO - texturaBotonPausa.getWidth()) {
                    estadoJuego = EstadoJuego.PAUSADO;
                    if (escenaPausa == null) {
                        escenaPausa = new EscenaPausa(vista, batch);
                    }
                }
            }
            return true;
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


    // Clase Pausa (ventana que se muestra cuando el usuario pausa la aplicación).
    class EscenaPausa extends Stage {
        public EscenaPausa(Viewport vista, SpriteBatch batch) {
            super(vista, batch);

            Pixmap pixmap = new Pixmap((int)(ANCHO * 0.7f), (int)(ALTO * 0.8f), Pixmap.Format.RGBA8888);
            pixmap.setColor(0,0,0,1);
            //pixmap.fillCircle(300,300,300);
            pixmap.fillRectangle((int)(ANCHO * 0.03), 50, (int)ANCHO - 450, (int)ALTO - 100);
            Texture texturaRectangulo = new Texture(pixmap);

            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(ANCHO/2 - pixmap.getWidth()/2, ALTO/2 - pixmap.getHeight()/2);
            this.addActor(imgRectangulo);
        }
    }

    private enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        GANO,
        PERDIO
    }
}
