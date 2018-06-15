package com.domain.navigationdrawer.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.domain.navigationdrawer.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter implements View.OnClickListener  {

    public interface MyRecyclerListener{
        public void OnClick(View view, int position);
    }

    private Context context;
    private ArrayList<String> objects;
    private MyRecyclerListener listener;

    public MyRecyclerViewAdapter(Context context, ArrayList<String> objects){
        this.context = context;
        this.objects = objects;
    }

    public void setOnClickListener(MyRecyclerListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        Log.d("App", "onClick");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View layoutView = View.inflate(context, R.layout.layout_recycler, null);
        final MyObjectViewHolder holder = new MyObjectViewHolder(layoutView);
        layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                listener.OnClick(view, position);
                Log.d("App", "onClick: " + position);
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyObjectViewHolder)holder).setText(this.objects.get(position));
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    static class MyObjectViewHolder extends RecyclerView.ViewHolder{

        private TextView myTextView;

        public MyObjectViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.myTextView);
        }

        public void setText(String value){
            myTextView.setText(value);
        }

    }

}


















