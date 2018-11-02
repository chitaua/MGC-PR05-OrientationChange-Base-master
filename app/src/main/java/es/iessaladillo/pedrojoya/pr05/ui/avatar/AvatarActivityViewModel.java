package es.iessaladillo.pedrojoya.pr05.ui.avatar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;

public class AvatarActivityViewModel extends ViewModel {

    @NonNull
    private Avatar avatar;

    @NonNull
    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(@NonNull Avatar avatar) {
        this.avatar = avatar;
    }
}
