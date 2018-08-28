package com.idi_ap.and015p2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pau on 4/1/2017.
 */

public class ModificarAdapter extends RecyclerView.Adapter<ModificarAdapter.BookViewHolder> {

    List<Book> modifylist;
    public ModificarAdapter(List<Book> modifylist) {
        this.modifylist = modifylist;
    }
    @Override
    public ModificarAdapter.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.modif_item,parent, false);
        ModificarAdapter.BookViewHolder holder = new ModificarAdapter.BookViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ModificarAdapter.BookViewHolder holder, int position) {
        holder.titol.setText(modifylist.get(position).getTitle());
        holder.autor.setText(modifylist.get(position).getAuthor());
        holder.any.setText(Integer.toString(modifylist.get(position).getYear()));
        holder.categoria.setText(modifylist.get(position).getCategory());
        holder.editorial.setText(modifylist.get(position).getPublisher());
        holder.valoracio.setText(modifylist.get(position).getPersonal_evaluation());

    }

    @Override
    public int getItemCount() {
        return modifylist.size();
    }
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titol, autor, any, categoria, editorial, valoracio;

        public BookViewHolder(View itemView) {
            super(itemView);
            titol = (TextView)itemView.findViewById(R.id.titol);
            autor = (TextView)itemView.findViewById(R.id.autor);
            any = (TextView)itemView.findViewById(R.id.any);
            categoria = (TextView)itemView.findViewById(R.id.categoria);
            editorial = (TextView)itemView.findViewById(R.id.editorial);
            valoracio = (TextView)itemView.findViewById(R.id.valoracio);
        }
    }

    public void setFilterAutor(ArrayList<Book> newList) {
        modifylist = new ArrayList<>();
        modifylist.addAll(newList);
        notifyDataSetChanged();
    }
}
