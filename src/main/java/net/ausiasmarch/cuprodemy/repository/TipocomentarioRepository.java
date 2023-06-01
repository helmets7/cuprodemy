package net.ausiasmarch.cuprodemy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.ausiasmarch.cuprodemy.entity.TipocomentarioEntity;

@Repository
public interface TipocomentarioRepository extends JpaRepository<TipocomentarioEntity, Long>{

    Page<TipocomentarioEntity> findByTipoIgnoreCaseContaining(String strFilter, Pageable oPageable);
    
}

