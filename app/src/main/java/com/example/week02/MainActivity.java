package com.example.week02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //class variables
    private Context context;
    private int duration = Toast.LENGTH_SHORT;

    //PLUMBING: Pairing GUI controls with Java objects
    private EditText txtColorSelected;
    private Button btnExit;
    private TextView txtViewSpy;
    private LinearLayout myScreen;
    private String PREFNAME = "myPrefFile1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnExit = (Button) findViewById(R.id.button);
        txtColorSelected = (EditText) findViewById(R.id.editTextColor);
        txtViewSpy = (TextView) findViewById(R.id.textViewSpy);
        myScreen = (LinearLayout) findViewById(R.id.myScreen1);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtColorSelected.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /* nothing TODO, needed by interface */
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /* nothing TODO, needed by interface */
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String chosenColor = editable.toString().toLowerCase(Locale.US);
                txtViewSpy.setText(chosenColor);
                setBackgroundColor(chosenColor, myScreen);
            }
        });

        context = getApplicationContext();
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
    } //onCreate
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(context, "onDestroy", duration).show();
    }
    
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences myFile1 = getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = myFile1.edit();
        String temp = txtColorSelected.getText().toString();
        myEditor.putString("mydata", temp);
        myEditor.commit();

        Toast.makeText(context, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(context, "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences myFile = getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        if ( (myFile != null) && (myFile.contains("mydata")) ) {
            String temp = myFile.getString("mydata", "");
            txtColorSelected.setText(temp);
        }

        Toast.makeText(context, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //if appropriate, change background color to chosen value
        updateMeUsingSavedStateData();
        Toast.makeText(context, "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(context, "onStop", Toast.LENGTH_SHORT).show();
    }

    private void setBackgroundColor(String chosenColor, LinearLayout myScreen) {
        if (chosenColor.contains("cuong")) myScreen.setBackgroundColor(getColor(R.color.red));
        else if (chosenColor.contains("hau")) myScreen.setBackgroundColor(getColor(R.color.green));
        else if (chosenColor.contains("huy")) myScreen.setBackgroundColor(getColor(R.color.blue));
        else if (chosenColor.contains("toan")) myScreen.setBackgroundColor(getColor(R.color.yellow));
        else myScreen.setBackgroundColor(getColor(R.color.white));
    }

    private void saveStateData(String chosenColor) {
        SharedPreferences myPrefContainer = getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor myPrefEditor = myPrefContainer.edit();
        String key = "chosenBackgroundColor", value = txtViewSpy.getText().toString();
        myPrefEditor.putString(key, value);
        myPrefEditor.commit();
    }

    private void updateMeUsingSavedStateData() {
        SharedPreferences myPrefContainer = getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        String key ="chosenBackgroundColor";
        String defaultValue = "white";
        if (( myPrefContainer != null ) && myPrefContainer.contains(key)){
            String color = myPrefContainer.getString(key, defaultValue);
            setBackgroundColor(color, myScreen);
        }
    }

}