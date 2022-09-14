package login.service;

import java.sql.SQLException;
import login.dto.LoginRequestDTO;
import login.dto.LoginResponseDTO;
import login.exception.AuthenticationException;
import login.exception.UserNotFoundException;
import login.repository.UserRepository;

public class DefaultLoginService implements LoginService {

    private final UserRepository userRepository;

    public DefaultLoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public synchronized LoginResponseDTO login(LoginRequestDTO request) throws SQLException {
        var user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);
        if(user.getPassword().equals(request.getPassword())) {
            return new LoginResponseDTO(user.getId(), "blablabla");
        }
        throw new AuthenticationException();
    }
}
