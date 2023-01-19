package net.ausiasmarch.cuprodemy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.ausiasmarch.cuprodemy.entity.CursoEntity;


@Repository
public interface CursoRepository extends JpaRepository<CursoEntity, Long>{

    CursoEntity findByNombre(String nombre);


    boolean existsByNombre(String curso);


    Page<CursoEntity> findByNombreIgnoreCaseContaining(String nombre, Pageable oPageable);


    Page<CursoEntity> findByLeccionId(Long id_leccion, Pageable oPageable);


    Page<CursoEntity> findByNombreIgnoreCaseContainingAndLeccionId(String strFilter, Long id_leccion, Pageable oPageable);
}
