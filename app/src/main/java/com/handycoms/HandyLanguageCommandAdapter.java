package com.handycoms;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.handycoms.HandyContracts.LANGUAGE_NAME;


public class HandyLanguageCommandAdapter extends RecyclerView.Adapter<HandyLanguageCommandAdapter.HandyLanguageCommandViewHolder> {

    private Context mContext;
    private Cursor mCursor;


    public HandyLanguageCommandAdapter(Context context, Cursor cursor ){
        mContext =context;
        mCursor = cursor;


    }



    public class HandyLanguageCommandViewHolder extends RecyclerView.ViewHolder{

        public TextView commandnameText;
        public TextView commanddescriptionText;

        public HandyLanguageCommandViewHolder(View itemView) {
            super(itemView);

            commandnameText = itemView.findViewById(R.id.txtLanguageCommandName_item);
            commanddescriptionText = itemView.findViewById(R.id.txtLanguageCommandDesc_item);

        }
    }

    @Override
    public HandyLanguageCommandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.command_items,parent,false);
        return new HandyLanguageCommandViewHolder(view);
    }





    @Override
    public void onBindViewHolder(HandyLanguageCommandViewHolder holder, final int position) {

        if(!mCursor.moveToPosition(position)){
            return;
        }

        String commandname= mCursor.getString(mCursor.getColumnIndex(HandyContracts.Commands.COLUMN_NAME));
        String commnddescription = mCursor.getString(mCursor.getColumnIndex(HandyContracts.Commands.COLUMN_DESCRIPTION));
        Long id = mCursor.getLong(mCursor.getColumnIndex(HandyContracts.Commands._ID));


        holder.commandnameText.setText(commandname);
        holder.commanddescriptionText.setText(commnddescription);
        holder.itemView.setTag(id);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCursor.moveToPosition(position);

                Intent intent = new Intent(mContext, View_Command.class);
                intent.putExtra("commandname",  mCursor.getString(mCursor.getColumnIndex(HandyContracts.Commands.COLUMN_NAME)));
                intent.putExtra("commanddescription",  mCursor.getString(mCursor.getColumnIndex(HandyContracts.Commands.COLUMN_DESCRIPTION)));
                intent.putExtra("id",  mCursor.getString(mCursor.getColumnIndex(HandyContracts.Commands.COLUMN_LANGUAGEID)));
                intent.putExtra("id",  mCursor.getString(mCursor.getColumnIndex(HandyContracts.Commands._ID)));
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor)
    {
        if(mCursor != null){
            mCursor.close();
        }

        mCursor = newCursor;

        if(newCursor != null){
            notifyDataSetChanged();
        }
    }





}
