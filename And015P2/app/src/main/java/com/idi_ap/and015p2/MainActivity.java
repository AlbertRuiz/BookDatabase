package com.idi_ap.and015p2;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static android.widget.AdapterView.*;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AddBookFragment.OnFragmentInteractionListener,
        RVCategoryFragment.OnFragmentInteractionListener, SearchView.OnQueryTextListener, ModificarFragment.OnFragmentInteractionListener, CanviarValFragment.OnFragmentInteractionListener,
           CercaAutorFragment.OnFragmentInteractionListener, AboutFragment.OnFragmentInteractionListener, HelpFragment.OnFragmentInteractionListener {
    public static BookData bookData;
    public static FloatingActionButton fab;
    public static MenuItem menuItem;
    public static List<Book> values;
    public static RecyclerView rv;
    public static Principaladapter pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bookData = new BookData(this);
        bookData.open();
        values = bookData.getBooksSortedByTitle();
        if (values.size() == 0) {
            bookData.createBook("Miguel Strogoff","Jules Verne");//Aquests son els 4 llibres inicials que hi han d'haver al sistema quan s'obre
            bookData.createBook("Ulysses","James Joyce");
            bookData.createBook("Don Quijote","Miguel de Cervantes");
            bookData.createBook("Metamorphosis","Kafka");
        }
        rv = (RecyclerView)findViewById(R.id.llista);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rv.setLayoutManager(l);
        rv.addItemDecoration(new SimpleDividerItemDecoration(this));
        pr = new Principaladapter(values);
        rv.setAdapter(pr);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, rv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Book b = values.get(position);
                Toast.makeText(view.getContext()," VALORACIO " + b.getPersonal_evaluation(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View view, final int position) {
                Book b = values.get(position);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                MainActivity.bookData.deleteBook(values.get(position));
                                values.remove(position);
                                rv.removeViewAt(position);
                                pr.notifyItemRemoved(position);
                                pr.notifyItemRangeChanged(position,values.size());
                                dialog.dismiss();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Vols esborrar el llibre titulat '"+values.get(position).getTitle()+"' ?").setPositiveButton("Si", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        }));
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.hide();
                rv.setVisibility(view.GONE);
                menuItem.setVisible(false);
                AddBookFragment fragment = new AddBookFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main,fragment)
                        .commit();
            }
        });
          DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Cerca Llibres per Títol");
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Boolean FragmentSelected = false;
        if (id == R.id.nav_llistar_categoria) {
            fab.hide();
            rv.setVisibility(this.getCurrentFocus().GONE);
            menuItem.setVisible(false);
            fragment = new RVCategoryFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();


        } else if (id == R.id.nav_Modificar) {
            fab.hide();
            rv.setVisibility(this.getCurrentFocus().GONE);
            menuItem.setVisible(false);
            fragment = new ModificarFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();

        } else if (id == R.id.nav_Cerca_Autor) {
            fab.hide();
            rv.setVisibility(this.getCurrentFocus().GONE);
            menuItem.setVisible(false);
            fragment = new CercaAutorFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();

        }
        else if (id == R.id.nav_help) {
            fab.hide();
            rv.setVisibility(this.getCurrentFocus().GONE);
            menuItem.setVisible(false);
            fragment = new HelpFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
        } else if (id == R.id.nav_about) {
            fab.hide();
            rv.setVisibility(this.getCurrentFocus().GONE);
            menuItem.setVisible(false);
            fragment = new AboutFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Book> newList = new ArrayList<>();
        for (Book book : values) {
            String title = book.getTitle().toLowerCase();
                if (title.contains(newText)) {
                    newList.add(book);
                }
        }
        pr.setFilterTitle(newList);
        return true;
    }
}
