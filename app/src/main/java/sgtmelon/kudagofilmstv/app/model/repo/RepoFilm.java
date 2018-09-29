package sgtmelon.kudagofilmstv.app.model.repo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import sgtmelon.kudagofilmstv.app.model.item.ItemFilm;
import sgtmelon.kudagofilmstv.office.annot.DefApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<ItemFilm> getListFilm() {
        return listFilm;
    }

    public List<ItemFilm> getListFilmReverse() {
        List<ItemFilm> listFilm = new ArrayList<>(this.listFilm);
        Collections.reverse(listFilm);
        return listFilm;
    }

    public void setListFilm(List<ItemFilm> listFilm) {
        this.listFilm = listFilm;
    }

}
