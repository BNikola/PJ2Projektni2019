package classes.domain.extras;

public enum FlightDirection {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    public boolean isOpposite(FlightDirection flightDirection) {
        boolean result = false;
        switch (this) {
            case UP:
                if (flightDirection.equals(DOWN)) {
                    result = true;
                }
                break;
            case RIGHT:
                if (flightDirection.equals(LEFT)) {
                    result = true;
                }
                break;
            case DOWN:
                if (flightDirection.equals(UP)) {
                    result = true;
                }
                break;
            case LEFT:
                if (flightDirection.equals(RIGHT)) {
                    result = true;
                }
                break;
            default:
                result = false;
                break;
        }
        return result;
    }
}
