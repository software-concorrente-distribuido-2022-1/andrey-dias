package login.service;

import java.sql.SQLException;
import login.dto.LoginRequestDTO;
import login.dto.LoginResponseDTO;

public interface LoginService {
    LoginResponseDTO login(LoginRequestDTO request) throws SQLException;
}
