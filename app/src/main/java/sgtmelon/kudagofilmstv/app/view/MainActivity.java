package sgtmelon.kudagofilmstv.app.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;

import sgtmelon.kudagofilmstv.R;

/**
 * Активити главного менюю
 */
public final class MainActivity extends FragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
