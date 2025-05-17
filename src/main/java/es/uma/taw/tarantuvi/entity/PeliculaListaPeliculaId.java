package es.uma.taw.tarantuvi.entity;
import java.io.Serializable;
import java.util.Objects;

public class PeliculaListaPeliculaId implements Serializable {

    private Integer pelicula;
    private Integer listaPelicula;

    public PeliculaListaPeliculaId() {}

    public PeliculaListaPeliculaId(Integer pelicula, Integer listaPelicula) {
        this.pelicula = pelicula;
        this.listaPelicula = listaPelicula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PeliculaListaPeliculaId that)) return false;
        return Objects.equals(pelicula, that.pelicula) &&
                Objects.equals(listaPelicula, that.listaPelicula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pelicula, listaPelicula);
    }
}
