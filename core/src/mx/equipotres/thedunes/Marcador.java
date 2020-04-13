package mx.equipotres.thedunes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Marcador {

    protected int puntos;
    private int vidaBoogie;
    private float x;
    private float y;

    private Texto texto;

    public Marcador (float x, float y, Boogie boogie){
        this.x = x;
        this.y = y;
        texto = new Texto("Fuentes/fuente.fnt");
        puntos = 0;
        vidaBoogie = boogie.vidas;
    }

    public void reset(){
        puntos = 0;
    }

    public void agregarPuntos(int puntos){
        this.puntos += puntos;
    }

    public void restarVidas(int vidas) { this.vidaBoogie -= vidas; }

    public void render(SpriteBatch batch){
        String mensaje = "Puntos: " + puntos + "  Vidas:" + vidaBoogie;
        texto.render(batch, mensaje, x, y);
    }

    public void render(SpriteBatch batch, float x, float y) {
        String mensaje = "Puntos: " + puntos;
        texto.render(batch, mensaje, x, y);
    }
}
