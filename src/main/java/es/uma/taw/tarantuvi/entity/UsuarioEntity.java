package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USUARIO", schema = "TaranTuvi")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USUARIOID", nullable = false)
    private Integer id;

    @Column(name = "NOMBRE")
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLID")
    private RolEntity rolid;

    @Lob
    @Column(name = "PASSWORDHASH", nullable = false)
    private String passwordhash;

    @Lob
    @Column(name = "SALT", nullable = false)
    private String salt;

    @OneToMany(mappedBy = "usuarioid")
    private List<ListaPeliculaEntity> listaPeliculaList;

    @OneToMany(mappedBy = "usuarioid")
    private List<ValoracionEntity> valoracionList;
}