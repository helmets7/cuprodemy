package net.ausiasmarch.cuprodemy.service;

import java.util.ArrayList;
import java.util.List;

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
        oAuthService.OnlyAdmins();
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


    

    public Page<LeccionEntity> getPage(Pageable oPageable, String strFilter, Long id_curso) {
        //oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<LeccionEntity> oPage = null;
        if (id_curso == null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oLeccionRepository.findAll(oPageable);
            } else {
                oPage = oLeccionRepository.findByDescripcionIgnoreCaseContaining( strFilter, oPageable);
            }
        } else {   
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oLeccionRepository.findByCursoId(id_curso, oPageable);
            } else {
                oPage = oLeccionRepository.findByCursoIdOrDescripcionIgnoreCaseContaining(id_curso, strFilter, oPageable);
            }
        }
        return oPage;
    }

    public Long create(LeccionEntity oNewLeccionEntity) {
        oAuthService.OnlyAdmins();
        oNewLeccionEntity.setId(0L);
        return oLeccionRepository.save(oNewLeccionEntity).getId();
    }

    @Transactional
    public Long update(LeccionEntity oLeccionEntity) {
        validate(oLeccionEntity.getId());
        oAuthService.OnlyAdminsOrOwnUsersData(oLeccionEntity.getId());
        return oLeccionRepository.save(oLeccionEntity).getId();
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

/*     public LeccionEntity generateLeccion() {
        oAuthService.OnlyAdmins();
        LeccionEntity oLeccionEntity = new LeccionEntity();
        oLeccionEntity.setDescripcion("Leccion de prueba");
        oLeccionEntity.setRecurso("Leccion de prueba");
        oLeccionEntity.setOrden("Leccion de prueba");
        return oLeccionEntity;

    } */

/*     public LeccionEntity generateOne() {
        oAuthService.OnlyAdmins();
        return oLeccionRepository.save(generateLeccion());
    }

    public Long generateSome(Long amount) {
        oAuthService.OnlyAdmins();
        List<LeccionEntity> LeccionToSave = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            LeccionToSave.add(generateLeccion());
        }
        oLeccionRepository.saveAll(LeccionToSave);
        return oLeccionRepository.count();
    }

 */

}
