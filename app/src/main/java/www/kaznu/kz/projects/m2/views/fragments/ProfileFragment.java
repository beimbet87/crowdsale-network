package www.kaznu.kz.projects.m2.views.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.user.RegistrationForm;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.models.User;
import www.kaznu.kz.projects.m2.views.activities.ChangeDataActivity;
import www.kaznu.kz.projects.m2.adapters.GenderTypeAdapter;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.utils.VolleyMultipartRequest;

import static android.app.Activity.RESULT_OK;
import static www.kaznu.kz.projects.m2.interfaces.Constants.BASE_URL;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView etUserName, etUserSurname;
    TextView etUserBirthday;
    EditText etUserPhone, etUserEmail;
    EditText etPassword;
    EditText etUserDescription;
    ImageView ivAvatar;
    Spinner spGender;
    LinearLayout llVerifyUser;

    Button btnSave;

    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST = 1;
    Calendar dateAndTime = Calendar.getInstance();

    CurrentUser currentUser;
    User user;

    RegistrationForm saveData;

    int userId;

    public interface DataFromProfileFragment {
        void FromProfileFragment(String data, int number);
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_profile, container, false);

        currentUser = new CurrentUser(requireContext());
        user = new User();

        etUserName = fv.findViewById(R.id.profile_name);
        etUserSurname = fv.findViewById(R.id.profile_surname);
        spGender = fv.findViewById(R.id.gender_type);
        etUserBirthday = fv.findViewById(R.id.profile_birthday);
        etUserPhone = fv.findViewById(R.id.profile_phone);
        etUserEmail = fv.findViewById(R.id.profile_email);
        etUserDescription = fv.findViewById(R.id.profile_description);
        etPassword = fv.findViewById(R.id.profile_password);
        ivAvatar = fv.findViewById(R.id.iv_profile_image);
        llVerifyUser = fv.findViewById(R.id.verify_user);
        btnSave = fv.findViewById(R.id.btn_save);

        etUserBirthday.setOnClickListener(this);
        etUserPhone.setOnClickListener(this);
        etUserEmail.setOnClickListener(this);
        etPassword.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        llVerifyUser.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        GenderTypeAdapter genderAdapter = new GenderTypeAdapter(requireContext());
        spGender.setAdapter(genderAdapter);

        String url = BASE_URL.concat(currentUser.getImageLink());

        Picasso.get().load(url).into(ivAvatar);

        etUserName.setText(currentUser.getName());
        etUserSurname.setText(currentUser.getSurname());
        etUserDescription.setText(currentUser.getDescription());

        if (currentUser.getGender() == 1) {
            spGender.setSelection(0);
        } else {
            spGender.setSelection(1);
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        if(currentUser.getBirth() != null && !currentUser.getBirth().equals("null")) {
            etUserBirthday.setText(Utils.parseDateWithDot(currentUser.getBirth()));
            try {
                Date date = format.parse(Utils.parseDateWithDot(currentUser.getBirth()));
                assert date != null;
                dateAndTime.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        etUserPhone.setText(currentUser.getPhone());
        etUserEmail.setText(currentUser.getEmail());

        userId = currentUser.getId();

        dataPasser.FromProfileFragment("Личная информация", 3);

        return fv;
    }

    private void showFileChooser() {
        if ((ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if (!(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && !(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Выбрать аватар"), PICK_IMAGE_REQUEST);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            String filePath = getPath(picUri);
            if (filePath != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), picUri);
                    uploadBitmap(bitmap);
                    ivAvatar.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(
                        requireContext(), "no image selected",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

    public String getPath(Uri uri) {
        Cursor cursor = requireActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = requireActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.URL_POST_IMAGE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(requireContext(), obj.getString("ResultMessage"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Logger.d("Error: " + error.getMessage());
                    }
                }) {


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("1", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("alt", "Test description");
                params.put("typeDocument", "2");
                params.put("refSource", String.valueOf(userId));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(requireContext()).add(volleyMultipartRequest);
    }

    DataFromProfileFragment dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (DataFromProfileFragment) context;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.profile_phone:
                intent = new Intent(requireContext(), ChangeDataActivity.class);
                intent.putExtra("fragment", 0);
                startActivity(intent);
                break;
            case R.id.verify_user:
            case R.id.profile_email:
                intent = new Intent(requireContext(), ChangeDataActivity.class);
                intent.putExtra("fragment", 1);
                startActivity(intent);
                break;
            case R.id.profile_password:
                intent = new Intent(requireContext(), ChangeDataActivity.class);
                intent.putExtra("fragment", 2);
                startActivity(intent);
                break;
            case R.id.profile_birthday:
                new DatePickerDialog(requireContext(), R.style.Dialog_Style_DatePicker, d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
                break;
            case R.id.iv_profile_image:
                showFileChooser();
                break;
            case R.id.btn_save:
                user.setName(etUserName.getText().toString());
                user.setSurname(etUserSurname.getText().toString());
                user.setBirth(Utils.parseDateTo(etUserBirthday.getText().toString()));
                user.setEmail(etUserEmail.getText().toString());
                user.setPassword(currentUser.getPassword());
                if(spGender.getSelectedItemPosition() == 0) {
                    user.setSex(1);
                } else {
                    user.setSex(0);
                }
                user.setDescription(etUserDescription.getText().toString());
                user.setPhone(etUserPhone.getText().toString());

                saveData = new RegistrationForm(requireContext(), requireActivity(), user, new Tokens(requireContext()).getAccessToken());
                saveData.setOnLoadListener(new RegistrationForm.CustomOnLoadListener() {
                    @Override
                    public void onComplete(int resultCode, String resultMessage) {
                        Toast.makeText(requireContext(), "Данные успешно сохранены!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String monthString = String.valueOf(monthOfYear+1);
            if (monthString.length() == 1) {
                monthString = "0" + monthString;
            }

            String dayString = String.valueOf(dayOfMonth);
            if (dayString.length() == 1) {
                dayString = "0" + dayString;
            }

            String birthDate = dayString + "." + monthString + "." + year;

            etUserBirthday.setText(birthDate);
        }
    };

}