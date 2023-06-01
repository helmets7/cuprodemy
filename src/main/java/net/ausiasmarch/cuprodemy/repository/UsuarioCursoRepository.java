package net.ausiasmarch.cuprodemy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.ausiasmarch.cuprodemy.entity.UsuarioCursoEntity;

@Repository
public interface UsuarioCursoRepository extends JpaRepository<UsuarioCursoEntity, Long> {

    Page<UsuarioCursoEntity> findByUsuarioAndCurso(Pageable oPageable, Long id_usuario, Long id_curso);

    Page<UsuarioCursoEntity> findByUsuario(Pageable oPageable, Long id_usuario);

    Page<UsuarioCursoEntity> findByCurso(Pageable oPageable, Long id_curso);
    

    
}
