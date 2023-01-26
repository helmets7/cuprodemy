package net.ausiasmarch.cuprodemy.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cuprodemy.entity.TipocomentarioEntity;
import net.ausiasmarch.cuprodemy.exception.ResourceNotFoundException;
import net.ausiasmarch.cuprodemy.exception.ResourceNotModifiedException;
import net.ausiasmarch.cuprodemy.helper.ValidationHelper;
import net.ausiasmarch.cuprodemy.repository.TipocomentarioRepository;

@Service
public class TipocomentarioService {
    
    @Autowired
    TipocomentarioRepository oTipocomentarioRepository;

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oTipocomentarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public TipocomentarioEntity get(Long id) {
        oAuthService.OnlyAdminsOrOwnUsersData(id);
        try {
            return oTipocomentarioRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oTipocomentarioRepository.count();
    }

     public Page<TipocomentarioEntity> getPage(Pageable oPageable, String strFilter) {
        oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<TipocomentarioEntity> oPage = null;
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty() ) {
            oPage = oTipocomentarioRepository.findAll(oPageable);
        } else {
            oPage = oTipocomentarioRepository.findByTipoIgnoreCaseContaining(strFilter, oPageable);
        }
        return oPage;
    }


   public Long create(TipocomentarioEntity oNewTipocomentarioEntity) {
        oAuthService.OnlyAdmins();
        oNewTipocomentarioEntity.setId(0L);
        return oTipocomentarioRepository.save(oNewTipocomentarioEntity).getId();
    }

    
    @Transactional
    public Long update(TipocomentarioEntity oTipocomentarioEntity) {
        validate(oTipocomentarioEntity.getId());
        oAuthService.OnlyAdminsOrOwnUsersData(oTipocomentarioEntity.getId());
        return oTipocomentarioRepository.save(oTipocomentarioEntity).getId();
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        if (oTipocomentarioRepository.existsById(id)) {
            oTipocomentarioRepository.deleteById(id);
            if (oTipocomentarioRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }
/* 
    public TipocomentarioEntity generateTipocomentario() {
        oAuthService.OnlyAdmins();
        TipocomentarioEntity oTipocomentarioEntity = new TipocomentarioEntity();
        oTipocomentarioEntity.setTipo("Tipocomentario de prueba");
        return oTipocomentarioEntity;

    } */

/*     public TipocomentarioEntity generateOne() {
        oAuthService.OnlyAdmins();
        return oTipocomentarioRepository.save(generateTipocomentario());
    }

    public Long generateSome(Long amount) {
        oAuthService.OnlyAdmins();
        List<TipocomentarioEntity> TipocomentarioToSave = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            TipocomentarioToSave.add(generateTipocomentario());
        }
        oTipocomentarioRepository.saveAll(TipocomentarioToSave);
        return oTipocomentarioRepository.count();
    } */

}
