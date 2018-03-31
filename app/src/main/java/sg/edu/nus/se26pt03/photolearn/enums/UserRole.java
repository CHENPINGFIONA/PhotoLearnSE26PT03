package sg.edu.nus.se26pt03.photolearn.enums;

/**
 * Created by part time team 3 on 15/3/2018.
 */

public enum UserRole {
    TRAINER,
    PARTICIPENT;

    public static UserRole fromInt(int x) {
        return UserRole.values()[x];
    }

    public static int toInt(UserRole role) {
        switch (role) {
            case TRAINER:
                return 0;
            case PARTICIPENT:
                return 1;
        }
        return 0;
    }
}

