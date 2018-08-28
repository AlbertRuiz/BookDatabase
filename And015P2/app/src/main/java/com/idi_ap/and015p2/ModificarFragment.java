package com.idi_ap.and015p2;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;




public class ModificarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static RecyclerView rv;
    public static List<Book> bookList;
    public static ModificarAdapter adapter;
    public static FloatingActionButton close;
    private OnFragmentInteractionListener mListener;
    public static Book modif;
    public static int pos;

    public ModificarFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public void closeFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    public static Book modif(Book b) {
        return b;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar, container, false);
        rv = (RecyclerView) view.findViewById(R.id.modif);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        bookList = MainActivity.bookData.getBooksSortedByTitle();
        adapter = new ModificarAdapter(bookList);
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, final int position) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                close.hide();
                                rv.setVisibility(view.GONE);
                                pos = position;
                                Fragment fragment = new CanviarValFragment(); //aqui anira el nou fragment per poder modificar-ho
                                getFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Vols modificar la valoracio personal del llibre titulat '"+bookList.get(position).getTitle()+"' ?").setPositiveButton("Si", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
        close = (FloatingActionButton) view.findViewById(R.id.closemodif);
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
