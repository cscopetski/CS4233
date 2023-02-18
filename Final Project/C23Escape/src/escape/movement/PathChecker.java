package escape.movement;

import escape.board.Board;
import escape.board.Location;
import escape.board.TileShapeCoordinate;
import escape.required.LocationType;

public class PathChecker {

    private static Board board;

    public static PathValidator linearPathValidator = PathChecker::isValidLinearPath;
    public static PathValidator orthogonalPathValidator = PathChecker::isValidOrthogonalPath;
    public static PathValidator omniPathValidator = PathChecker::isValidOmniPath;

    private static boolean isValidLinearPath(TileShapeCoordinate from, TileShapeCoordinate to, int distance, boolean fly, boolean jump, boolean unblock){
        return dfs(from,to, new TileShapeCoordinate.Direction[]{getLinearDirection(from, to)},distance, fly, jump, unblock);
    }

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

    private static boolean isValidOmniPath(TileShapeCoordinate from, TileShapeCoordinate to, int distance, boolean fly, boolean jump, boolean unblock){
        return dfs(from,to, getOmniDirections(),distance, fly, jump, unblock);
    }

    private static TileShapeCoordinate.Direction[] getOmniDirections(){
        return TileShapeCoordinate.Direction.values();
    }

    private static boolean isValidOrthogonalPath(TileShapeCoordinate from, TileShapeCoordinate to, int distance, boolean fly, boolean jump, boolean unblock){
        return dfs(from,to, getOrthogonalDirections(),distance, fly, jump, unblock);
    }

    private static TileShapeCoordinate.Direction[] getOrthogonalDirections(){
        return TileShapeCoordinate.getOrthogonalDirections();
    }


    //TODO: look into using bfs instead will be much faster
    private static boolean dfs(TileShapeCoordinate from, TileShapeCoordinate to, TileShapeCoordinate.Direction[] directions, int distance, boolean fly, boolean jump, boolean unblock) {
        return dfsRecursive(from, to, true, directions, distance, fly, jump, unblock);
    }

    private static boolean dfsRecursive(TileShapeCoordinate current, TileShapeCoordinate to, boolean isFirst, TileShapeCoordinate.Direction[] directions, int distance, boolean fly, boolean jump, boolean unblock) {

        if(distance < 0){
            return false;
        }

        if(current.equals(to)){
            return isValidLocation(board.getLocation(current),fly, jump, unblock);
        }

        if (!isFirst && !isValidLocation(board.getLocation(current),fly, jump, unblock)){
            return false;
        }

        boolean isPath = false;

        for (TileShapeCoordinate dest : current.getNeighbors(directions)) {
                isPath = isPath || dfsRecursive(dest, to, false,directions, distance-1, fly, jump, unblock);
        }

        return isPath;
    }

    /**
     * Checks if a location is a valid move based on the piece attributes and location type
     * @param location The location to check
     * @param fly If the piece can fly
     * @param jump If the piece can jump
     * @param unblock If the piece can unblock
     * @return true if the location is a valid move, false otherwise
     */
    private static boolean isValidLocation(Location location, boolean fly, boolean jump, boolean unblock){

        if(location == null){
            return false;
        }

        LocationType type = location.getLocationType();

        switch(type){
            case CLEAR -> {
                return fly || location.getPiece()==null;
            }
            case BLOCK -> {
                //TODO: Implement unblock & jump
                return fly;
            }
            case EXIT -> {
                return false;
            }
        }

        return false;

    }

    public static void setBoard(Board board){
        PathChecker.board = board;
    }
}
