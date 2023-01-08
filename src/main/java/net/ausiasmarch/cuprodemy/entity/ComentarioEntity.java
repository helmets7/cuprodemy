package net.ausiasmarch.cuprodemy.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "comentario")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ComentarioEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comentario;
    private String firmatiempo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_usuario")
    private UserEntity usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_tipocomentario")
    private TipoComentarioEntity tipocomentario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_curso")
    private CursoEntity curso;

    public ComentarioEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFirmatiempo() {
        return firmatiempo;
    }

    public void setFirmatiempo(String firmatiempo) {
        this.firmatiempo = firmatiempo;
    }

    public UserEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UserEntity usuario) {
        this.usuario = usuario;
    }

    public TipoComentarioEntity getTipoComentario() {
        return tipocomentario;
    }

    public void setTipoComentario(TipoComentarioEntity tipocomentario) {
        this.tipocomentario = tipocomentario;
    }

    public CursoEntity getCurso() {
        return curso;
    }

    public void setCurso(CursoEntity curso) {
        this.curso = curso;
    }

    

}
