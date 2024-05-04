package hexlet.code.service;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.dto.UserUpdateDTO;
import hexlet.code.exeption.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> getAll() {
        var users = userRepository.findAll();
        var result = users.stream()
                .map(userMapper::map)
                .toList();
        return result;
    }

    public UserDTO create(UserCreateDTO userData) {
        var user = userMapper.map(userData);
        userRepository.save(user);
        var userDto = userMapper.map(user);
        return userDto;
    }

    public UserDTO findById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        var userDto = userMapper.map(user);
        return userDto;
    }

    public UserDTO update(UserUpdateDTO userData, Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        userMapper.update(userData, user);
        userRepository.save(user);
        var userDto = userMapper.map(user);
        return userDto;
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
