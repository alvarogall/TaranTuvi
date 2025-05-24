/*
User: jesus
*/

package es.uma.taw.tarantuvi.dto;

import lombok.Data;

import java.util.List;

@Data
public class Departamento {
    protected Integer id;
    protected String departamentonombre;
    protected List<Trabajo> trabajoList;
}
