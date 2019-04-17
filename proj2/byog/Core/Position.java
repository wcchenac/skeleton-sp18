package byog.Core;

import java.io.Serializable;

public class Position implements Serializable {
    public int x;
    public int y;

    /** This class is used to record GameObject position.
     * @param x: start from 0.
     * @param y: start from 0.
     */

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
