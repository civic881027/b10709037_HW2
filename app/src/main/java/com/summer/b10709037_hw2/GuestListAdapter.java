package com.summer.b10709037_hw2;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.summer.b10709037_hw2.data.WaitlistContract;


public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {
    private Cursor mCursor;
    private Context mContext;
    private int color= Color.RED;

    public GuestListAdapter(Context context,Cursor cursor){
        this.mContext=context;
        this.mCursor=cursor;
    }

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.guest_list_item,parent,false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position))return;

        String name=mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME));
        int partySize=mCursor.getInt(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE));
        long id=mCursor.getLong(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry._ID));
        setupColor();
        holder.nameTextView.setText(name);
        holder.partySizeTextView.setText(String.valueOf(partySize));
        holder.partySizeTextView.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        holder.itemView.setTag(id);

    }
    public void setupColor(){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(mContext);
        String c=sharedPreferences.getString("color","red");
        switch (c){
            case("red"):{
                color=Color.RED;
                break;
            }
            case("green"):{
                color=Color.GREEN;
                break;
            }
            case("blue"):{
                color=Color.BLUE;
                break;
            }
        }

        Log.d("color",String.valueOf(color));
    }
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor cursor){
        if(mCursor!=null)mCursor.close();
        mCursor=cursor;
        if(cursor!=null){
            this.notifyDataSetChanged();
        }
    }

    class GuestViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView partySizeTextView;

        public GuestViewHolder(View itemView){
            super(itemView);
            nameTextView=(TextView)itemView.findViewById(R.id.name_text_view);
            partySizeTextView=(TextView)itemView.findViewById(R.id.party_size_text_view);
        }
    }

}
