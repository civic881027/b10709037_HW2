package com.summer.b10709037_hw2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.summer.b10709037_hw2.data.WaitlistContract;
import com.summer.b10709037_hw2.data.WaitlistDbHelper;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    private GuestListAdapter mAdapter;
    private SQLiteDatabase mDb;
    private AlertDialog.Builder dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupSharedPref();
        setupDialog();
        RecyclerView waitlistRecyclerView;
        waitlistRecyclerView=(RecyclerView)this.findViewById(R.id.all_guests_list_view);
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        WaitlistDbHelper dbHelper=new WaitlistDbHelper(this);
        mDb=dbHelper.getWritableDatabase();
        Cursor cursor=getAllguest();
        mAdapter=new GuestListAdapter(this,cursor);
        waitlistRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final long id=(long)viewHolder.itemView.getTag();
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeGuest(id);
                        mAdapter.swapCursor(getAllguest());
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.swapCursor(getAllguest());
                    }
                });
                dialog.show();

            }
        }).attachToRecyclerView(waitlistRecyclerView);
    }
    public void setupDialog(){
        dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Warring!");
        dialog.setMessage("Are you sure to delete data?");
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.waitlist_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            Intent startSettingActivity=new Intent(this,SettingActivity.class);
            startActivity(startSettingActivity);
            return true;
        }else if(id==R.id.action_add){
            Intent intent=new Intent(this,AddActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private Cursor getAllguest(){
        return mDb.query(WaitlistContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP);
    }
    private boolean removeGuest(long id){
        return mDb.delete(WaitlistContract.WaitlistEntry.TABLE_NAME, WaitlistContract.WaitlistEntry._ID+"="+id,null)>0;
    }
    private long addNewGuest(String name,int partySize){
        ContentValues cv = new ContentValues();
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME,name);
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE,partySize);
        return mDb.insert(WaitlistContract.WaitlistEntry.TABLE_NAME,null,cv);
    }

    @Override
    protected void onResume() {
        Intent intent=getIntent();
        String name=new String();
        int partySize=0;
        try{
            name=intent.getStringExtra("guestName");
            partySize=intent.getIntExtra("partySize",0);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(name!=null && partySize!=0){
            addNewGuest(name,partySize);
        }
        super.onResume();
    }

    private void setupSharedPref(){
        SharedPreferences sharedPref= PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals("red")){

        }
    }
}
