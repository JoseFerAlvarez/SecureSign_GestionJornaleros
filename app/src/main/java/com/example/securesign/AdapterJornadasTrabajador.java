package com.example.securesign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterJornadasTrabajador extends RecyclerView.Adapter<AdapterJornadasTrabajador.ViewHolder> {

    ArrayList<ObjectJornadas> listaJornadas;

    public AdapterJornadasTrabajador(ArrayList<ObjectJornadas> listaJornadas){
        this.listaJornadas=listaJornadas;
    }

    String euro=" \u20AC";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jornadas, parent, false);

        return new AdapterJornadasTrabajador.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String [] partes=listaJornadas.get(position).getsHoras().split(":");
        String sHoras=partes[0];
        String sMinutos=partes[1];

        String tiempo;

        if(sMinutos.length()==1){
            sMinutos="" + 0 + sMinutos;
        }

        tiempo=sHoras + ":" + sMinutos;

        holder.txt_JndFecha.setText(listaJornadas.get(position).getsFecha());
        holder.txt_JndHoraEntrada.setText(listaJornadas.get(position).getsHoraEntrada());
        holder.txt_JndHoraSalida.setText(listaJornadas.get(position).getsHoraSalida());
        holder.txt_JndHoras.setText(tiempo);
        holder.txt_JndDinero.setText(listaJornadas.get(position).getfDinero() + euro);
    }

    @Override
    public int getItemCount() {
        return listaJornadas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_JndFecha;
        TextView txt_JndHoraEntrada;
        TextView txt_JndHoraSalida;
        TextView txt_JndHoras;
        TextView txt_JndDinero;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_JndFecha=(TextView)itemView.findViewById(R.id.txt_JndFecha);
            txt_JndHoraEntrada=(TextView)itemView.findViewById(R.id.txt_JndHoraEntrada);
            txt_JndHoraSalida=(TextView)itemView.findViewById(R.id.txt_JndHoraSalida);
            txt_JndHoras=(TextView)itemView.findViewById(R.id.txt_JndHoras);
            txt_JndDinero=(TextView)itemView.findViewById(R.id.txt_JndDinero);
        }
    }
}
