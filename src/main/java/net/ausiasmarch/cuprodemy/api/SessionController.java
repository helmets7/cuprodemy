package net.ausiasmarch.cuprodemy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<UserEntity> login(@RequestBody UsuarioBean oUserBean) {
        return new ResponseEntity<UserEntity>(oAuthService.login(oUserBean), HttpStatus.OK);
    }
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout() {
        oAuthService.logout();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<UserEntity> check() {
        return new ResponseEntity<UserEntity>(oAuthService.check(), HttpStatus.OK);
    }

}
