package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

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
    private Shield escudoTorre;

    // Torres de Poder: Superior Derecha
    private Torre torreSuperiorDerecha;
    private Shield escudoTorreSuperiorDerecha;
    // Torres de Poder: Inferior Derecha
    private Torre torreInferiorDerecha;
    private Shield escudoTorreInferiorDerecha;
    // Torres de Poder: Superior Izquierda
    private Torre torreSuperiorIzquierda;
    private Shield escudoTorreSuperiorIzquierda;

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
    private HealthBar healthBarTorreSuperiorDerecha;
    private HealthBar healthBarTorreInferiorDerecha;
    private HealthBar healthBarTorreSuperiorIzquierda;

    // Texto
    private Texto ganar;

    // Pausa
    private EscenaPausa escenaPausa;
    private Texture texturaBotonPausa;

    // Mover
    private Texture texturaBotonAcelerar;

    // Estado
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO;

    // Música
    private Music musicaFondo;
    private Sound shoot;
    private Sound shield;
    private int hit = 0;

    //ENEMIGOS: En movimiento.
    private LinkedList<EnemigoBasico> arrEnemigos1;
    private LinkedList<EnemigoBasico> arrEnemigos2;
    private Texture texturaEnemigos;
    private int MAX_PASOS = 1100;
    private  int numeroPasos = 0;
    private int cantidadEnemigos = 20;
    private int horda = 1;

    // ENEMIGOS: Torre.
    private LinkedList<EnemigoBasico> arrEnemigosCirculo;
    private int cantidadEnemigosCirculo = 10;
    private float TIEMPO_PASO = 0.5f;
    private float tiempoMoverEnemigo = 0;
    private float MAX_PASOS_CIRCLE = 50;
    private int counter = 0;
    private boolean trigger = false;

    // ESCENA MENU
    private Stage escenaMenu;
    private Stage escenaPausado;

    // CONSTRUCTOR
    public PantallaPrimerNivel(Juego juego) { this.juego = juego; }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,0,1);
        cargarTexturas();
        crearObjetos();
        cargarMusica();
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    public void cargarMusica() {
        AssetManager manager = new AssetManager();
        manager.load("Musica/musicaFondo.mp3", Music.class);
        manager.load("Musica/shoot.wav", Sound.class);
        manager.load("Musica/shieldDown.mp3", Sound.class);
        manager.finishLoading();  // carga sincrona

        musicaFondo = manager.get("Musica/musicaFondo.mp3");
        musicaFondo.setLooping(true);  // Infinito
        musicaFondo.play();

        // Disparo
        shoot = manager.get("Musica/shoot.wav");
        shield = manager.get("Musica/shieldDown.mp3");
    }

    // CREACIÓN: Crea todos los objetos.
    private void crearObjetos() {
        crearBoogie();
        crearMarcador();
        crearTorres();
        crearBarraVidaTorre();
        crearBotones();
        crearEnemigos();
        crearEnemigosAlrededorTorre();
    }

    // Texturas: Carga todas las texturas.
    private void cargarTexturas() {
        texturaEnemigos = new Texture("Sprites/enemigo1.png");
        texturaFondo = new Texture(("Fondos/fondoNivel1.jpeg"));
        texturaBoogie = new Texture("Sprites/boogie1_frente.png");
        texturaBala = new Texture("Sprites/bala1.png");
        texturaTorre = new Texture("Sprites/torre.png");
        texturaBotonPausa = new Texture("Botones/pausa.jpeg");
        texturaBotonAcelerar = new Texture("Botones/move.jpeg");
        ganar = new Texto("Fuentes/fuente.fnt");
    }

    // Boogie
    private void crearBoogie() {
        boogie = new Boogie(texturaBoogie, 10, 10);
    }

    // Marcador
    private void crearMarcador() {
        marcador = new Marcador(0.20f*ANCHO, 0.95f*ALTO, boogie);
    }

    // Torre
    private void crearTorres() {
        torre = new Torre(texturaTorre, ANCHO/2 - texturaTorre.getWidth()/2 + 30, ALTO/2 - texturaTorre.getHeight()/2 + 30);
        escudoTorre = new Shield(vista, batch);
        escudoTorre.posicionarEscudo(30, 30);

        torreSuperiorDerecha = new Torre(texturaTorre, ANCHO - texturaTorre.getWidth()/2 - 200, ALTO - texturaTorre.getHeight()/2 - 100);
        escudoTorreSuperiorDerecha = new Shield(vista, batch);
        //escudoTorreSuperiorDerecha.posicionarEscudo(440, 260);

        torreInferiorDerecha = new Torre(texturaTorre, ANCHO - texturaTorre.getWidth()/2 - 300, texturaTorre.getHeight()/2 + 100);
        escudoTorreInferiorDerecha = new Shield(vista, batch);
        escudoTorreInferiorDerecha.posicionarEscudo(340, -190);

        torreSuperiorIzquierda = new Torre(texturaTorre, 200, ALTO - texturaTorre.getHeight()/2 - 170);
        escudoTorreSuperiorIzquierda = new Shield(vista, batch);
        escudoTorreSuperiorIzquierda.posicionarEscudo(-405, 190);
    }

    // Barra Vida
    private void crearBarraVidaTorre() {
        healthBarTorre = new HealthBar();
        healthBarTorreSuperiorDerecha = new HealthBar();
        healthBarTorreInferiorDerecha = new HealthBar();
        healthBarTorreSuperiorIzquierda = new HealthBar();
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

    // Enemigos: Patrones
    private void crearEnemigos() {
        arrEnemigos1 = new LinkedList<>();
        arrEnemigos2 = new LinkedList<>();
        //Para primera horda
        for (int y = 0; y < 10; y++) {
            EnemigoBasico enemigo = new EnemigoBasico(texturaEnemigos, 400-texturaEnemigos.getWidth(),
                    ALTO + texturaEnemigos.getHeight()*y + 20*y);
            arrEnemigos1.add(enemigo);
            EnemigoBasico enemigo2 = new EnemigoBasico(texturaEnemigos, ANCHO + texturaEnemigos.getWidth()*y + 20*y,
                    350 - texturaEnemigos.getHeight() - 50);
            arrEnemigos2.add(enemigo2);
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

    // Dibuja el juego
    @Override
    public void render(float delta) {

        if (estadoJuego == EstadoJuego.JUGANDO) {
            //Actualizaciones
            moverEnemigos(delta);
            llamarHorda();
            moverEnemigosCirculo(delta);
            // Colisones
            probarColisionesEnemigos();
            probarColisionesTorres();
            probarColisionesEscudos();
        }

        // Init: Default initializers.
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        // Draw game: Everything in here is drawn into the game.
        batch.begin();

        // Background: The image of the background is displayed.
        batch.draw(texturaFondo, 0, 0);

        if (estadoJuego == EstadoJuego.JUGANDO || estadoJuego == EstadoJuego.PAUSADO) {

            // Boogie: The image of the boogie is displayed.
            boogie.render(batch);
            // mx.equipotres.thedunes.Torre: The image of the torre is displayed.
            torre.render(batch);

            if (torreSuperiorDerecha.vida >= 0.0f) {
                torreSuperiorDerecha.render(batch);
                healthBarTorreSuperiorDerecha.render(batch, torreSuperiorDerecha, ANCHO - texturaTorre.getWidth()/2 - 220, ALTO - texturaTorre.getHeight()/2 - 20);
            }

            if (torreInferiorDerecha.vida >= 0.0f) {
                torreInferiorDerecha.render(batch);
                healthBarTorreInferiorDerecha.render(batch, torreInferiorDerecha, ANCHO - texturaTorre.getWidth()/2 - 320, texturaTorre.getHeight()/2 + 175);
            }

            if (torreSuperiorIzquierda.vida >= 0.0f) {
                torreSuperiorIzquierda.render(batch);
                healthBarTorreSuperiorIzquierda.render(batch, torreSuperiorIzquierda, 180, ALTO - texturaTorre.getHeight()/2 - 95);
            }

            // Marcador: Lo dibuja en la pantalla.
            marcador.render(batch);
            // Health Bar: Torre Central.
            healthBarTorre.render(batch, torre, ANCHO / 2 - 50 + 30, ALTO / 2 + 40 + 30);
            batch.setColor(Color.WHITE);

            // Balas: Mover al ser creadas.
            for (int i = 0; i < b.size(); i++) {
                if (b.get(i) != null) {
                    b.get(i).render(batch);
                    moverBala(b.get(i), delta);
                }
            }

            // Enemigos
            for (EnemigoBasico enemigo : arrEnemigos1) {
                if (enemigo.estado == Enemigo.Estado.ACTIVADO) {
                    enemigo.render(batch);
                }
            }

            for (EnemigoBasico enemigo2 : arrEnemigos2) {
                if (enemigo2.estado == Enemigo.Estado.ACTIVADO) {
                    enemigo2.render(batch);
                }
            }

            for (EnemigoBasico enemigoBasico : arrEnemigosCirculo) {
                enemigoBasico.render(batch);
            }
        }

        // Indicador de Victoria
        if (torre.vida <= 0.0f && torreSuperiorDerecha.vida <= 0.0f &&
                torreInferiorDerecha.vida <= 0.0f && torreSuperiorIzquierda.vida <= 0.0f) {
            String mensaje = "Enhorabuena, has ganado!";
            ganar.render(batch, mensaje, ANCHO/2 - 20, ALTO/2 + 10);
            marcador.render(batch, ANCHO/2 - 10, ALTO/2 + 55);
            estadoJuego = EstadoJuego.GANO;

            Texture texturaVolverMenu = new Texture("Botones/botonVolverMenu.png");
            TextureRegionDrawable trVM = new TextureRegionDrawable(new TextureRegion(texturaVolverMenu));
            Image btnVolverMenu = new Image(trVM);
            btnVolverMenu.setPosition(ANCHO/2-250, ALTO/2-144);

            btnVolverMenu.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaMenu(juego));
                }
            });

            escenaMenu.addActor(btnVolverMenu);
            Gdx.input.setInputProcessor(escenaMenu);
        }

        // Indicador de derrota
        if (boogie.vidas <= 0.0f) {
            String mensaje = "Has sido derrotado";
            ganar.render(batch, mensaje, ANCHO/2 - 20, ALTO/2 + 10);
            estadoJuego = EstadoJuego.PERDIO;

            Texture texturaVolverMenu = new Texture("Botones/botonVolverMenu.png");
            TextureRegionDrawable trVM = new TextureRegionDrawable(new TextureRegion(texturaVolverMenu));
            Image btnVolverMenu = new Image(trVM);
            btnVolverMenu.setPosition(ANCHO/2-250, ALTO/2-144);

            btnVolverMenu.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaMenu(juego));
                }
            });

            escenaMenu.addActor(btnVolverMenu);
            Gdx.input.setInputProcessor(escenaMenu);
        }

        // Finaliza el batch.
        batch.end();

        if ( estadoJuego == EstadoJuego.JUGANDO) {
            // Visibility: When this is activated everything is visible from show().
            escenaMenu.draw();

            if (torreSuperiorIzquierda.vida >= 0.0f) {
                escudoTorre.draw();
            }
            //escudoTorreSuperiorDerecha.draw();
            if (torreSuperiorDerecha.vida >= 0.0f) {
                escudoTorreInferiorDerecha.draw();
            }
            if (torreInferiorDerecha.vida >= 0.0f) {
                escudoTorreSuperiorIzquierda.draw();
            }

        }

        // Juego Pausado.
        if (estadoJuego == EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        }

        if (estadoJuego == EstadoJuego.PERDIO) {
            escenaMenu.draw();
        }

        if (estadoJuego == EstadoJuego.GANO) {
            escenaMenu.draw();
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
            if (counter >= 100) {
                counter = 0;
            }
        }

    }


    /* Cambia el número de horda de acuerdo al número de pasos que los enemigos han dado y activa los enemigos correspondientes
    a la horda */
    private void llamarHorda() {
        if (numeroPasos==1){
            for(int i = 0; i < arrEnemigos1.size()-1; i++ ){
                EnemigoBasico enemigoBasico = arrEnemigos1.get(i);
                enemigoBasico.estado = Enemigo.Estado.ACTIVADO;
            }
        }
        if (numeroPasos == 300){
            for(int i = 0; i < arrEnemigos2.size()-1; i++ ){
                EnemigoBasico enemigoBasico = arrEnemigos2.get(i);
                enemigoBasico.estado = Enemigo.Estado.ACTIVADO;
            }
        }
        numeroPasos++;
        if (numeroPasos==980){
            crearEnemigos();
            numeroPasos = 0;
        }
    }

    //TO-DO: falta poner los límites de la pantalla para que los enemigos no se salgan
    private void moverEnemigos(float delta) {
        for (EnemigoBasico enemigoBasico : arrEnemigos1) {
            if (enemigoBasico.estado == Enemigo.Estado.ACTIVADO){
                enemigoBasico.moverH1Nvl1(5, -4);
            }
        }
        for (EnemigoBasico enemigoBasico2:arrEnemigos2) {
            if (enemigoBasico2.estado == Enemigo.Estado.ACTIVADO){
                enemigoBasico2.moverH2Nvl1(2, 2);
            }
        }


    }

    // Colisiones Enemigos.
    private void probarColisionesEnemigos() {
        if (b.size() > 0) {
            for (int j = 0; j <= b.size()-1; j++) {
                probarColisionesEnemigos(arrEnemigos1, b.get(j));
            }
            for (int j = 0; j <= b.size()-1; j++) {
                probarColisionesEnemigos(arrEnemigos2, b.get(j));
            }
            for (int j = 0; j <= b.size()-1; j++) {
                probarColisionesEnemigos(arrEnemigosCirculo, b.get(j));
            }
        }
        probarColisionesBoogie(arrEnemigosCirculo);
        probarColisionesBoogie(arrEnemigos1);
        probarColisionesBoogie(arrEnemigos2);

    }

    private void probarColisionesBoogie(LinkedList<EnemigoBasico> enemigos){
        for (int i = enemigos.size() - 1; i >= 0; i--) {
            EnemigoBasico enemigoBasico = enemigos.get(i);
            Rectangle rectEnemigoBasico = enemigoBasico.sprite.getBoundingRectangle();
            Rectangle rectBoogie = boogie.sprite.getBoundingRectangle();
            if (rectEnemigoBasico.overlaps(rectBoogie)) {
                boogie.restarVida(1);
                marcador.restarVidas(1);
                boogie.sprite.setPosition(10, 10);
            }
        }
    }

    private void probarColisionesEnemigos(LinkedList<EnemigoBasico> enemigos, Bala bala){
        for (int i = enemigos.size() - 1; i >= 0; i--){
            EnemigoBasico enemigoBasico = enemigos.get(i);
            Rectangle rectEnemigoBasico = enemigoBasico.sprite.getBoundingRectangle();
            Rectangle rectBala = bala.sprite.getBoundingRectangle();
            if (rectEnemigoBasico.overlaps(rectBala)) {
                b.remove(bala);
                if (enemigoBasico.recibirDaño()){
                    enemigos.remove(i);
                    marcador.agregarPuntos(5);
                }
                break;
            }
        }
    }

    // Colisiones Torres
    private void probarColisionesTorres() {
        Rectangle rectTorre = torre.sprite.getBoundingRectangle();
        Rectangle rectTorreSuperiorDerecha = torreSuperiorDerecha.sprite.getBoundingRectangle();
        Rectangle rectTorreInferiorDerecha = torreInferiorDerecha.sprite.getBoundingRectangle();
        Rectangle rectTorreSuperiorIzquierda = torreSuperiorIzquierda.sprite.getBoundingRectangle();
        Rectangle rectBoogie = boogie.sprite.getBoundingRectangle();

        if (rectBoogie.overlaps(rectTorreSuperiorDerecha)) {
            if (torreSuperiorDerecha.vida >= 0.0f) {
                boogie.restarVida(1);
                marcador.restarVidas(1);
                boogie.sprite.setPosition(10, 10);
            }
        } else if (rectBoogie.overlaps(rectTorreInferiorDerecha)) {
            if (torreInferiorDerecha.vida >= 0.0f) {
                boogie.restarVida(1);
                marcador.restarVidas(1);
                boogie.sprite.setPosition(10, 10);
            }
        } else if (rectBoogie.overlaps(rectTorreSuperiorIzquierda)) {
            if (torreSuperiorIzquierda.vida >= 0.0f) {
                boogie.restarVida(1);
                marcador.restarVidas(1);
                boogie.sprite.setPosition(10, 10);
            }
        } else if (rectBoogie.overlaps(rectTorre)) {
            if (torre.vida >= 0.0f) {
                boogie.restarVida(1);
                marcador.restarVidas(1);
                boogie.sprite.setPosition(10, 10);
            }
        }

        for (int j = 0; j < b.size(); j++) {
            Rectangle rectBala = b.get(j).sprite.getBoundingRectangle();
            if (rectTorre.overlaps(rectBala)) {
                torre.restarVida();
                b.remove(j);
                break;
            } else if (rectTorreSuperiorDerecha.overlaps(rectBala)) {
                torreSuperiorDerecha.restarVida();
                if (torreSuperiorDerecha.vida >= 0.0f) {
                    hit++;
                    b.remove(j);
                    if (hit == 9) {
                        System.out.println(hit);
                        shield.play();
                        hit = 0;
                    }
                }
                break;
            } else if (rectTorreInferiorDerecha.overlaps(rectBala)) {
                torreInferiorDerecha.restarVida();
                if (torreInferiorDerecha.vida >= 0.0f) {
                    hit++;
                    b.remove(j);
                    if (hit == 9) {
                        shield.play();
                        hit = 0;
                    }
                }
                break;
            } else if (rectTorreSuperiorIzquierda.overlaps(rectBala)) {
                torreSuperiorIzquierda.restarVida();
                if (torreSuperiorIzquierda.vida >= 0.0f) {
                    hit++;
                    System.out.println(hit);
                    b.remove(j);
                    if (hit == 9) {
                        shield.play();
                        hit = 0;
                    }
                }
                break;
            }
        }
    }

    // Colisiones Escudos
    private void probarColisionesEscudos() {
        Rectangle rectEscudoTorre = escudoTorre.getBoundaries(ANCHO/2 - 30, ALTO/2 - 30);
        Rectangle rectEscudoTorreInferiorDerecha = escudoTorreInferiorDerecha.getBoundaries(
                ANCHO - texturaTorre.getWidth()/2 - 330 - 15, texturaTorre.getHeight()/2 + 145 - 60);
        Rectangle rectEscudoTorreSuperiorIzquierda = escudoTorreSuperiorIzquierda.getBoundaries(
                170 - 10, ALTO - texturaTorre.getHeight()/2 - 125 - 60);
        Rectangle rectBoogie = boogie.sprite.getBoundingRectangle();

        if (rectBoogie.overlaps(rectEscudoTorreInferiorDerecha)) {
            boogie.restarVida(1);
            marcador.restarVidas(1);
            boogie.sprite.setPosition(10, 10);
        } else if (rectBoogie.overlaps(rectEscudoTorreSuperiorIzquierda)) {
            boogie.restarVida(1);
            marcador.restarVidas(1);
            boogie.sprite.setPosition(10, 10);
        } else if (rectBoogie.overlaps(rectEscudoTorre)) {
            boogie.restarVida(1);
            marcador.restarVidas(1);
            boogie.sprite.setPosition(10, 10);
        }

        for (int j = 0; j < b.size(); j++) {
            Rectangle rectBala = b.get(j).sprite.getBoundingRectangle();
            if (rectEscudoTorre.overlaps(rectBala)) {
                if (torreSuperiorIzquierda.vida >= 0.0f) {
                    b.remove(j);
                }
                break;
            } else if (rectEscudoTorreInferiorDerecha.overlaps(rectBala)) {
                if (torreSuperiorDerecha.vida >= 0.0f) {
                    b.remove(j);
                }
                break;
            } else if (rectEscudoTorreSuperiorIzquierda.overlaps(rectBala)) {
                if (torreInferiorDerecha.vida >= 0.0f) {
                    b.remove(j);
                }
                break;
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
                    shoot.play();
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

            if (v.y >= ALTO - texturaBotonPausa.getHeight() &&
                    v.x >= ANCHO - texturaBotonPausa.getWidth()) {
                estadoJuego = EstadoJuego.PAUSADO;
                if (escenaPausa == null) {
                    escenaPausa = new EscenaPausa(vista, batch);
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

            Texture texturaReanudar = new Texture("Botones/botonReanudar.png");
            Texture texturaReiniciar = new Texture("Botones/botonReiniciar.png");
            Texture texturaVolverMenu = new Texture("Botones/botonVolverMenu.png");
            Texture texturaRectangulo = new Texture("Fondos/Fondopausa.jpeg");
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(ANCHO/2 - texturaRectangulo.getWidth()/2, ALTO/2 - texturaRectangulo.getHeight()/2);

            TextureRegionDrawable trRd = new TextureRegionDrawable(new TextureRegion(texturaReanudar));
            Image btnReanudar = new Image(trRd);
            btnReanudar.setPosition(imgRectangulo.getX()+200, imgRectangulo.getY()+392);

            TextureRegionDrawable trRe = new TextureRegionDrawable(new TextureRegion(texturaReiniciar));
            Image btnReiniciar = new Image(trRe);
            btnReiniciar.setPosition(imgRectangulo.getX()+200, imgRectangulo.getY()+268);

            TextureRegionDrawable trVM = new TextureRegionDrawable(new TextureRegion(texturaVolverMenu));
            Image btnVolverMenu = new Image(trVM);
            btnVolverMenu.setPosition(imgRectangulo.getX()+200, imgRectangulo.getY()+144);

            btnReanudar.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    estadoJuego = EstadoJuego.JUGANDO;
                }
            });

            btnReiniciar.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaPrimerNivel(juego));
                }
            });

            btnVolverMenu.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaMenu(juego));
                }
            });


            this.addActor(imgRectangulo);
            this.addActor(btnReanudar);
            this.addActor(btnReiniciar);
            this.addActor(btnVolverMenu);

            Gdx.input.setInputProcessor(this);
        }

    }

    private enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        GANO,
        PERDIO
    }
}
