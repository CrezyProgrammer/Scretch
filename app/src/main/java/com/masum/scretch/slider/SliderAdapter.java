package com.masum.scretch.slider;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.masum.scretch.R;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.MyViewHolder> {


    private ArrayList<Integer> IMAGES;
    private Context context;
    private ArrayList<String> arrayList = new ArrayList<>();

    public SliderAdapter(Context context, ArrayList<Integer> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.slidingimages_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.imageView.setImageResource(IMAGES.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("123321", "postion:"+position);
                Toast.makeText(holder.imageView.getContext(), position+" Clicked ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return IMAGES.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}