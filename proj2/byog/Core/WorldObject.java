package byog.Core;

public class WorldObject {
    public int width;
    public int height;
    public int direction;

    /** This class is used to record player/door/hallway/room properties.
     * @param w: door/player/hallway are 0.
     * @param h: door/player is 0.
     * @param direction: 0(none), +X(1), -X(2), +Y(3), -Y(4)
     */

    public WorldObject(int w, int h, int direction) {
        this.width = w;
        this.height = h;
        this.direction = direction;
    }
}
