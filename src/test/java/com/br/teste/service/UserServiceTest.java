package com.br.teste.service;

import com.br.teste.dto.UserDto;
import com.br.teste.entity.User;
import com.br.teste.exception.UserNotFoundException;
import com.br.teste.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarUsuario() {
        UserDto userDto = new UserDto();
        userDto.setName("Test User");
        userDto.setEmail("testuser@gmail.com");
        User user = new User(1L, userDto.getName(), userDto.getEmail());

        when(userRepository.save(any())).thenReturn(user);
        User createdUser = userService.salvar(userDto);

        assertEquals("Test User", createdUser.getName());
        assertEquals("testuser@gmail.com", createdUser.getEmail());
    }

    @Test
    void deveListarUsuarios() {
        List<User> users = List.of(
                new User(1L, "User One", "userone@gmail.com"),
                new User(2L, "User Two", "usertwo@gmail.com")
        );
        when(userRepository.findAll()).thenReturn(users);
        List<UserDto> userList = userService.listar();

        assertEquals(2, userList.size());
    }

    @Test
    void deveBuscarPorId() {
        User user = new User(1L, "User One", "userone@gmail.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User foundUser = userService.buscarPorId(1L);

        assertEquals("User One", foundUser.getName());
        assertEquals("userone@gmail.com", foundUser.getEmail());
    }

    @Test
    void deveLancarExcecaoSeNaoEncontrarPorId() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.buscarPorId(99L));
    }

    @Test
    void deveAtualizarUsuario() {
        User existingUser = new User(1L, "Old Name", "old@mail.com");
        UserDto userDto = new UserDto(null,"Updated Name", "updated@mail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any())).thenReturn(new User(1L, userDto.getName(), userDto.getEmail()));

        User updatedUser = userService.atualizar(1L, userDto);

        assertEquals("Updated Name", updatedUser.getName());
        assertEquals("updated@mail.com", updatedUser.getEmail());
    }

    @Test
    void deveDeletarUsuario() {
        User user = new User(1L, "User One", "userone@gmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deletar(1L);

        verify(userRepository, times(1)).delete(user);
    }
}