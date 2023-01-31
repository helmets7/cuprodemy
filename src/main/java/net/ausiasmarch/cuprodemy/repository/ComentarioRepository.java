package net.ausiasmarch.cuprodemy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.ausiasmarch.cuprodemy.entity.ComentarioEntity;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Long>{

    Page<ComentarioEntity> findByTipocomentario(Pageable oPageable, Long id_tipocomentario);

    Page<ComentarioEntity> findByUsuario(Pageable oPageable, Long id_usuario);

    Page<ComentarioEntity> findByCurso(Pageable oPageable, Long id_curso);

    Page<ComentarioEntity> findByCursoAndTipocomentario(Pageable oPageable, Long id_curso, Long id_tipocomentario);

    Page<ComentarioEntity> findByCursoAndUsuario(Pageable oPageable, Long id_curso, Long id_usuario);

    Page<ComentarioEntity> findByUsuarioAndTipocomentario(Pageable oPageable, Long id_usuario, Long id_tipocomentario);

    Page<ComentarioEntity> findByCursoAndUsuarioAndTipocomentario(Pageable oPageable, Long id_curso, Long id_usuario, Long id_tipocomentario);

    Page<ComentarioEntity> findByComentarioIgnoreCaseContaining(String comentario, Pageable oPageable);
   
 
}
