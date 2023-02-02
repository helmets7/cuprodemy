package net.ausiasmarch.cuprodemy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.ausiasmarch.cuprodemy.entity.UserEntity;



@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

    UserEntity findByNicknameAndPass(String nickname, String pass);

    UserEntity findByNickname(String nickname);

    boolean existsByNickname(String usuario);
    
    Page<UserEntity> findByTipousuarioIdOrNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContainingOrDniIgnoreCaseContaining(Long id_tipousuario, String nombre, String apellido1, String apellido2, String dni, Pageable oPageable);


    Page<UserEntity> findByTipousuarioId(Long tipoproducto, Pageable oPageable);

    Page<UserEntity> findByNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContainingOrDniIgnoreCaseContaining(
            String nombre, String apellido1, String apellido2, String dni, Pageable oPageable);


}
