package sgtmelon.kudagofilmstv.app.presenter;

import android.databinding.DataBindingUtil;
import android.support.v17.leanback.widget.Presenter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import sgtmelon.kudagofilmstv.R;
import sgtmelon.kudagofilmstv.app.model.item.ItemFilm;
import sgtmelon.kudagofilmstv.databinding.ItemDetailsBinding;

/**
 * Презентер для детальной информации о фильме
 */
public class PresenterDetails extends Presenter {

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
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    class DetailsViewHolder extends Presenter.ViewHolder {

        private final ItemDetailsBinding binding;

        DetailsViewHolder(ItemDetailsBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        void bind(ItemFilm itemFilm) {
            binding.setItemFilm(itemFilm);
            binding.executePendingBindings();
        }
    }

}
