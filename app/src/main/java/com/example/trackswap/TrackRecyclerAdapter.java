package com.example.trackswap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackswap.model.Track;

import java.util.List;


class TrackViewHolder extends RecyclerView.ViewHolder{
    TextView nameTv;
    TextView idTv;
    List<Track> data;
    public TrackViewHolder(@NonNull View itemView, TrackRecyclerAdapter.OnItemClickListener listener, List<Track> data) {
        super(itemView);
        this.data = data;
        nameTv = itemView.findViewById(R.id.tracklistrow_name_tv);
        idTv = itemView.findViewById(R.id.tracklistrow_id_tv);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(Track track, int pos) {
        nameTv.setText(track.name);
        idTv.setText(track.id);
    }
}

public class TrackRecyclerAdapter extends RecyclerView.Adapter<TrackViewHolder>{
    OnItemClickListener listener;
    public static interface OnItemClickListener{
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Track> data;
    public TrackRecyclerAdapter(LayoutInflater inflater, List<Track> data){
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
        return new TrackViewHolder(view,listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Track track = data.get(position);
        holder.bind(track,position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}

