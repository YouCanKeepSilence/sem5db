package labs.currencies.DbStuff.Repositories.repositories;

import labs.currencies.DbStuff.Repositories.Entities.Currency;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends MongoRepository<Currency, String>, CurrencyAggregationRepository {

}
