package sgtmelon.kudagofilmstv.task;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.util.Log;
import com.squareup.picasso.Picasso;
import sgtmelon.kudagofilmstv.R;
import sgtmelon.kudagofilmstv.model.ItemFilm;

import java.io.IOException;

public class TaskDetails extends AsyncTask<ItemFilm, Integer, DetailsOverviewRow> {

    private static final String TAG = TaskDetails.class.getSimpleName();

    private final Context context;
    private final ItemFilm itemFilm;

    private final int thumbWidth, thumbHeight;

    public TaskDetails(Context context, ItemFilm itemFilm) {
        this.context = context;
        this.itemFilm = itemFilm;

        Resources res = context.getResources();
        thumbWidth = res.getDimensionPixelSize(R.dimen.detail_thumb_width);
        thumbHeight = res.getDimensionPixelSize(R.dimen.detail_thumb_height);
    }

    @Override
    protected DetailsOverviewRow doInBackground(ItemFilm... itemFilms) {
        Log.i(TAG, "doInBackground");

        DetailsOverviewRow row = new DetailsOverviewRow(itemFilm);

        try {
            Bitmap poster = Picasso.get()
                    .load(itemFilm.getPoster().toString())
                    .resize(thumbWidth, thumbHeight)
                    .centerCrop()
                    .get();

            row.setImageBitmap(context, poster);
        } catch (IOException e) {
            Log.w(TAG, e.toString());
        }

        return row;
    }

//    @Override
//    protected void onPostExecute(DetailsOverviewRow detailsOverviewRow) {
//        Log.i(TAG, "onPostExecute");
//        super.onPostExecute(detailsOverviewRow);
//
//        SparseArrayObjectAdapter sparseArrayObjectAdapter = new SparseArrayObjectAdapter();
//        for (int i = 0; i<10; i++){
//            sparseArrayObjectAdapter.set(i, new Action(i, "label1", "label2"));
//        }
//        row.setActionsAdapter(sparseArrayObjectAdapter);
//
//        /* 2nd row: ListRow */
//        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new CardPresenter());
//        for(int i = 0; i < 10; i++){
//            Movie movie = new Movie();
//            if(i%3 == 0) {
//                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02580.jpg");
//            } else if (i%3 == 1) {
//                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02630.jpg");
//            } else {
//                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02529.jpg");
//            }
//            movie.setTitle("title" + i);
//            movie.setStudio("studio" + i);
//            listRowAdapter.add(movie);
//        }
//        HeaderItem headerItem = new HeaderItem(0, "Related Videos");
//
//        ClassPresenterSelector classPresenterSelector = new ClassPresenterSelector();
//        mFwdorPresenter.setInitialState(FullWidthDetailsOverviewRowPresenter.STATE_SMALL);
//        Log.e(TAG, "mFwdorPresenter.getInitialState: " +mFwdorPresenter.getInitialState());
//
//        classPresenterSelector.addClassPresenter(DetailsOverviewRow.class, mFwdorPresenter);
//        classPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());
//
//        ArrayObjectAdapter adapter = new ArrayObjectAdapter(classPresenterSelector);
//        /* 1st row */
//        adapter.add(row);
//        /* 2nd row */
//        adapter.add(new ListRow(headerItem, listRowAdapter));
//        /* 3rd row */
//        //adapter.add(new ListRow(headerItem, listRowAdapter));
//        setAdapter(adapter);
//    }
}
