package com.greatnorthcap.compass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/**
 * Created by aspiree15 on 11/07/17.
 */

public class UploadImageActivity extends AppCompatActivity {

    private UserSharedPref UserPref = new UserSharedPref();
    private static final int RESULT_BANK_STATEMENT_IMAGE_GALLERY = 1;
    private static final int RESULT_BANK_STATEMENT_IMAGE_CAMERA = 2;
    private Bitmap bitmap;
    private String KEY_IMAGE = "Image";
    private String KEY_TYPE = "Type";
    private String KEY_LOANID = "LoanID";
    TextView textViewUploadImage;
    ImageView imageViewUploadImage;
    Button buttonUploadImageGallery, buttonUploadImageCamera, buttonUploadImageUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadimage);

        SharedPreferences sharedPreferences = getSharedPreferences(UserPref.getSharedPrefName(), Context.MODE_PRIVATE);
        String type = sharedPreferences.getString(UserPref.getUploadtypeSharedPref(), "Not Available");
        textViewUploadImage = (TextView) findViewById(R.id.uploadimagetext);
        textViewUploadImage.setText(type);

        imageViewUploadImage = (ImageView) findViewById(R.id.uploadimageimageview);
        buttonUploadImageGallery = (Button) findViewById(R.id.uploadimagegallerybutton);
        buttonUploadImageCamera = (Button) findViewById(R.id.uploadimagecamerabutton);
        buttonUploadImageUpload = (Button) findViewById(R.id.uploadimageuploadbutton);

        buttonUploadImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bank_statement_gallery_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                bank_statement_gallery_intent.setType("image/*");
                startActivityForResult(bank_statement_gallery_intent, RESULT_BANK_STATEMENT_IMAGE_GALLERY);
            }
        });

        buttonUploadImageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bank_statement_camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(bank_statement_camera_intent, RESULT_BANK_STATEMENT_IMAGE_CAMERA);
            }
        });

        buttonUploadImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBankStatement();
            }
        });
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        //The below code will go and convert the BLOB to a String.
        //
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadBankStatement() {
        //final SharedPreferences sharedPreferences = getSharedPreferences(UserPref.getSharedPrefName(), Context.MODE_PRIVATE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserPref.getImageuploadUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UploadImageActivity.this, response, Toast.LENGTH_LONG).show();
                        finish();
                        Intent intent = new Intent(UploadImageActivity.this, UploadLoanImagesActivity.class);
                        //startActivity(new Intent(UploadImageActivity.this, UploadLoanImagesActivity.class));
                        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        //an Intent should be used to change pages after the user successfully uploaded their image.
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(UploadImageActivity.this, "Please Select An Image.", Toast.LENGTH_LONG).show();
                        //String type = sharedPreferences.getString(UserPref.getUploadtypeSharedPref(), "Not Available");
                        //Toast.makeText(UploadImageActivity.this, type, Toast.LENGTH_LONG).show();
                        //Toast.makeText(UploadImageActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences sharedPreferences = getSharedPreferences(UserPref.getSharedPrefName(), Context.MODE_PRIVATE);
                String image = getStringImage(bitmap);
                String type = sharedPreferences.getString(UserPref.getUploadtypeSharedPref(), "Not Available");
                String loanid = sharedPreferences.getString(UserPref.getSearchedloanidSharedPref(), "Not Available");
                //Toast.makeText(UploadImageActivity.this, image.toString(), Toast.LENGTH_LONG).show();
                Map<String,String> params = new Hashtable<>();
                params.put(KEY_IMAGE, image);
                params.put(KEY_TYPE, type);
                params.put(KEY_LOANID, loanid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_BANK_STATEMENT_IMAGE_GALLERY && resultCode == RESULT_OK && data!=null) {
            try {
                Uri selectedImage = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                //Toast.makeText(UploadImageActivity.this, selectedImage.toString(), Toast.LENGTH_LONG).show();
                imageViewUploadImage.setImageBitmap(bitmap);
            } catch(IOException ex) {
                throw new RuntimeException("The selected image size might be too large", ex);
            }
        }
        if(requestCode == RESULT_BANK_STATEMENT_IMAGE_CAMERA && resultCode == RESULT_OK && data!=null) {
            bitmap = (Bitmap)data.getExtras().get("data");
            imageViewUploadImage.setImageBitmap(bitmap);
        }
    }

}
