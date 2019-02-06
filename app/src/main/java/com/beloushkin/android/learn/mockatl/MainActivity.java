package com.beloushkin.android.learn.mockatl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Boolean>,
        View.OnClickListener {

    public final String TAG = this.getClass().getSimpleName();

    private TextView tvStage;
    private Button btnAction;
    private ProgressBar pbCircular;
    private Loader<Boolean> mLoader;
    public static final int LOADER_ID = 10201;
    private final int mockTime = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStage = findViewById(R.id.tv_stage);
        pbCircular = findViewById(R.id.pb_circular);
        btnAction = findViewById(R.id.btn_action);
        mLoader = getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        btnAction.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (((MockATL) mLoader).isRunning()) {
            pbCircular.setVisibility(View.VISIBLE);
            tvStage.setText(getString(R.string.lbl_stage_loading));
            btnAction.setEnabled(false);
        } else {
            pbCircular.setVisibility(View.INVISIBLE);
            if (((MockATL) mLoader).isDone()) {
                tvStage.setText(getString(R.string.lbl_stage_ready));
            } else {
                tvStage.setText("");
            }
            btnAction.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        Bundle args = new Bundle();
        args.putInt(MockATL.MOCK_TIME, mockTime);
        getSupportLoaderManager().restartLoader(LOADER_ID, args,
                MainActivity.this);
    }

    @NonNull
    @Override
    public Loader<Boolean> onCreateLoader(int id, @Nullable Bundle bundle) {
        Log.d(TAG, "onCreateLoader");
        if (bundle != null) {
            // we're really starting
            pbCircular.setVisibility(View.VISIBLE);
            tvStage.setText(getString(R.string.lbl_stage_loading));
            btnAction.setEnabled(false);
        }
        return new MockATL(this, bundle);

    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        Log.d(TAG, "onLoadFinished: ");
        pbCircular.setVisibility(View.INVISIBLE);
        tvStage.setText(getString(R.string.lbl_stage_ready));
        btnAction.setEnabled(true);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Boolean> loader) {
        Log.d(TAG, "onLoaderReset: ");
    }
}
