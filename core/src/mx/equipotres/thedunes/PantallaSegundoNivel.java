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

class PantallaSegundoNivel extends Pantalla {
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
    private Texture texturaTorreNivel1;
    private Texture texturaTorreNivel2;
    private Torre torre1;
    private Torre torre2;
    private Torre torre3;
    private Torre torre4;
    private Torre torre5;
    private Escudo escudoTorre2;
    private Escudo escudoTorre3;
    private Escudo escudoTorre4;
    private Escudo escudoTorre5;
    private BarraVida barraVidaTorre1;
    private BarraVida barraVidaTorre2;
    private BarraVida barraVidaTorre3;
    private BarraVida barraVidaTorre4;
    private BarraVida barraVidaTorre5;


    public PantallaSegundoNivel(Juego juego) { this.juego = juego; }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,0,1);
        cargarTexturas();
        crearObjetos();
        cargarMusica();
        crearHUD();
        crearEscenaFinal();

        Gdx.input.setCatchKey(Input.Keys.BACK, true);

    }
    private void cargarTexturas() {
        texturaFondo = new Texture(("Fondos/FondoNivel2.png"));
        //texturaEnemigos = new Texture("Sprites/enemigo1.png");
        texturaBoogie = new Texture("Sprites/boogie1_frente.png");
        texturaBala = new Texture("Sprites/bala1.png");
        texturaBotonPausa = new Texture("Botones/pausa.png");
        texturaBotonAcelerar = new Texture("Botones/disparar.png");
        ganar = new Texto("Fuentes/fuente.fnt");

        texturaTorreNivel1 = new Texture("Sprites/torre.png");
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
        btnVolverMenu.setPosition(ANCHO/2-250, ALTO/2-144);

        btnVolverMenu.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                musicaFondo.stop();
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        escenaFinal.addActor(btnVolverMenu);
    }

    // CREACIÓN: Boodie, Marcador
    private void crearObjetos() {
        crearBoogie();
        crearMarcador();
        crearTorres();
        crearEscudos();
        crearBarraVidaTorre();
        //crearEnemigos();
        //crearEnemigosAlrededorTorre();
    }
    private void crearTorres() {
        torre1 = new Torre(texturaTorreNivel1, ANCHO - texturaTorreNivel1.getWidth()-100, ALTO - texturaTorreNivel1.getHeight() - 100);
        torre2 = new Torre(texturaTorreNivel1, ANCHO/2 - texturaTorreNivel1.getWidth()/2, ALTO - texturaTorreNivel1.getHeight() - 100);
        torre3 = new Torre(texturaTorreNivel2, ANCHO/2 - texturaTorreNivel2.getWidth()/2, 100);
        torre4 = new Torre(texturaTorreNivel1, ANCHO/4, ALTO/2);
        torre5 = new Torre(texturaTorreNivel2, 100, ALTO - texturaTorreNivel2.getHeight() - 100);
    }

    private void crearEscudos(){

        escudoTorre2 = new Escudo(vista, batch);
        escudoTorre2.posicionarEscudo(0,225); //coordenadas 0,0 en el centro de la pantalla

        escudoTorre3 = new Escudo(vista, batch);
        escudoTorre3.posicionarEscudo(0,-200);

        escudoTorre4 = new Escudo(vista, batch);
        escudoTorre4.posicionarEscudo(-285,35);

        escudoTorre5 = new Escudo(vista, batch);
        escudoTorre5.posicionarEscudo(-480, 200);

    }

    private void crearBarraVidaTorre() {
        barraVidaTorre1 = new BarraVida();
        barraVidaTorre2 = new BarraVida();
        barraVidaTorre3 = new BarraVida();
        barraVidaTorre4 = new BarraVida();
        barraVidaTorre5 = new BarraVida();
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
        if (torre5.vida <= 0.0f) {
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
            if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
                estadoJuego = EstadoJuego.JUGANDO;
                Gdx.input.setInputProcessor(escenaHUD);
            }
            dibujarEscudos();
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

    private void dibujarEscudos(){
        if(torre1.vida >=0.0f){escudoTorre2.draw();}
        if(torre2.vida >=0.0f){escudoTorre3.draw();}
        if(torre3.vida >=0.0f){escudoTorre4.draw();}
        if(torre4.vida >=0.0f){escudoTorre5.draw();}

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
        if(torre1.vida >= 0.0f) {
            torre1.render(batch);
            barraVidaTorre1.render(batch, torre1, torre1.sprite.getX() - barraVidaTorre1.textura.getWidth() / 2 - 10, torre1.sprite.getY());
        }
        if(torre2.vida>=0.0f) {
            torre2.render(batch);
            barraVidaTorre2.render(batch, torre2, torre2.sprite.getX() - barraVidaTorre2.textura.getWidth() / 2 - 10, torre2.sprite.getY());
        }
        if(torre3.vida>=0.0f) {
            torre3.render(batch);
            barraVidaTorre3.render(batch, torre3, torre3.sprite.getX() + barraVidaTorre3.textura.getWidth() / 2 + 5, torre3.sprite.getY());
        }
        if(torre4.vida>=0.0f) {
            torre4.render(batch);
            barraVidaTorre4.render(batch, torre4, torre4.sprite.getX() - barraVidaTorre4.textura.getWidth() / 2 - 10, torre4.sprite.getY());
        }
        if(torre5.vida>=0.0f) {
            torre5.render(batch);
            barraVidaTorre5.render(batch, torre5, torre5.sprite.getX() + barraVidaTorre5.textura.getWidth() / 2 + 5, torre5.sprite.getY());
        }
        batch.setColor(Color.WHITE);


        // Enemigos

    }

    // Actualiza: el movimiento de los enemigos y las hordas
    private void actualizar(float delta) {
        if(joystickPresionado){
            boogie.mover();
        }
    }

    //Enemigos, torres y escudos
    private void verificarColisiones() {
        //probarColisionesEnemigos();
        probarColisionesTorres();
        probarColisionesEscudos();
    }

    //ColisionesTorres
    private void probarColisionesTorres() {
        Rectangle rectTorre1 = torre1.sprite.getBoundingRectangle();
        Rectangle rectTorre2 = torre2.sprite.getBoundingRectangle();
        Rectangle rectTorre3 = torre3.sprite.getBoundingRectangle();
        Rectangle rectTorre4 = torre4.sprite.getBoundingRectangle();
        Rectangle rectTorre5 = torre5.sprite.getBoundingRectangle();

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
                boogie.sprite.setPosition(10, 10);
            }
        } else if (rectBoogie.overlaps(rectTorre4)) {
            if (torre4.vida >= 0.0f) {
                boogie.restarVida(1);
                marcador.restarVidas(1);
                boogie.sprite.setPosition(10, 10);
            }
        } else if (rectBoogie.overlaps(rectTorre5)) {
            if (torre5.vida >= 0.0f) {
                boogie.restarVida(1);
                marcador.restarVidas(1);
                boogie.sprite.setPosition(10, 10);
            }
        }else if (rectBoogie.overlaps(rectTorre1)) {
            if (torre1.vida >= 0.0f) {
                boogie.restarVida(1);
                marcador.restarVidas(1);
                boogie.sprite.setPosition(10, 10);
            }
        }

        for (int j = 0; j < listaBalas.size(); j++) {
            Rectangle rectBala = listaBalas.get(j).sprite.getBoundingRectangle();
            if (rectTorre1.overlaps(rectBala)) {
                torre1.restarVida();
                listaBalas.remove(j);
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
            } else if (rectTorre5.overlaps(rectBala)) {
                torre5.restarVida();
                if (torre5.vida >= 0.0f) {
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
        Rectangle rectEscudoTorre2 = escudoTorre2.getBoundaries(torre2.sprite.getX(),torre2.sprite.getY());  //coordenadas 0,0 en la esquina infereior izquierda
        Rectangle rectEscudoTorre3 = escudoTorre3.getBoundaries(torre3.sprite.getX(),torre3.sprite.getY());
        Rectangle rectEscudoTorre4 = escudoTorre4.getBoundaries(torre4.sprite.getX(),torre4.sprite.getY());
        Rectangle rectEscudoTorre5 = escudoTorre5.getBoundaries(torre5.sprite.getX(),torre5.sprite.getY());
        Rectangle rectBoogie = boogie.sprite.getBoundingRectangle();

        if (rectBoogie.overlaps(rectEscudoTorre2)) {
            boogie.restarVida(1);
            marcador.restarVidas(1);
            boogie.sprite.setPosition(10, 10);
        } else if (rectBoogie.overlaps(rectEscudoTorre3)) {
            boogie.restarVida(1);
            marcador.restarVidas(1);
            boogie.sprite.setPosition(10, 10);
        } else if (rectBoogie.overlaps(rectEscudoTorre4)) {
            boogie.restarVida(1);
            marcador.restarVidas(1);
            boogie.sprite.setPosition(10, 10);
        } else if (rectBoogie.overlaps(rectEscudoTorre5)) {
            boogie.restarVida(1);
            marcador.restarVidas(1);
            boogie.sprite.setPosition(10, 10);
        }

        for (int j = 0; j < listaBalas.size(); j++) {
            Rectangle rectBala = listaBalas.get(j).sprite.getBoundingRectangle();
            if (rectEscudoTorre2.overlaps(rectBala)) {
                if (torre1.vida >= 0.0f) {
                    listaBalas.remove(j);
                }
                break;
            } else if (rectEscudoTorre3.overlaps(rectBala)) {
                if (torre2.vida >= 0.0f) {
                    listaBalas.remove(j);
                }
                break;
            } else if (rectEscudoTorre4.overlaps(rectBala)) {
                if (torre3.vida >= 0.0f) {
                    listaBalas.remove(j);
                }
                break;
            } else if (rectEscudoTorre5.overlaps(rectBala)) {
                if (torre4.vida >= 0.0f) {
                    listaBalas.remove(j);
                }
                break;
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
                    juego.setScreen(new PantallaSegundoNivel(juego));
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
