package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaInstrucciones extends Pantalla
{
    private final Juego juego;
    private Texto instrucciones;

    // Texturas
    private Texture texturaFondo;
    private Texture texturaRectangulo;
    private Texture texturaBoogie;
    private Texture texturaPadKnob;
    private Texture texturaPadBack;
    private Texture texturaBotonDisparar;

    // Botones
    private Boton btnRegresar;
    private Boton btnSiguiente;

    private Stage escenaPantallaInstrucciones;

    public PantallaInstrucciones(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoMenu.png");
        texturaRectangulo = new Texture("Fondos/Fondopausa.jpeg");
        texturaBoogie = new Texture("Sprites/boogie1_frente.png");
        texturaPadKnob = new Texture("Botones/padKnob.png");
        texturaPadBack = new Texture("Botones/padBack.png");
        texturaBotonDisparar = new Texture("Botones/disparar.png");
        instrucciones = new Texto("Fuentes/fuente.fnt");
        crearPantalla();

        Gdx.input.setCatchKey(Input.Keys.BACK,true);

    }

    private void crearPantalla() {
        escenaPantallaInstrucciones = new Stage(vista);

        btnRegresar = new Boton("Botones/botonRegresar.png");
        btnRegresar.posicionarBoton(escenaPantallaInstrucciones.getWidth() - 150,escenaPantallaInstrucciones.getHeight() - 150);
        btnRegresar.presionar(juego, 1);

        btnSiguiente = new Boton("Botones/BotonSiguiente.png");
        btnSiguiente.posicionarBoton(2*ANCHO/3+90, texturaRectangulo.getHeight()*.1f);
        btnSiguiente.presionar(juego, 6);

        btnRegresar.agregar(escenaPantallaInstrucciones);
        btnSiguiente.agregar(escenaPantallaInstrucciones);

        Gdx.input.setInputProcessor(escenaPantallaInstrucciones);
    }

    @Override
    public void render(float delta) {

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            juego.setScreen(new PantallaAcercaDe(juego));
        }

        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.draw(texturaRectangulo,ANCHO/2-texturaRectangulo.getWidth()/2,ALTO/2-texturaRectangulo.getHeight()/2);
        batch.draw(texturaBoogie, 100, 100);
        batch.draw(texturaPadBack, 2*ANCHO/3+100 - texturaPadBack.getWidth()/2,2*ALTO/3-50 - texturaPadBack.getHeight()/2);
        batch.draw(texturaPadKnob, 2*ANCHO/3+100 - texturaPadKnob.getWidth()/2,2*ALTO/3-50- texturaPadKnob.getHeight()/2);
        batch.draw(texturaBotonDisparar, 2*ANCHO/3+100 - texturaBotonDisparar.getWidth()/2, ALTO/3+25 - texturaBotonDisparar.getHeight()/2);
        String instruccionesT = "Instrucciones";
        instrucciones.render(batch, instruccionesT, ANCHO/2, ALTO-100);
        String instJoystick = "Usa el joystick para\n        mover el boogie";
        instrucciones.render(batch, instJoystick,ANCHO/3+125,2*ALTO/3);
        String instDisparo = "  Presiona el boton\nrojo para disparar";
        instrucciones.render(batch, instDisparo,ANCHO/3+125,ALTO/3+75);
        batch.end();

        escenaPantallaInstrucciones.draw();
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
