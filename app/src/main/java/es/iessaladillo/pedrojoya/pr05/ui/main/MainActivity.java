package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.ui.avatar.AvatarActivity;
import es.iessaladillo.pedrojoya.pr05.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr05.utils.ValidationUtils;

@SuppressWarnings("WeakerAccess")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MainActivityViewModel viewModel;

    public static final int RC_OTRA = 50;

    private Avatar avatar = Database.getInstance().getDefaultAvatar();
    private ImageView imgAvatar;
    private TextView lblAvatar;
    private TextView lblName;
    private EditText txtName;
    private TextView lblEmail;
    private EditText txtEmail;
    private ImageView imgEmail;
    private TextView lblPhonenumber;
    private EditText txtPhonenumber;
    private ImageView imgPhonenumber;
    private TextView lblAddress;
    private EditText txtAddress;
    private ImageView imgAddress;
    private TextView lblWeb;
    private EditText txtWeb;
    private ImageView imgWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        setupViews();
        initAvatar(avatar);
        lblAvatar.setOnClickListener(v -> {
            changeAvatar();
        });
        imgAvatar.setOnClickListener(v -> changeAvatar());
        onFocus();
        addChangeListener();
        txtWeb.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                save();
                return true;
            }
            return false;
        });

    }

    private void setupViews() {
        imgAvatar = ActivityCompat.requireViewById(this, R.id.imgAvatar);
        lblAvatar = ActivityCompat.requireViewById(this, R.id.lblAvatar);
        lblName = ActivityCompat.requireViewById(this, R.id.lblName);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        lblPhonenumber = ActivityCompat.requireViewById(this, R.id.lblPhonenumber);
        txtPhonenumber = ActivityCompat.requireViewById(this, R.id.txtPhonenumber);
        imgPhonenumber = ActivityCompat.requireViewById(this, R.id.imgPhonenumber);
        lblEmail = ActivityCompat.requireViewById(this, R.id.lblEmail);
        txtEmail = ActivityCompat.requireViewById(this, R.id.txtEmail);
        imgEmail = ActivityCompat.requireViewById(this, R.id.imgEmail);
        lblAddress = ActivityCompat.requireViewById(this, R.id.lblAddress);
        txtAddress = ActivityCompat.requireViewById(this, R.id.txtAddress);
        imgAddress = ActivityCompat.requireViewById(this, R.id.imgAddress);
        lblWeb = ActivityCompat.requireViewById(this, R.id.lblWeb);
        txtWeb = ActivityCompat.requireViewById(this, R.id.txtWeb);
        imgWeb = ActivityCompat.requireViewById(this, R.id.imgWeb);
        imgEmail.setOnClickListener(this);
        imgPhonenumber.setOnClickListener(this);
        imgAddress.setOnClickListener(this);
        imgWeb.setOnClickListener(this);
    }


    // DO NOT TOUCH
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // DO NOT TOUCH
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Checks if form is valid or not and shows a Snackbar accordingly
     **/

    private void initAvatar(Avatar avatar) {
        viewModel.setAvatar(avatar);
        imgAvatar.setImageResource(avatar.getImageResId());
        imgAvatar.setTag(avatar.getImageResId());
        lblAvatar.setText(avatar.getName());
    }

    private void changeAvatar() {
        Intent intent = new Intent(MainActivity.this, AvatarActivity.class);
        intent.putExtra(AvatarActivity.EXTRA_AVATAR, (Parcelable) avatar);
        initAvatar(avatar);
        startActivityForResult(intent, RC_OTRA);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_OTRA) {
            if (data != null && data.hasExtra(AvatarActivity.EXTRA_AVATAR)) {
                avatar = data.getParcelableExtra(AvatarActivity.EXTRA_AVATAR);
                initAvatar(avatar);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.imgEmail:
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + txtEmail.getText().toString()));
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.imgPhonenumber:
                intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:(+34)" + txtPhonenumber.getText().toString()));
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.imgAddress:
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?" + txtAddress.getText().toString()));
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.imgWeb:
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(txtWeb.getText().toString()));
                if (!ValidationUtils.isValidUrl(txtWeb.getText().toString())) {
                    txtWeb.setError(getString(R.string.main_invalid_data));
                    lblWeb.setEnabled(false);
                    imgWeb.setEnabled(false);
                } else {
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

    }

    private void onFocus() {
        txtName.setOnFocusChangeListener((v, hasFocus) -> {
            setLblToBold(hasFocus, lblName);
        });
        txtEmail.setOnFocusChangeListener((v, hasFocus) -> {
            setLblToBold(hasFocus, lblEmail);
        });
        txtAddress.setOnFocusChangeListener((v, hasFocus) -> {
            setLblToBold(hasFocus, lblAddress);
        });
        txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> setLblToBold(hasFocus, lblPhonenumber));
        txtWeb.setOnFocusChangeListener((v, hasFocus) -> setLblToBold(hasFocus, lblWeb));
    }

    private void setLblToBold(boolean hasFocus, TextView lbl) {
        if (hasFocus)
            lbl.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        else
            lbl.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
    }

    private void addChangeListener() {

        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txtName.getText().toString().isEmpty()) {
                    txtName.setError(getString(R.string.main_invalid_data));
                    lblName.setEnabled(false);
                } else {
                    lblName.setEnabled(true);
                }
            }
        });


        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!ValidationUtils.isValidEmail(txtEmail.getText().toString())) {
                    txtEmail.setError(getString(R.string.main_invalid_data));
                    lblEmail.setEnabled(false);
                    imgEmail.setEnabled(false);
                } else {
                    lblEmail.setEnabled(true);
                    imgEmail.setEnabled(true);
                }
            }
        });

        txtPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!ValidationUtils.isValidPhone(txtPhonenumber.getText().toString())) {
                    txtPhonenumber.setError(getString(R.string.main_invalid_data));
                    lblPhonenumber.setEnabled(false);
                    imgPhonenumber.setEnabled(false);
                } else {
                    lblPhonenumber.setEnabled(true);
                    imgPhonenumber.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtAddress.getText().toString().isEmpty()) {
                    txtAddress.setError(getString(R.string.main_invalid_data));
                    lblAddress.setEnabled(false);
                    imgAddress.setEnabled(false);
                } else {
                    lblAddress.setEnabled(true);
                    imgAddress.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtWeb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!ValidationUtils.isValidUrl(txtWeb.getText().toString())) {
                    txtWeb.setError(getString(R.string.main_invalid_data));
                    lblWeb.setEnabled(false);
                    imgWeb.setEnabled(false);
                } else {
                    lblWeb.setEnabled(true);
                    imgWeb.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void save() {
        String message;
        boolean valid = checkAllFields();

        if (valid) {
            message = getString(R.string.main_saved_succesfully);
        } else {
            message = getString(R.string.main_error_saving);
        }


        KeyboardUtils.hideSoftKeyboard(this);
        Snackbar.make(lblName, message, Snackbar.LENGTH_LONG).show();

    }

    private boolean checkAllFields() {
        boolean valid = true;
        if (txtName.getText().toString().isEmpty()) {
            txtName.setError(getString(R.string.main_invalid_data));
            lblName.setEnabled(false);
            valid = false;
        }
        if (!ValidationUtils.isValidEmail(txtEmail.getText().toString())) {
            txtEmail.setError(getString(R.string.main_invalid_data));
            lblEmail.setEnabled(false);
            imgEmail.setEnabled(false);
            valid = false;
        }
        if (!ValidationUtils.isValidPhone(txtPhonenumber.getText().toString())) {
            txtPhonenumber.setError(getString(R.string.main_invalid_data));
            lblPhonenumber.setEnabled(false);
            imgPhonenumber.setEnabled(false);
            valid = false;
        }
        if (txtAddress.getText().toString().isEmpty()) {
            txtAddress.setError(getString(R.string.main_invalid_data));
            lblAddress.setEnabled(false);
            imgAddress.setEnabled(false);
            valid = false;
        }
        if (!ValidationUtils.isValidUrl(txtWeb.getText().toString())) {
            txtWeb.setError(getString(R.string.main_invalid_data));
            lblWeb.setEnabled(false);
            imgWeb.setEnabled(false);
            valid = false;
        }
        return valid;
    }

}
