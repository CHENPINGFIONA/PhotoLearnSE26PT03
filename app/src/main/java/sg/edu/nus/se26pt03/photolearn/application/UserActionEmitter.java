package sg.edu.nus.se26pt03.photolearn.application;

import android.util.Log;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizAnswer;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;

/**
 * Created by MyatMin on 17/3/18.
 */

public class UserActionEmitter {

    private UserActionListener sourceOrigin = null;
    private  UserActionListener base = null;
    public UserActionEmitter(UserActionListener base) {
        this.base = base;
    }
    private boolean before = false;

    public final void dynamicEmit(final UserActionListener.Event event, final boolean outbound, final Object object, final UserActionCallback callback, final UserActionListener source) {
        final UserActionCallback validatedCallback  = (callback != null ? callback : new UserActionCallback());
        if (outbound) {
            if (event != UserActionListener.Event.BEFORE && !before) {
                before = true;

                base.onBefore(event, new UserActionCallback(this) {
                    @Override
                    public void onReject() {
                        before = false;
                        super.onReject();
                    }

                    @Override
                    public void onPass() {
                        getUserActionEmitter().dymnamicRoute(event,outbound, object,validatedCallback,source);
                    }
                });
            }
            else {
                if (event != UserActionListener.Event.BEFORE ) {
                    before = false;
                }
                dymnamicRoute(event,outbound,object,validatedCallback,source);
            }
        }
        else {
            dymnamicRoute(event,outbound,object,validatedCallback,source);
        }

    }

    public final void dymnamicRoute(final UserActionListener.Event event, boolean outbound, final Object object, final UserActionCallback callback , final UserActionListener source) {
        if (outbound) {
            boolean exist = false;
            for (UserActionListener userActionListener : base.getRelatives()) {
                if (userActionListener != sourceOrigin) {
                    exist = true;
                    switch (event) {
                        case BEFORE:
                            if (object instanceof UserActionListener.Event)
                                userActionListener.onBefore((UserActionListener.Event) object, callback, base);
                            break;
                        case MODE_CHANGE:
                            if (object instanceof AppMode)
                                userActionListener.onModeChange((AppMode) object, callback, base);
                            else if (object instanceof AccessMode)
                                userActionListener.onModeChange((AccessMode) object, callback, base);
                            break;
                        case LOGIN:
                            if (object instanceof User)
                                userActionListener.onLogIn((User) object, callback, base);
                            break;
                        case LOGOUT:
                            if (object instanceof User)
                                userActionListener.onLogOut((User) object, callback, base);
                            break;
                        case LOAD:
                            userActionListener.onLoad(object, callback, base);
                            break;
                        case CREATE:
                            userActionListener.onCreate(object, callback, base);
                            break;
                        case EDIT:
                            userActionListener.onEdit(object, callback, base);
                            break;
                        case BACKSTACK:
                            userActionListener.onBackstack(object, callback, base);
                            break;
                    }
                }
            }
            if (!exist) callback.onPass();
        }
        else {
            sourceOrigin = source;
            switch (event) {
                case BEFORE:
                        before = true;
                    if (object instanceof UserActionListener.Event)
                        base.onBefore((UserActionListener.Event) object, callback);
                    break;
                case MODE_CHANGE:
                    if (object instanceof AppMode)
                        base.onModeChange((AppMode) object, callback);
                    else if (object instanceof AccessMode)
                        base.onModeChange((AccessMode) object, callback);
                    break;
                case LOGIN:
                    if (object instanceof User)
                        base.onLogIn((User) object, callback);
                    break;
                case LOGOUT:
                    if (object instanceof User)
                        base.onLogOut((User) object, callback);
                    break;
                case LOAD:
                    if (object instanceof LearningSession)
                        base.onLoad((LearningSession) object, callback);
                    else if (object instanceof LearningTitle)
                        base.onLoad((LearningTitle) object, callback);
                    else if (object instanceof LearningItem)
                        base.onLoad((LearningItem) object, callback);
                    else if (object instanceof QuizTitle)
                        base.onLoad((QuizTitle) object, callback);
                    else if (object instanceof QuizItem)
                        base.onLoad((QuizItem) object, callback);
                    else if (object instanceof QuizItem)
                        base.onCreate((QuizItem) object, callback);
                    break;
                case CREATE:
                    if (object instanceof LearningSession)
                        base.onCreate((LearningSession) object, callback);
                    else if (object instanceof LearningTitle)
                        base.onCreate((LearningTitle) object, callback);
                    else if (object instanceof LearningItem)
                        base.onCreate((LearningItem) object, callback);
                    else if (object instanceof QuizTitle)
                        base.onCreate((QuizTitle) object, callback);
                    else if (object instanceof QuizItem)
                        base.onCreate((QuizItem) object, callback);
                    else if (object instanceof QuizAnswer)
                        base.onCreate((QuizAnswer) object, callback);
                    break;
                case EDIT:
                    if (object instanceof LearningSession)
                        base.onEdit((LearningSession) object, callback);
                    else if (object instanceof LearningTitle)
                        base.onEdit((LearningTitle) object, callback);
                    else if (object instanceof LearningItem)
                        base.onEdit((LearningItem) object, callback);
                    else if (object instanceof QuizTitle)
                        base.onEdit((QuizTitle) object, callback);
                    else if (object instanceof QuizItem)
                        base.onEdit((QuizItem) object, callback);
                    else if (object instanceof QuizAnswer)
                        base.onEdit((QuizAnswer) object, callback);
                    break;
                case BACKSTACK:
                    base.onBackstack(object, callback);
            }
            sourceOrigin = null;
        }
    }

}
