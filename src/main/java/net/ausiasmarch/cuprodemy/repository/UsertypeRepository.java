package net.ausiasmarch.cuprodemy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.ausiasmarch.cuprodemy.entity.UsertypeEntity;

@Repository
public interface UsertypeRepository extends JpaRepository<UsertypeEntity, Long> {

    public Page<UsertypeEntity> findByNombreIgnoreCaseContaining(String strFilter, Pageable oPageable);

}