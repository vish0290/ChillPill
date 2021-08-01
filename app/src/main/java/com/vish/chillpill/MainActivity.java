package com.vish.chillpill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {


        private RecyclerView recyclerView;
        memberAdapter adapter;
        DatabaseReference mbase;



        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mbase = FirebaseDatabase.getInstance("https://chillpill-f4edb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("video");
            recyclerView = findViewById(R.id.recycler1);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            FirebaseRecyclerOptions<members> options
                    = new FirebaseRecyclerOptions.Builder<members>()
                    .setQuery(mbase, members.class)
                    .build();
            adapter = new memberAdapter(options);
            recyclerView.setAdapter(adapter);

        }

        @Override protected void onStart()
        {
            super.onStart();
            adapter.startListening();
        }
        @Override protected void onStop()
        {
            super.onStop();
            adapter.stopListening();
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.searchmenu,menu);
        MenuItem item= menu.findItem(R.id.search);
        SearchView searchView =(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });
            return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s)
    {
        mbase = FirebaseDatabase.getInstance("https://chillpill-f4edb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("video");
        recyclerView = findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<members> options
                = new FirebaseRecyclerOptions.Builder<members>()
                .setQuery(mbase.orderByChild("search").startAt(s).endAt(s+"\uf8ff"), members.class)
                .build();
        adapter = new memberAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}
