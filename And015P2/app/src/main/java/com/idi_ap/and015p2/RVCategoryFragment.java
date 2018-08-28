package com.idi_ap.and015p2;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;


public class RVCategoryFragment extends Fragment {

    private RecyclerView rv;
    private List<Book> bookList;
    private BookRVAdapter adapter;
    private FloatingActionButton close;
    private OnFragmentInteractionListener mListener;

    public RVCategoryFragment() {
        // Required empty public constructor
    }

    public void closeFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rvcategory, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rv_llibres);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        bookList = MainActivity.bookData.getBooksSortedByCategory();
        adapter = new BookRVAdapter(bookList);
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                MainActivity.bookData.deleteBook(bookList.get(position));
                                bookList.remove(position);
                                rv.removeViewAt(position);
                                adapter.notifyItemRemoved(position);
                                adapter.notifyItemRangeChanged(position,bookList.size());
                                MainActivity.values = MainActivity.bookData.getBooksSortedByTitle();
                                MainActivity.pr = new Principaladapter(MainActivity.values);
                                MainActivity.rv.setAdapter(MainActivity.pr);
                                dialog.dismiss();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Vols esborrar el llibre titulat '"+bookList.get(position).getTitle()+"' ?").setPositiveButton("Si", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
        close = (FloatingActionButton) view.findViewById(R.id.closeListCateg);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fab.show();
                MainActivity.rv.setVisibility(v.VISIBLE);
                MainActivity.menuItem.setVisible(true);
                closeFragment();
            }
        });
        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
