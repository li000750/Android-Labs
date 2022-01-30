package com.cst2335.li000750;

import static com.google.android.material.snackbar.Snackbar.LENGTH_LONG;
import static com.google.android.material.snackbar.Snackbar.make;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private Button clickHere;
    private Switch switchOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickHere = findViewById(R.id.button);
        clickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,getResources().getString(R.string.toast_message),
                        Toast.LENGTH_LONG).show();
            }
        });


        switchOnOff = findViewById(R.id.switch1);
        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){

                if (isChecked){
                    Snackbar.make(buttonView,getResources().getString(R.string.switchOn), LENGTH_LONG).setAction("Undo",click->buttonView.setChecked(!isChecked)).show();
                }
                else{
                    Snackbar.make(buttonView,getResources().getString(R.string.switchOff), LENGTH_LONG).setAction("Undo",click->buttonView.setChecked(!isChecked)).show();
                }
            }
        });


    }
}