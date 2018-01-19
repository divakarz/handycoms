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




public class HandyLanguageAdapter extends RecyclerView.Adapter<HandyLanguageAdapter.HandyLanguageViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    public HandyLanguageAdapter(Context context, Cursor cursor){
        mContext =context;
        mCursor = cursor;
    }

    public class HandyLanguageViewHolder extends RecyclerView.ViewHolder{

        public TextView nameText;


        public HandyLanguageViewHolder(View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.txtLanguageName_item);

        }
    }

    @Override
    public HandyLanguageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.language_items,parent,false);
        return new HandyLanguageViewHolder(view);
    }





    @Override
    public void onBindViewHolder(HandyLanguageViewHolder holder, final int position) {

        if(!mCursor.moveToPosition(position)){
            return;
        }

        String name= mCursor.getString(mCursor.getColumnIndex(HandyContracts.Language.COLUMN_NAME));
        Long id = mCursor.getLong(mCursor.getColumnIndex(HandyContracts.Language._ID));

        holder.nameText.setText(name);
        holder.itemView.setTag(id);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCursor.moveToPosition(position);


                SharedPreferences sp = mContext.getSharedPreferences(com.handycoms.HandyContracts.LANGUAGE_PREFERENCES ,Context.MODE_PRIVATE);
                sp.edit().putString(com.handycoms.HandyContracts.LANGUAGE_NAME,mCursor.getString(mCursor.getColumnIndex(HandyContracts.Language.COLUMN_NAME))).commit();
                sp.edit().putString(com.handycoms.HandyContracts.LANGUAGE_ID,mCursor.getString(mCursor.getColumnIndex(HandyContracts.Language._ID))).commit();

                Intent intent = new Intent(mContext, LanguageCommands.class);
                intent.putExtra("name",  mCursor.getString(mCursor.getColumnIndex(HandyContracts.Language.COLUMN_NAME)));
                intent.putExtra("id",  mCursor.getString(mCursor.getColumnIndex(HandyContracts.Language._ID)));
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
