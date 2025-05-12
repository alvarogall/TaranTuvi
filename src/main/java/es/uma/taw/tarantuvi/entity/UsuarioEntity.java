package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.Usuario;
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

    @Column(name = "USUARIO")
    private String usuario;

    @Column(name = "PASSWORD")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLID")
    private RolEntity rolid;

    @OneToMany(mappedBy = "usuarioid")
    private List<ListaPeliculaEntity> listaPeliculaList;

    @OneToMany(mappedBy = "usuarioid")
    private List<ValoracionEntity> valoracionList;

    public Usuario toDto() {
        Usuario usuario = new Usuario();

        usuario.setUsuarioId(this.id);
        usuario.setUsuario(this.usuario);
        usuario.setPassword(this.password);
        usuario.setRol(this.rolid.getRolnombre());

        return usuario;
    }
}