package escape.managers;

import escape.Observable;
import escape.required.GameObserver;
import escape.required.GameStatus;
import escape.required.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnManager implements Observable {

    private String[] players;
    private int currentPlayer = 0;
    private int moveCount = 0;
    private int turnCount = -1;

    private HashMap<String, Integer> scoreMap = new HashMap<>();
    private HashMap<Rule.RuleID, Integer> ruleMap;
    private List<GameObserver> gameObservers = new ArrayList<>();

    public TurnManager(String[] players, HashMap<Rule.RuleID, Integer> ruleMap){
        this.players = players;

        for (String player: this.players) {
            this.scoreMap.put(player,0);
        }

        this.ruleMap = ruleMap;
    }

    public int getScore(String player){
        return scoreMap.get(player);
    }

    public void updateScore(String player, int scoreIncrement){

        int currentScore = scoreMap.get(player);

        scoreMap.put(player, currentScore+scoreIncrement);

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
     * Get the opposing player
     * @return the opposing player name
     */
    public String getOtherPlayer(){
        return players[(currentPlayer+1)%players.length];
    }

    public GameStatus.MoveResult endPlayerTurn(int currentPlayerPieceCount, boolean otherPlayerValidMove){

        moveCount++;

        GameStatus.MoveResult result =  checkWinConditions(currentPlayerPieceCount, otherPlayerValidMove);

        setNextPlayer();

        return result;

    }

    private GameStatus.MoveResult checkWinConditions(int currentPlayerPieceCount, boolean otherPlayerValidMove){
        String currentPlayerName = getCurrentPlayer();

        if(isScoreActive()){

            if(checkScore()){
                String message = String.format("%s won by SCORE with a score of %d",currentPlayerName,getScore(currentPlayerName));

                notifyObservers(message);


                return GameStatus.MoveResult.WIN;
            }

            if(checkTurnLimit()){

                return getTurnLimitGameResult();
            }

        }else {

            if(checkTurnLimit()){

                return getTurnLimitGameResult();
            }

            if (currentPlayerPieceCount == 0){

                String message = String.format("%s won by removing all pieces",currentPlayerName);
                notifyObservers(message);

                return GameStatus.MoveResult.WIN;
            }

        };

        if(!otherPlayerValidMove){
            String message = String.format("%s won by %s not having a valid move",currentPlayerName,getOtherPlayer());
            notifyObservers(message);

            return GameStatus.MoveResult.WIN;
        }

        return GameStatus.MoveResult.NONE;
    }

    private boolean isScoreActive(){
        return ruleMap.get(Rule.RuleID.SCORE) != null;
    }

    /**
     * Check if the turn limit has been reached (or exits)
     * @return true if the turn limit has been reached
     */
    private boolean checkTurnLimit(){

        if(moveCount%players.length != 0){
            return false;
        }

        Integer turnCount = ruleMap.get(Rule.RuleID.TURN_LIMIT);

        if(turnCount == null){
            return false;
        }

        turnCount--;
        ruleMap.put(Rule.RuleID.TURN_LIMIT,turnCount);

        return turnCount == 0;
    }

    /**
     * Get the game result based on the score
     * @return the game result
     */
    private GameStatus.MoveResult getTurnLimitGameResult(){

        int currentPlayerScore = getScore(getCurrentPlayer());
        int otherPlayerScore = getScore(getOtherPlayer());

        if(currentPlayerScore > otherPlayerScore){

            String message = String.format("%s won by TURN_LIMIT with a score of %d",getCurrentPlayer(),currentPlayerScore);
            notifyObservers(message);

            return GameStatus.MoveResult.WIN;
        }

        if(currentPlayerScore < otherPlayerScore){

            String message = String.format("%s won by TURN_LIMIT with a score of %d",getOtherPlayer(),otherPlayerScore);
            notifyObservers(message);

            return GameStatus.MoveResult.LOSE;
        }

        String message = String.format("DRAW by TURN_LIMIT with a score of %d",otherPlayerScore);
        notifyObservers(message);

        return GameStatus.MoveResult.DRAW;

    }

    /**
     * Checks the score to see if the current player won
     * @return
     */
    private boolean checkScore(){
        return ruleMap.get(Rule.RuleID.SCORE) <= getScore(getCurrentPlayer());
    }

    public Map<Rule.RuleID, Integer> getRules(){
        return this.ruleMap;
    }

    @Override
    public GameObserver addObserver(GameObserver observer) {
        this.gameObservers.add(observer);

        return observer;
    }

    @Override
    public GameObserver removeObserver(GameObserver observer) {
        return this.gameObservers.remove(observer) ? observer:null;
    }

    /**
     * Notifies all observers
     *
     * @param message the message to notify
     */
    @Override
    public void notifyObservers(String message) {
        for (GameObserver observer: this.gameObservers) {
            observer.notify(message);
        }
    }
}
