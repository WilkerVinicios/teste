package com.br.teste.service;

import com.br.teste.dto.UserDto;
import com.br.teste.entity.User;
import com.br.teste.exception.UserNotFoundException;
import com.br.teste.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final String USER_NOT_FOUND = "Usuário não encontrado! Verifique o ID informado.";
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User salvar(UserDto dto) {
        return userRepository.save(new User(null, dto.getName(), dto.getEmail()));
    }

    public List<UserDto> listar() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail()))
                .toList();
    }

    public void deletar(Long id) {
        User user = buscarPorId(id);
        userRepository.delete(user);
    }

    public User atualizar(Long id, UserDto dto) {
        User existingUser = buscarPorId(id);
        existingUser.setName(dto.getName());
        existingUser.setEmail(dto.getEmail());
        return userRepository.save(existingUser);
    }

    public User buscarPorId(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(USER_NOT_FOUND)
        );
    }
}
