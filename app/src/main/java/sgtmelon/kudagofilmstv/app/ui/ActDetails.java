package sgtmelon.kudagofilmstv.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import sgtmelon.kudagofilmstv.R;

/**
 * Активити детальной информации
 */
public class ActDetails extends FragmentActivity {

    private static final String TAG = ActDetails.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_details);
    }

}
