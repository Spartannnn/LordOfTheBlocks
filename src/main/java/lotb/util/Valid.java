package lotb.util;

public class Valid {

    public static void notNull(Object object, String message) {
        if(object == null)
            throw new NullPointerException(message);
    }

    public static void checkTrue(boolean condition, String message) {
        if(condition)
            throw new IllegalStateException(message);
    }

}
