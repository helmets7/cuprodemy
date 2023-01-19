package net.ausiasmarch.cuprodemy.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "leccion")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class LeccionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private String recurso;
    private String orden;

    
    @OneToMany(mappedBy = "leccion", fetch = FetchType.LAZY)
    private List<CursoEntity> cursos;

    public LeccionEntity() {
        this.cursos = new ArrayList<>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }


    public int getCursos() {
        return cursos.size();
    }


    public void setPass(String cUPRODEMY_DEFAULT_PASSWORD) {
    }

    
}
