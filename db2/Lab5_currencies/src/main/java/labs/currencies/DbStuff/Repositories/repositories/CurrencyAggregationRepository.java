package labs.currencies.DbStuff.Repositories.repositories;

import labs.currencies.DbStuff.Repositories.Entities.CurrencyAggregated;

import java.util.Date;
import java.util.List;

public interface CurrencyAggregationRepository {
    List<CurrencyAggregated> getInfoForCurrencies(Date dateFrom, Date dateTo);
}
