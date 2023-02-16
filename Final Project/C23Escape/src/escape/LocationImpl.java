package escape;

import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.LocationType;

public class LocationImpl<C extends Coordinate> implements Location<C> {

    private EscapePiece piece;
    private C coordinate;
    private LocationType locationType;

    public LocationImpl(EscapePiece piece, C coordinate, LocationType locationType){
        this.piece = piece;
        this.coordinate = coordinate;
        this.locationType = locationType;
    }

    @Override
    public EscapePiece getPiece() {
        return piece;
    }

    @Override
    public C getCoordinate() {
        return coordinate;
    }

    @Override
    public LocationType getLocationType() {
        return locationType;
    }
}
