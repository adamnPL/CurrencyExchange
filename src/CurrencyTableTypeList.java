import java.util.ArrayList;
import java.util.List;

public class CurrencyTableTypeList {

	List<CurrencyTableType> currencyTabList = new ArrayList<CurrencyTableType>();

	public CurrencyTableTypeList() {
		currencyTabList.add(new CurrencyTableType("a", " tabela kurs�w �rednich walut obcych"));
		currencyTabList.add(new CurrencyTableType("b", " tabela kurs�w �rednich walut niewymienialnych"));
		currencyTabList.add(new CurrencyTableType("c", " tabela kurs�w kupna i sprzeda�y"));
		currencyTabList.add(new CurrencyTableType("h", " tabela kurs�w jednostek rozliczeniowych"));

	}

	public Object[] getCurrencyTabList() {
		return currencyTabList.toArray();
	}

}
