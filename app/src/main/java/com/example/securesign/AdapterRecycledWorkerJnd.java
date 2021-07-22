package com.example.securesign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecycledWorkerJnd extends RecyclerView.Adapter<AdapterRecycledWorkerJnd.ViewHolder>
    implements View.OnClickListener{

    ArrayList<ObjectWorker> listJndTrabajadores;
    private View.OnClickListener listener;

    public AdapterRecycledWorkerJnd(ArrayList<ObjectWorker> listJndTrabajadores){
        this.listJndTrabajadores = listJndTrabajadores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jndworker, parent, false);

        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_JndNomApeTrabajador.setText(listJndTrabajadores.get(position).getsNombreTrabajador() + " "
        + listJndTrabajadores.get(position).getsApellido());
        holder.txt_JndDniTrabajador.setText(listJndTrabajadores.get(position).getsDNI());
    }

    @Override
    public int getItemCount() {
        return listJndTrabajadores.size();
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
        TextView txt_JndNomApeTrabajador;
        TextView txt_JndDniTrabajador;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_JndNomApeTrabajador=(TextView)itemView.findViewById(R.id.txt_JndNomApeTrabajador);
            txt_JndDniTrabajador=(TextView)itemView.findViewById(R.id.txt_JndHoraEntrada);
        }
    }
}
