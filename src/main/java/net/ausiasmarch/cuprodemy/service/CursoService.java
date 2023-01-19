package net.ausiasmarch.cuprodemy.service;


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

        private final String CUPRODEMY_DEFAULT_PASSWORD = "4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64";



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
        if (strFilter == null || strFilter.length() == 0) {
            if (id_leccion == null) {
                return oCursoRepository.findAll(oPageable);
            } else {
                return oCursoRepository.findByLeccionId(id_leccion, oPageable);
            }
        } else {
            if (id_leccion == null) {
                return oCursoRepository.findByNombreIgnoreCaseContaining(strFilter, oPageable);
            } else {
                return oCursoRepository.findByNombreIgnoreCaseContainingAndLeccionId(strFilter, id_leccion, oPageable);
            }
        }
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

    @Transactional
    private CursoEntity update4Admins(CursoEntity oUpdatedCursoEntity) {
        CursoEntity oCursoEntity = oCursoRepository.findById(oUpdatedCursoEntity.getId()).get();
        oCursoEntity.setNombre(oUpdatedCursoEntity.getNombre());
        oCursoEntity.setDescripcion(oUpdatedCursoEntity.getDescripcion());
        oCursoEntity.setMiniatura(oUpdatedCursoEntity.getMiniatura());
        oCursoEntity.setVideoUrl(oUpdatedCursoEntity.getVideoUrl());
        oCursoEntity.setDuracion(oUpdatedCursoEntity.getDuracion());
        //oCursoEntity.setLeccion(oLeccionService.get(oUpdatedCursoEntity.getLeccion().getId()));
        return oCursoRepository.save(oCursoEntity);
    }

    @Transactional
    private CursoEntity update4Users(CursoEntity oUpdatedCursoEntity) {
        CursoEntity oCursoEntity = oCursoRepository.findById(oUpdatedCursoEntity.getId()).get();
        oCursoEntity.setNombre(oUpdatedCursoEntity.getNombre());
        oCursoEntity.setDescripcion(oUpdatedCursoEntity.getDescripcion());
        oCursoEntity.setMiniatura(oUpdatedCursoEntity.getMiniatura());
        oCursoEntity.setVideoUrl(oUpdatedCursoEntity.getVideoUrl());
        oCursoEntity.setDuracion(oUpdatedCursoEntity.getDuracion());
        //oCursoEntity.setLeccion(oLeccionService.get(2L));
        return oCursoRepository.save(oCursoEntity);
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

    public CursoEntity generate() {
        oAuthService.OnlyAdmins();
        CursoEntity oCursoEntity = new CursoEntity();
        oCursoEntity.setNombre("Curso de prueba");
        oCursoEntity.setDescripcion("Curso de prueba");
        oCursoEntity.setMiniatura("Curso de prueba");
        oCursoEntity.setVideoUrl("Curso de prueba");
        //oCursoEntity.setDuracion();
        oCursoRepository.save(oCursoEntity);
        return oCursoEntity;

    }





}