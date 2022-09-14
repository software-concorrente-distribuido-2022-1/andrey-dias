import crud.dto.AuthenticatedRequest;
import crud.dto.FindBeerByNameDTO;
import crud.dto.PurchaseBeerDTO;
import crud.service.BeerService;
import login.dto.LoginRequestDTO;
import login.service.LoginService;

public class ClientOperation extends Thread {
    private final String email;
    private final String beerName;
    private final LoginService loginService;
    private final BeerService beerService;

    public ClientOperation(String email, String beerName, LoginService loginService, BeerService beerService) {
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
                var beer = this.beerService.findByName(
                        new AuthenticatedRequest<>(
                                response.getUserId(),
                                response.getToken(),
                                new FindBeerByNameDTO(beerName)
                        )
                );

                if (beer != null) {
                    this.beerService.purchase(
                            new AuthenticatedRequest<>(
                                    response.getUserId(),
                                    response.getToken(),
                                    new PurchaseBeerDTO(beer.getId(), beer.getPrice())
                            )
                    );
                }
            }

        } catch(Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("An error was founded during thread execution. User Email: [" + this.email + "]");
        }
    }
}
