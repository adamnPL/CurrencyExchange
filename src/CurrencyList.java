
public class CurrencyList {

	private String currencyCode;
	private String currencyName;
	private int currencyExchangeRate;
	private double currencyRate;

	public CurrencyList(String currencyCode, String currencyName, int currencyExchangeRate, double currencyRate) {

		this.currencyCode = currencyCode;
		this.currencyName = currencyName;
		this.currencyExchangeRate = currencyExchangeRate;
		this.currencyRate = currencyRate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public int getCurrencyExchangeRate() {
		return currencyExchangeRate;
	}

	public void setCurrencyExchangeRate(int currencyExchangeRate) {
		this.currencyExchangeRate = currencyExchangeRate;
	}

	public double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}

}
