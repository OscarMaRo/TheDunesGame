package mx.equipotres.thedunes;

public class Enemigo extends Objeto {

    public Estado estado;

    public Enemigo(Texture textura, float x, float y) {
        super(textura, x, y);
        estado = Estado.DESACTIVADO;
    }

    public void mover(float dx) {
        sprite.setX(sprite.getX()+dx);
    }
    
    //Dependiendo del bloque en que está el boggie, ajusta la posición del sprite para estár más cerca del boogie 
    public void seguirBoggie(float dy, int bloque) {
        float posY = sprite.getY();
        if (bloque == 1) {
            sprite.setY(posY - dy);
        }else if (bloque == 3){
            sprite.setY(posY + dy);
        }else{
            sprite.setY(posY);
        }
    }
    
    //Estado del sprite 
    public enum Estado{
        ACTIVADO, //En pantalla
        DESACTIVADO //Oculto
    }



}
