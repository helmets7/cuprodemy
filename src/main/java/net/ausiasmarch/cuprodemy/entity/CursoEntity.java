package net.ausiasmarch.cuprodemy.entity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "curso")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class CursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String miniatura;
    private String videoUrl;
    private Time duracion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_leccion")
    private LeccionEntity leccion;

    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY)
    private final List<ComentarioEntity> comentarios;

    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY)
    private final List<UsuarioCursoEntity> usuariocursos;


    public CursoEntity() {
        this.usuariocursos = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    public CursoEntity(Long id) {
        this.usuariocursos = new ArrayList<>();
        this.comentarios = new ArrayList<>();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Time getDuracion() {
        return duracion;
    }

    public void setDuracion(Time time) {
        this.duracion = time;
    }

    public LeccionEntity getLeccion() {
        return leccion;
    }

    public void setLeccion(LeccionEntity leccion) {
        this.leccion = leccion;
    }

    public int getComentarios() {
        return comentarios.size();
    }

    public int getUsuariocursos() {
        return usuariocursos.size();
    }

    public void setName(String string) {
    }

    public void setPass(String cUPRODEMY_DEFAULT_PASSWORD) {
    }

    





    
}
