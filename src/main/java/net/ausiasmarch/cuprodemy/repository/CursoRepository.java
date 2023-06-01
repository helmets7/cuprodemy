package net.ausiasmarch.cuprodemy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.ausiasmarch.cuprodemy.entity.CursoEntity;


@Repository
public interface CursoRepository extends JpaRepository<CursoEntity, Long>{



    Page<CursoEntity> findByNombreIgnoreCaseContainingOrDescripcionIgnoreCaseContaining(
            String nombre, String descripcion, Pageable oPageable);
}
