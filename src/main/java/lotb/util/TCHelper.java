package lotb.util;

import net.minecraft.util.text.StringTextComponent;

public class TCHelper {

    public static StringTextComponent string(String rawMessage) {
        return new StringTextComponent(rawMessage);
    }

    /**
     * Use this method to normalize a enum variable name
     * Example:
     * <p>
     * If you have a enum class with following variables:
     * <p>
     * CAT,
     * DOG_BIG,
     * DOG_LITTLE_MIDDLE,
     * DUCK
     * <p>
     * Then this method will normalize this variables:
     * <p>
     * CAT -> Cat
     * DOG_BIG -> Dog Big
     * DOG_LITTLE_MIDDLE -> Dog Little Middle
     * DUCK -> Duck
     *
     * @param _enum the enum variable
     * @return the normalized enum name
     */
    public static String normalizeEnum(Enum<?> _enum) {
        Valid.notNull(_enum, "Can not normalize enum name, enum is null");
        String str = _enum.toString();
        if (str.contains("_")) {
            String[] spl = str.split("_");
            String[] spl0 = new String[spl.length];
            for (int i = 0; i < spl.length; i++) {
                String str0 = spl[i].toLowerCase();
                str0 = str0.replace(str0.charAt(0), Character.toUpperCase(str0.charAt(0)));
                spl0[i] = str0;
            }
            StringBuilder builder = new StringBuilder();
            for (String str1 : spl0) {
                builder.append(str1).append(" ");
            }
            return builder.substring(0, builder.length() - 1);
        } else {
            str = str.toLowerCase();
            return str.replace(str.charAt(0), Character.toUpperCase(str.charAt(0)));
        }
    }


}
