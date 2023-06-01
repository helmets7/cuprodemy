package net.ausiasmarch.cuprodemy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.ausiasmarch.cuprodemy.entity.ComentarioEntity;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Long>{

    Page<ComentarioEntity> findByTipocomentarioId(Pageable oPageable, Long id_tipocomentario);

    Page<ComentarioEntity> findByUsuarioId(Pageable oPageable, Long id_usuario);

    Page<ComentarioEntity> findByCursoId(Pageable oPageable, Long id_curso);

    Page<ComentarioEntity> findByCursoIdAndTipocomentarioId(Pageable oPageable, Long id_curso, Long id_tipocomentario);

    Page<ComentarioEntity> findByCursoIdAndUsuarioId(Pageable oPageable, Long id_curso, Long id_usuario);

    Page<ComentarioEntity> findByUsuarioIdAndTipocomentarioId(Pageable oPageable, Long id_usuario, Long id_tipocomentario);

    Page<ComentarioEntity> findByCursoIdAndUsuarioIdAndTipocomentarioId(Pageable oPageable, Long id_curso, Long id_usuario, Long id_tipocomentario);

    Page<ComentarioEntity> findByComentarioIgnoreCaseContaining(String comentario, Pageable oPageable);

    Page<ComentarioEntity> findByCursoAndTipocomentario(Pageable oPageable, Long id_curso, Long id_tipocomentario);

    Page<ComentarioEntity> findByCursoAndUsuarioAndTipocomentario(Pageable oPageable, Long id_curso, Long id_usuario,
            Long id_tipocomentario);
   
 
}
