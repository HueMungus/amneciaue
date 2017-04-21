/**
 * Created by gustem on 21/04/2017.
 */
public class Block {
    public Vector2 min, max;

    public Block(Vector2 min, Vector2 max) {
        this.min = min;
        this.max = max;


    }

    public Block() {
        this.min = new Vector2(0,0);
        this.max = new Vector2(0,0);


    }

}
