import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

public class CurrencyConverterGUI {

    // Average exchange rates (Base: USD)
    private static final HashMap<String, Double> exchangeRates = new HashMap<>();
    static {
        exchangeRates.put("USD (United States)", 1.0);
        exchangeRates.put("EUR (Eurozone)", 0.92);
        exchangeRates.put("JPY (Japan)", 144.57);
        exchangeRates.put("GBP (United Kingdom)", 0.77);
        exchangeRates.put("AUD (Australia)", 1.47);
        exchangeRates.put("CAD (Canada)", 1.31);
        exchangeRates.put("CNY (China)", 7.29);
        exchangeRates.put("INR (India)", 82.78);
        exchangeRates.put("BRL (Brazil)", 4.92);
        exchangeRates.put("MXN (Mexico)", 17.07);
        exchangeRates.put("ZAR (South Africa)", 18.90);
        exchangeRates.put("RUB (Russia)", 97.25);
        exchangeRates.put("KRW (South Korea)", 1323.27);
        exchangeRates.put("IDR (Indonesia)", 15000.0);
        exchangeRates.put("TRY (Turkey)", 26.95);
        exchangeRates.put("SAR (Saudi Arabia)", 3.75);
        exchangeRates.put("ARG (Argentina)", 270.45);
    }


    private Image converterImage = new ImageIcon("currency.png").getImage(); // Update with your converter image path

    public static void main(String[] args) {
        new CurrencyConverterGUI();
    }

    public CurrencyConverterGUI() {
        JFrame frame = new JFrame("Currency Converter");
        frame.setSize(800, 400); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Set layout for the frame

        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);

        // Add top panel
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Add converter panel
        JPanel converterPanel = createConverterPanel();
        mainPanel.add(converterPanel, BorderLayout.CENTER);

        // Add about panel
        JPanel aboutPanel = createAboutPanel();
        mainPanel.add(aboutPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel(" Currency Converter");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        topPanel.add(titleLabel);
        return topPanel;
    }

    private JPanel createConverterPanel() {
        JPanel converterPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(converterImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        converterPanel.setLayout(new BorderLayout());
        converterPanel.setOpaque(false);

        JPanel inputPanel = new JPanel(new FlowLayout());
        JComboBox<String> fromCurrencyDropdown = new JComboBox<>(exchangeRates.keySet().toArray(new String[0]));
        JComboBox<String> toCurrencyDropdown = new JComboBox<>(exchangeRates.keySet().toArray(new String[0]));
        JTextField amountField = new JTextField(10);
        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(e -> convertCurrency(fromCurrencyDropdown, toCurrencyDropdown, amountField, converterPanel));

        inputPanel.add(new JLabel("From:"));
        inputPanel.add(fromCurrencyDropdown);
        inputPanel.add(new JLabel("To:"));
        inputPanel.add(toCurrencyDropdown);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(convertButton);

        converterPanel.add(inputPanel, BorderLayout.NORTH);

        return converterPanel;
    }

    private JPanel createAboutPanel() {
        JPanel aboutPanel = new JPanel();
        aboutPanel.setBackground(Color.LIGHT_GRAY);
        JLabel aboutLabel = new JLabel("About This App");
        aboutPanel.add(aboutLabel);
        return aboutPanel;
    }

    private void convertCurrency(JComboBox<String> fromCurrencyDropdown, JComboBox<String> toCurrencyDropdown,
                                 JTextField amountField, JPanel converterPanel) {
        String fromCurrency = (String) fromCurrencyDropdown.getSelectedItem();
        String toCurrency = (String) toCurrencyDropdown.getSelectedItem();
        double amount;

        try {
            amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                throw new NumberFormatException("Amount must be positive.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(converterPanel, "Please enter a valid positive numeric amount.");
            amountField.requestFocus();
            return;
        }

        double fromRate = exchangeRates.get(fromCurrency);
        double toRate = exchangeRates.get(toCurrency);
        double convertedAmount = amount * (toRate / fromRate);
        JOptionPane.showMessageDialog(converterPanel, String.format("Converted Amount: %.2f %s", convertedAmount, toCurrency),
                                  "Conversion Result", JOptionPane.INFORMATION_MESSAGE);
    }
}
