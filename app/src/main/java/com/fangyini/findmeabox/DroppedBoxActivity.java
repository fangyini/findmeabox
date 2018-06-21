package com.fangyini.findmeabox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;
import android.view.MenuItem;
import java.util.List;
import android.support.v7.widget.DividerItemDecoration;
import static android.support.v7.widget.DividerItemDecoration.VERTICAL;
import android.support.v7.widget.helper.ItemTouchHelper;

public class DroppedBoxActivity extends AppCompatActivity implements BoxAdapter.ListItemClickListener  {
    private AppDatabase mDb;
    private BoxAdapter mAdapter;
    private RecyclerView mNumbersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropped_box);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        mDb = AppDatabase.getsInstance((getApplicationContext()));

        mNumbersList = (RecyclerView) findViewById(R.id.recycle_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);
        mAdapter = new BoxAdapter(this, this);
        mNumbersList.setAdapter(mAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mNumbersList.addItemDecoration(decoration);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // call the diskIO execute method with a new Runnable and implement its run method
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Box> boxes = mAdapter.getBoxes();
                        mDb.boxDao().deleteBox(boxes.get(position));
                        retrieveBoxes();
                    }
                });
            }
        }).attachToRecyclerView(mNumbersList);
    }

    private void retrieveBoxes() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Box> boxes = mDb.boxDao().loadAllBoxes();
                //TODO: simplify
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setBoxes(boxes);
                    }
                });
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        retrieveBoxes();
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent seeDetail = new Intent(DroppedBoxActivity.this, ShowBoxDetailActivity.class);
        seeDetail.putExtra(ShowBoxDetailActivity.EXTRA_BOX_ID, clickedItemIndex);
        startActivity(seeDetail);
    }
}
