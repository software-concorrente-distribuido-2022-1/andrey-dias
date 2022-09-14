package crud.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import crud.domain.Beer;

public class MemoryBeerRepository implements BeerRepository {
    private final List<Beer> beers = new ArrayList<>();

    @Override
    public void create(String name, BigDecimal price) {
        this.beers.add(new Beer(1L, name, price));
    }

    @Override
    public Optional<Beer> read(Long id) {
        return this.beers.stream()
                .filter(beer -> beer.getId().equals(id))
                .findFirst();
    }

    @Override
    public void update(Long id, String name, BigDecimal price) {
        var beerOptional = read(id);
        if (beerOptional.isPresent()) {
            var beer = beerOptional.get();
            beer.setName(name);
            beer.setPrice(price);
        }
    }

    @Override
    public void delete(Long id) {
        var beerOptional = read(id);
        beerOptional.ifPresent(this.beers::remove);
    }

    @Override
    public Optional<Beer> findByName(String name) {
        return Optional.empty();
    }
}
