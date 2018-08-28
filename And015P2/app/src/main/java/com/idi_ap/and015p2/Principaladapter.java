package com.idi_ap.and015p2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pau on 03/01/17.
 */

public class Principaladapter extends RecyclerView.Adapter<Principaladapter.BookViewHolder> {


    List<Book> list;

    public Principaladapter(List<Book> list) {
        this.list = list;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.principal_item,parent, false);
        BookViewHolder holder = new BookViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.titol.setText(list.get(position).getTitle());
        holder.autor.setText(list.get(position).getAuthor());
        holder.any.setText(Integer.toString(list.get(position).getYear()));
        holder.categoria.setText(list.get(position).getCategory());
        holder.editorial.setText(list.get(position).getPublisher());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

  public static class BookViewHolder extends RecyclerView.ViewHolder {
      TextView titol, autor, any, categoria, editorial;

      public BookViewHolder(View itemView) {
          super(itemView);
          titol = (TextView)itemView.findViewById(R.id.titol);
          autor = (TextView)itemView.findViewById(R.id.autor);
          any = (TextView)itemView.findViewById(R.id.any);
          categoria = (TextView)itemView.findViewById(R.id.categoria);
          editorial = (TextView)itemView.findViewById(R.id.editorial);
      }
  }
    public void setFilterTitle(ArrayList<Book> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}



