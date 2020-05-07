package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;

// Primer Nivel del Juego.
class PantallaPrimerNivel extends Pantalla {

    // Juego: allows to create another screen when the listener is activated.
    private final Juego juego;

    //Juego
    private Texture texturaFondo;
    private Marcador marcador;
    private Texto ganar;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO;
    private Stage escenaFinal;

    // Pausa
    private EscenaPausa escenaPausa;
    private Texture texturaBotonPausa;

    // Música
    private Preferences prefsMusic = Gdx.app.getPreferences("TheDunes.settings.music");
    private Preferences prefsSoundFX = Gdx.app.getPreferences("TheDunes.settings.soundFX");;
    private Music musicaFondo;
    private Sound shoot;
    private Sound shield;
    private int hit = 0;

    //Joystick
    private Stage escenaHUD;
    private OrthographicCamera cameraHUD;
    private Viewport vistaHUD;
    private boolean joystickPresionado;

    // Torres
    private Texture texturaTorre;
    private Torre torre;
    private Escudo escudoTorre;
    private Torre torreSuperiorDerecha;
    private Torre torreInferiorDerecha;
    private Escudo escudoTorreInferiorDerecha;
    private Torre torreSuperiorIzquierda;
    private Escudo escudoTorreSuperiorIzquierda;

    // Barras de vida
    private BarraVida barraVidaTorre;
    private BarraVida barraVidaTorreSuperiorDerecha;
    private BarraVida barraVidaTorreInferiorDerecha;
    private BarraVida barraVidaTorreSuperiorIzquierda;

    // Boogie
    private Boogie boogie;
    private Texture texturaBoogie;
    // Mover
    private Texture texturaBotonAcelerar;

    // Balas: Las que sean.
    private Texture texturaBala;
    private LinkedList<Bala> listaBalas = new LinkedList<>();
    private float direccionBala;

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
    private int contador = 0;
    private boolean trigger = false;


    public PantallaPrimerNivel(Juego juego) { this.juego = juego; }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,0,1);
        cargarTexturas();
        crearObjetos();
        cargarMusica();
        crearHUD();
    }

    public void crearHUD(){
        cameraHUD = new OrthographicCamera(ANCHO, ALTO);
        cameraHUD.position.set(ANCHO/2, ALTO/2, 0);
        cameraHUD.update();
        vistaHUD = new StretchViewport(ANCHO, ALTO, cameraHUD);

        Skin skin = new Skin();
        skin.add("fondo", new Texture("Botones/padBack.png"));
        skin.add("boton", new Texture("Botones/padKnob.png"));
        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("fondo");
        estilo.knob = skin.getDrawable("boton");

        Touchpad pad = new Touchpad(64, estilo);
        pad.setBounds(16, 16, 170, 170);
        pad.setColor(1, 1, 1, 0.7f);
        pad.addListener(new ActorGestureListener() {
            public void touchDown (InputEvent event, float x, float y, int pointer, int button) {
                joystickPresionado = true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                joystickPresionado = false;
            }
        });
        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad)actor;
                if (pad.getKnobPercentX() != 0 || pad.getKnobPercentY() != 0){
                    boogie.rotacion(pad.getKnobPercentX(), pad.getKnobPercentY());
                }
            }
        });
        escenaHUD = new Stage(vistaHUD);
        // Botón: Acelerar.
        TextureRegionDrawable trdAcelerar = new TextureRegionDrawable(new TextureRegion(texturaBotonAcelerar));
        ImageButton btnAcelerar = new ImageButton(trdAcelerar);
        btnAcelerar.setPosition(ANCHO - btnAcelerar.getWidth(),0);

        // Botón pausa
        TextureRegionDrawable trdPausa = new TextureRegionDrawable(new TextureRegion(texturaBotonPausa));
        ImageButton btnPausa = new ImageButton(trdPausa);
        btnPausa.setPosition(ANCHO - btnPausa.getWidth() - 5 ,ALTO - btnPausa.getHeight() - 5);

        //Acción de acelerar
        btnAcelerar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                crearBala();
                if (prefsSoundFX.getBoolean("soundFXOn")==true) {
                    shoot.play();
                }
            }
        });

        //Acción de pausa
        btnPausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                estadoJuego = EstadoJuego.PAUSADO;
                escenaPausa = new EscenaPausa(vista, batch);
            }
        });

        escenaHUD.addListener(new InputListener(){
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(keycode == Input.Keys.SPACE){
                    crearBala();
                    if (prefsSoundFX.getBoolean("soundFXOn")==true) {
                        shoot.play();
                    }
                }

                return super.keyUp(event, keycode) ;
            }
        });


        escenaHUD.addActor(pad);
        escenaHUD.addActor(btnAcelerar);
        escenaHUD.addActor(btnPausa);

        Gdx.input.setInputProcessor(escenaHUD);
    }

    public void cargarMusica() {
        AssetManager manager = new AssetManager();
        manager.load("Musica/musicaFondo.mp3", Music.class);
        manager.load("Musica/shoot.wav", Sound.class);
        manager.load("Musica/shieldDown.mp3", Sound.class);
        manager.finishLoading();  // carga sincrona
        musicaFondo = manager.get("Musica/musicaFondo.mp3");
        musicaFondo.setLooping(true);  // Infinito

        if (prefsMusic.getBoolean("musicOn")==true) {
            musicaFondo.play();
        }
        // Disparo
        shoot = manager.get("Musica/shoot.wav");
        shield = manager.get("Musica/shieldDown.mp3");
    }

    // CREACIÓN: Boodie, Marcador, Torres, Escudos, Barras de vida y enemigos
    private void crearObjetos() {
        crearBoogie();
        crearMarcador();
        crearTorres();
        crearEscudos();
        crearBarraVidaTorre();
        crearEnemigos();
        crearEnemigosAlrededorTorre();
    }

    private void crearBala() {
        float xBala = boogie.sprite.getX() + boogie.sprite.getWidth()/2;
        float yBala = boogie.sprite.getY() + boogie.sprite.getHeight()/2;
        listaBalas.add(new Bala(texturaBala, xBala, yBala));
        direccionBala = listaBalas.size() - 1;
        listaBalas.get((int) direccionBala).sprite.setRotation(boogie.sprite.getRotation());
    }


    private void cargarTexturas() {
        texturaEnemigos = new Texture("Sprites/enemigo1.png");
        texturaFondo = new Texture(("Fondos/FondoNivel1.jpeg"));
        texturaBoogie = new Texture("Sprites/boogie1_frente.png");
        texturaBala = new Texture("Sprites/bala1.png");
        texturaTorre = new Texture("Sprites/torre.png");
        texturaBotonPausa = new Texture("Botones/pausa.png");
        texturaBotonAcelerar = new Texture("Botones/disparar.png");
        ganar = new Texto("Fuentes/fuente.fnt");
    }

    private void crearBoogie() {
        boogie = new Boogie(texturaBoogie, 180, 10);
    }

    private void crearMarcador() {

        marcador = new Marcador(0.20f*ANCHO, 0.95f*ALTO, boogie);
    }

    private void crearTorres() {
        torre = new Torre(texturaTorre, ANCHO/2 - texturaTorre.getWidth()/2 + 30, ALTO/2 - texturaTorre.getHeight()/2 + 30);
        torreSuperiorDerecha = new Torre(texturaTorre, ANCHO - texturaTorre.getWidth()/2 - 200, ALTO - texturaTorre.getHeight()/2 - 100);
        torreInferiorDerecha = new Torre(texturaTorre, ANCHO - texturaTorre.getWidth()/2 - 300, texturaTorre.getHeight()/2 + 100);
        torreSuperiorIzquierda = new Torre(texturaTorre, 200, ALTO - texturaTorre.getHeight()/2 - 170);
    }
    private void crearEscudos(){
        escudoTorre = new Escudo(vista, batch);
        escudoTorre.posicionarEscudo(30, 30);

        escudoTorreInferiorDerecha = new Escudo(vista, batch);
        escudoTorreInferiorDerecha.posicionarEscudo(340, -190);

        escudoTorreSuperiorIzquierda = new Escudo(vista, batch);
        escudoTorreSuperiorIzquierda.posicionarEscudo(-405, 190);

    }

    private void crearBarraVidaTorre() {
        barraVidaTorre = new BarraVida();
        barraVidaTorreSuperiorDerecha = new BarraVida();
        barraVidaTorreInferiorDerecha = new BarraVida();
        barraVidaTorreSuperiorIzquierda = new BarraVida();
    }


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
        /*DECIDÍ COMENTARLO PORQUE NO ESTOY SEGURA. SEGÚN YO ES MEJOR PARA EL PROCESADOR SIMPLEMENTE CREARLOS TODOS Y AGREGARLOS A
        ESTAR RECORRIENDO EL FOR Y CREANDOLOS
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

        }*/

        EnemigoBasico enemigoBasico = new EnemigoBasico(texturaEnemigos, x + paso, y);
        arrEnemigosCirculo.add(enemigoBasico);

        enemigoBasico = new EnemigoBasico(texturaEnemigos, x + paso/2, y + paso/2);
        arrEnemigosCirculo.add(enemigoBasico);

        enemigoBasico = new EnemigoBasico(texturaEnemigos, x,  y + paso);
        arrEnemigosCirculo.add(enemigoBasico);

        enemigoBasico = new EnemigoBasico(texturaEnemigos,  x - paso/2, y + paso/2);
        arrEnemigosCirculo.add(enemigoBasico);

        enemigoBasico = new EnemigoBasico(texturaEnemigos, x - paso, y);
        arrEnemigosCirculo.add(enemigoBasico);

        enemigoBasico = new EnemigoBasico(texturaEnemigos, x - paso/2 ,y - paso/2);
        arrEnemigosCirculo.add(enemigoBasico);

        enemigoBasico = new EnemigoBasico(texturaEnemigos, x,y - paso );
        arrEnemigosCirculo.add(enemigoBasico);

        enemigoBasico = new EnemigoBasico(texturaEnemigos, x + paso/2, y - paso/2);
        arrEnemigosCirculo.add(enemigoBasico);
    }

    // Dibuja el juego
    @Override
    public void render(float delta) {

        if (estadoJuego == EstadoJuego.JUGANDO) {
            //Actualizaciones
            actualizar(delta);

            // Colisones
            verificarColisiones();
        }

        // Iniciar por Default
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.draw(texturaFondo, 0, 0);

        //Dibujar elementos del juego
        if (estadoJuego == EstadoJuego.JUGANDO || estadoJuego == EstadoJuego.PAUSADO) {
            dibujarJuego(batch, delta);
        }

        // Indicador de Victoria
        if (torre.vida <= 0.0f && torreSuperiorDerecha.vida <= 0.0f &&
                torreInferiorDerecha.vida <= 0.0f && torreSuperiorIzquierda.vida <= 0.0f) {
            dibujarVictoria(batch);
        }

        // Indicador de derrota
        if (boogie.vidas <= 0.0f) {
            dibujarDerrota(batch);
        }

        batch.end();

        batch.setProjectionMatrix(cameraHUD.combined);



        if ( estadoJuego == EstadoJuego.JUGANDO) {
            dibujarEscudos();
            escenaHUD.draw();
        }

        if (estadoJuego == EstadoJuego.PAUSADO) {
            dibujarEscudos();
            escenaPausa.draw();
        }

        if (estadoJuego == EstadoJuego.PERDIO) {
            escenaFinal.draw();
            escenaHUD.draw();
        }

        if (estadoJuego == EstadoJuego.GANO) {
            escenaFinal.draw();
            escenaHUD.draw();
        }

    }

    private void dibujarEscudos() {
        if (torreSuperiorIzquierda.vida >= 0.0f) {
            escudoTorre.draw();
        }
        if (torreSuperiorDerecha.vida >= 0.0f) {
            escudoTorreInferiorDerecha.draw();
        }
        if (torreInferiorDerecha.vida >= 0.0f) {
            escudoTorreSuperiorIzquierda.draw();
        }
    }

    private void dibujarDerrota(SpriteBatch batch) {
        escenaFinal = new Stage(vista);
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
                musicaFondo.stop();
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        escenaFinal.addActor(btnVolverMenu);
        Gdx.input.setInputProcessor(escenaFinal);
    }

    private void dibujarVictoria(SpriteBatch batch) {
        escenaFinal = new Stage(vista);
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
                musicaFondo.stop();
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        escenaFinal.addActor(btnVolverMenu);
        Gdx.input.setInputProcessor(escenaFinal);
    }

    public void dibujarJuego(SpriteBatch batch, float delta){
        boogie.render(batch);
        torre.render(batch);

        if (torreSuperiorDerecha.vida >= 0.0f) {
            torreSuperiorDerecha.render(batch);
            barraVidaTorreSuperiorDerecha.render(batch, torreSuperiorDerecha, ANCHO - texturaTorre.getWidth()/2 - 220, ALTO - texturaTorre.getHeight()/2 - 20);
        }
        if (torreInferiorDerecha.vida >= 0.0f) {
            torreInferiorDerecha.render(batch);
            barraVidaTorreInferiorDerecha.render(batch, torreInferiorDerecha, ANCHO - texturaTorre.getWidth()/2 - 320, texturaTorre.getHeight()/2 + 175);
        }
        if (torreSuperiorIzquierda.vida >= 0.0f) {
            torreSuperiorIzquierda.render(batch);
            barraVidaTorreSuperiorIzquierda.render(batch, torreSuperiorIzquierda, 180, ALTO - texturaTorre.getHeight()/2 - 95);
        }
        barraVidaTorre.render(batch, torre, ANCHO / 2 - 50 + 30, ALTO / 2 + 40 + 30);
        batch.setColor(Color.WHITE);

        marcador.render(batch);

        // Balas:
        for (int i = 0; i < listaBalas.size(); i++) {
            if (listaBalas.get(i) != null) {
                listaBalas.get(i).render(batch);
                moverBala(listaBalas.get(i), delta);
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

    //Enemigos, torres y escudos
    private void verificarColisiones() {
        probarColisionesEnemigos();
        probarColisionesTorres();
        probarColisionesEscudos();
    }

    // Actualiza: el movimiento de los enemigos y las hordas
    private void actualizar(float delta) {
        moverEnemigos(delta);
        llamarHorda();
        moverEnemigosCirculo(delta);
        if(joystickPresionado){
            boogie.mover();
        }
    }


    // Mover Enemigos alrededor del Castillo.
    private void moverEnemigosCirculo(float delta) {
        tiempoMoverEnemigo += delta;
        if (contador < MAX_PASOS_CIRCLE) {
            if (tiempoMoverEnemigo >= TIEMPO_PASO) {
                tiempoMoverEnemigo = 0;
                contador += 5;
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
        } else if (contador >= MAX_PASOS_CIRCLE) {
            if (tiempoMoverEnemigo >= TIEMPO_PASO) {
                trigger = true;
                tiempoMoverEnemigo = 0;
                contador += 5;
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
            if (contador >= 100) {
                contador = 0;
            }
        }

    }


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
        if (listaBalas.size() > 0) {
            for (int j = 0; j <= listaBalas.size()-1; j++) {
                probarColisionesEnemigos(arrEnemigos1, listaBalas.get(j));
            }
            for (int j = 0; j <= listaBalas.size()-1; j++) {
                probarColisionesEnemigos(arrEnemigos2, listaBalas.get(j));
            }
            for (int j = 0; j <= listaBalas.size()-1; j++) {
                probarColisionesEnemigos(arrEnemigosCirculo, listaBalas.get(j));
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
                boogie.sprite.setPosition(180, 10);
            }
        }
    }

    private void probarColisionesEnemigos(LinkedList<EnemigoBasico> enemigos, Bala bala){
        for (int i = enemigos.size() - 1; i >= 0; i--){
            EnemigoBasico enemigoBasico = enemigos.get(i);
            Rectangle rectEnemigoBasico = enemigoBasico.sprite.getBoundingRectangle();
            Rectangle rectBala = bala.sprite.getBoundingRectangle();
            if (rectEnemigoBasico.overlaps(rectBala)) {
                listaBalas.remove(bala);
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
                boogie.sprite.setPosition(180, 10);
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

        for (int j = 0; j < listaBalas.size(); j++) {
            Rectangle rectBala = listaBalas.get(j).sprite.getBoundingRectangle();
            if (rectTorre.overlaps(rectBala)) {
                torre.restarVida();
                listaBalas.remove(j);
                break;
            } else if (rectTorreSuperiorDerecha.overlaps(rectBala)) {
                torreSuperiorDerecha.restarVida();
                if (torreSuperiorDerecha.vida >= 0.0f) {
                    hit++;
                    listaBalas.remove(j);
                    if (hit == 9) {
                        System.out.println(hit);
                        if (prefsSoundFX.getBoolean("soundFXOn")==true) {
                            shield.play();
                        }
                        hit = 0;
                    }
                }
                break;
            } else if (rectTorreInferiorDerecha.overlaps(rectBala)) {
                torreInferiorDerecha.restarVida();
                if (torreInferiorDerecha.vida >= 0.0f) {
                    hit++;
                    listaBalas.remove(j);
                    if (hit == 9) {
                        if (prefsSoundFX.getBoolean("soundFXOn")==true) {
                            shield.play();
                        }
                        hit = 0;
                    }
                }
                break;
            } else if (rectTorreSuperiorIzquierda.overlaps(rectBala)) {
                torreSuperiorIzquierda.restarVida();
                if (torreSuperiorIzquierda.vida >= 0.0f) {
                    hit++;
                    System.out.println(hit);
                    listaBalas.remove(j);
                    if (hit == 9) {
                        if (prefsSoundFX.getBoolean("soundFXOn")==true) {
                            shield.play();
                        }
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

        for (int j = 0; j < listaBalas.size(); j++) {
            Rectangle rectBala = listaBalas.get(j).sprite.getBoundingRectangle();
            if (rectEscudoTorre.overlaps(rectBala)) {
                if (torreSuperiorIzquierda.vida >= 0.0f) {
                    listaBalas.remove(j);
                }
                break;
            } else if (rectEscudoTorreInferiorDerecha.overlaps(rectBala)) {
                if (torreSuperiorDerecha.vida >= 0.0f) {
                    listaBalas.remove(j);
                }
                break;
            } else if (rectEscudoTorreSuperiorIzquierda.overlaps(rectBala)) {
                if (torreInferiorDerecha.vida >= 0.0f) {
                    listaBalas.remove(j);
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
            //bala.sprite.setPosition(boogie.sprite.getX() + boogie.sprite.getWidth()/2, boogie.sprite.getY() + boogie.sprite.getHeight()/2);
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
                    crearBala();
                    if (prefsSoundFX.getBoolean("soundFXOn")==true) {
                        shoot.play();
                    }
                    break;
            }
            return true;
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
                    Gdx.input.setInputProcessor(escenaHUD);
                }
            });

            btnReiniciar.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    musicaFondo.stop();
                    juego.setScreen(new PantallaPrimerNivel(juego));
                }
            });

            btnVolverMenu.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    musicaFondo.stop();
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
