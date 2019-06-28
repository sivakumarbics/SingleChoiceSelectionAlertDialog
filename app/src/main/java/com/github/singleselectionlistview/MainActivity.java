package com.github.singleselectionlistview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    TextView text_person_name,
            text_select_person;
    private String[] NameArray = {"Alice","Bob","Catherine","Daniel","Elizabeth"};
    SharedPreferences shared_pref;
    SharedPreferences.Editor shared_pref_edit;
    String shared_pref_selectedPerson;
    int Position;
    String selectedItem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_person_name = findViewById(R.id.text_person_name);
        text_select_person = findViewById(R.id.text_select_person);

        shared_pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //At first we are Removing the SharedPreference
        shared_pref.edit().remove("selectPerson").apply();

        text_select_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NameArray != null && NameArray.length > 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Select the Persons");
                    int selectedIndex = -1;
                    shared_pref_selectedPerson = shared_pref.getString("selectPerson", "");
                    if (shared_pref_selectedPerson != null) {
                        for (int i = 0; i < NameArray.length; i++) {
                            if (NameArray[i].equals(shared_pref_selectedPerson)) {
                                selectedIndex = i;
                                break;
                            }
                        }
                    }

                    builder.setSingleChoiceItems(NameArray, selectedIndex, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedItem = Arrays.asList(NameArray).get(which);
                            //Getting the selected Person Index Position
                            Position = which;
                        }
                    });
                    builder.setCancelable(false);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            //here we are adding the selected Person to Shared Preference
                            text_select_person.setText(selectedItem);
                            shared_pref_edit = shared_pref.edit();
                            shared_pref_edit.putString("selectPerson", selectedItem);
                            shared_pref_edit.apply();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog mDialog = builder.create();
                    mDialog.show();

                } else {
                    Toast.makeText(getApplicationContext(), "No Persons Found", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        shared_pref.edit().remove("selectPerson").apply();
    }
}
