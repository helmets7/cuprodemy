package net.ausiasmarch.cuprodemy.entity;


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
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "usuario")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String dni;
    private String apellido1;
    private String apellido2;
    private String nickname;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pass;
    
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_tipousuario")
    private UsertypeEntity tipousuario;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private final List<ComentarioEntity> comentarios;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private final List<UsuarioCursoEntity> usuariocursos;

    public UserEntity() {
        this.comentarios = new ArrayList<>();
        this.usuariocursos = new ArrayList<>();
    }

    public UserEntity(Long id) {
        this.id = id;
        this.comentarios = new ArrayList<>();
        this.usuariocursos = new ArrayList<>();
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getComentarios() {
        return this.comentarios.size();
    }

    public UsertypeEntity getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(UsertypeEntity tipousuario) {
        this.tipousuario = tipousuario;
    }

    public int getUsuariocursos() {
        return usuariocursos.size();
    }




    
}
