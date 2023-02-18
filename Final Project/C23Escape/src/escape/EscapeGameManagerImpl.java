package escape;

import escape.board.*;
import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.GameObserver;
import escape.required.GameStatus;

public class EscapeGameManagerImpl<C extends Coordinate> implements EscapeGameManager<C> {

    private Coordinate.CoordinateType coordinateType;
    private int xMax, yMax;
    private Board<C> board;
    private String[] players;
    private int currentPlayer = 0;

    /**
     * Make the move in the current game.
     * @param from starting location
     * @param to ending location
     * @return true if the move was legal, false otherwise
     */
    @Override
    public GameStatus move(C from, C to) {

        if(!board.move(from, to, getCurrentPlayer())){
            return invalidMoveStatus(from);
        };

        setNextPlayer();

        return validMoveStatus(to);
    }

    /**
     * Gets the current player
     * @return The name of the current player
     */
    public String getCurrentPlayer(){
        return players[currentPlayer];
    }

    /**
     * Sets the current player to the next player
     */
    private void setNextPlayer(){
        currentPlayer = (currentPlayer+1)%players.length;
    }

    /**
     * Gets default valid moveStatus
     * @param finalLocation The final location
     * @return default valid moveStatus
     */
    private GameStatus validMoveStatus(C finalLocation){
        return getGameStatus(finalLocation, true);
    }

    /**
     * Generates default invalid move status
     * @return default invalid move status
     */
    private GameStatus invalidMoveStatus(C finalLocation){
        return getGameStatus(finalLocation, false);
    }

    /**
     * Returns the game status
     * @param finalLocation The final location
     * @param isValid Whether the move was valid or not
     * @return the game status
     */
    private GameStatus getGameStatus(C finalLocation, boolean isValid) {
        return new GameStatus() {
            @Override
            public boolean isValidMove() {
                return isValid;
            }

            @Override
            public boolean isMoreInformation() {
                return false;
            }

            @Override
            public MoveResult getMoveResult() {
                return MoveResult.NONE;
            }

            @Override
            public Coordinate finalLocation() {
                return finalLocation;
            }
        };
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
    public EscapePiece getPieceAt(C coordinate) {
        return board.getLocation(coordinate).getPiece();
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
    public C makeCoordinate(int x, int y) {

        C coordinate = (C) new CoordinateImpl(x,y);

        switch (coordinateType){
            case SQUARE -> {
                coordinate = (C) new SquareCoordinate(x,y);
            }
            case HEX -> {
                coordinate = (C) new HexagonalCoordinate(x,y);
            }
        }

        return coordinate;
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

    public void setPlayers(String[] players){
        this.players = players;
    }
    /**
     * Set the board
     * @param board the board to set
     */
    public void setBoard(Board<C> board){
        this.board = board;
    }

    /**
     * Get a location
     * @param coordinate The coordinate to get the location from
     * @return the location
     */
    public Location getLocation(C coordinate){
        return board.getLocation(coordinate);
    }

}