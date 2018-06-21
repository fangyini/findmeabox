package com.fangyini.findmeabox;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowBoxDetailActivity extends AppCompatActivity {
    private TextView mDisplay;
    private Button mShare;
    private  AppDatabase mDb;
    public static final String EXTRA_BOX_ID = "extraBoxId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_BOX_ID = "instanceBoxId";
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_BOX_ID = -1;
    private int mBoxId = DEFAULT_BOX_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_box_detail);
        mDisplay = (TextView) findViewById(R.id.content);

        mDb = AppDatabase.getsInstance((getApplicationContext()));
        if(savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_BOX_ID)) {
            mBoxId = savedInstanceState.getInt(INSTANCE_BOX_ID, DEFAULT_BOX_ID);
        }
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_BOX_ID)) {
            if(mBoxId == DEFAULT_BOX_ID) {
                mBoxId = intent.getIntExtra(EXTRA_BOX_ID, DEFAULT_BOX_ID);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        final Box targetBox = mDb.boxDao().loadBoxById(mBoxId);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mDisplay.setText(targetBox.getContent());
                            }
                        });
                    }
                });
            }
        }
        mShare = (Button) findViewById(R.id.share);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:Share the box
                String mimeType = "text/plain";
                String title = "Share test";
                String content = "Share";
                ShareCompat.IntentBuilder.from(ShowBoxDetailActivity.this)
                        .setChooserTitle(title)
                        .setType(mimeType)
                        .setText(content)
                        .startChooser();
            }
        });
    }


}
