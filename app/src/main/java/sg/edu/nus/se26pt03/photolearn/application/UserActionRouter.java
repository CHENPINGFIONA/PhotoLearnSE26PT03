package sg.edu.nus.se26pt03.photolearn.application;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.User;

/**
 * Created by Drake on 17/3/18.
 */

public class UserActionRouter {

    private UserActionListener sourceOrigin = null;
    private  UserActionListener base = null;

    public UserActionRouter(UserActionListener base) {
        this.base = base;
    }

    public final boolean dynamicRoute(UserActionListener.Event event, boolean outbound, Object object, UserActionListener source) {
        boolean result  = true;
        if (outbound) {
            if (event != UserActionListener.Event.BEFORE)
                result = base.onBefore(event);
            if (result) {
                for (UserActionListener userActionListener : base.getRelatives()) {
                    if (userActionListener != sourceOrigin) {
                        switch (event) {
                            case BEFORE:
                                if (object instanceof UserActionListener.Event)
                                    result = userActionListener.onBefore((UserActionListener.Event) object, base);
                                break;
                            case MODE_CHANGE:
                                if (object instanceof AppMode)
                                    result = userActionListener.onModeChange((AppMode) object, base);
                                else if (object instanceof AccessMode)
                                    result = userActionListener.onModeChange((AccessMode) object, base);
                                break;
                            case LOGIN:
                                if (object instanceof User)
                                    result = userActionListener.onLogIn((User) object, base);
                                break;
                            case LOGOUT:
                                if (object instanceof User)
                                    result = userActionListener.onLogOut((User) object, base);
                                break;
                            case LOAD:
                                if (object instanceof LearningSession)
                                    result = userActionListener.onLoad((LearningSession) object, base);
                                else if (object instanceof LearningTitle)
                                    result = userActionListener.onLoad((LearningTitle) object, base);
                                break;
                            case CREATE:
                                if (object instanceof LearningSession)
                                    result = userActionListener.onCreate((LearningSession) object, base);
                                break;
                            case EDIT:
                                if (object instanceof LearningSession)
                                    result = userActionListener.onEdit((LearningSession) object, base);
                                break;
                        }
                        if (!result) return result;
                    }
                }
            }
        }
        else {
            sourceOrigin = source;
            switch (event) {
                case BEFORE:
                    if (object instanceof UserActionListener.Event)
                        return base.onBefore((UserActionListener.Event) object);
                    break;
                case MODE_CHANGE:
                    if (object instanceof AppMode)
                        return base.onModeChange((AppMode) object);
                    else if (object instanceof AccessMode)
                        return base.onModeChange((AccessMode) object);
                    break;
                case LOGIN:
                    if (object instanceof User)
                        return base.onLogIn((User) object);
                    break;
                case LOGOUT:
                    if (object instanceof User)
                        return base.onLogOut((User) object);
                    break;
                case LOAD:
                    if (object instanceof LearningSession)
                        return base.onLoad((LearningSession) object);
                    else if (object instanceof LearningTitle)
                        return base.onLoad((LearningTitle) object);
                    break;
                case CREATE:
                    if (object instanceof LearningSession)
                        return base.onCreate((LearningSession) object);
                    break;
                case EDIT:
                    if (object instanceof LearningSession)
                        return base.onEdit((LearningSession) object);
                    break;
            }
        }
        return  result;
    }

}
