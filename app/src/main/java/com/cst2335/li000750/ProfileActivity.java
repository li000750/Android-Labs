package com.cst2335.li000750;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;


public class ProfileActivity extends AppCompatActivity {

    public static final String TAG="PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.e(TAG, "onCreate");
        //take pic
        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(click ->{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                myPictureTakerLauncher.launch(takePictureIntent);
            }
        });

        //display email
        EditText emailEditText = findViewById(R.id.enterEmail);
        Intent fromMain = getIntent();
        //fromMain.getStringExtra("EMAIL");
        emailEditText.setText(fromMain.getStringExtra(MainActivity.storeEmail));

        //go to chat room
        Button goToChatroom = findViewById(R.id.goToChatroom);
        goToChatroom.setOnClickListener(click -> {
                Intent gotoChatroom = new Intent(ProfileActivity.this,ChatRoomActivity.class);
                startActivity(gotoChatroom);
        });

        Button goToToolbar=findViewById(R.id.goToToolbar);
        goToToolbar.setOnClickListener(v->{
            Intent goToToolbarPage=new Intent(ProfileActivity.this,TestToolbar.class);
            startActivity(goToToolbarPage);
        });
    }
    ActivityResultLauncher<Intent> myPictureTakerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            ,new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap imgbitmap = (Bitmap) data.getExtras().get("data");
                        ImageView imgView;
                        imgView=(ImageView)findViewById(R.id.imageButton);
                        imgView.setImageBitmap(imgbitmap);
                    }
                    else if(result.getResultCode() == Activity.RESULT_CANCELED)
                        Log.i(TAG, "User refused to capture a picture.");
                }
            } );

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }
}