import java.math.BigDecimal;

public class Currency {

	private String currencyCode;
	private String currencyName;
	private int currencyExchangeRate;
	private BigDecimal currencyRate;

	public Currency(String currencyCode, String currencyName, int currencyExchangeRate, BigDecimal currencyRate) {

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

	public BigDecimal getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String toString() {
		return getCurrencyCode() + " | " + getCurrencyName();
	}

}
