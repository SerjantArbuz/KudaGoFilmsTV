package sgtmelon.kudagofilmstv.app.model.repo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import sgtmelon.kudagofilmstv.app.model.item.ItemFilm;
import sgtmelon.kudagofilmstv.office.annot.DefApi;

/**
 * Репозиторий фильма
 */
public class RepoFilm {

    @SerializedName(DefApi.field_count)
    @Expose
    private int count;
    @SerializedName(DefApi.field_next)
    @Expose
    private String next;

    @SerializedName(DefApi.field_results)
    @Expose
    private List<ItemFilm> listFilm;

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public List<ItemFilm> getListFilm() {
        return listFilm;
    }

}
