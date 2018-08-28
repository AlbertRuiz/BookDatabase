package com.idi_ap.and015p2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;



public class CanviarValFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Button modificar;
    private Spinner val;
    private FloatingActionButton close;
    private String[] valoracions = {"Molt Bo", "Bo", "Regular", "Dolent", "Molt Dolent"};
    private OnFragmentInteractionListener mListener;
    private List<Book> l;
    private Book b;
    private int pos;


    public CanviarValFragment() {

        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
   /* public static CanviarValFragment newInstance(int position) {
        CanviarValFragment fragment = new CanviarValFragment();
        Bundle args = new Bundle();
        args.putInt("pos",position);
        return fragment;
    }*/

    private void closeFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    /*@Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("pos");
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_canviar_val, container, false);
        val = (Spinner) v.findViewById(R.id.spinnerValPers);
        close = (FloatingActionButton) v.findViewById(R.id.closeButton);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valoracions);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        val.setAdapter(adapter);
        modificar = (Button) v.findViewById(R.id.buttonModif);
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = ModificarFragment.pos;
                Book b = ModificarFragment.bookList.get(pos);
                b.setPersonal_evaluation(valoracions[val.getSelectedItemPosition()]);
                MainActivity.bookData.deleteBook(b);
                MainActivity.bookData.addBook(b.getTitle(),b.getAuthor(),b.getYear(),b.getPublisher(),b.getCategory(),b.getPersonal_evaluation());
                MainActivity.values = MainActivity.bookData.getBooksSortedByTitle();
                MainActivity.pr = new Principaladapter(MainActivity.values);
                MainActivity.rv.setAdapter(MainActivity.pr);
                Toast.makeText(v.getContext(),"S'ha modificat la valoracio personal correctament", Toast.LENGTH_SHORT).show();


            }
        });
        close = (FloatingActionButton) v.findViewById(R.id.closeButton);
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
