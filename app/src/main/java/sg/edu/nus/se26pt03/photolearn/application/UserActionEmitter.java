package sg.edu.nus.se26pt03.photolearn.application;

import android.util.Log;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizAnswer;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.EventType;

/**
 * Created by part time team 3  on 17/3/18.
 */

public class UserActionEmitter {

    private UserActionListener sourceOrigin = null;
    private  UserActionListener base = null;
    public UserActionEmitter(UserActionListener base) {
        this.base = base;
    }
    private boolean before = false;

    public final void dynamicEmit(final @EventType.Event int event, final boolean outbound, final Object object, final UserActionCallback callback, final UserActionListener source) {
        final UserActionCallback validatedCallback  = (callback != null ? callback : new UserActionCallback());
        if (outbound) {
            if (event != EventType.BEFORE && !before) {
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
                if (event != EventType.BEFORE ) {
                    before = false;
                }
                dymnamicRoute(event,outbound,object,validatedCallback,source);
            }
        }
        else {
            dymnamicRoute(event,outbound,object,validatedCallback,source);
        }

    }

    public final void dymnamicRoute(final @EventType.Event int event, boolean outbound, final Object object, final UserActionCallback callback , final UserActionListener source) {
        if (outbound) {
            boolean exist = false;
            List<UserActionListener> outBounds = base.getRelatives();
            UserActionCallback outboundCallback = new UserActionCallback(){
                int totalOutBound = outBounds.size();
                int totalInBound = 0;
                boolean rejected = false;
                @Override
                public void onReject() {
                    totalInBound = totalInBound + 1;
                    rejected = true;
                    callback.onReject();
                }

                @Override
                public void onPass() {
                    totalInBound = totalInBound + 1;
                    if (!rejected && totalOutBound - (sourceOrigin == null ? 0 : 1) == totalInBound) {
                        callback.onPass();
                    }
                }
            };
            for (UserActionListener outBound : outBounds) {
                if (outBound != sourceOrigin) {
                    exist = true;
                    switch (event) {
                        case EventType.BEFORE:
                            if (object instanceof Integer)
                                outBound.onBefore((int) object, outboundCallback, base);
                            break;
                        case EventType.APPMODE_CHANGE:
                            if (object instanceof Integer)
                                outBound.onAppModeChange((int) object, outboundCallback, base);
                            break;
                        case EventType.ACCESSMODE_CHANGE:
                            if (object instanceof Integer)
                                outBound.onAccessModeChange((int) object, outboundCallback, base);
                            break;
                        case EventType.LOGIN:
                            if (object instanceof User)
                                outBound.onLogIn((User) object, outboundCallback, base);
                            break;
                        case EventType.LOGOUT:
                            if (object instanceof User)
                                outBound.onLogOut((User) object, outboundCallback, base);
                            break;
                        case EventType.LOAD:
                            outBound.onLoad(object, outboundCallback, base);
                            break;
                        case EventType.CREATE:
                            outBound.onCreate(object, outboundCallback, base);
                            break;
                        case EventType.EDIT:
                            outBound.onEdit(object, outboundCallback, base);
                            break;
                        case EventType.SUMMARY:
                            outBound.onSummary(object, outboundCallback, base);
                            break;
                        case EventType.BACKSTACK:
                            outBound.onBackstack(object, outboundCallback, base);
                            break;
                    }
                }
            }
            if (!exist) callback.onPass();
        }
        else {
            sourceOrigin = source;
            switch (event) {
                case EventType.BEFORE:
                        before = true;
                    if (object instanceof Integer)
                        base.onBefore((int) object, callback);
                    break;
                case EventType.APPMODE_CHANGE:
                    if (object instanceof Integer)
                        base.onAppModeChange((int) object, callback);
                    break;
                case EventType.ACCESSMODE_CHANGE:
                    if (object instanceof Integer)
                        base.onAccessModeChange((int) object, callback);
                    break;
                case EventType.LOGIN:
                    if (object instanceof User)
                        base.onLogIn((User) object, callback);
                    break;
                case EventType.LOGOUT:
                    if (object instanceof User)
                        base.onLogOut((User) object, callback);
                    break;
                case EventType.LOAD:
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
                case EventType.CREATE:
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
                case EventType.EDIT:
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
                    case EventType.SUMMARY:
                        if (object instanceof QuizTitle)
                            base.onSummary((QuizTitle) object, callback);
                        break;
                case EventType.BACKSTACK:
                    base.onBackstack(object, callback);
            }
            sourceOrigin = null;
        }
    }

}
