package escape;

import escape.board.*;
import escape.board.Board;
import escape.coordinate.CoordinateImpl;
import escape.coordinate.HexagonalCoordinate;
import escape.coordinate.SquareCoordinate;
import escape.piece.EscapePieceImpl;
import escape.required.*;

import java.util.HashMap;
import java.util.Map;

public class EscapeGameManagerImpl<C extends Coordinate> implements EscapeGameManager<C> {

    private Coordinate.CoordinateType coordinateType;
    private int xMax, yMax;
    private Board<C> board;

    private TurnManager turnManager;

    private boolean pointConflict = false;

    private boolean gameFinished = false;

    private HashMap<String, Integer> scoreMap = new HashMap<>();

    /**
     * Make the move in the current game.
     * @param from starting location
     * @param to ending location
     * @return true if the move was legal, false otherwise
     */
    @Override
    public GameStatus move(C from, C to) {

        String currentPlayer = turnManager.getCurrentPlayer();
        String otherPlayer = turnManager.getOtherPlayer();


        if(gameFinished || !board.move(from, to, currentPlayer)){
            return invalidMoveStatus();
        };

        if(board.getLocation(to).getLocationType() == LocationType.EXIT){
            exitPiece(to);
        }

        GameStatus.MoveResult result = turnManager.endPlayerTurn(board.getPieceCount(currentPlayer), board.hasValidMove(otherPlayer));

        if(result != GameStatus.MoveResult.NONE){
            gameFinished = true;
        }

        return validMoveStatus(result);
    }

    /**
     * Exit a piece from the board on an exit location, and increment score
     * @param to the coordinate location of the exit containing a piece
     */
    private void exitPiece(C to){
        EscapePieceImpl piece = (EscapePieceImpl) getPieceAt(to);

        if(piece == null){
            return;
        }

        int pieceScore = piece.getValue();

        turnManager.updateScore(turnManager.getCurrentPlayer(), pieceScore);

        board.removePiece(to);
    }

    /**
     * Gets default valid moveStatus
     * @param result
     * @return default valid moveStatus
     */
    private GameStatus validMoveStatus(GameStatus.MoveResult result){
        return getGameStatus(true, result);
    }

    /**
     * Generates default invalid move status
     * @return default invalid move status
     */
    private GameStatus invalidMoveStatus(){
        return getGameStatus(false, GameStatus.MoveResult.LOSE);
    }

    /**
     * Returns the game status
     * @param isValid Whether the move was valid or not
     * @return the game status
     */
    private GameStatus getGameStatus(boolean isValid, GameStatus.MoveResult gameStatus) {
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
                return gameStatus;
            }

            @Override
            public Coordinate finalLocation() {
                return null;
            }


            @Override
            public CombatResult getCombatResult() {
                return CombatResult.NONE;
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

    /**
     * Set the point conflict rule
     * @param pointConflict true or false
     */
    public void setPointConflict(boolean pointConflict) {
        this.pointConflict = pointConflict;
    }

    /**
     * Set the turn manager
     * @param turnManager the turn manager
     */
    public void setTurnManager(TurnManager turnManager){
        this.turnManager = turnManager;
    }

    /**
     * Gets the current player
     * @return the current player
     */
    public String getCurrentPlayer(){
        return this.turnManager.getCurrentPlayer();
    }

    public Map<Rule.RuleID, Integer> getRules(){
        return turnManager.getRules();
    }
}