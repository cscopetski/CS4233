package escape;

import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.GameObserver;
import escape.required.GameStatus;

public class EscapeGameManagerImpl<C extends Coordinate> implements EscapeGameManager<CoordinateImpl> {

    private Coordinate.CoordinateType coordinateType;
    private int xMax, yMax;

    /**
     * Make the move in the current game.
     *
     * @param from starting location
     * @param to   ending location
     * @return true if the move was legal, false otherwise
     */
    @Override
    public GameStatus move(CoordinateImpl from, CoordinateImpl to) {
        return EscapeGameManager.super.move(from, to);
    }

    /**
     * Return the piece located at the specified coordinate. If executing
     * this method in the game instance causes an exception, then this method
     * returns null and sets the status message appropriately.
     *
     * @param coordinate the location to probe
     * @return the piece at the specified location or null if there is none
     */
    @Override
    public EscapePiece getPieceAt(CoordinateImpl coordinate) {
        return EscapeGameManager.super.getPieceAt(coordinate);
    }

    /**
     * Returns a coordinate of the appropriate type. If the coordinate cannot be
     * created, then null is returned and the status message is set appropriately.
     *
     * @param x the x component
     * @param y the y component
     * @return the coordinate or null if the coordinate cannot be implemented
     */
    @Override
    public CoordinateImpl makeCoordinate(int x, int y) {
        return new CoordinateImpl(x,y);
    }

    /**
     * Add an observer to this manager. Whever the move() method returns
     * false, the observer will be notified with a message indication the
     * problem.
     *
     * @param observer
     * @return the observer
     */
    @Override
    public GameObserver addObserver(GameObserver observer) {
        return EscapeGameManager.super.addObserver(observer);
    }

    /**
     * Remove an observer from this manager. The observer will no longer
     * receive notifications from this game manager.
     *
     * @param observer
     * @return the observer that was removed or null if it had not previously
     * been registered
     */
    @Override
    public GameObserver removeObserver(GameObserver observer) {
        return EscapeGameManager.super.removeObserver(observer);
    }


    /**
     * @return the coordinateType
     */
    public Coordinate.CoordinateType getCoordinateType()
    {
        return coordinateType;
    }

    /**
     * @param coordinateType the coordinate type to set
     */
    public void setCoordinateType(Coordinate.CoordinateType coordinateType)
    {
        this.coordinateType = coordinateType;
    }

    /**
     * @return the xMax
     */
    public int getxMax()
    {
        return xMax;
    }

    /**
     * @param xMax the xMax to set
     */
    public void setxMax(int xMax)
    {
        this.xMax = xMax;
    }

    /**
     * @return the yMax
     */
    public int getyMax()
    {
        return yMax;
    }

    /**
     * @param yMax the yMax to set
     */
    public void setyMax(int yMax)
    {
        this.yMax = yMax;
    }

}
