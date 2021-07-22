package com.example.securesign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecycledWorker extends RecyclerView.Adapter<AdapterRecycledWorker.ViewHolder>
        implements View.OnClickListener {

    ArrayList<ObjectWorker> listTrabajadores;
    private View.OnClickListener listener;

    public AdapterRecycledWorker(ArrayList<ObjectWorker> listTrabajadores){
        this.listTrabajadores=listTrabajadores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_worker, parent, false);

        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycledWorker.ViewHolder holder, int position) {
        holder.txt_NomApeTrabajador.setText(listTrabajadores.get(position).getsNombreTrabajador() + " "
                + listTrabajadores.get(position).getsApellido());
        holder.txt_DniTrabajador.setText(listTrabajadores.get(position).getsDNI());
        holder.txt_TlfTrabajador.setText(listTrabajadores.get(position).getsTelefono());
    }

    @Override
    public int getItemCount() {
        return listTrabajadores.size();
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_NomApeTrabajador;
        TextView txt_DniTrabajador;
        TextView txt_TlfTrabajador;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_NomApeTrabajador=(TextView)itemView.findViewById(R.id.txt_JndNomApeTrabajador);
            txt_DniTrabajador=(TextView)itemView.findViewById(R.id.txt_JndHoraEntrada);
            txt_TlfTrabajador=(TextView)itemView.findViewById(R.id.txt_TlfTrabajador);
        }
    }
}
