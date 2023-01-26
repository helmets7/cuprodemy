package net.ausiasmarch.cuprodemy.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ausiasmarch.cuprodemy.entity.CursoEntity;
import net.ausiasmarch.cuprodemy.exception.ResourceNotFoundException;
import net.ausiasmarch.cuprodemy.exception.ResourceNotModifiedException;
import net.ausiasmarch.cuprodemy.helper.ValidationHelper;
import net.ausiasmarch.cuprodemy.repository.CursoRepository;



@Service
public class CursoService {

    @Autowired
    CursoRepository oCursoRepository;

    @Autowired
    AuthService oAuthService;
    

    public void validate(Long id) {
        if (!oCursoRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public CursoEntity get(Long id) {
        oAuthService.OnlyAdminsOrOwnUsersData(id);
        try {
            return oCursoRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oCursoRepository.count();
    }

    public Page<CursoEntity> getPage(Pageable oPageable, String strFilter, Long id_leccion) {
        oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<CursoEntity> oPage = null;
        if (id_leccion == null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oCursoRepository.findAll(oPageable);
            } else {
                oPage = oCursoRepository.findByNombreIgnoreCaseContainingOrDescripcionIgnoreCaseContaining(id_leccion, strFilter, strFilter, oPageable);
            }
        } else {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oCursoRepository.findByLeccionId(id_leccion, oPageable);
            } else {
                oPage = oCursoRepository.findByLeccionIdOrNombreIgnoreCaseContainingOrDescripcionIgnoreCaseContaining(id_leccion, strFilter, strFilter, oPageable);
            }
        }
        return oPage;
    }
 

    public Long create(CursoEntity oNewCursoEntity) {
        oAuthService.OnlyAdmins();
        oNewCursoEntity.setId(0L);
        return oCursoRepository.save(oNewCursoEntity).getId();
    }

    
    @Transactional
    public Long update(CursoEntity oCursoEntity) {
        validate(oCursoEntity.getId());
        oAuthService.OnlyAdminsOrOwnUsersData(oCursoEntity.getId());
        return oCursoRepository.save(oCursoEntity).getId();
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        if (oCursoRepository.existsById(id)) {
            oCursoRepository.deleteById(id);
            if (oCursoRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }

/*     public CursoEntity generateCurso() {
        oAuthService.OnlyAdmins();
        CursoEntity oCursoEntity = new CursoEntity();
        oCursoEntity.setNombre("Curso de prueba");
        oCursoEntity.setDescripcion("Curso de prueba");
        oCursoEntity.setMiniatura("Curso de prueba");
        oCursoEntity.setVideoUrl("Curso de prueba");
        //oCursoEntity.setDuracion();
        return oCursoEntity;

    } */
/* 
    public CursoEntity generateOne() {
        oAuthService.OnlyAdmins();
        return oCursoRepository.save(generateCurso());
    }

    public Long generateSome(Long amount) {
        oAuthService.OnlyAdmins();
        List<CursoEntity> CursoToSave = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            CursoToSave.add(generateCurso());
        }
        oCursoRepository.saveAll(CursoToSave);
        return oCursoRepository.count();
    }
 */





}