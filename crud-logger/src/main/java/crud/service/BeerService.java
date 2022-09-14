package crud.service;

import java.sql.SQLException;
import crud.domain.Beer;
import crud.dto.AuthenticatedRequest;
import crud.dto.BeerDeleteDTO;
import crud.dto.BeerReadDTO;
import crud.dto.BeerCreateDTO;
import crud.dto.BeerUpdateDTO;
import crud.dto.FindBeerByNameDTO;
import crud.dto.PurchaseBeerDTO;

public interface BeerService {
    void create(AuthenticatedRequest<BeerCreateDTO> request) throws SQLException;
    Beer read(AuthenticatedRequest<BeerReadDTO> request);
    void update(AuthenticatedRequest<BeerUpdateDTO> request);
    void delete(AuthenticatedRequest<BeerDeleteDTO> request);
    Beer findByName(AuthenticatedRequest<FindBeerByNameDTO> request);
    void purchase(AuthenticatedRequest<PurchaseBeerDTO> request) throws SQLException;
}
