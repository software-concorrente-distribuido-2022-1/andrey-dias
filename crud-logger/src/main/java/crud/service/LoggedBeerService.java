package crud.service;

import java.sql.SQLException;
import crud.domain.Beer;
import crud.dto.AuthenticatedRequest;
import crud.dto.BeerCreateDTO;
import crud.dto.BeerDeleteDTO;
import crud.dto.BeerReadDTO;
import crud.dto.BeerUpdateDTO;
import crud.dto.FindBeerByNameDTO;
import crud.dto.PurchaseBeerDTO;
import crud.repository.BeerPurchaseRepository;
import crud.repository.BeerRepository;
import database.Database;
import log.dto.LogRequestDTO;
import log.repository.PostgresLogRepository;
import log.service.DefaultLogService;
import log.service.LogService;

public class LoggedBeerService implements BeerService {
    private final LogService logService;
    private final DefaultBeerService beerService;

    public LoggedBeerService(final BeerRepository beerRepository, final BeerPurchaseRepository beerPurchaseRepository) {
        this.logService = new DefaultLogService(new PostgresLogRepository());
        this.beerService = new DefaultBeerService(beerRepository, beerPurchaseRepository);
    }

    @Override
    public void create(AuthenticatedRequest<BeerCreateDTO> request) {
        try {
            this.beerService.create(request);
            this.logService.log(
                    new LogRequestDTO(
                            request.getUserId(),
                            "Tentativa de criação de cerveja: [" + request.getPayload().getName() + "] realizada com sucesso"
                    )
            );
        } catch (Exception e) {
            this.logService.log(
                    new LogRequestDTO(
                            request.getUserId(),
                            "Tentativa de criação de cerveja: [" + request.getPayload().getName() + "] realizada, mas sem sucesso"
                    )
            );
            System.out.println("Error on creating beer");
        }
    }

    @Override
    public Beer read(AuthenticatedRequest<BeerReadDTO> request) {
        try {
            var beer = this.beerService.read(request);
            this.logService.log(
                    new LogRequestDTO(
                            request.getUserId(),
                            "Tentativa de leitura de cerveja para ID: [" + request.getPayload().getId() + "] realizada com sucesso"
                    )
            );
            return beer;
        } catch (Exception e) {
            this.logService.log(
                    new LogRequestDTO(
                            request.getUserId(),
                            "Tentativa de leitura de cerveja para ID: [" + request.getPayload().getId() + "] realizada, mas sem sucesso"
                    )
            );
            System.out.println("Error on reading beer");
        }
        return null;
    }

    @Override
    public void update(AuthenticatedRequest<BeerUpdateDTO> request) {
        try {
            this.beerService.update(request);
            this.logService.log(
                    new LogRequestDTO(
                            request.getUserId(),
                            "Tentativa de atualização de cerveja: [" + request.getPayload().getName() + "] feita com sucesso"
                    )
            );
        } catch (Exception e) {
            this.logService.log(
                    new LogRequestDTO(
                            request.getUserId(),
                            "Tentativa de atualização de cerveja: [" + request.getPayload().getName() + "] feita, mas sem sucesso"
                    )
            );
            System.out.println("Error on updating beer");
        }
    }

    @Override
    public void delete(AuthenticatedRequest<BeerDeleteDTO> request) {
        try {
            this.beerService.delete(request);
            this.logService.log(
                    new LogRequestDTO(
                            request.getUserId(),
                            "Tentativa de remoção de cerveja de ID: [" + request.getPayload().getId() + "] feita com sucesso"
                    )
            );
        } catch (Exception e) {
            this.logService.log(
                    new LogRequestDTO(
                            request.getUserId(),
                            "Tentativa de remoção de cerveja de ID: [" + request.getPayload().getId() + "] feita, mas sem sucesso"
                    )
            );
            System.out.println("Error on deleting beer");
        }
    }

    @Override
    public Beer findByName(AuthenticatedRequest<FindBeerByNameDTO> request) {
        try {
            var beer = this.beerService.findByName(request);
            this.logService.log(
                    new LogRequestDTO(
                            request.getUserId(),
                            "Tentativa de leitura de cerveja pelo nome: [" + request.getPayload().getName() + "] realizada com sucesso"
                    )
            );
            return beer;
        } catch (Exception e) {
            this.logService.log(
                    new LogRequestDTO(
                            request.getUserId(),
                            "Tentativa de leitura de cerveja pelo nome: [" + request.getPayload().getName() + "] realizada, mas sem sucesso"
                    )
            );
            System.out.println("Error on reading beer");
        }
        return null;
    }

    @Override
    public void purchase(AuthenticatedRequest<PurchaseBeerDTO> request) {
        try {
            this.beerService.purchase(request);
            this.logService.log(
                    new LogRequestDTO(
                            request.getUserId(),
                            "Tentativa de compra de cerveja de ID: [" + request.getPayload().getBeerId() + "] realizada com sucesso"
                    )
            );
        } catch (Exception e) {
            this.logService.log(
                    new LogRequestDTO(
                            request.getUserId(),
                            "Tentativa de compra de cerveja de ID: [" + request.getPayload().getBeerId() + "] realizada, mas sem sucesso"
                    )
            );
            System.out.println("Error on purchasing beer");
        }
    }
}
