package labs.currencies;

import labs.currencies.DbStuff.Repositories.Entities.Currency;
import labs.currencies.DbStuff.Repositories.Entities.CurrencyAggregated;
import labs.currencies.DbStuff.Repositories.repositories.CurrencyRepository;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
import java.util.List;

// TODO csv parse and add;
// TODO aggregation

@SpringBootApplication
public class CurrenciesApplication implements CommandLineRunner {

	private CurrencyRepository repo;
	public CurrenciesApplication(CurrencyRepository repo) {
		this.repo = repo;
	}

	public static void main(String[] args) {
		SpringApplication.run(CurrenciesApplication.class, args);
	}

    /**
     * refresh all repository
     * @param sheetNames - имена валют
     * @throws Throwable
     */
	public void pushAllDataFormXls(String[] sheetNames) throws Throwable {
		HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream("/Users/silence/Workbench/Actual_Study/Production/Bondin stuff/Silence/db2/currencies2/currencies.xls"));
        List<Currency> list = new ArrayList<>();
		for(String sheet : sheetNames) {
			HSSFSheet myExcelSheet = myExcelBook.getSheet(sheet);
			for (Row row : myExcelSheet) {
				Date date = new Date();
				double rate = 0.0;
				if (row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					 date = row.getCell(0).getDateCellValue();

				}

				if (row.getCell(2).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					rate = row.getCell(2).getNumericCellValue();
				}
				if (rate == 0.0 ) {
				    continue;
                }
				Currency currency = new Currency(sheet + "/RUB", date, rate);
				list.add(currency);
			}
		}
		this.repo.deleteAll();
		this.repo.saveAll(list);
		myExcelBook.close();
	}

	@Override
	public void run(String ...args) throws Exception {
//        String[] strArr = { "USD" , "EUR" , "AUD", "TRY" , "SEK", "RON"};
//        try {
//            this.pushAllDataFormXls(strArr);
//        } catch (Throwable err) {
//            System.out.println(err);
//        }
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");//задаю формат даты
		Date dateFrom = formatter.parse("29.11.2015");
		Date dateTo = formatter.parse("29.11.2018");
		List<CurrencyAggregated> values = this.repo.getInfoForCurrencies(dateFrom, dateTo);
		values.forEach((CurrencyAggregated x) -> System.out.println(x));

	}
}
