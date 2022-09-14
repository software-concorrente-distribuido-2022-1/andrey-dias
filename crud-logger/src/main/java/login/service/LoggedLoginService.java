package login.service;

import log.repository.PostgresLogRepository;
import log.service.DefaultLogService;
import log.dto.LogRequestDTO;
import log.service.LogService;
import login.dto.LoginRequestDTO;
import login.dto.LoginResponseDTO;
import login.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoggedLoginService implements LoginService {
    private final LogService logService;
    private final DefaultLoginService loginService;

    public LoggedLoginService(UserRepository userRepository) {
        this.logService = new DefaultLogService(new PostgresLogRepository());
        this.loginService = new DefaultLoginService(userRepository);
    }

    @Override
    public synchronized LoginResponseDTO login(LoginRequestDTO request) {
        try {
            var response = loginService.login(request);
            logService.log(
                    new LogRequestDTO(null, "Tentativa de login para e-mail [" + request.getEmail() + "] realizada com sucesso")
            );
            return response;
        } catch (Exception exception) {
            logService.log(
                    new LogRequestDTO(null, "Tentativa de login para e-mail [" + request.getEmail() + "] realizada, mas sem sucesso")
            );
            System.out.println("Error on request login");
        }
        return null;
    }

}
