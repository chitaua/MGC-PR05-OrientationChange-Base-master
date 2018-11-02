package es.iessaladillo.pedrojoya.pr05.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;

public class MainActivityViewModel extends ViewModel {

    @NonNull
    private Avatar avatar = new Avatar(1 , "ME");

    @NonNull
    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(@NonNull Avatar avatar) {
        this.avatar = avatar;
    }
}
