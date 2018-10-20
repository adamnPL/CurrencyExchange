import java.awt.Component;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

public class MyComboBox extends JComboBox {
	static final long serialVersionUID = 1L;

	public MyComboBox(List<CurrencyList> list) {
		super(list.toArray());

		if (!list.isEmpty()) {
			this.setRenderer(new ComboCustomRender());
		}

	}

	private class ComboCustomRender extends DefaultListCellRenderer {
		static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			CurrencyList currency = (CurrencyList) value;

			label.setText(currency.getCurrencyCode() + " | " + currency.getCurrencyName());
			return label;
		}

	}
}
