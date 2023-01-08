package net.ausiasmarch.cuprodemy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cuprodemy.entity.UsertypeEntity;
import net.ausiasmarch.cuprodemy.exception.ResourceNotFoundException;
import net.ausiasmarch.cuprodemy.helper.ValidationHelper;
import net.ausiasmarch.cuprodemy.repository.UsertypeRepository;

@Service
public class TipousuarioService {

    @Autowired
    UsertypeRepository oTipousuarioRepository;

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oTipousuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(UsertypeEntity oTipousuarioEntity) {
        ValidationHelper.validateStringLength(oTipousuarioEntity.getNombre(), 2, 100, "campo nombre de Tipousuario (el campo debe tener longitud de 2 a 100 caracteres)");
    }

    public UsertypeEntity get(Long id) {
        validate(id);
        return oTipousuarioRepository.getById(id);
    }

}