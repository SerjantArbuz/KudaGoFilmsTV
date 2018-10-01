package sgtmelon.kudagofilmstv.app.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import android.util.Log;

import sgtmelon.kudagofilmstv.R;

/**
 * Активити детальной информации
 */
public final class DetailsActivity extends FragmentActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }

}
