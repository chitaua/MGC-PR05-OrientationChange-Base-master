package es.iessaladillo.pedrojoya.pr05.ui.avatar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.utils.ResourcesUtils;

public class AvatarActivity extends AppCompatActivity {

    AvatarActivityViewModel viewModel;

    @VisibleForTesting
    public static final String EXTRA_AVATAR = "EXTRA_AVATAR";
    public static final double IMGSELECTED = 0.5;

    private Avatar avatar;
    private List<Avatar> avatares;
    private Database database = Database.getInstance();
    private ImageView imgAvatar1;
    private ImageView imgAvatar2;
    private ImageView imgAvatar3;
    private ImageView imgAvatar4;
    private ImageView imgAvatar5;
    private ImageView imgAvatar6;
    private TextView lblAvatar1;
    private TextView lblAvatar2;
    private TextView lblAvatar3;
    private TextView lblAvatar4;
    private TextView lblAvatar5;
    private TextView lblAvatar6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        viewModel = ViewModelProviders.of(this).get(AvatarActivityViewModel.class);
        getIntentData();
        setupViews();
        initImageViews();
        //

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_avatar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_AVATAR)) {
            avatar = intent.getParcelableExtra(EXTRA_AVATAR);
            viewModel.setAvatar(avatar);
        }
    }

    private void setupViews() {
        avatares = database.queryAvatars();
        imgAvatar1 = ActivityCompat.requireViewById(this, R.id.imgAvatar1);
        imgAvatar2 = ActivityCompat.requireViewById(this, R.id.imgAvatar2);
        imgAvatar3 = ActivityCompat.requireViewById(this, R.id.imgAvatar3);
        imgAvatar4 = ActivityCompat.requireViewById(this, R.id.imgAvatar4);
        imgAvatar5 = ActivityCompat.requireViewById(this, R.id.imgAvatar5);
        imgAvatar6 = ActivityCompat.requireViewById(this, R.id.imgAvatar6);
        lblAvatar1 = ActivityCompat.requireViewById(this, R.id.lblAvatar1);
        lblAvatar2 = ActivityCompat.requireViewById(this, R.id.lblAvatar2);
        lblAvatar3 = ActivityCompat.requireViewById(this, R.id.lblAvatar3);
        lblAvatar4 = ActivityCompat.requireViewById(this, R.id.lblAvatar4);
        lblAvatar5 = ActivityCompat.requireViewById(this, R.id.lblAvatar5);
        lblAvatar6 = ActivityCompat.requireViewById(this, R.id.lblAvatar6);

    }

    private void initImageViews() {
        imgAvatar1.setImageResource(avatares.get(0).getImageResId());
        imgAvatar1.setTag(avatares.get(0).getImageResId());
        lblAvatar1.setText(avatares.get(0).getName());
        imgAvatar2.setImageResource(avatares.get(1).getImageResId());
        imgAvatar2.setTag(avatares.get(1).getImageResId());
        lblAvatar2.setText(avatares.get(1).getName());
        imgAvatar3.setImageResource(avatares.get(2).getImageResId());
        imgAvatar3.setTag(avatares.get(2).getImageResId());
        lblAvatar3.setText(avatares.get(2).getName());
        imgAvatar4.setImageResource(avatares.get(3).getImageResId());
        imgAvatar4.setTag(avatares.get(3).getImageResId());
        lblAvatar4.setText(avatares.get(3).getName());
        imgAvatar5.setImageResource(avatares.get(4).getImageResId());
        imgAvatar5.setTag(avatares.get(4).getImageResId());
        lblAvatar5.setText(avatares.get(4).getName());
        imgAvatar6.setImageResource(avatares.get(5).getImageResId());
        imgAvatar6.setTag(avatares.get(5).getImageResId());
        lblAvatar6.setText(avatares.get(5).getName());

        imgAvatar1.setOnClickListener(v -> selectImageView(imgAvatar1));
        imgAvatar2.setOnClickListener(v -> selectImageView(imgAvatar2));
        imgAvatar3.setOnClickListener(v -> selectImageView(imgAvatar3));
        imgAvatar4.setOnClickListener(v -> selectImageView(imgAvatar4));
        imgAvatar5.setOnClickListener(v -> selectImageView(imgAvatar5));
        imgAvatar6.setOnClickListener(v -> selectImageView(imgAvatar6));

    }

    private void imgSend(int index) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_AVATAR, avatares.get(index));
        setResult(RESULT_OK, intent);
        finish();
    }

    private void checkImgToSelect() {
        if (imgAvatar1.getAlpha() == IMGSELECTED) {
            imgSend(0);
        } else if (imgAvatar2.getAlpha() == IMGSELECTED) {
            imgSend(1);
        } else if (imgAvatar3.getAlpha() == IMGSELECTED) {
            imgSend(2);
        } else if (imgAvatar4.getAlpha() == IMGSELECTED) {
            imgSend(3);
        } else if (imgAvatar5.getAlpha() == IMGSELECTED) {
            imgSend(4);
        } else {
            imgSend(5);
        }

    }

    private void selectImageView(ImageView imageView) {
        viewModel.getAvatar();
        imgAvatar1.setAlpha(ResourcesUtils.getFloat(this, R.dimen.deselect_image_alpha));
        imgAvatar2.setAlpha(ResourcesUtils.getFloat(this, R.dimen.deselect_image_alpha));
        imgAvatar3.setAlpha(ResourcesUtils.getFloat(this, R.dimen.deselect_image_alpha));
        imgAvatar4.setAlpha(ResourcesUtils.getFloat(this, R.dimen.deselect_image_alpha));
        imgAvatar5.setAlpha(ResourcesUtils.getFloat(this, R.dimen.deselect_image_alpha));
        imgAvatar6.setAlpha(ResourcesUtils.getFloat(this, R.dimen.deselect_image_alpha));
        imageView.setAlpha(ResourcesUtils.getFloat(this, R.dimen.selected_image_alpha));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSelect) {
            checkImgToSelect();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
