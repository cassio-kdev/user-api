package br.com.kdev.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.kdev.dto.UserDTO;
import br.com.kdev.exception.UserNotFoundException;
import br.com.kdev.model.User;
import br.com.kdev.model.dto.DTOConverter;
import br.com.kdev.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<UserDTO> getAll() {

		List<User> usuarios = userRepository.findAll();

		return usuarios.stream().map(DTOConverter::convert).collect(Collectors.toList());
	}

	public UserDTO findById(long userId) {
		Optional<User> usuario = userRepository.findById(userId);
		if (usuario.isPresent()) {
			return DTOConverter.convert(usuario.get());
		}
		throw new UserNotFoundException();
	}

	public UserDTO save(UserDTO userDTO) {
		userDTO.setKey(UUID.randomUUID().toString());
		User user = userRepository.save(User.convert(userDTO));
		return DTOConverter.convert(user);
	}

	public DTOConverter delete(long userId) {

		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			userRepository.delete(user.get());
		}
		throw new UserNotFoundException();
	}

	public UserDTO findByCpf(String cpf, String key) {
		User user = userRepository.findByCpfAndKey(cpf, key);
		if (user != null) {
			return DTOConverter.convert(user);
		}
		throw new UserNotFoundException();
	}

	public List<UserDTO> queryByName(String name) {
		List<User> usuarios = userRepository.queryByNomeLike(name);
		return usuarios.stream().map(DTOConverter::convert).collect(Collectors.toList());
	}
}
