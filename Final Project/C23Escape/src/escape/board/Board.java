package escape.board;

import escape.piece.EscapePieceImpl;
import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.LocationType;

import java.util.HashMap;
import java.util.Map;

public class Board<C extends Coordinate> {

    private int xMax, yMax;
    private final int xMin = 1;
    private final int yMin = 1;
    private Map<C, Location> board = new HashMap<>();

    private final int INFINITE_AXIS = 0;

    public Board(int xMax, int yMax){
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * Move from one location to another
     * @param from coordinate to move from
     * @param to coordinate to move to
     * @param currentPlayer the current player's turn
     * @return true if the move was valid and successfully made, false otherwise
     */
    public boolean move(C from, C to, String currentPlayer){

        Location fromLocation = getLocation(from);
        Location toLocation = getLocation(to);

        if(fromLocation == null || toLocation== null){
            return false;
        }

        EscapePieceImpl piece = (EscapePieceImpl) fromLocation.getPiece();

        if(!isInBounds(from) || !isInBounds(to) || !hasPiece(from) || hasPiece(to) || !piece.getPlayer().equals(currentPlayer)){
            return false;
        }

        if(!piece.isValidMove(from, to)){
            return false;
        }

        movePiece(from, to);

        return true;

    }

    /**
     * Move a piece from one location to another
     * @param from coordinate of start location
     * @param to coordinate of end location
     */
    private void movePiece(C from, C to){

        Location fromLocation = getLocation(from);
        Location toLocation = getLocation(to);

        toLocation.setPiece(fromLocation.getPiece());
        fromLocation.setPiece(null);

    }

    /**
     * remove a piece from a location
     * @param coordinate coordinate of location
     */
    public void removePiece(C coordinate){

        Location fromLocation = getLocation(coordinate);

        fromLocation.setPiece(null);

    }

    /**
     * Check if coordinate is in bounds
     * @param coordinate Coordinate to check
     * @return If the coordinate is in the bounds of the board
     */
    private boolean isInBounds(C coordinate){
        return checkBounds(xMax, xMin, coordinate.getRow())
                && checkBounds(yMax, yMin, coordinate.getColumn());
    }

    /**
     * Helper function that checks the bounds of one axis
     * @param max The maximum of the axis
     * @param min The minimum of the axis
     * @param val The row to check the bounds of
     * @return True if the row and col are in the bounds, or if the axis is infinite
     */
    private boolean checkBounds(int max, int min, int val){
        return max == INFINITE_AXIS || (val <= max && val >= min);
    }

    /**
     * Checks if there is a piece at the coordinate
     * @param coordinate The coordinate to check
     * @return True if there is a piece at the coordinate
     */
    private boolean hasPiece(C coordinate){
        return getLocation(coordinate).getPiece() != null;
    }

    /**
     * Add a location to the board
     * @param coordinate The coordinate to add the location at
     * @param location The location to add
     */
    public void addLocation(C coordinate, Location location){
        board.put(coordinate, location);
    }

    /**
     * Get a location
     * @param coordinate The coordinate to get the location from
     * @return the location
     */
    public Location getLocation(C coordinate){

        if(!isInBounds(coordinate)) {

            return null;
        }

        return board.computeIfAbsent(coordinate, this::createBaseLocation);
    }

    /**
     * Creates a clear location at the coordinate
     * @param coordinate The coordinate to create the location at
     * @return the created location
     */
    private Location createBaseLocation(C coordinate){
        return new LocationImpl(null, LocationType.CLEAR);
    }

    /**
     * counts the number of pieces a player has
     * @param player The player to count the pieces of
     * @return
     */
    public int getPieceCount(String player){

        int count = 0;

        for (Location location: board.values()) {

            EscapePiece piece = location.getPiece();

            if(piece!=null && piece.getPlayer().equals(player)){
                count++;
            }
        }

        return count;
    }

    /**
     * checks if a player has a valid move
     * @param player The player to check the pieces of
     * @return if the player can move or not
     */
    public boolean hasValidMove(String player){

        for (C coordinate: board.keySet()) {

            EscapePieceImpl piece = (EscapePieceImpl) getLocation(coordinate).getPiece();

            if(piece!=null && piece.getPlayer().equals(player) && piece.canMove(coordinate)){
               return true;
            }
        }

        return false;
    }
}
