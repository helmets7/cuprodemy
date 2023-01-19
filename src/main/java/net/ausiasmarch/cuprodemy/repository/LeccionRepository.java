package net.ausiasmarch.cuprodemy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.ausiasmarch.cuprodemy.entity.LeccionEntity;

@Repository
public interface LeccionRepository extends JpaRepository<LeccionEntity, Long> {

    Page<LeccionEntity> findByDescripcionIgnoreCaseContaining(String strFilter, Pageable oPageable);
    
    
}
