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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class AddBookFragment extends Fragment {
    private EditText titol, autor, any, editorial, categoria;
    private Spinner valoracio;
    private Button afegir;
    private String[] values = {"Molt Bo", "Bo", "Regular", "Dolent", "Molt Dolent"};
    private FloatingActionButton tancar;
    private OnFragmentInteractionListener mListener;


    public AddBookFragment() {
        // Required empty public constructor
    }
    private void closeFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);
        titol = (EditText) view.findViewById(R.id.editTitol);
        autor = (EditText) view.findViewById(R.id.editAutor);
        any = (EditText) view.findViewById(R.id.editAny);
        editorial = (EditText) view.findViewById(R.id.editEditorial);
        categoria = (EditText) view.findViewById(R.id.editCategoria);
        valoracio = (Spinner) view.findViewById(R.id.spinnerValPers);
        tancar = (FloatingActionButton) view.findViewById(R.id.floatingCloseButton);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        valoracio.setAdapter(adapter);
        afegir = (Button) view.findViewById(R.id.buttonAfegir);
        afegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titol.getText().toString().equals("") || autor.getText().toString().equals("") ||
                        any.getText().toString().equals("") || editorial.getText().toString().equals("") ||
                        categoria.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Han quedat paràmetres sense omplir", Toast.LENGTH_LONG).show();
                }
                else {
                    MainActivity.bookData.addBook(titol.getText().toString(), autor.getText().toString(),
                            Integer.parseInt(any.getText().toString()), editorial.getText().toString(),
                            categoria.getText().toString(), values[valoracio.getSelectedItemPosition()]);
                    titol.setText("", TextView.BufferType.EDITABLE);
                    autor.setText("", TextView.BufferType.EDITABLE);
                    any.setText("", TextView.BufferType.EDITABLE);
                    editorial.setText("", TextView.BufferType.EDITABLE);
                    categoria.setText("", TextView.BufferType.EDITABLE);
                    MainActivity.values = MainActivity.bookData.getBooksSortedByTitle();
                    MainActivity.pr = new Principaladapter(MainActivity.values);
                    MainActivity.rv.setAdapter(MainActivity.pr);

                }
            }
        });
        tancar.setOnClickListener(new View.OnClickListener() {
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
