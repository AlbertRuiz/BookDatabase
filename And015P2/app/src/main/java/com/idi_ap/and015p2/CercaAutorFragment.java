package com.idi_ap.and015p2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;



public class CercaAutorFragment extends Fragment {

    private RecyclerView recv;
    private  List<Book> list;
    private  ModificarAdapter adapt;
    private  FloatingActionButton close;
    private SearchView cer;
    private OnFragmentInteractionListener mListener;

    public CercaAutorFragment() {
        // Required empty public constructor
    }

    public void closeFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cerca_autor, container, false);
        recv = (RecyclerView) v.findViewById(R.id.cercar);
        cer = (SearchView)v.findViewById(R.id.search);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recv.setLayoutManager(llm);
        recv.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        list = MainActivity.bookData.getBooksSortedByAutor();
        adapt = new ModificarAdapter(list);
        recv.setAdapter(adapt);

        cer.setQueryHint("Cerca per autors...");
        cer.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<Book> newList = new ArrayList<>();
                for (Book book : list) {
                    String autor = book.getAuthor().toLowerCase();
                    if (autor.contains(newText)) {
                        newList.add(book);
                    }
                }
                adapt.setFilterAutor(newList);
                return true;
            }
        });
        close = (FloatingActionButton) v.findViewById(R.id.closecerca);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fab.show();
                MainActivity.rv.setVisibility(v.VISIBLE);
                MainActivity.menuItem.setVisible(true);
                closeFragment();
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
