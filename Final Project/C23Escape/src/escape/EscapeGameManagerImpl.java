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
    private String[] players;
    private int currentPlayer = 0;
    private int moveCount = 0;
    private int turnCount = -1;
    private int scoreGoal = -1;
    private boolean pointConflict = false;

    private boolean gameFinished = false;

    private HashMap<String, Integer> scoreMap = new HashMap<>();
    private Map<Rule.RuleID, Integer> rules;

    /**
     * Make the move in the current game.
     * @param from starting location
     * @param to ending location
     * @return true if the move was legal, false otherwise
     */
    @Override
    public GameStatus move(C from, C to) {

        if(gameFinished || !board.move(from, to, getCurrentPlayer())){
            return invalidMoveStatus(null);
        };

        if(board.getLocation(to).getLocationType() == LocationType.EXIT){
            exitPiece(to);
        }

        GameStatus.MoveResult result = endPlayerTurn();

        return validMoveStatus(null, result);
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
        int currentScore = scoreMap.get(getCurrentPlayer());

        scoreMap.put(getCurrentPlayer(), currentScore+pieceScore);
        board.removePiece(to);
    }

    /**
     * Gets the current player
     * @return The name of the current player
     */
    public String getCurrentPlayer(){
        return players[currentPlayer];
    }

    /**
     * End the current player turn and check win conditions
     * @return The result of the game
     */
    private GameStatus.MoveResult endPlayerTurn(){

        moveCount++;

        if(checkScore() || board.getPieceCount(getCurrentPlayer()) == 0){
            return GameStatus.MoveResult.WIN;
        };

        if(!board.hasValidMove(getOtherPlayer())){
            return GameStatus.MoveResult.WIN;
        }

        if(moveCount%players.length == 0){
            checkTurnLimit();
        }

        if(gameFinished){
           return getGameResult();
        }

        setNextPlayer();

        return GameStatus.MoveResult.NONE;

    }

    /**
     * Sets the current player to the next player
     */
    private void setNextPlayer(){
        currentPlayer = (currentPlayer+1)%players.length;

    }

    /**
     * Get the opposing player
     * @return the opposing player name
     */
    private String getOtherPlayer(){
        return players[(currentPlayer+1)%players.length];
    }

    /**
     * Check the turn limit, and end the game if the turn limit is reached
     */
    private void checkTurnLimit(){
        turnCount--;
        if(turnCount == 0){
            gameFinished = true;
        }
    }

    /**
     * Checks the score to see if the current player won
     * @return
     */
    private boolean checkScore(){
        return scoreGoal != -1 && scoreGoal <= scoreMap.get(getCurrentPlayer());
    }

    /**
     * Get the game result based on the score
     * @return the game result
     */
    private GameStatus.MoveResult getGameResult(){

        int currentPlayerScore = scoreMap.get(getCurrentPlayer());
        int otherPlayerScore = scoreMap.get(getOtherPlayer());

        if(currentPlayerScore > otherPlayerScore){
            return GameStatus.MoveResult.WIN;
        }

        if(currentPlayerScore < otherPlayerScore){
            return GameStatus.MoveResult.LOSE;
        }

        return GameStatus.MoveResult.DRAW;

    }

    /**
     * Gets default valid moveStatus
     * @param finalLocation The final location
     * @param result
     * @return default valid moveStatus
     */
    private GameStatus validMoveStatus(C finalLocation, GameStatus.MoveResult result){
        return getGameStatus(finalLocation, true, result);
    }

    /**
     * Generates default invalid move status
     * @return default invalid move status
     */
    private GameStatus invalidMoveStatus(C finalLocation){
        return getGameStatus(finalLocation, false, GameStatus.MoveResult.LOSE);
    }

    /**
     * Returns the game status
     * @param finalLocation The final location
     * @param isValid Whether the move was valid or not
     * @return the game status
     */
    private GameStatus getGameStatus(C finalLocation, boolean isValid, GameStatus.MoveResult gameStatus) {
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
                return finalLocation;
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
     * Set the players
     * @param players List of player names
     */
    public void setPlayers(String[] players){
        this.players = players;

        for (String player: this.players) {
            this.scoreMap.put(player,0);
        }
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
     * Set the game rules
     * @param ruleMap Hashmap of rules and values
     */
    public void setRules(Map<Rule.RuleID, Integer> ruleMap) {
        this.rules = ruleMap;

        this.turnCount = rules.get(Rule.RuleID.TURN_LIMIT) != null ? rules.get(Rule.RuleID.TURN_LIMIT) : -1;
        this.scoreGoal = rules.get(Rule.RuleID.SCORE) != null ? rules.get(Rule.RuleID.SCORE) : -1;
        this.pointConflict = rules.get(Rule.RuleID.POINT_CONFLICT) != null;
    }

    /**
     * Get game rules
     * @return the game rules
     */
    public Map<Rule.RuleID, Integer> getRules() {
        return this.rules;
    }
}