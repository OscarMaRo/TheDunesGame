package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

class PantallaTercerNivel extends Pantalla {
    // Juego:
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

    // Boogie
    private Boogie boogie;
    private Texture texturaBoogie;
    // Mover
    private Texture texturaBotonAcelerar;

    // Balas:
    private Texture texturaBala;
    private LinkedList<Bala> listaBalas = new LinkedList<>();
    private float direccionBala;

    //Torres
    private Texture texturaTorreNivel2;
    private Texture texturaTorreNivel3;
    private Torre torre1;
    private Torre torre2;
    private Torre torre3;
    private Torre torre4;
    private BarraVida barraVidaTorre1;
    private BarraVida barraVidaTorre2;
    private BarraVida barraVidaTorre3;
    private BarraVida barraVidaTorre4;


    // Balas: torres
    private Bala bala1Torre4;
    private Bala bala2Torre4;
    private Bala bala3Torre4;
    private int timerBalas1 = 0;
    private int timerBalas2 = 0;
    private int timerBalas3 = 0;


    public PantallaTercerNivel(Juego juego) { this.juego = juego; }


    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,0,1);
        cargarTexturas();
        crearObjetos();
        cargarMusica();
        crearHUD();
        crearEscenaFinal();

        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }
    private void cargarTexturas() {
        texturaFondo = new Texture(("Fondos/FondoNivel2.png"));
        //texturaEnemigos = new Texture("Sprites/enemigo1.png");
        texturaBoogie = new Texture("Sprites/boogie1_frente.png");
        texturaBala = new Texture("Sprites/bala1.png");
        texturaBotonPausa = new Texture("Botones/pausa.png");
        texturaBotonAcelerar = new Texture("Botones/disparar.png");
        ganar = new Texto("Fuentes/fuente.fnt");

        texturaTorreNivel3 = new Texture("Sprites/torreNivel3.png");
        texturaTorreNivel2 = new Texture("Sprites/torreNivel2.png");
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

    public void crearHUD() {
        cameraHUD = new OrthographicCamera(ANCHO, ALTO);
        cameraHUD.position.set(ANCHO / 2, ALTO / 2, 0);
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
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                joystickPresionado = true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                joystickPresionado = false;
            }
        });
        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad) actor;
                if (pad.getKnobPercentX() != 0 || pad.getKnobPercentY() != 0) {
                    boogie.rotacion(pad.getKnobPercentX(), pad.getKnobPercentY());
                }
            }
        });
        escenaHUD = new Stage(vistaHUD);
        // Botón: Acelerar.
        TextureRegionDrawable trdAcelerar = new TextureRegionDrawable(new TextureRegion(texturaBotonAcelerar));
        ImageButton btnAcelerar = new ImageButton(trdAcelerar);
        btnAcelerar.setPosition(ANCHO - btnAcelerar.getWidth(), 0);

        // Botón pausa
        TextureRegionDrawable trdPausa = new TextureRegionDrawable(new TextureRegion(texturaBotonPausa));
        ImageButton btnPausa = new ImageButton(trdPausa);
        btnPausa.setPosition(ANCHO - btnPausa.getWidth() - 5, ALTO - btnPausa.getHeight() - 5);

        //Acción de acelerar
        btnAcelerar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                crearBala();
                if (prefsSoundFX.getBoolean("soundFXOn") == true) {
                    shoot.play();
                }
            }
        });

        //Acción de pausa
        btnPausa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                estadoJuego = EstadoJuego.PAUSADO;
                escenaPausa = new EscenaPausa(vista, batch);
            }
        });

        escenaHUD.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    crearBala();
                    if (prefsSoundFX.getBoolean("soundFXOn") == true) {
                        shoot.play();
                    }
                }

                return super.keyUp(event, keycode);
            }
        });


        escenaHUD.addActor(pad);
        escenaHUD.addActor(btnAcelerar);
        escenaHUD.addActor(btnPausa);

        Gdx.input.setInputProcessor(escenaHUD);
    }

    private void crearBala() {
        float xBala = boogie.sprite.getX() + boogie.sprite.getWidth()/2;
        float yBala = boogie.sprite.getY() + boogie.sprite.getHeight()/2;
        listaBalas.add(new Bala(texturaBala, xBala, yBala));
        direccionBala = listaBalas.size() - 1;
        listaBalas.get((int) direccionBala).sprite.setRotation(boogie.sprite.getRotation());
    }

    private void crearBoogie() {
        boogie = new Boogie(texturaBoogie, 180, 10);
    }

    private void crearMarcador() {
        marcador = new Marcador(0.20f*ANCHO, 0.95f*ALTO, boogie);
    }

    public void crearEscenaFinal(){
        escenaFinal = new Stage(vista);

        Texture texturaVolverMenu = new Texture("Botones/botonVolverMenu.png");
        TextureRegionDrawable trVM = new TextureRegionDrawable(new TextureRegion(texturaVolverMenu));
        Image btnVolverMenu = new Image(trVM);
        btnVolverMenu.setPosition(ANCHO/2-200, ALTO/2-144);

        btnVolverMenu.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                musicaFondo.stop();
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        escenaFinal.addActor(btnVolverMenu);
    }

    private void crearTorres() {
        torre1 = new Torre(texturaTorreNivel2, ANCHO/2 - texturaTorreNivel2.getWidth()/2, ALTO - texturaTorreNivel2.getHeight() - 100);
        torre2 = new Torre(texturaTorreNivel2, ANCHO/2  + texturaTorreNivel2.getWidth() / 2, ALTO/2- texturaTorreNivel2.getHeight()/2);
        torre3 = new Torre(texturaTorreNivel2, ANCHO/2  - texturaTorreNivel2.getWidth() / 2, 100);
        torre4 = new Torre(texturaTorreNivel3, ANCHO -300, ALTO / 2 - texturaTorreNivel3.getHeight()/2,2.0f);

    }


    private void crearBarraVidaTorre() {
        barraVidaTorre1 = new BarraVida();
        barraVidaTorre2 = new BarraVida();
        barraVidaTorre3 = new BarraVida();
        barraVidaTorre4 = new BarraVida();

    }

    private void crearBalasTorres() {
        bala1Torre4 = new Bala(texturaBala, torre4.sprite.getX() + torre4.sprite.getWidth() / 2, torre4.sprite.getY() + torre4.sprite.getHeight() - 70);
        bala2Torre4 = new Bala(texturaBala, torre4.sprite.getX() + torre4.sprite.getWidth() / 2, torre4.sprite.getY() + torre4.sprite.getHeight()/2 - 7);
        bala3Torre4 = new Bala(texturaBala, torre4.sprite.getX() + torre4.sprite.getWidth() / 2, torre4.sprite.getY() + 50);
    }

    // CREACIÓN: Boodie, Marcador
    private void crearObjetos() {
        crearBoogie();
        crearMarcador();
        crearTorres();
        //crearEscudos();
        crearBarraVidaTorre();
        crearBalasTorres();
        //crearEnemigos();
        //crearEnemigosAlrededorTorre();
    }

    @Override
    public void render(float delta) {
        if (estadoJuego == EstadoJuego.JUGANDO) {
            //Actualizaciones
            actualizar(delta);

            // Colisones
            verificarColisiones();

            if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
                estadoJuego = EstadoJuego.PAUSADO;
                escenaPausa = new EscenaPausa(vista, batch);
            }
        }

        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        batch.draw(texturaFondo, 0, 0);

        //Dibujar elementos del juego
        if (estadoJuego == EstadoJuego.JUGANDO || estadoJuego == EstadoJuego.PAUSADO) {
            dibujarJuego(batch, delta);
        }

        // Indicador de Victoria
        if (torre4.vida <= 0.0f) {
            dibujarVictoria(batch);
        }

        // Indicador de derrota
        if (boogie.vidas <= 0.0f) {
            dibujarDerrota(batch);
        }
        batch.end();

        batch.setProjectionMatrix(cameraHUD.combined);
        if ( estadoJuego == EstadoJuego.JUGANDO) {

            escenaHUD.draw();
        }

        if (estadoJuego == EstadoJuego.PAUSADO) {
            if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
                estadoJuego = EstadoJuego.JUGANDO;
                Gdx.input.setInputProcessor(escenaHUD);
            }
            escenaPausa.draw();
        }

        if (estadoJuego == EstadoJuego.PERDIO) {
            escenaFinal.draw();

        }

        if (estadoJuego == EstadoJuego.GANO) {
            escenaFinal.draw();

        }

    }

    private void dibujarDerrota(SpriteBatch batch) {
        String mensaje = "Has sido derrotado";
        ganar.render(batch, mensaje, ANCHO/2 - 20, ALTO/2 + 10);
        estadoJuego = EstadoJuego.PERDIO;

        Gdx.input.setInputProcessor(escenaFinal);
    }

    private void dibujarVictoria(SpriteBatch batch) {
        String mensaje = "Enhorabuena, has ganado!";
        ganar.render(batch, mensaje, ANCHO/2 - 20, ALTO/2 + 10);
        marcador.render(batch, ANCHO/2 - 10, ALTO/2 + 55);
        estadoJuego = EstadoJuego.GANO;
        Gdx.input.setInputProcessor(escenaFinal);
    }

    public void dibujarJuego(SpriteBatch batch, float delta){
        boogie.render(batch);

        marcador.render(batch);

        // Balas:
        for (int i = 0; i < listaBalas.size(); i++) {
            if (listaBalas.get(i) != null) {
                listaBalas.get(i).render(batch);
                moverBala(listaBalas.get(i), delta);
            }
        }

        //torres
        if (torre1.vida >= 0.0f) {
            torre1.render(batch);
            barraVidaTorre1.render(batch, torre1, torre1.sprite.getX()+ 10, torre1.sprite.getY());
        }
        if (torre2.vida >= 0.0f) {
            torre2.render(batch);
            barraVidaTorre2.render(batch, torre2, torre2.sprite.getX() + 10, torre2.sprite.getY());
        }
        if (torre3.vida >= 0.0f) {
            torre3.render(batch);
            barraVidaTorre3.render(batch, torre3, torre3.sprite.getX() + 10, torre3.sprite.getY());
        }
        if (torre4.vida >= 0.0f) {
            torre4.render(batch);
            barraVidaTorre4.render(batch, torre4, torre4.sprite.getX()-30, torre4.sprite.getY());
            if (timerBalas1 >= 20) {
                bala1Torre4.render(batch);
                moverBalaTorre(bala1Torre4, delta,1);
            }
            if (timerBalas2 >= 20) {
                bala2Torre4.render(batch);
                moverBalaTorre(bala2Torre4, delta,2);
            }
            if (timerBalas3 >= 20) {
                bala3Torre4.render(batch);
                moverBalaTorre(bala3Torre4, delta,3);
            }

        }
        batch.setColor(Color.WHITE);

        // Enemigos

    }

    // Actualiza: el movimiento de los enemigos y las hordas
    private void actualizar(float delta) {
        timerBalas1 += 1;
        timerBalas2 += 1;
        timerBalas3 += 1;
        if(joystickPresionado){
            boogie.mover();
        }
    }

    private void moverBalaTorre(Bala bala, float delta, int numBala) {
        if(estadoJuego == EstadoJuego.JUGANDO) {
            float x = torre4.sprite.getX() + torre4.sprite.getWidth() / 2;
            if (numBala == 1) {
                float y = torre4.sprite.getY() + torre4.sprite.getHeight() - 70;
                if (bala != null) {
                    bala.moverLeft(delta);
                    if (bala.sprite.getX() < 0) {
                        bala.sprite.setPosition(x, y);
                        timerBalas1 = 0;
                    }
                }
            } else if (numBala == 2) {
                float y = torre4.sprite.getY() + torre4.sprite.getHeight() / 2 - bala2Torre4.sprite.getHeight() / 2;

                if (bala != null) {
                    bala.moverLeft(delta);
                    if (bala.sprite.getX() < 0) {
                        bala.sprite.setPosition(x, y);
                        timerBalas2 = 0;
                    }
                }
            } else if (numBala == 3) {
                float y = torre4.sprite.getY() + 50;

                if (bala != null) {
                    bala.moverLeft(delta);
                    if (bala.sprite.getX() < 0) {
                        bala.sprite.setPosition(x, y);
                        timerBalas3 = 0;
                    }
                }
            }
        }
    }

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

    //Torres
    private void verificarColisiones() {
        probarColisionesBalaTorre();
        probarColisionesTorres();
    }

    //colisiones bala torre
    private void probarColisionesBalaTorre(){
        Rectangle rectBala1 = bala1Torre4.sprite.getBoundingRectangle();
        Rectangle rectBala2 = bala2Torre4.sprite.getBoundingRectangle();
        Rectangle rectBala3 = bala3Torre4.sprite.getBoundingRectangle();
        Rectangle rectBoogie = boogie.sprite.getBoundingRectangle();
        float x = torre4.sprite.getX() + torre4.sprite.getWidth() / 2;

        if (rectBala1.overlaps(rectBoogie)) {
            float y = torre4.sprite.getY() + torre4.sprite.getHeight() - 70;
            boogie.restarVida(1);
            marcador.restarVidas(1);
            bala1Torre4.sprite.setPosition(x,y);
            timerBalas1 = 0;

        } else if(rectBala2.overlaps(rectBoogie)){
            float y = torre4.sprite.getY() + torre4.sprite.getHeight()/2 - bala2Torre4.sprite.getHeight()/2;
            boogie.restarVida(1);
            marcador.restarVidas(1);
            bala2Torre4.sprite.setPosition(x,y);
            timerBalas2 = 0;

        }
        else if(rectBala3.overlaps(rectBoogie)){
            float y = torre4.sprite.getY() + 50;
            boogie.restarVida(1);
            marcador.restarVidas(1);
            bala3Torre4.sprite.setPosition(x,y);
            timerBalas3 = 0;

        }

    }

    //ColisionesTorres
    private void probarColisionesTorres() {
        Rectangle rectTorre1 = torre1.sprite.getBoundingRectangle();
        Rectangle rectTorre2 = torre2.sprite.getBoundingRectangle();
        Rectangle rectTorre3 = torre3.sprite.getBoundingRectangle();
        Rectangle rectTorre4 = torre4.sprite.getBoundingRectangle();


        Rectangle rectBoogie = boogie.sprite.getBoundingRectangle();

        if (rectBoogie.overlaps(rectTorre2)) {
            if (torre2.vida >= 0.0f) {
                boogie.restarVida(1);
                marcador.restarVidas(1);
                boogie.sprite.setPosition(180, 10);
            }
        } else if (rectBoogie.overlaps(rectTorre3)) {
            if (torre3.vida >= 0.0f) {
                boogie.restarVida(1);
                marcador.restarVidas(1);
                boogie.sprite.setPosition(180, 10);
            }
        } else if (rectBoogie.overlaps(rectTorre4)) {
            if (torre4.vida > 0.0f) {
                boogie.restarVida(1);
                marcador.restarVidas(1);
                boogie.sprite.setPosition(180, 10);
            }
        } else if (rectBoogie.overlaps(rectTorre1)) {
            if (torre1.vida >= 0.0f) {
                boogie.restarVida(1);
                marcador.restarVidas(1);
                boogie.sprite.setPosition(180, 10);
            }
        }

        for (int j = 0; j < listaBalas.size(); j++) {
            Rectangle rectBala = listaBalas.get(j).sprite.getBoundingRectangle();
            if (rectTorre1.overlaps(rectBala)) {
                torre1.restarVida();
                if (torre1.vida >= 0.0f) {
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
            } else if (rectTorre2.overlaps(rectBala)) {
                torre2.restarVida();
                if (torre2.vida >= 0.0f) {
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
            } else if (rectTorre3.overlaps(rectBala)) {
                torre3.restarVida();
                if (torre3.vida >= 0.0f) {
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
            } else if (rectTorre4.overlaps(rectBala)) {
                torre4.restarVida();
                if (torre4.vida >= 0.0f) {
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

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

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
                    juego.setScreen(new PantallaTercerNivel(juego));
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
