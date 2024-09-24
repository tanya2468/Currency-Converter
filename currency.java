import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CurrencyConverter {

    private static Map<String, Double> exchangeRates;
    private static DefaultListModel<String> historyModel = new DefaultListModel<>();
    private static JPanel historyPanel; // Panel to toggle visibility
    private static boolean isDarkMode = false; // Dark mode flag

    static {
        exchangeRates = new HashMap<>();
        exchangeRates.put("INR-USD", 0.012);
        exchangeRates.put("INR-EUR", 0.011);
        exchangeRates.put("INR-GBP", 0.0096);
        exchangeRates.put("INR-JPY", 1.86);
        exchangeRates.put("USD-INR", 83.29);
        exchangeRates.put("USD-EUR", 0.93);
        exchangeRates.put("USD-GBP", 0.80);
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
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Custom fonts
        Font titleFont = new Font("Arial", Font.BOLD, 30);
        Font amountFont = new Font("Arial", Font.BOLD, 14);

        // Main Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(135, 206, 235));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Currency Converter");
        titleLabel.setFont(titleFont);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        inputPanel.add(titleLabel, constraints);

        constraints.gridwidth = 1;

        // Amount Label & Field
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(amountFont);
        constraints.gridx = 0;
        constraints.gridy = 1;
        inputPanel.add(amountLabel, constraints);

        JTextField amountField = new JTextField(10);
        constraints.gridx = 1;
        inputPanel.add(amountField, constraints);

        // Source Currency Dropdown
        JLabel sourceCurrencyLabel = new JLabel("Source Currency:");
        sourceCurrencyLabel.setFont(amountFont);
        constraints.gridx = 0;
        constraints.gridy = 2;
        inputPanel.add(sourceCurrencyLabel, constraints);

        JComboBox<String> sourceCurrencyDropdown = new JComboBox<>(new String[]{"INR", "USD", "EUR", "GBP", "JPY"});
        constraints.gridx = 1;
        inputPanel.add(sourceCurrencyDropdown, constraints);

        // Target Currency 1 Dropdown
        JLabel targetCurrency1Label = new JLabel("Target Currency 1:");
        targetCurrency1Label.setFont(amountFont);
        constraints.gridx = 0;
        constraints.gridy = 3;
        inputPanel.add(targetCurrency1Label, constraints);

        JComboBox<String> targetCurrency1Dropdown = new JComboBox<>(new String[]{"INR", "USD", "EUR", "GBP", "JPY"});
        constraints.gridx = 1;
        inputPanel.add(targetCurrency1Dropdown, constraints);

        // Target Currency 2 Dropdown
        JLabel targetCurrency2Label = new JLabel("Target Currency 2:");
        targetCurrency2Label.setFont(amountFont);
        constraints.gridx = 0;
        constraints.gridy = 4;
        inputPanel.add(targetCurrency2Label, constraints);

        JComboBox<String> targetCurrency2Dropdown = new JComboBox<>(new String[]{"INR", "USD", "EUR", "GBP", "JPY"});
        constraints.gridx = 1;
        inputPanel.add(targetCurrency2Dropdown, constraints);

        // Target Currency 3 Dropdown
        JLabel targetCurrency3Label = new JLabel("Target Currency 3:");
        targetCurrency3Label.setFont(amountFont);
        constraints.gridx = 0;
        constraints.gridy = 5;
        inputPanel.add(targetCurrency3Label, constraints);

        JComboBox<String> targetCurrency3Dropdown = new JComboBox<>(new String[]{"INR", "USD", "EUR", "GBP", "JPY"});
        constraints.gridx = 1;
        inputPanel.add(targetCurrency3Dropdown, constraints);

        // Convert Button
        JButton convertButton = new JButton("Convert");
        convertButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        convertButton.setForeground(Color.BLACK);
        convertButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        inputPanel.add(convertButton, constraints);

        // Show/Hide History Button
        JButton toggleHistoryButton = new JButton("Show/Hide History");
        toggleHistoryButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        toggleHistoryButton.setForeground(Color.BLACK);
        toggleHistoryButton.setFocusPainted(false);
        constraints.gridy = 7;
        inputPanel.add(toggleHistoryButton, constraints);

        // Clear History Button
        JButton clearHistoryButton = new JButton("Clear History");
        clearHistoryButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        clearHistoryButton.setForeground(Color.BLACK);
        clearHistoryButton.setFocusPainted(false);
        constraints.gridy = 8;
        inputPanel.add(clearHistoryButton, constraints);

        // Dark Mode Button
        JButton darkModeButton = new JButton("Dark Mode");
        darkModeButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        darkModeButton.setForeground(Color.BLACK);
        darkModeButton.setFocusPainted(false);
        constraints.gridy = 9;
        inputPanel.add(darkModeButton, constraints);

        // Result Panel
        JPanel resultPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        resultPanel.setBackground(new Color(135, 206, 235));

        JLabel resultLabel1 = new JLabel();
        resultLabel1.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel resultLabel2 = new JLabel();
        resultLabel2.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel resultLabel3 = new JLabel();
        resultLabel3.setFont(new Font("Arial", Font.BOLD, 16));

        resultPanel.add(resultLabel1);
        resultPanel.add(resultLabel2);
        resultPanel.add(resultLabel3);

        // History Panel
        historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Conversion History"));
        historyPanel.setVisible(false); // Initially hidden

        JList<String> historyList = new JList<>(historyModel);
        JScrollPane historyScrollPane = new JScrollPane(historyList);
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);

        frame.add(historyPanel, BorderLayout.SOUTH);

        // Action Listeners

        // Real-time conversion logic
        DocumentListener realTimeConversionListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateConversion(amountField, sourceCurrencyDropdown, targetCurrency1Dropdown, targetCurrency2Dropdown, targetCurrency3Dropdown, resultLabel1, resultLabel2, resultLabel3);
            }

            public void removeUpdate(DocumentEvent e) {
                updateConversion(amountField, sourceCurrencyDropdown, targetCurrency1Dropdown, targetCurrency2Dropdown, targetCurrency3Dropdown, resultLabel1, resultLabel2, resultLabel3);
            }

            public void changedUpdate(DocumentEvent e) {
                updateConversion(amountField, sourceCurrencyDropdown, targetCurrency1Dropdown, targetCurrency2Dropdown, targetCurrency3Dropdown, resultLabel1, resultLabel2, resultLabel3);
            }
        };

        // Apply the real-time listener to the relevant fields
        amountField.getDocument().addDocumentListener(realTimeConversionListener);
        sourceCurrencyDropdown.addActionListener(e -> updateConversion(amountField, sourceCurrencyDropdown, targetCurrency1Dropdown, targetCurrency2Dropdown, targetCurrency3Dropdown, resultLabel1, resultLabel2, resultLabel3));
        targetCurrency1Dropdown.addActionListener(e -> updateConversion(amountField, sourceCurrencyDropdown, targetCurrency1Dropdown, targetCurrency2Dropdown, targetCurrency3Dropdown, resultLabel1, resultLabel2, resultLabel3));
        targetCurrency2Dropdown.addActionListener(e -> updateConversion(amountField, sourceCurrencyDropdown, targetCurrency1Dropdown, targetCurrency2Dropdown, targetCurrency3Dropdown, resultLabel1, resultLabel2, resultLabel3));
        targetCurrency3Dropdown.addActionListener(e -> updateConversion(amountField, sourceCurrencyDropdown, targetCurrency1Dropdown, targetCurrency2Dropdown, targetCurrency3Dropdown, resultLabel1, resultLabel2, resultLabel3));

        // Convert Button Action
        convertButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String sourceCurrency = (String) sourceCurrencyDropdown.getSelectedItem();
                String targetCurrency1 = (String) targetCurrency1Dropdown.getSelectedItem();
                String targetCurrency2 = (String) targetCurrency2Dropdown.getSelectedItem();
                String targetCurrency3 = (String) targetCurrency3Dropdown.getSelectedItem();

                double convertedAmount1 = convertCurrency(amount, sourceCurrency, targetCurrency1);
                double convertedAmount2 = convertCurrency(amount, sourceCurrency, targetCurrency2);
                double convertedAmount3 = convertCurrency(amount, sourceCurrency, targetCurrency3);

                String result1 = amount + " " + sourceCurrency + " = " + convertedAmount1 + " " + targetCurrency1;
                String result2 = amount + " " + sourceCurrency + " = " + convertedAmount2 + " " + targetCurrency2;
                String result3 = amount + " " + sourceCurrency + " = " + convertedAmount3 + " " + targetCurrency3;

                resultLabel1.setText(result1);
                resultLabel2.setText(result2);
                resultLabel3.setText(result3);

                // Add to history
                historyModel.addElement(result1);
                historyModel.addElement(result2);
                historyModel.addElement(result3);
            } catch (NumberFormatException ex) {
                resultLabel1.setText("Invalid input");
                resultLabel2.setText("Invalid input");
                resultLabel3.setText("Invalid input");
            }
        });

        // Show/Hide History Button Action
        toggleHistoryButton.addActionListener(e -> {
            historyPanel.setVisible(!historyPanel.isVisible());
            frame.revalidate();
        });

        // Clear History Button Action
        clearHistoryButton.addActionListener(e -> historyModel.clear());

        // Dark Mode Button Action
        darkModeButton.addActionListener(e -> {
            isDarkMode = !isDarkMode;
            if (isDarkMode) {
                inputPanel.setBackground(Color.DARK_GRAY);
                resultPanel.setBackground(Color.DARK_GRAY);
                historyPanel.setBackground(Color.WHITE);
                frame.getContentPane().setBackground(Color.BLACK);
                titleLabel.setForeground(Color.WHITE);
                amountLabel.setForeground(Color.WHITE);
                sourceCurrencyLabel.setForeground(Color.WHITE);
                targetCurrency1Label.setForeground(Color.WHITE);
                targetCurrency2Label.setForeground(Color.WHITE);
                targetCurrency3Label.setForeground(Color.WHITE);
                resultLabel1.setForeground(Color.WHITE);
                resultLabel2.setForeground(Color.WHITE);
                resultLabel3.setForeground(Color.WHITE);
                toggleHistoryButton.setForeground(Color.BLACK);
                clearHistoryButton.setForeground(Color.BLACK);
                darkModeButton.setForeground(Color.BLACK);
                convertButton.setBackground(Color.GRAY);
                toggleHistoryButton.setBackground(Color.GRAY);
                clearHistoryButton.setBackground(Color.GRAY);
                darkModeButton.setBackground(Color.GRAY);
                amountField.setBackground(Color.WHITE);
                amountField.setForeground(Color.BLACK);
            } 
            else {
                inputPanel.setBackground(new Color(135, 206, 235));
                resultPanel.setBackground(new Color(135, 206, 235));
                historyPanel.setBackground(new Color(135, 206, 235));
                frame.getContentPane().setBackground(Color.WHITE);
                titleLabel.setForeground(Color.BLACK);
                amountLabel.setForeground(Color.BLACK);
                sourceCurrencyLabel.setForeground(Color.BLACK);
                targetCurrency1Label.setForeground(Color.BLACK);
                targetCurrency2Label.setForeground(Color.BLACK);
                targetCurrency3Label.setForeground(Color.BLACK);
                resultLabel1.setForeground(Color.BLACK);
                resultLabel2.setForeground(Color.BLACK);
                resultLabel3.setForeground(Color.BLACK);
                toggleHistoryButton.setForeground(Color.BLACK);
                clearHistoryButton.setForeground(Color.BLACK);
                darkModeButton.setForeground(Color.BLACK);
                convertButton.setBackground(new Color(70, 130, 180)); // Steel Blue
                toggleHistoryButton.setBackground(new Color(70, 130, 180)); // Steel Blue
                clearHistoryButton.setBackground(new Color(70, 130, 180)); // Steel Blue
                darkModeButton.setBackground(new Color(70, 130, 180)); // Steel Blue
                amountField.setBackground(Color.WHITE);
                amountField.setForeground(Color.BLACK);
            }
            frame.revalidate();
        });

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(resultPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }

    private static void updateConversion(JTextField amountField, JComboBox<String> sourceCurrencyDropdown,
      JComboBox<String> targetCurrency1Dropdown, JComboBox<String> targetCurrency2Dropdown,
      JComboBox<String> targetCurrency3Dropdown,
       JLabel resultLabel1, JLabel resultLabel2, JLabel resultLabel3) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String sourceCurrency = (String) sourceCurrencyDropdown.getSelectedItem();
            String targetCurrency1 = (String) targetCurrency1Dropdown.getSelectedItem();
            String targetCurrency2 = (String) targetCurrency2Dropdown.getSelectedItem();
            String targetCurrency3 = (String) targetCurrency3Dropdown.getSelectedItem();

            double convertedAmount1 = convertCurrency(amount, sourceCurrency, targetCurrency1);
            double convertedAmount2 = convertCurrency(amount, sourceCurrency, targetCurrency2);
            double convertedAmount3 = convertCurrency(amount, sourceCurrency, targetCurrency3);

            resultLabel1.setText(amount + " " + sourceCurrency + " = " + convertedAmount1 + " " + targetCurrency1);
            resultLabel2.setText(amount + " " + sourceCurrency + " = " + convertedAmount2 + " " + targetCurrency2);
            resultLabel3.setText(amount + " " + sourceCurrency + " = " + convertedAmount3 + " " + targetCurrency3);
        } 
        catch (NumberFormatException ex) {
            resultLabel1.setText("Invalid input");
            resultLabel2.setText("Invalid input");
            resultLabel3.setText("Invalid input");
        }
    }

    private static double convertCurrency(double amount, String sourceCurrency, String targetCurrency) {
        String key = sourceCurrency + "-" + targetCurrency;
        return exchangeRates.getOrDefault(key, 1.0) * amount;
    }
}
