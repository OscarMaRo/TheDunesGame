package mx.equipotres.thedunes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Boogie extends Objeto {

    // Variables.
    private final int ROTACION = 360 / 16;
    private final int PASO = 2;
    public int vidas;
    public Texture textura;

    // Constructor principal.
    public Boogie(Texture textura, float x, float y) {
        super(textura, x, y);
        vidas = 5;
    }

    public void setTextura(Texture textura) {
        this.textura = textura;
    }

    public void checarBordes() {
        if (sprite.getX() < 0) sprite.setX(0);
        if (sprite.getX() + sprite.getWidth() > 1280) sprite.setX(1280 - sprite.getWidth());
        if (sprite.getY() < 0) sprite.setY(0);
        if (sprite.getY() + sprite.getHeight() > 720) sprite.setY(720 - sprite.getHeight());
    }

    public void restarVida(int cantidad){
        vidas -= cantidad;
    }

    public void agregarVida(int cantidad){
        vidas += cantidad;
    }

    // Mover: Mueve el vehiculo a las coordenadas (x, y).
    public void mover() {
        checarBordes();
        // Rotaciones
        // 1. DERECHA
        if (sprite.getRotation() == -90 || sprite.getRotation() == 270) {
            sprite.setX(sprite.getX() + PASO);
        }
        // 2. ARRIBA
        else if (sprite.getRotation() == 0) {
            sprite.setY(sprite.getY() + PASO);
        }
        // 3. ABAJO
        else if (Math.abs(sprite.getRotation()) == 180) {
            sprite.setY(sprite.getY() - PASO);
        }
        // 4. IZQUIERDA
        else if (sprite.getRotation() == 90 || sprite.getRotation() == -270) {
            sprite.setX(sprite.getX() - PASO);
        }
        // 5. --- -45°, 315° ---
        else if (sprite.getRotation() == -45 || sprite.getRotation() == 315) {
            sprite.setX(sprite.getX() + PASO);
            sprite.setY(sprite.getY() + PASO);
        }
        // 6. --- -135°, 225° ---
        else if (sprite.getRotation() == -135 || sprite.getRotation() == 225) {
            sprite.setX(sprite.getX() + PASO);
            sprite.setY(sprite.getY() - PASO);
        }
        // 7. --- -315°, 45° ---
        else if (sprite.getRotation() == 45 || sprite.getRotation() == -315) {
            sprite.setX(sprite.getX() - PASO);
            sprite.setY(sprite.getY() + PASO);
        }
        // 8. --- -225°, 135° ---
        else if (sprite.getRotation() == 135 || sprite.getRotation() == -225) {
            sprite.setX(sprite.getX() - PASO);
            sprite.setY(sprite.getY() - PASO);
        }
        // SUB-DIVISIONS
        // 9. --- -30°, 330° ---
        else if (sprite.getRotation() == -30 || sprite.getRotation() == 330) {
            sprite.setX(sprite.getX() + 2 + PASO);
            sprite.setY(sprite.getY() + 1 + PASO);
        }
        // 10. --- -60°, 300°  ---
        else if (sprite.getRotation() == -60 || sprite.getRotation() == 300) {
            sprite.setX(sprite.getX() + 2 + PASO);
            sprite.setY(sprite.getY() - 1 - PASO);
        }
        // 11. --- -120°, 240° ---
        else if (sprite.getRotation() == -120 || sprite.getRotation() == 240) {
            sprite.setX(sprite.getX() + 2 + PASO);
            sprite.setY(sprite.getY() + 1 - PASO);
        }
        // 12. --- -150°, 210° ---
        else if (sprite.getRotation() == -150 || sprite.getRotation() == 210) {
            sprite.setX(sprite.getX() + 2 + PASO);
            sprite.setY(sprite.getY() - 1 - PASO);
        }
        // 13. --- -210°, 150° ---
        else if (sprite.getRotation() == -210 || sprite.getRotation() == 150) {
            sprite.setX(sprite.getX() - 2 - PASO);
            sprite.setY(sprite.getY() - 1 - PASO);
        }
        // 14. --- -240°, 120° ---
        else if (sprite.getRotation() == -240 || sprite.getRotation() == 120) {
            sprite.setX(sprite.getX() - 2 - PASO);
            sprite.setY(sprite.getY() + 1 - PASO);
        }
        // 15. --- -300°, 60° ---
        else if (sprite.getRotation() == -300 || sprite.getRotation() == 60) {
            sprite.setX(sprite.getX() - 2 - PASO);
            sprite.setY(sprite.getY() - 1 + PASO);
        }
        // 16. --- -330°, 30° ---
        else if (sprite.getRotation() == -330 || sprite.getRotation() == 30) {
            sprite.setX(sprite.getX() - 2 - PASO);
            sprite.setY(sprite.getY() + 1 + PASO);
        }

    }

    public void rotacion(float x, float y){
        System.out.println(x + " " + y);
        // Arriba
        if (x <= 0.35 && x >= -0.35 && y > 0){
            sprite.setRotation(0);
            this.mover();
        }
        // Hemisferio Superior Izquierdo
        else if (x < 0 && y > 0){
            sprite.setRotation(45);
            this.mover();
        }
        // Izquierda
        else if (x < 0 && y <= 0.35 && y >= -0.35){
            sprite.setRotation(90);
            this.mover();
        }
        // Hemisferio inferior izquierdo
        else if (x < 0 && y < 0){
            sprite.setRotation(135);
            this.mover();
        }
        // Abajo
        else if (x <= 0.35 && x> -0.35 && y < 0){
            sprite.setRotation(180);
            this.mover();
        }
        // Hemisferio Inferior derecho
        else if (x > 0 && y < 0){
            sprite.setRotation(225);
            this.mover();
        }
        // Derecha
        else if (x > 0 && y <= 0.35 && y >= -0.35) {
            sprite.setRotation(270);
            this.mover();
        }
        // Hemisferio Superior Derecho
        else if (x > 0 && y > 0){
            sprite.setRotation(315);
            this.mover();
        }
        // SUB-DIVISIONS
        else if (x > 300) {
            sprite.setRotation(30);
            this.mover();
        }
        else if (x > 300) {
            sprite.setRotation(60);
            this.mover();
        }
        else if (x > 300) {
            sprite.setRotation(120);
            this.mover();
        }
        else if (x > 300) {
            sprite.setRotation(150);
            this.mover();
        }
        else if (x > 300) {
            sprite.setRotation(210);
            this.mover();
        }
        else if (x > 300) {
            sprite.setRotation(240);
            this.mover();
        }
        else if (x > 300) {
            sprite.setRotation(300);
            this.mover();
        }
        else if (x > 300) {
            sprite.setRotation(330);
            this.mover();
        }
    }

    //https://books.google.com.mx/books?id=opQeBQAAQBAJ&pg=PT534&lpg=PT534&dq=uiskin.json+horizontal&source=bl&ots=3_iZj4pVqE&sig=ACfU3U1l9FQbUjojd39ppxkfNQ27GW9PMg&hl=es-419&sa=X&ved=2ahUKEwiOjp3KpdDpAhUJMawKHYSIDjoQ6AEwA3oECAoQAQ#v=onepage&q=uiskin.json%20horizontal&f=false


    // Rotar: Rota el vehiculo en un ángulo de 360°.
    public void rotar(float dr) {
        sprite.setRotation( sprite.getRotation() + ROTACION * -dr);
        if (Math.abs(sprite.getRotation()) == 360) {
            sprite.setRotation(0);
        }
    }

}
