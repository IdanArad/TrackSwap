package com.example.trackswap;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackswap.model.ModelTracks;
import com.example.trackswap.model.Track;

import java.util.List;


class TrackViewHolder extends RecyclerView.ViewHolder{
    TextView nameTv;
    TextView artistTv;
    CheckBox checkbox;
    ConstraintLayout row_linearlayout;
    RecyclerView rv2;
    List<Track> data;

    public TrackViewHolder(@NonNull View itemView, TrackRecyclerAdapter.OnItemClickListener listener) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.tracklistrow_name_tv);
        artistTv = itemView.findViewById(R.id.tracklistrow_id_tv);
        row_linearlayout=(ConstraintLayout)itemView.findViewById(R.id.row_linrLayout);
        rv2=(RecyclerView)itemView.findViewById(R.id.tracklistfrag_list);

        row_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
            }
        });
    }

    public void bind(Track track, int pos) {
        nameTv.setText(track.name);
        artistTv.setText(track.artist);
    }
}

public class TrackRecyclerAdapter extends RecyclerView.Adapter<TrackViewHolder>{
    int row_index = -1;
    OnItemClickListener listener;
    public static interface OnItemClickListener{
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Track> data;
    public TrackRecyclerAdapter(LayoutInflater inflater, List<Track> data) {
        this.inflater = inflater;
        this.data = data;
    }

    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.track_list_row,parent,false);
        return new TrackViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Track track = data.get(position);
        holder.bind(track,position);

        holder.row_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
            }
        });
        if(row_index==position){
            holder.row_linearlayout.setBackgroundColor(Color.parseColor("#567845"));
            holder.nameTv.setTextColor(Color.parseColor("#ffffff"));
            AddPostAction.instance().setName(holder.nameTv.getText().toString());
            AddPostAction.instance().setArtist(holder.artistTv.getText().toString());
        }
        else
        {
            holder.row_linearlayout.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.nameTv.setTextColor(Color.parseColor("#000000"));
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Track> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }
}

