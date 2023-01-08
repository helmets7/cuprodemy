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
@Table(name = "tipocomentario")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class TipoComentarioEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    @OneToMany(mappedBy = "tipocomentario", fetch = FetchType.LAZY)
    private List<ComentarioEntity> comentarios;

    public TipoComentarioEntity() {
        this.comentarios = new ArrayList<>();
    }

    public TipoComentarioEntity(Long id, String tipo, List<ComentarioEntity> comentarios) {
        this.id = id;
        this.tipo = tipo;
        this.comentarios = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getComentarios() {
        return comentarios.size();
    }

    public void setComentarios(List<ComentarioEntity> comentarios) {
        this.comentarios = comentarios;
    }

    
}
