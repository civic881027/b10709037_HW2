package com.summer.b10709037_hw2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.summer.b10709037_hw2.data.WaitlistContract;


public class AddActivity extends AppCompatActivity {
    private GuestListAdapter mAdapter;
    private SQLiteDatabase mDb;
    private EditText mNewGuestNameEditText;
    private EditText mNewPartySizeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mNewGuestNameEditText=(EditText)this.findViewById(R.id.person_name_edit_text);
        mNewPartySizeEditText=(EditText)this.findViewById(R.id.party_count_edit_text);
    }

    public void addToWaitlist(View view) {
        if (mNewPartySizeEditText.getText().length() == 0 || mNewGuestNameEditText.getText().length() == 0) {
            return;
        }
        int partySize = 1;
        String name="";
        try {
            partySize = Integer.parseInt(mNewPartySizeEditText.getText().toString());
            name=mNewGuestNameEditText.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }


        mNewGuestNameEditText.getText().clear();
        mNewPartySizeEditText.getText().clear();
        mNewPartySizeEditText.clearFocus();
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("guestName",name);
        intent.putExtra("partySize",partySize);
        startActivity(intent);
    }
    public void backToMain(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }




}
