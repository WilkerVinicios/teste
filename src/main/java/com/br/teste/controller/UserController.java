package com.br.teste.controller;

import com.br.teste.dto.UserDto;
import com.br.teste.entity.User;
import com.br.teste.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User criar(@RequestBody UserDto dto) {
        return userService.salvar(dto);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> listar() {
        return userService.listar();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User buscarPorId(@PathVariable Long id) {
        return userService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User atualizar(@PathVariable Long id, @RequestBody UserDto dto) {
        return userService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        userService.deletar(id);
    }
}
