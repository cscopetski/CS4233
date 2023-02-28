package escape.piece;

import escape.board.Board;
import escape.board.Location;
import escape.coordinate.TileShapeCoordinate;
import escape.required.LocationType;

public class PathChecker {

    private static Board board;

    public static PathValidator linearPathValidator = PathChecker::isValidLinearPath;
    public static PathValidator orthogonalPathValidator = PathChecker::isValidOrthogonalPath;
    public static PathValidator omniPathValidator = PathChecker::isValidOmniPath;
    public static PathValidator diagonalPathValidator = PathChecker::isValidDiagonalPath;

    /**
     * Search the board and find a valid linear path
     * @param from the starting coordinate
     * @param to the ending coordinate
     * @param distance The total distance the piece can move
     * @param fly if the piece can fly or not
     * @param jump if the piece can jump or not
     * @param unblock if the piece can unblock or not
     * @return true if a valid path was found, false otherwise
     */
    private static boolean isValidLinearPath(TileShapeCoordinate from, TileShapeCoordinate to, int distance, boolean fly, boolean jump, boolean unblock){
        return dfs(from,to, new TileShapeCoordinate.Direction[]{getLinearDirection(from, to)},distance, fly, jump, unblock);
    }

    /**
     * Get the direction to move linearly in
     * @param from the starting coordinate
     * @param to the ending coordinate
     * @return The linear direction to get from point A to point B
     */
    private static TileShapeCoordinate.Direction getLinearDirection(TileShapeCoordinate from, TileShapeCoordinate to){

        int minDistance = from.getDistance(to);
        TileShapeCoordinate.Direction bestDirection = TileShapeCoordinate.Direction.NORTH;

        for (TileShapeCoordinate.Direction direction: TileShapeCoordinate.Direction.values()) {

            int distance = from.getNeighbor(direction).getDistance(to);

            if(distance <= minDistance){
                minDistance = distance;
                bestDirection = direction;
            }
        }

        return bestDirection;
    }

    /**
     * Search the board and find a valid omni path
     * @param from the starting coordinate
     * @param to the ending coordinate
     * @param distance The total distance the piece can move
     * @param fly if the piece can fly or not
     * @param jump if the piece can jump or not
     * @param unblock if the piece can unblock or not
     * @return true if a valid path was found, false otherwise
     */
    private static boolean isValidOmniPath(TileShapeCoordinate from, TileShapeCoordinate to, int distance, boolean fly, boolean jump, boolean unblock){
        return dfs(from,to, getOmniDirections(),distance, fly, jump, unblock);
    }

    /**
     * Get the directions for omni movement
     * @return the directions for omni movement
     */
    private static TileShapeCoordinate.Direction[] getOmniDirections(){
        return TileShapeCoordinate.Direction.values();
    }

    /**
     * Search the board and find a valid orthogonal path
     * @param from the starting coordinate
     * @param to the ending coordinate
     * @param distance The total distance the piece can move
     * @param fly if the piece can fly or not
     * @param jump if the piece can jump or not
     * @param unblock if the piece can unblock or not
     * @return true if a valid path was found, false otherwise
     */
    private static boolean isValidOrthogonalPath(TileShapeCoordinate from, TileShapeCoordinate to, int distance, boolean fly, boolean jump, boolean unblock){
        return dfs(from,to, getOrthogonalDirections(),distance, fly, jump, unblock);
    }

    /**
     * Get the directions for orthogonal movement
     * @return the directions for orthogonal movement
     */
    private static TileShapeCoordinate.Direction[] getOrthogonalDirections(){
        return TileShapeCoordinate.getOrthogonalDirections();
    }

    private static boolean isValidDiagonalPath(TileShapeCoordinate from, TileShapeCoordinate to, int distance, boolean fly, boolean jump, boolean unblock){
        return dfs(from,to, getDiagonalDirections(),distance, fly, jump, unblock);
    }

    /**
     * Get the directions for diagonal movement
     * @return the directions for diagonal movement
     */
    private static TileShapeCoordinate.Direction[] getDiagonalDirections(){
        return TileShapeCoordinate.getDiagonalDirections();
    }
    //TODO: look into using bfs instead will be much faster

    /**
     * Search the board and find a valid path using the given movement pattern
     * @param from the starting coordinate
     * @param to the ending coordinate
     * @param directions The directions to search in
     * @param distance The total distance the piece can move
     * @param fly if the piece can fly or not
     * @param jump if the piece can jump or not
     * @param unblock if the piece can unblock or not
     * @return true if a valid path was found, false otherwise
     */
    private static boolean dfs(TileShapeCoordinate from, TileShapeCoordinate to, TileShapeCoordinate.Direction[] directions, int distance, boolean fly, boolean jump, boolean unblock) {
        return dfsRecursive(from, to, directions[0], true, directions, distance, fly, jump, unblock, false);
    }

    /**
     * Search the board and find a valid path using the given movement pattern
     * @param current The current coordinate
     * @param to the goal coordinate
     * @param isFirst true if this is the first recursive step
     * @param directions The directions to search in
     * @param distance The remaining distance the piece can move
     * @param fly if the piece can fly or not
     * @param jump if the piece can jump or not
     * @param unblock if the piece can unblock or not
     * @return true if a valid path was found, false otherwise
     */
    private static boolean dfsRecursive(TileShapeCoordinate current, TileShapeCoordinate to, TileShapeCoordinate.Direction currentDirection, boolean isFirst, TileShapeCoordinate.Direction[] directions, int distance, boolean fly, boolean jump, boolean unblock, boolean hasJumped) {

        if(distance < 0){
            return false;
        }
        Location currentLocation = board.getLocation(current);


        if(current.equals(to)){
            return isValidLocation(currentLocation,fly, unblock, true);
        }


        boolean jumped = false;

        if (!isFirst && !isValidLocation(currentLocation,fly, unblock, false)){
            if(!hasJumped && isValidJumpLocation(currentLocation, jump)){
                jumped = true;

            }else{
                return false;
            }

        }

        boolean isPath = false;

        if(jumped){
            TileShapeCoordinate destination = current.getNeighbor(currentDirection);

            isPath = isPath || dfsRecursive(destination, to, currentDirection, false,directions, distance-1, fly, jump, unblock, true);
        }else{

            for (TileShapeCoordinate.Direction direction : directions) {

                TileShapeCoordinate destination = current.getNeighbor(direction);

                isPath = isPath || dfsRecursive(destination, to, direction, false,directions, distance-1, fly, jump, unblock, false);
            }

        }



        return isPath;
    }

    /**
     * Checks if a location is a valid move based on the piece attributes and location type
     * @param location The location to check
     * @param fly If the piece can fly
     * @param unblock If the piece can unblock
     * @return true if the location is a valid move, false otherwise
     */
    //TODO: add isLastMove to check if exit (only valid if last location) or if we can unblock(only valid if not last location)?
    private static boolean isValidLocation(Location location, boolean fly, boolean unblock, boolean isLastLocation){

        if(location == null){
            return false;
        }

        LocationType type = location.getLocationType();

        switch(type){
            case CLEAR -> {
                return fly || location.getPiece()==null;
            }
            case BLOCK -> {
                return fly || (unblock && !isLastLocation);
            }
            case EXIT -> {
                return isLastLocation || fly;
            }
        }

        return false;

    }

    /**
     * Checks if a location is a valid move based on the piece attributes and location type
     * @param location The location to check
     * @param jump If the piece can jump
     * @return true if the location is a valid move, false otherwise
     */
    //TODO: add isLastMove to check if exit (only valid if last location) or if we can unblock(only valid if not last location)?
    private static boolean isValidJumpLocation(Location location, boolean jump){

        if(location == null){
            return false;
        }

        LocationType type = location.getLocationType();

        switch(type){
            case CLEAR, EXIT -> {
                return jump;
            }
            case BLOCK -> {
                return false;
            }
        }

        return false;

    }

    /**
     * Set the board instance
     * @param board the board instance
     */
    public static void setBoard(Board board){
        PathChecker.board = board;
    }
}
