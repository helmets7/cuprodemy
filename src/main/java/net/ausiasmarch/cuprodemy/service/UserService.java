package net.ausiasmarch.cuprodemy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ausiasmarch.cuprodemy.entity.UserEntity;
import net.ausiasmarch.cuprodemy.exception.CannotPerformOperationException;
import net.ausiasmarch.cuprodemy.exception.ResourceNotFoundException;
import net.ausiasmarch.cuprodemy.exception.ResourceNotModifiedException;
import net.ausiasmarch.cuprodemy.helper.RandomHelper;
import net.ausiasmarch.cuprodemy.helper.TipoUsuarioHelper;
import net.ausiasmarch.cuprodemy.helper.ValidationHelper;
import net.ausiasmarch.cuprodemy.repository.UserRepository;
import net.ausiasmarch.cuprodemy.repository.UsertypeRepository;


@Service
public class UserService {

    private final String CUPRODEMY_DEFAULT_PASSWORD = "4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64";

    private final String DNI_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";
    private final List<String> names = List.of("Ainhoa", "Kevin", "Estefania", "Cristina",
            "Jose", "Lucas", "Carlos", "Elliot", "Alexis", "Ruben", "Luis", "Karim", "Luis",
            "David", "Nerea", "Ximo", "Iris", "Alvaro", "Mario", "Raimon");

    private final List<String> surnames = List.of("Benito", "Blanco", "Boriko", "Carrascosa", "Guerrero", "Gyori",
            "Lazaro", "Luque", "Perez", "Perez", "Perez", "Rezgaoui", "Rodríguez", "Rosales", "Soler", "Soler", "Suay", "Talaya", "Tomas", "Vilar");

    private final List<String> lastnames = List.of("Sanchis", "Bañuls", "Laenos", "Torres", "Sanchez", "Gyori",
            "Luz", "Pascual", "Blayimir", "Castello", "Hurtado", "Mourad", "Fernández", "Ríos", "Benavent", "Benavent", "Patricio", "Romance", "Zanon", "Morera");



    @Autowired
    UserRepository oUserRepository;

    @Autowired
    AuthService oAuthService;

    @Autowired
    UsertypeService oTipouserService;

    @Autowired
    UsertypeRepository oTipoUserRepository; 

    public void validate(Long id) {
        if (!oUserRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }


    public UserEntity get(Long id) {
        
        oAuthService.OnlyAdminsOrOwnUsersData(id);
        try {
            return oUserRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }
    public Long count() {
        oAuthService.OnlyAdmins();
        return oUserRepository.count();
    }

    public Page<UserEntity> getPage(Pageable oPageable, String strFilter, Long id_tipousuario) {
        oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<UserEntity> oPage = null;
        if (id_tipousuario == null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oUserRepository.findAll(oPageable);
            } else {
                oPage = oUserRepository.findByNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContainingOrDniIgnoreCaseContaining(strFilter, strFilter, strFilter, strFilter, oPageable);
            }
        } else {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oUserRepository.findByTipousuarioId(id_tipousuario, oPageable);
            } else {
                oPage = oUserRepository.findByTipousuarioIdOrNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContainingOrDniIgnoreCaseContaining(id_tipousuario, strFilter, strFilter, strFilter, strFilter,oPageable);
            }
        }
        return oPage;
    } 

    public Long create(UserEntity oNewUserEntity) {
        oAuthService.OnlyAdmins();
        oNewUserEntity.setId(0L);
        oNewUserEntity.setPass(CUPRODEMY_DEFAULT_PASSWORD);
        return oUserRepository.save(oNewUserEntity).getId();
    }

   @Transactional
    public Long update(UserEntity oUserEntity) {
        validate(oUserEntity.getId());
        oAuthService.OnlyAdminsOrOwnUsersData(oUserEntity.getId());
        oTipouserService.validate(oUserEntity.getTipousuario().getId());
        UserEntity oldUserEntity = oUserRepository.findById(oUserEntity.getId()).get();
        oUserEntity.setPass(oldUserEntity.getPass());
        if (oAuthService.isAdmin()) {
            return update4Admins(oUserEntity).getId();
        } else {
            return update4Users(oUserEntity).getId();
        }
    }

    @Transactional
    private UserEntity update4Admins(UserEntity oUpdatedUserEntity) {
        UserEntity oUserEntity = oUserRepository.findById(oUpdatedUserEntity.getId()).get();
        oUserEntity.setNombre(oUpdatedUserEntity.getNombre());
        oUserEntity.setApellido1(oUpdatedUserEntity.getApellido1());
        oUserEntity.setApellido2(oUpdatedUserEntity.getApellido2());
        oUserEntity.setEmail(oUpdatedUserEntity.getEmail());
        oUserEntity.setNickname(oUpdatedUserEntity.getNickname());
        oUserEntity.setTipousuario(oTipouserService.get(oUpdatedUserEntity.getTipousuario().getId()));
        return oUserRepository.save(oUserEntity);
    }

    @Transactional
    private UserEntity update4Users(UserEntity oUpdatedUserEntity) {
        UserEntity oUserEntity = oUserRepository.findById(oUpdatedUserEntity.getId()).get();
        oUserEntity.setNombre(oUpdatedUserEntity.getNombre());
        oUserEntity.setApellido1(oUpdatedUserEntity.getApellido1());
        oUserEntity.setApellido2(oUpdatedUserEntity.getApellido2());
        oUserEntity.setEmail(oUpdatedUserEntity.getEmail());
        oUserEntity.setNickname(oUpdatedUserEntity.getNickname());
        oUserEntity.setTipousuario(oTipouserService.get(oUpdatedUserEntity.getTipousuario().getId()));      
        return oUserRepository.save(oUserEntity);
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        if (oUserRepository.existsById(id)) {
            oUserRepository.deleteById(id);
            if (oUserRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }

    public UserEntity generate() {
        oAuthService.OnlyAdmins();
        return oUserRepository.save(generateRandomUser());
    } 

   public Long generateSome(Integer amount) {
        oAuthService.OnlyAdmins();
        List<UserEntity> userList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            UserEntity oUserEntity = generateRandomUser();
            oUserRepository.save(oUserEntity);
            userList.add(oUserEntity);
        }
        return oUserRepository.count();
    }
    public UserEntity getOneRandom() {
        if (count() > 0) {
            UserEntity oUserEntity = null;
            int iPosicion = RandomHelper.getRandomInt(0, (int) oUserRepository.count() - 1);
            Pageable oPageable = PageRequest.of(iPosicion, 1);
            Page<UserEntity> UserPage = oUserRepository.findAll(oPageable);
            List<UserEntity> UserList = UserPage.getContent();
            oUserEntity = oUserRepository.getById(UserList.get(0).getId());
            return oUserEntity;
        } else {
            throw new CannotPerformOperationException("ho hay Users en la base de datos");
        }
    }

    private UserEntity generateRandomUser() {
        UserEntity oUserEntity = new UserEntity();
        oUserEntity.setNombre(names.get(RandomHelper.getRandomInt(0, names.size() - 1)));
        oUserEntity.setDni(generateDNI());
        oUserEntity.setApellido1(surnames.get(RandomHelper.getRandomInt(0, names.size() - 1)));
        oUserEntity.setApellido2(lastnames.get(RandomHelper.getRandomInt(0, lastnames.size() - 1)));
        oUserEntity.setNickname(oUserEntity.getNombre() + "_" + oUserEntity.getApellido1());
        oUserEntity.setPass(CUPRODEMY_DEFAULT_PASSWORD);
        oUserEntity.setEmail(generateEmail(oUserEntity.getNombre(), oUserEntity.getApellido1()));
        if (RandomHelper.getRandomInt(0, 10) > 1) {
            oUserEntity.setTipousuario(oTipoUserRepository.getById(TipoUsuarioHelper.USER));
        } else {
            oUserEntity.setTipousuario(oTipoUserRepository.getById(TipoUsuarioHelper.ADMIN));
        }
        return oUserEntity;
    }

    private String generateDNI() {
        String dni = "";
        int dniNumber = RandomHelper.getRandomInt(11111111, 99999999 + 1);
        dni += dniNumber + "" + DNI_LETTERS.charAt(dniNumber % 23);
        return dni;
    }

    private String generateEmail(String name, String surname) {
        List<String> list = new ArrayList<>();
        list.add(name);
        list.add(surname);
        return getFromList(list) + "_" + getFromList(list) + "@daw.tk";
    }

    private String getFromList(List<String> list) {
        int randomNumber = RandomHelper.getRandomInt(0, list.size() - 1);
        String value = list.get(randomNumber);
        list.remove(randomNumber);
        return value;
    } 



}