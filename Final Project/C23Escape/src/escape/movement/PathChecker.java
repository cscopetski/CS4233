package escape.movement;

import escape.board.Board;
import escape.board.Location;
import escape.board.TileShapeCoordinate;
import escape.required.LocationType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class PathChecker {

    private static Board board;

    public static PathValidator linearPathValidator = (from, to, distance, fly, jump, unblock) -> isValidLinearPath(from ,to, distance, fly, jump, unblock);

    private static boolean isValidLinearPath(TileShapeCoordinate from, TileShapeCoordinate to, int distance, boolean fly, boolean jump, boolean unblock){

        //TODO: Rewrite all of this ;(
        // DFS out in each direction, pass in direction (use enum or coord as direction) or
        // find direction that goes to goal and just go out in that direction

        return linearPathSearch(from,to, findBestDirection(from, to),distance, fly, jump, unblock);
    }

    private static boolean linearPathSearch(TileShapeCoordinate current, TileShapeCoordinate to, TileShapeCoordinate.Direction direction, int distance, boolean fly, boolean jump, boolean unblock){

        while(distance >0){

            current = current.getNeighbor(direction);

            if (!isValidLocation(board.getLocation(current),fly, jump, unblock)){
                return false;
            }

            if(current.equals(to)){
                return true;
            }

            distance--;
        }

        return false;
    }

    private static TileShapeCoordinate.Direction findBestDirection(TileShapeCoordinate from, TileShapeCoordinate to){

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

    private static ArrayList<TileShapeCoordinate> getLinearCoordinates(TileShapeCoordinate[] coordinates, TileShapeCoordinate currentCoord, boolean fly, boolean jump, boolean unblock){

        ArrayList<TileShapeCoordinate> validCoords = new ArrayList<>();

        for (TileShapeCoordinate coord: getValidCoordinates(coordinates, fly, jump, unblock)) {
            if (MoveChecker.linearValidator.isLegalMovePattern(currentCoord, coord)){
                validCoords.add(coord);
            }
        }

        return validCoords;
    }

    private static ArrayList<TileShapeCoordinate> getValidCoordinates(TileShapeCoordinate[] coordinates, boolean fly, boolean jump, boolean unblock){

        ArrayList<TileShapeCoordinate> validCoords = new ArrayList<>();

        for (TileShapeCoordinate coord: coordinates) {
            if (isValidLocation(board.getLocation(coord),fly, jump, unblock)){
                validCoords.add(coord);
            }
        }

        return validCoords;
    }

    /**
     * Checks if a location is a valid move based on the piece attributes and location type
     * @param location The location to check
     * @param fly If the piece can fly
     * @param jump If the piece can jump
     * @param unblock If the piece can unblock
     * @return
     */
    private static boolean isValidLocation(Location location, boolean fly, boolean jump, boolean unblock){

        LocationType type = location.getLocationType();

        switch(type){
            case CLEAR -> {
                return location.getPiece()==null;
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
