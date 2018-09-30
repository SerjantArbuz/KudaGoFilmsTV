package sgtmelon.kudagofilmstv.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import sgtmelon.kudagofilmstv.R;

/**
 * Активити главного менюю
 */
public class ActMain extends FragmentActivity {

    private static final String TAG = ActMain.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
    }
}
