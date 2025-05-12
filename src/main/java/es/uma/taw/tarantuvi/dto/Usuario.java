package es.uma.taw.tarantuvi.dto;

import lombok.Data;

@Data
public class Usuario {
    protected String usuario;
    protected String password;
    protected String rol;
    protected Integer usuarioId;
}
