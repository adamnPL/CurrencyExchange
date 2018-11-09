import java.util.ArrayList;
import java.util.List;

public class CurrencyTableTypeList {

	List<CurrencyTableType> currencyTabList = new ArrayList<CurrencyTableType>();

	public CurrencyTableTypeList() {
		currencyTabList.add(new CurrencyTableType("a", " tabela kursów œrednich walut obcych"));
		currencyTabList.add(new CurrencyTableType("b", " tabela kursów œrednich walut niewymienialnych"));
		currencyTabList.add(new CurrencyTableType("c", " tabela kursów kupna i sprzeda¿y"));
		currencyTabList.add(new CurrencyTableType("h", " tabela kursów jednostek rozliczeniowych"));

	}

	public Object[] getCurrencyTabList() {
		return currencyTabList.toArray();
	}

}
