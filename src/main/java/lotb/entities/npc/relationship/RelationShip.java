package lotb.entities.npc.relationship;

public enum RelationShip {

    BAD,
    NEUTRAL,
    GOOD,
    VERY_GOOD;

    public static RelationShip[] VALUES = values();

    public static RelationShip nextBest(RelationShip rs) {
        switch (rs) {
            case BAD:
                return NEUTRAL;
            case NEUTRAL:
                return GOOD;
            case GOOD:
            case VERY_GOOD:
            default:
                return VERY_GOOD;
        }
    }

    public static RelationShip nextBad(RelationShip rs) {
        switch (rs) {
            case GOOD:
                return NEUTRAL;
            case VERY_GOOD:
                return GOOD;
            case BAD:
            case NEUTRAL:
            default:
                return BAD;
        }
    }

}
