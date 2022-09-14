import java.math.BigDecimal;
import crud.dto.AuthenticatedRequest;
import crud.dto.BeerCreateDTO;
import crud.dto.BeerDeleteDTO;
import crud.dto.BeerReadDTO;
import crud.dto.BeerUpdateDTO;
import crud.service.BeerService;
import login.dto.LoginRequestDTO;
import login.service.LoginService;

public class AdminOperation extends Thread {
    private final String email;
    private final String beerName;
    private final LoginService loginService;
    private final BeerService beerService;

    public AdminOperation(String email, String beerName, LoginService loginService, BeerService beerService) {
        this.email = email;
        this.beerName = beerName;
        this.loginService = loginService;
        this.beerService = beerService;
    }

    @Override
    public void run() {
        try {
            var response = loginService.login(new LoginRequestDTO(this.email, "123412"));
            if (response != null) {
                this.beerService.create(
                        new AuthenticatedRequest<>(
                                response.getUserId(),
                                response.getToken(),
                                new BeerCreateDTO(beerName, new BigDecimal(20))
                        )
                );
                this.beerService.read(
                        new AuthenticatedRequest<>(
                                response.getUserId(),
                                response.getToken(),
                                new BeerReadDTO(1L)
                        )
                );
                this.beerService.delete(
                        new AuthenticatedRequest<>(
                                response.getUserId(),
                                response.getToken(),
                                new BeerDeleteDTO(1L)
                        )
                );
                this.beerService.update(
                        new AuthenticatedRequest<>(
                                response.getUserId(),
                                response.getToken(),
                                new BeerUpdateDTO(1000L, "Hello", new BigDecimal(20))
                        )
                );
            }

        } catch(Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("An error was founded during thread execution. User Email: [" + this.email + "]");
        }

    }
}
