package net.ausiasmarch.cuprodemy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.cuprodemy.bean.UsuarioBean;
import net.ausiasmarch.cuprodemy.entity.UserEntity;
import net.ausiasmarch.cuprodemy.service.AuthService;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    AuthService oAuthService;

    @PostMapping
    public ResponseEntity<String> login(@org.springframework.web.bind.annotation.RequestBody UsuarioBean oUsuarioBean) {
        return new ResponseEntity<String>("\"" + oAuthService.login(oUsuarioBean) + "\"", HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<UserEntity> check() {
        return new ResponseEntity<UserEntity>(oAuthService.check(), HttpStatus.OK);
    }
    
    @GetMapping("/getId")
    public ResponseEntity<Long> getId() {
        return new ResponseEntity<Long>(oAuthService.getID(), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@org.springframework.web.bind.annotation.RequestBody String token) {
        return new ResponseEntity<String>("\"" + oAuthService.logout(token) + "\"", HttpStatus.OK);
    }


}
