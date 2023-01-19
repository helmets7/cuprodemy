package net.ausiasmarch.cuprodemy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ausiasmarch.cuprodemy.entity.LeccionEntity;
import net.ausiasmarch.cuprodemy.exception.ResourceNotFoundException;
import net.ausiasmarch.cuprodemy.exception.ResourceNotModifiedException;
import net.ausiasmarch.cuprodemy.helper.ValidationHelper;
import net.ausiasmarch.cuprodemy.repository.LeccionRepository;

@Service
public class LeccionService {
    
        private final String CUPRODEMY_DEFAULT_PASSWORD = "4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64";


    @Autowired
    LeccionRepository oLeccionRepository;

    
    @Autowired
    AuthService oAuthService;


    public void validate(Long id) {
        if (!oLeccionRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    

    public LeccionEntity get(Long id) {
        
        oAuthService.OnlyAdminsOrOwnUsersData(id);
        try {
            return oLeccionRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oLeccionRepository.count();
    }


    public Page<LeccionEntity> getPage(Pageable oPageable, String strFilter, String sNombre){
        oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<LeccionEntity> oPage = null;
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = oLeccionRepository.findAll(oPageable);
        } else {
            oPage = oLeccionRepository.findByDescripcionIgnoreCaseContaining(strFilter, oPageable);
        }
        return oPage;
    }

    public Long create(LeccionEntity oNewLeccionEntity) {
        oAuthService.OnlyAdmins();
        oNewLeccionEntity.setId(0L);
        oNewLeccionEntity.setPass(CUPRODEMY_DEFAULT_PASSWORD);
        return oLeccionRepository.save(oNewLeccionEntity).getId();
    }

    @Transactional
    public Long update(LeccionEntity oLeccionEntity) {
        validate(oLeccionEntity.getId());
        oAuthService.OnlyAdminsOrOwnUsersData(oLeccionEntity.getId());
        return oLeccionRepository.save(oLeccionEntity).getId();
    }

    @Transactional
    private LeccionEntity update4Admins(LeccionEntity oUpdatedLeccionEntity) {
        LeccionEntity oLeccionEntity = oLeccionRepository.findById(oUpdatedLeccionEntity.getId()).get();
        oLeccionEntity.setDescripcion(oUpdatedLeccionEntity.getDescripcion());
        oLeccionEntity.setRecurso(oUpdatedLeccionEntity.getRecurso());
        oLeccionEntity.setOrden(oUpdatedLeccionEntity.getOrden());
        return oLeccionRepository.save(oLeccionEntity);
    }

    @Transactional
    private LeccionEntity update4Users(LeccionEntity oUpdatedLeccionEntity) {
        LeccionEntity oLeccionEntity = oLeccionRepository.findById(oUpdatedLeccionEntity.getId()).get();
        oLeccionEntity.setDescripcion(oUpdatedLeccionEntity.getDescripcion());
        oLeccionEntity.setRecurso(oUpdatedLeccionEntity.getRecurso());
        oLeccionEntity.setOrden(oUpdatedLeccionEntity.getOrden());
        return oLeccionRepository.save(oLeccionEntity);
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        if (oLeccionRepository.existsById(id)) {
            oLeccionRepository.deleteById(id);
            if (oLeccionRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }

    public LeccionEntity generate() {
        oAuthService.OnlyAdmins();
        LeccionEntity oLeccionEntity = new LeccionEntity();
        oLeccionEntity.setDescripcion("Leccion de prueba");
        oLeccionEntity.setRecurso("Leccion de prueba");
        oLeccionEntity.setOrden("Leccion de prueba");
        return oLeccionEntity;

    }

}
