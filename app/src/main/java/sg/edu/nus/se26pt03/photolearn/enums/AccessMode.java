package sg.edu.nus.se26pt03.photolearn.enums;

/**
 * Created by chen ping on 11/3/2018.
 */

public enum AccessMode {
    EDIT,
    VIEW;

    public static AccessMode fromInt(int x) {
        return AccessMode.values()[x];
    }

    public static int toInt(AccessMode mode) {
        switch (mode) {
            case VIEW:
                return 0;
            case EDIT:
                return 1;
        }
        return 0;
    }
}

