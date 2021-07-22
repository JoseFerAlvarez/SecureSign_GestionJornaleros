package com.example.securesign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecycledSeason extends RecyclerView.Adapter<AdapterRecycledSeason.ViewHolder>
    implements View.OnClickListener {

    ArrayList<ObjectSeason> listTemporadas;
    private View.OnClickListener listener;

    public AdapterRecycledSeason(ArrayList<ObjectSeason> listTemporadas){
        this.listTemporadas=listTemporadas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_season, parent, false);

        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_nombreTemporada.setText(listTemporadas.get(position).getsNombreTemporada());
        holder.txt_fechaInicio.setText(listTemporadas.get(position).getsFechaInicio());
        holder.txt_fechaFin.setText(listTemporadas.get(position).getsFechaFin());
    }

    @Override
    public int getItemCount() {
        return listTemporadas.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nombreTemporada;
        TextView txt_fechaInicio;
        TextView txt_fechaFin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_nombreTemporada=itemView.findViewById(R.id.txt_nombreTemporada);
            txt_fechaInicio=itemView.findViewById(R.id.txt_fechaInicio);
            txt_fechaFin=itemView.findViewById(R.id.txt_fechaFin);

        }

    }
}
