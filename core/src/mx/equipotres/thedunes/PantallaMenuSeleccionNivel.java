package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class PantallaMenuSeleccionNivel extends Pantalla {
    // Juego: allows to create another screen when the listener is activated.
    private final Juego juego;

    // Texturas: The sprites initialized.
    private Texture texturaFondoNiveles;
    // Primer Nivel
    private Texture texturaBotonPrimerNivel;
    private Texture texturaBotonPrimerNivelPress;
    // Segundo Nivel
    private Texture texturaBotonSegundoNivel;
    private Texture texturaBotonSegundoNivelPress;
    // Tercer Nivel
    private Texture texturaBotonTercerNivel;
    private Texture texturaBotonTercerNivelPress;
    // Botón: Regresar
    private Texture texturaBotonRegresar;

    // MENU: The values of the class are generated.
    private Stage escenaMenu;

    // Screen: This creates a new screen to be displayed.
    public PantallaMenuSeleccionNivel(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        // Background: Initialize the sprite for the background.
        texturaFondoNiveles = new Texture("Fondos/fondoNiveles.jpg");
        crearMenu();
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);

        // Botón: jugar primer nivel.
        // Steps to create a fully functional button.
        // 1.1 Texturize: Creates the image in the game. Idle.
        Texture texturaBotonPrimerNivel = new Texture("Botones/botonPrimerNivel.png");
        TextureRegionDrawable trdJugarPrimerNivel = new TextureRegionDrawable(new TextureRegion(texturaBotonPrimerNivel));
        // 1.2 Texturize: Creates the image in the game. Clicked.
        Texture texturaBotonPrimerNivelPress = new Texture("Botones/botonPrimerNivelPress.png");
        TextureRegionDrawable trdJugarPrimerNivelP = new TextureRegionDrawable(new TextureRegion(texturaBotonPrimerNivelPress));
        // 2. Creation: Creates the button to be used.
        ImageButton btnJugarPrimerNivel = new ImageButton(trdJugarPrimerNivel,trdJugarPrimerNivelP);
        // 3. Position: Sets the position of the button.
        btnJugarPrimerNivel.setPosition(0,0);

        // Botón: jugar segundo nivel.
        // Steps to create a fully functional button.
        // 1.1 Texturize: Creates the image in the game. Idle.
        Texture texturaBotonSegundoNivel = new Texture("Botones/botonSegundoNivel.png");
        TextureRegionDrawable trdJugarSegundoNivel = new TextureRegionDrawable(new TextureRegion(texturaBotonSegundoNivel));
        // 1.2 Texturize: Creates the image in the game. Clicked.
        Texture texturaBotonSegundoNivelPress = new Texture("Botones/botonSegundoNivelPress.png");
        TextureRegionDrawable trdJugarSegundoNivelP = new TextureRegionDrawable(new TextureRegion(texturaBotonSegundoNivelPress));
        // 2. Creation: Creates the button to be used.
        ImageButton btnJugarSegundoNivel = new ImageButton(trdJugarSegundoNivel,trdJugarSegundoNivelP);
        // 3. Position: Sets the position of the button.
        btnJugarSegundoNivel.setPosition(btnJugarPrimerNivel.getWidth(),0);

        // Botón: jugar tercer nivel.
        // Steps to create a fully functional button.
        // 1.1 Texturize: Creates the image in the game. Idle.
        Texture texturaBotonTercerNivel = new Texture("Botones/botonTercerNivel.png");
        TextureRegionDrawable trdJugarTercerNivel = new TextureRegionDrawable(new TextureRegion(texturaBotonTercerNivel));
        // 1.2 Texturize: Creates the image in the game. Clicked.
        Texture texturaBotonTercerNivelPress = new Texture("Botones/botonTercerNivelPress.png");
        TextureRegionDrawable trdJugarTercerNivelP = new TextureRegionDrawable(new TextureRegion(texturaBotonTercerNivelPress));
        // 2. Creation: Creates the button to be used.
        ImageButton btnJugarTercerNivel = new ImageButton(trdJugarTercerNivel,trdJugarTercerNivelP);
        // 3. Position: Sets the position of the button.
        btnJugarTercerNivel.setPosition(btnJugarSegundoNivel.getX() + btnJugarSegundoNivel.getWidth(),0);

        // Botón: regresar al Menu Principal.
        // Steps to create a fully functional button.
        // 1.1 Texturize: Creates the image in the game. Idle.
        Texture texturaBotonRegresar = new Texture("Botones/botonRegresar.png");
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable(new TextureRegion(texturaBotonRegresar));
        // 1.2 Texturize: Creates the image in the game. Clicked.
        //Texture texturaBotonTercerNivelPress = new Texture("Botones/botonTercerNivelPress.png");
        //TextureRegionDrawable trdJugarTercerNivelP = new TextureRegionDrawable(new TextureRegion(texturaBotonTercerNivelPress));
        // 2. Creation: Creates the button to be used.
        ImageButton btnRegresar = new ImageButton(trdRegresar);
        // 3. Position: Sets the position of the button.
        btnRegresar.setPosition(escenaMenu.getWidth() - 150,escenaMenu.getHeight() - 150);

        // Listener: This calls the functionality of the buttons.
        // Primer Nivel
        btnJugarPrimerNivel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // TO OTHER SCREEN: when clicked it displays the Menu Level Screen.
                juego.setScreen(new PantallaPrimerNivel(juego));
            }
        });

        // Segundo Nivel
        btnJugarSegundoNivel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // TO OTHER SCREEN: when clicked it displays the Menu Level Screen.
                juego.setScreen(new PantallaSegundoNivel(juego));
            }
        });

        // Tercer Nivel
        btnJugarTercerNivel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // TO OTHER SCREEN: when clicked it displays the Menu Level Screen.
                juego.setScreen(new PantallaTercerNivel(juego));
            }
        });

        // Función: Regresar
        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // TO OTHER SCREEN: when clicked it displays the Menu Level Screen.
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        // Display images: This attributes draw the images in screen.
        escenaMenu.addActor(btnJugarPrimerNivel);
        escenaMenu.addActor(btnJugarSegundoNivel);
        escenaMenu.addActor(btnJugarTercerNivel);
        escenaMenu.addActor(btnRegresar);

        // Action: This attributes allow the buttons to have interaction with the user.
        Gdx.input.setInputProcessor(escenaMenu);
    }


    @Override
    public void render(float delta) {
        // Init: Default initializers.
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        // Draw game: Everything in here is drawn into the game.
        batch.begin();
        // Background: The image of the background is displayed.
        batch.draw(texturaFondoNiveles,0,0);
        batch.end();

        // Visibility: When this is activated everything is visible from show().
        escenaMenu.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaFondoNiveles.dispose();
    }
}
