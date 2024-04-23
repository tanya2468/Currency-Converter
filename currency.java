import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class project {

    private static Map<String, Double> exchangeRates;

    static {
        exchangeRates = new HashMap<>();
        exchangeRates.put("INR-USD", 0.012);  // 1 INR = 0.014 USD
        exchangeRates.put("INR-EUR", 0.011);  // 1 INR = 0.013 EUR
        exchangeRates.put("INR-GBP", 0.0096);  // 1 INR = 0.011 GBP
        exchangeRates.put("INR-JPY", 1.86);   // 1 INR = 1.52 JPY

        exchangeRates.put("USD-INR", 83.29);
        exchangeRates.put("USD-EUR", 0.93);  // 1 USD = 0.85 EUR
        exchangeRates.put("USD-GBP", 0.80);  // 1 USD = 0.73 GBP
        exchangeRates.put("USD-JPY", 154.82);

        exchangeRates.put("EUR-INR", 89.10);
        exchangeRates.put("EUR-USD", 1.07);
        exchangeRates.put("EUR-GBP", 0.86);
        exchangeRates.put("EUR-JPY", 165.65);

        exchangeRates.put("GBP-INR", 103.62);
        exchangeRates.put("GBP-USD", 1.24);
        exchangeRates.put("GBP-EUR", 1.16);
        exchangeRates.put("GBP-JPY", 192.67);
       
        exchangeRates.put("JPY-INR", 0.54);
        exchangeRates.put("JPY-USD", 0.0065);
        exchangeRates.put("JPY-EUR", 0.0060);
        exchangeRates.put("JPY-GBP", 0.0052);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Currency Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.pink); // Set background color

        // Create a custom font for titles
        Font titleFont = new Font("Arial", Font.BOLD, 20);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBounds(0,0,500,500);
        inputPanel.setBackground(Color.pink); // Set background color

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Currency Converter");
        titleLabel.setFont(titleFont);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        inputPanel.add(titleLabel, constraints);

        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;

        JLabel amountLabel = new JLabel("Amount:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        inputPanel.add(amountLabel, constraints);

        JTextField amountField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 1;
        inputPanel.add(amountField, constraints);

        JLabel sourceCurrencyLabel = new JLabel("Source Currency:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        inputPanel.add(sourceCurrencyLabel, constraints);

        JComboBox<String> sourceCurrencyDropdown = new JComboBox<>(new String[]{"INR", "USD", "EUR", "GBP","JPY"});
        constraints.gridx = 1;
        constraints.gridy = 2;
        inputPanel.add(sourceCurrencyDropdown, constraints);

        JLabel targetCurrency1Label = new JLabel("Target Currency 1:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        inputPanel.add(targetCurrency1Label, constraints);

        JComboBox<String> targetCurrency1Dropdown = new JComboBox<>(new String[]{"INR", "USD", "EUR", "GBP", "JPY"});
        constraints.gridx = 1;
        constraints.gridy = 3;
        inputPanel.add(targetCurrency1Dropdown, constraints);

        JLabel targetCurrency2Label = new JLabel("Target Currency 2:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        inputPanel.add(targetCurrency2Label, constraints);

        JComboBox<String> targetCurrency2Dropdown = new JComboBox<>(new String[]{"INR", "USD", "EUR", "GBP", "JPY"});
        constraints.gridx = 1;
        constraints.gridy = 4;
        inputPanel.add(targetCurrency2Dropdown, constraints);
        
        JLabel targetCurrency3Label = new JLabel("Target Currency 3:");
        constraints.gridx = 0;
        constraints.gridy = 5; // Update the grid y position
        inputPanel.add(targetCurrency3Label, constraints);

        JComboBox<String> targetCurrency3Dropdown = new JComboBox<>(new String[]{"INR", "USD", "EUR", "GBP", "JPY"});
        constraints.gridx = 1;
        constraints.gridy = 5; // Update the grid y position
        inputPanel.add(targetCurrency3Dropdown, constraints);

        JButton convertButton = new JButton("Convert");
        convertButton.setBackground(new Color(51, 153, 255));
        convertButton.setForeground(Color.BLACK);
        convertButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 6; // Update the grid y position
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        inputPanel.add(convertButton, constraints);

        JPanel resultPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // Update GridLayout to accommodate the third result label
        resultPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        resultPanel.setBackground(Color.pink); // Set background color

        JLabel resultLabel1 = new JLabel();
        resultLabel1.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel resultLabel2 = new JLabel();
        resultLabel2.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel resultLabel3 = new JLabel(); // Declare resultLabel3
        resultLabel3.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for resultLabel3

        resultPanel.add(resultLabel1);
        resultPanel.add(resultLabel2);
        resultPanel.add(resultLabel3); // Add resultLabel3 to resultPanel

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    String sourceCurrency = (String) sourceCurrencyDropdown.getSelectedItem();
                    String targetCurrency1 = (String) targetCurrency1Dropdown.getSelectedItem();
                    String targetCurrency2 = (String) targetCurrency2Dropdown.getSelectedItem();
                    String targetCurrency3 = (String) targetCurrency3Dropdown.getSelectedItem(); // Add this line

                    double convertedAmount1 = convertCurrency(amount, sourceCurrency, targetCurrency1);
                    double convertedAmount2 = convertCurrency(amount, sourceCurrency, targetCurrency2);
                    double convertedAmount3 = convertCurrency(amount, sourceCurrency, targetCurrency3); // Calculate conversion for the third currency
                    String result1 = amount + " " + sourceCurrency + " = " + convertedAmount1 + " " + targetCurrency1;
                    String result2 = amount + " " + sourceCurrency + " = " + convertedAmount2 + " " + targetCurrency2;
                    String result3 = amount + " " + sourceCurrency + " = " + convertedAmount3 + " " + targetCurrency3; // Format result for the third currency
                    resultLabel1.setText(result1);
                    resultLabel2.setText(result2);
                    resultLabel3.setText(result3); // Display result for the third currency

                } catch (NumberFormatException ex) {
                    resultLabel1.setText("Invalid input!");
                    resultLabel2.setText("Invalid input!");
                    resultLabel3.setText("Invalid input!"); // Handle invalid input for the third currency
                }
            }
        });

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(resultPanel, BorderLayout.EAST);

        frame.setVisible(true);
    }

    private static double convertCurrency(double amount, String sourceCurrency, String targetCurrency) {
        String currencyPair = sourceCurrency + "-" + targetCurrency;
        double exchangeRate = exchangeRates.get(currencyPair);

        if (exchangeRate != 0) {
            return amount * exchangeRate;
        } else {
            // If direct conversion is not available, try converting through INR
            double inrAmount;
            if (sourceCurrency.equals("INR")) {
                inrAmount = amount;
            } else {
                inrAmount = amount / exchangeRates.get("INR-" + sourceCurrency);
            }
            return inrAmount * exchangeRates.get("INR-" + targetCurrency);
        }
    }
}
