package escape.piece.movement;

import escape.board.Board;
import escape.board.Location;
import escape.coordinate.CoordinateImpl;
import escape.coordinate.CoordinateStrategy;
import escape.required.LocationType;

public class PathChecker {

    private static Board board;
    private static CoordinateStrategy coordinateStrategy;
    private static boolean combat = false;
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
    private static boolean isValidLinearPath(CoordinateImpl from, CoordinateImpl to, int distance, boolean fly, boolean jump, boolean unblock, boolean anyMove){

        if(anyMove){

            for (CoordinateStrategy.Direction direction: getOmniDirections()) {
                boolean validPath = dfs(from,to, new CoordinateStrategy.Direction[]{direction},distance, fly, jump, unblock, true);

                if(validPath){
                    return true;
                }
            }

            return false;
        }

        return dfs(from,to, new CoordinateStrategy.Direction[]{getLinearDirection(from, to)},distance, fly, jump, unblock, false);

    }

    /**
     * Get the direction to move linearly in
     * @param from the starting coordinate
     * @param to the ending coordinate
     * @return The linear direction to get from point A to point B
     */
    private static CoordinateStrategy.Direction getLinearDirection(CoordinateImpl from, CoordinateImpl to){

        int minDistance = from.getDistance(to, coordinateStrategy);
        CoordinateStrategy.Direction bestDirection = CoordinateStrategy.Direction.NORTH;

        for (CoordinateStrategy.Direction direction: CoordinateStrategy.Direction.values()) {

            int distance = ((CoordinateImpl)from.getNeighbor(direction, coordinateStrategy)).getDistance(to, coordinateStrategy);

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
    private static boolean isValidOmniPath(CoordinateImpl from, CoordinateImpl to, int distance, boolean fly, boolean jump, boolean unblock, boolean anyMove){

        return dfs(from,to, getOmniDirections(),distance, fly, jump, unblock, anyMove);
    }

    /**
     * Get the directions for omni movement
     * @return the directions for omni movement
     */
    private static CoordinateStrategy.Direction[] getOmniDirections(){
        return CoordinateStrategy.Direction.values();
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
    private static boolean isValidOrthogonalPath(CoordinateImpl from, CoordinateImpl to, int distance, boolean fly, boolean jump, boolean unblock, boolean anyMove){
        return dfs(from,to, getOrthogonalDirections(),distance, fly, jump, unblock, anyMove);
    }

    /**
     * Get the directions for orthogonal movement
     * @return the directions for orthogonal movement
     */
    private static CoordinateStrategy.Direction[] getOrthogonalDirections(){
        return CoordinateStrategy.getOrthogonalDirections();
    }

    private static boolean isValidDiagonalPath(CoordinateImpl from, CoordinateImpl to, int distance, boolean fly, boolean jump, boolean unblock, boolean anyMove){
        return dfs(from,to, getDiagonalDirections(),distance, fly, jump, unblock, anyMove);
    }

    /**
     * Get the directions for diagonal movement
     * @return the directions for diagonal movement
     */
    private static CoordinateStrategy.Direction[] getDiagonalDirections(){
        return CoordinateStrategy.getDiagonalDirections();
    }

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
    private static boolean dfs(CoordinateImpl from, CoordinateImpl to, CoordinateStrategy.Direction[] directions, int distance, boolean fly, boolean jump, boolean unblock, boolean anyMove) {
        return dfsRecursive(from, to, directions[0], true, directions, distance, fly, jump, unblock, anyMove,false);
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
    private static boolean dfsRecursive(CoordinateImpl current, CoordinateImpl to, CoordinateStrategy.Direction currentDirection, boolean isFirst, CoordinateStrategy.Direction[] directions, int distance, boolean fly, boolean jump, boolean unblock, boolean anyMove, boolean hasJumped) {

        if(distance < 0){
            return false;
        }
        Location currentLocation = board.getLocation(current);

        if(anyMove){
            if(!isFirst && isValidLocation(currentLocation,fly, unblock, true)){
                return true;
            }
        }else if(current.equals(to)){
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
            CoordinateImpl destination = (CoordinateImpl) current.getNeighbor(currentDirection, coordinateStrategy);

            isPath = isPath || dfsRecursive(destination, to, currentDirection, false,directions, distance-1, fly, jump, unblock, anyMove,true);
        }else{

            for (CoordinateStrategy.Direction direction : directions) {

                CoordinateImpl destination = (CoordinateImpl) current.getNeighbor(direction, coordinateStrategy);

                isPath = isPath || dfsRecursive(destination, to, direction, false,directions, distance-1, fly, jump, unblock, anyMove,false);
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
    private static boolean isValidLocation(Location location, boolean fly, boolean unblock, boolean isLastLocation){

        if(location == null){
            return false;
        }

        LocationType type = location.getLocationType();

        switch(type){
            case CLEAR -> {
                return (isLastLocation && combat) || fly || location.getPiece()==null;
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

    /**
     * Set whether combat is enabled or not
     * @param combat
     */
    public static void setCombat(boolean combat){
        PathChecker.combat = combat;
    }
    public static void setCoordinateStrategy(CoordinateStrategy coordinateStrategy){
        PathChecker.coordinateStrategy = coordinateStrategy;
    }
}
