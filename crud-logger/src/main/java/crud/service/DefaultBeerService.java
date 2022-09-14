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
import crud.exception.BeerNotFoundException;
import crud.repository.BeerPurchaseRepository;
import crud.repository.BeerRepository;

public class DefaultBeerService implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerPurchaseRepository beerPurchaseRepository;

    public DefaultBeerService(BeerRepository beerRepository, BeerPurchaseRepository beerPurchaseRepository) {
        this.beerRepository = beerRepository;
        this.beerPurchaseRepository = beerPurchaseRepository;
    }

    @Override
    public void create(AuthenticatedRequest<BeerCreateDTO> request) throws SQLException {
        this.beerRepository.create(request.getPayload().getName(), request.getPayload().getPrice());
    }

    @Override
    public Beer read(AuthenticatedRequest<BeerReadDTO> request) {
        return this.beerRepository.read(request.getPayload().getId())
                .orElseThrow(BeerNotFoundException::new);
    }

    @Override
    public void update(AuthenticatedRequest<BeerUpdateDTO> request) {
        this.beerRepository.update(
                request.getPayload().getId(),
                request.getPayload().getName(),
                request.getPayload().getPrice()
        );
    }

    @Override
    public void delete(AuthenticatedRequest<BeerDeleteDTO> request) {
        this.beerRepository.delete(
                request.getPayload().getId()
        );
    }

    @Override
    public Beer findByName(AuthenticatedRequest<FindBeerByNameDTO> request) {
        return this.beerRepository.findByName(request.getPayload().getName())
                .orElseThrow(BeerNotFoundException::new);
    }

    @Override
    public void purchase(AuthenticatedRequest<PurchaseBeerDTO> request) throws SQLException {
        this.beerPurchaseRepository.create(
                request.getUserId(), request.getPayload().getBeerId(), request.getPayload().getTotalValue());
    }
}
