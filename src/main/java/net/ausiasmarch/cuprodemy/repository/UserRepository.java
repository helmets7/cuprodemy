package net.ausiasmarch.cuprodemy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.ausiasmarch.cuprodemy.entity.UserEntity;



@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

    UserEntity findByNicknameAndPass(String nickname, String pass);

    
    boolean existsByNickname(String usuario);
    
    @Query(value = "SELECT * FROM usuario WHERE id_tipousuario = ?1 AND (nombre LIKE %?3% OR apellido1 LIKE %?4% OR apellido2 LIKE %?5%)", nativeQuery = true)
    Page<UserEntity> findByTipousuarioIdOrNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(Long filtertype, String nombre, String apellido1, String apellido2, Pageable oPageable);

    Page<UserEntity> findByNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(String nombre, String apellido1, String apellido2, Pageable oPageable);

    Page<UserEntity> findByTipousuarioId(Long tipoproducto, Pageable oPageable);

}
