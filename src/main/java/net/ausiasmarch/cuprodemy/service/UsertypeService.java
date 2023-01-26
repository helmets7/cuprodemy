package net.ausiasmarch.cuprodemy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ausiasmarch.cuprodemy.entity.UsertypeEntity;
import net.ausiasmarch.cuprodemy.exception.ResourceNotFoundException;
import net.ausiasmarch.cuprodemy.exception.ResourceNotModifiedException;
import net.ausiasmarch.cuprodemy.helper.ValidationHelper;
import net.ausiasmarch.cuprodemy.repository.UsertypeRepository;

@Service
public class UsertypeService {

    @Autowired
    UsertypeRepository oUsertypeRepository;

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oUsertypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(UsertypeEntity oUsertypeEntity) {
        ValidationHelper.validateStringLength(oUsertypeEntity.getNombre(), 2, 100, "campo nombre de Tipousuario (el campo debe tener longitud de 2 a 100 caracteres)");
    }

    public UsertypeEntity get(Long id) {
        validate(id);
        return oUsertypeRepository.getById(id);
    }   

    public Long count() {
        oAuthService.OnlyAdmins();
        return oUsertypeRepository.count();
    }


    public Page<UsertypeEntity> getPage(Pageable oPageable, String strFilter) {
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<UsertypeEntity> oPage = null;
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = oUsertypeRepository.findAll(oPageable);
        } else {
            oPage = oUsertypeRepository.findByNombreIgnoreCaseContaining(strFilter, oPageable);
        }
        return oPage;
    }

    public Long create(UsertypeEntity oNewUsertypeEntity) {
        validate(oNewUsertypeEntity);
        oNewUsertypeEntity.setId(0L);
        return oUsertypeRepository.save(oNewUsertypeEntity).getId();
    }

    @Transactional
    public Long update(UsertypeEntity oUsertypeEntity) {
        validate(oUsertypeEntity);
        oAuthService.OnlyAdminsOrOwnUsersData(oUsertypeEntity.getId());
        return oUsertypeRepository.save(oUsertypeEntity).getId();
    }

    public Long delete(Long id){
        oAuthService.OnlyAdmins();
        if (oUsertypeRepository.existsById(id)) {
            oUsertypeRepository.deleteById(id);
            if (oUsertypeRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }

/*     public UsertypeEntity generateUsertypeEntity() {
        UsertypeEntity oUsertypeEntity = new UsertypeEntity();
        oUsertypeEntity.setNombre("nombre");
        return oUsertypeEntity;
    } */

/* 
    public UsertypeEntity generateOne(){
        oAuthService.OnlyAdmins();
        return oUsertypeRepository.save(generateUsertypeEntity());
    }

    public Long generateSome(Long amount){
        oAuthService.OnlyAdmins();
        List<UsertypeEntity> UsertypeToSave = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            oUsertypeRepository.save(generateUsertypeEntity());
        }
        return amount;
    } */

}