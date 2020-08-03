package com.example.airport.Mechanic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airport.R;

import java.util.ArrayList;
import java.util.List;

public class CListAdapter extends RecyclerView.Adapter<CListAdapter.ListViewHolder> implements Filterable {
    Context mContext;
    List<CListItem> mData ;
    List<CListItem> mDataFiltered ;
    boolean isDark = false;
    private OnCListListner mOnCListListner;


    public CListAdapter(Context mContext, List<CListItem> mData, boolean isDark, OnCListListner onCListListner) {
        this.mContext = mContext;
        this.mData = mData;
        this.isDark = isDark;
        this.mDataFiltered = mData;
        this.mOnCListListner= onCListListner;
        
    }

    public CListAdapter(Context mContext, List<CListItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;

    }

    public CListAdapter(Context applicationContext, List<CListItem> mData, boolean isDark, View.OnClickListener onClickListener) {
    }


    @NonNull
    @Override
    public CListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_list,viewGroup,false);
        return new CListAdapter.ListViewHolder(layout,mOnCListListner);
    }

    @Override
    public void onBindViewHolder(@NonNull CListAdapter.ListViewHolder ListViewHolder, int position) {

        // bind data here

        // we apply animation to views here
        // first lets create an animation for user photo
        ListViewHolder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        ListViewHolder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));



        ListViewHolder.tv_title.setText(mDataFiltered.get(position).getTitle());
        ListViewHolder.tv_content.setText(mDataFiltered.get(position).getContent());
        ListViewHolder.tv_date.setText(mDataFiltered.get(position).getDate());
        ListViewHolder.img_user.setImageResource(mDataFiltered.get(position).getUserPhoto());



    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    mDataFiltered = mData ;

                }
                else {
                    List<CListItem> lstFiltered = new ArrayList<>();
                    for (CListItem row : mData) {

                        if (row.getTitle().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }

                    }

                    mDataFiltered = lstFiltered;

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values= mDataFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                mDataFiltered = (List<CListItem>) results.values;
                notifyDataSetChanged();

            }
        };




    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



        TextView tv_title,tv_content,tv_date;
        ImageView img_user;
        RelativeLayout container;


        OnCListListner onCListListner;


        public ListViewHolder(@NonNull View itemView, OnCListListner onCListListner) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_description);
            tv_date = itemView.findViewById(R.id.tv_date);
            img_user = itemView.findViewById(R.id.img_user);
            
            this.onCListListner = onCListListner;
            
            itemView.setOnClickListener(this);


            if (isDark) {
                setDarkTheme();
            }
        }
        


        private void setDarkTheme() {

            container.setBackgroundResource(R.drawable.card_bg_dark);

        }


        @Override
        public void onClick(View view) {
            onCListListner.onCListClick(getAdapterPosition());
        }
    }
    public interface OnCListListner{
        void onCListClick(int position);
    }
}
