package net.ausiasmarch.cuprodemy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ausiasmarch.cuprodemy.entity.ComentarioEntity;
import net.ausiasmarch.cuprodemy.exception.ResourceNotFoundException;
import net.ausiasmarch.cuprodemy.exception.ResourceNotModifiedException;
import net.ausiasmarch.cuprodemy.helper.ValidationHelper;
import net.ausiasmarch.cuprodemy.repository.ComentarioRepository;


@Service
public class ComentarioService {
    
    private final String CUPRODEMY_DEFAULT_PASSWORD = "4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64";

    @Autowired
    ComentarioRepository oComentarioRepository;

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oComentarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public ComentarioEntity get(Long id) {
        oAuthService.OnlyAdminsOrOwnUsersData(id);
        try {
            return oComentarioRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oComentarioRepository.count();
    }

    public Page<ComentarioEntity> getPage(Pageable oPageable, String strFilter, String sNombre){
        oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<ComentarioEntity> oPage = null;
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = oComentarioRepository.findAll(oPageable);
        } else {
            oPage = oComentarioRepository.findByUsuarioIgnoreCaseContaining(strFilter, oPageable);
        }
        return oPage;
    }


}
