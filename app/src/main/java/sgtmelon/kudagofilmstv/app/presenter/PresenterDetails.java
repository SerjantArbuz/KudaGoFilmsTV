package sgtmelon.kudagofilmstv.app.presenter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.leanback.widget.Presenter;
import sgtmelon.kudagofilmstv.R;
import sgtmelon.kudagofilmstv.app.model.item.ItemFilm;
import sgtmelon.kudagofilmstv.app.model.item.ItemGenre;
import sgtmelon.kudagofilmstv.databinding.ItemDetailsBinding;

import java.util.List;

/**
 * Презентер для детальной информации о фильме
 */
public final class PresenterDetails extends Presenter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        ItemDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_details, viewGroup, false);
        return new DetailsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object o) {
        if (viewHolder instanceof DetailsViewHolder && o instanceof ItemFilm) {
            ItemFilm itemFilm = (ItemFilm) o;
            ((DetailsViewHolder) viewHolder).bind(itemFilm);

            List<ItemGenre> listGenre = itemFilm.getGenres();
            StringBuilder gen = new StringBuilder();

            if (listGenre != null && listGenre.size() != 0) {
                for (ItemGenre itemGenre : listGenre) {
                    gen.append(itemGenre.getName().toLowerCase());
                    gen.append(", ");
                }
                gen.delete(gen.length() - 2, gen.length());
            }

            ((DetailsViewHolder) viewHolder).genresText.setText(gen.toString());

        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    private static final class DetailsViewHolder extends Presenter.ViewHolder {

        private final ItemDetailsBinding binding;

        private final TextView genresText;

        DetailsViewHolder(ItemDetailsBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            genresText = binding.getRoot().findViewById(R.id.genres_text);
        }

        void bind(ItemFilm itemFilm) {
            binding.setItemFilm(itemFilm);
            binding.executePendingBindings();
        }
    }

}
