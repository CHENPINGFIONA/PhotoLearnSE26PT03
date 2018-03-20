package sg.edu.nus.se26pt03.photolearn.application;

/**
 * Created by MyatMin on 17/3/18.
 */

public class UserActionCallback implements IUserActionCallback {
    private  UserActionEmitter userActionEmitter;
    @Override
    public void onReject() {

    }

    @Override
    public void onPass() {

    }

    public final UserActionEmitter getUserActionEmitter() {
        return userActionEmitter;
    }

    public UserActionCallback() {
    }

    public UserActionCallback(UserActionEmitter userActionEmitter) {
        this.userActionEmitter = userActionEmitter;
    }
}
