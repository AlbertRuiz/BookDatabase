package com.idi_ap.and015p2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Albert.Ruiz on 29/12/2016.
 */

public class BookRVAdapter extends RecyclerView.Adapter<BookRVAdapter.BookViewHolder>{

    List<Book> bookList;

    public BookRVAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_book_item,parent,false);
        BookViewHolder holder = new BookViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.txt_titol.setText(bookList.get(position).getTitle());
        holder.txt_autor.setText(bookList.get(position).getAuthor());
        holder.txt_categ.setText(bookList.get(position).getCategory());
        holder.txt_edit.setText(bookList.get(position).getPublisher());
        holder.txt_any.setText(Integer.toString(bookList.get(position).getYear()));
        holder.txt_val.setText(bookList.get(position).getPersonal_evaluation());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView txt_titol, txt_autor, txt_categ, txt_edit, txt_any, txt_val;
        public BookViewHolder(View itemView) {
            super(itemView);
            txt_titol = (TextView) itemView.findViewById(R.id.item_title);
            txt_autor = (TextView) itemView.findViewById(R.id.item_author);
            txt_categ = (TextView) itemView.findViewById(R.id.item_categ);
            txt_edit = (TextView) itemView.findViewById(R.id.item_publisher);
            txt_any = (TextView) itemView.findViewById(R.id.item_year);
            txt_val = (TextView) itemView.findViewById(R.id.item_valoration);

        }
    }

}
