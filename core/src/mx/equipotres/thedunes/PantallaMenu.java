package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class PantallaMenu extends Pantalla {
    // Juego: allows to create another screen when the listener is activated.
    private final Juego juego;

    // Texturas: The sprites initialized.
    Texture texturaFondo;
    Texture texturaBotonJugar;
    Texture texturaBotonJugarPress;
    Texture texturaBotonConfig;
    Texture texturaBotonConfigPress;

    // MENU: The values of the class are generated.
    private Stage escenaMenu;

    // Screen: This creates a new screen to be displayed.
    public PantallaMenu(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        // Background: Initialize the sprite for the background.
        texturaFondo = new Texture("Fondos/fondoMenu.png");
        crearMenu();
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);

        // Botón: jugar.
        // Steps to create a fully functional button.
        // 1.1 Texturize: Creates the image in the game. Idle.
        Texture texturaBotonJugar = new Texture("Botones/button_jugar.png");
        TextureRegionDrawable trdJugar = new TextureRegionDrawable(new TextureRegion(texturaBotonJugar));
        // 1.2 Texturize: Creates the image in the game. Clicked.
        Texture texturaBotonJugarPress = new Texture("Botones/button_jugarP.png");
        TextureRegionDrawable trdJugarP = new TextureRegionDrawable(new TextureRegion(texturaBotonJugarPress));
        // 2. Creation: Creates the button to be used.
        ImageButton btnJugar = new ImageButton(trdJugar,trdJugarP);
        // 3. Position: Sets the position of the button.
        btnJugar.setPosition(ANCHO/2 - btnJugar.getWidth()/2,ALTO/2);

        // Botón: Configuración.
        // Steps to create a fully functional button.
        // 1.1 Texturize: Creates the image in the game. Idle.
        Texture texturaBotonConfig = new Texture("Botones/button_configuracion.png");
        TextureRegionDrawable trdConfig = new TextureRegionDrawable(new TextureRegion(texturaBotonConfig));
        // 1.2 Texturize: Creates the image in the game. Clicked.
        Texture texturaBotonConfigPress = new Texture("Botones/button_configuracionP.png");
        TextureRegionDrawable trdConfigP = new TextureRegionDrawable(new TextureRegion(texturaBotonConfigPress));
        // 2. Creation: Creates the button to be used.
        ImageButton btnConfiguracion = new ImageButton(trdConfig,trdConfigP);
        // 3. Position: Sets the position of the button.
        btnConfiguracion.setPosition(ANCHO/2 - btnConfiguracion.getWidth()/2, (ALTO/2)-100);


        // Listener: This calls the functionality of the buttons.
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // TO OTHER SCREEN: when clicked it displays the Menu Level Screen.
                juego.setScreen(new PantallaMenuSeleccionNivel(juego));
            }
        });

        // Display images: This attributes draw the images in screen.
        escenaMenu.addActor(btnJugar);
        escenaMenu.addActor(btnConfiguracion);

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
        batch.draw(texturaFondo,0,0);
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
        texturaFondo.dispose();
    }
}
