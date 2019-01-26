/*
 * Java GUI for Number Converter.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NumberBaseConverterGUI {
    private JFrame frm = new JFrame("Number Base Converter");
    private JPanel pnlMain = new JPanel();

    private JTextField decimalTxt = new JTextField(20);
    private JTextField binaryTxt = new JTextField(20);
    private JTextField octalTxt = new JTextField(20);
    private JTextField hexadecimalTxt = new JTextField(20);
    private JTextField charactersTxt = new JTextField(20);
    private JTextField colorTxt = new JTextField(20);
    private JTextField floatDecimalTxt = new JTextField(20);

    private JLabel decimalLbl = new JLabel("Decimal", SwingConstants.CENTER);
    private JLabel binaryLbl = new JLabel("Binary", SwingConstants.CENTER);
    private JLabel octalLbl = new JLabel("Octal", SwingConstants.CENTER);
    private JLabel hexadecimalLbl = new JLabel("Hexadecimal", SwingConstants.CENTER);
    private JLabel charactersLbl = new JLabel("Character(s)", SwingConstants.CENTER);
    private JLabel colorLbl = new JLabel("Color", SwingConstants.CENTER);
    private JLabel floatDecimalLbl = new JLabel("Float Decimal", SwingConstants.CENTER);

    private JButton convertBtn = new JButton("CONVERT");
    private JButton chooseBtn = new JButton("Choose Color");
    private JButton clearBtn = new JButton("Clear");

    public NumberBaseConverterGUI() {
        pnlMain.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;


        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.insets = new Insets(5,10,0,0);
        pnlMain.add(decimalLbl, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlMain.add(binaryLbl, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlMain.add(octalLbl, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        pnlMain.add(hexadecimalLbl, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        pnlMain.add(charactersLbl, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 5;
        pnlMain.add(colorLbl, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 6;
        pnlMain.add(floatDecimalLbl, gbc);


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        pnlMain.add(decimalTxt, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        pnlMain.add(binaryTxt, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        pnlMain.add(octalTxt, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 3;
        pnlMain.add(hexadecimalTxt, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 4;
        pnlMain.add(charactersTxt, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 5;
        pnlMain.add(colorTxt, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 6;
        pnlMain.add(floatDecimalTxt, gbc);


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.ipady = 20;
        pnlMain.add(convertBtn, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 0;
        gbc.gridx = 4;
        gbc.gridy = 5;
        pnlMain.add(chooseBtn, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.ipady = 10;
        pnlMain.add(clearBtn, gbc);


        frm.add(pnlMain);

        // Clear all text fields
        clearBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                decimalTxt.setText("");
                binaryTxt.setText("");
                octalTxt.setText("");
                hexadecimalTxt.setText("");
                charactersTxt.setText("");
                colorTxt.setBackground(null);
                floatDecimalTxt.setText("");
            }
        });

        // Convert entered input
        convertBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                BaseConverter bc = new BaseConverter();

                // Check which text field is populated and execute conversions
                if (!decimalTxt.getText().isEmpty()) {
                    bc.setDecimal(decimalTxt.getText());
                } else if (!binaryTxt.getText().isEmpty()) {
                    bc.setBinary(binaryTxt.getText());
                } else if (!octalTxt.getText().isEmpty()) {
                    bc.setOctal(decimalTxt.getText());
                } else if (!hexadecimalTxt.getText().isEmpty()) {
                    bc.setHexadecimal(hexadecimalTxt.getText());
                } else if (!charactersTxt.getText().isEmpty()) {
                    bc.setCharacters(charactersTxt.getText());
                } else if (!floatDecimalTxt.getText().isEmpty()) {
                    bc.setFloatDecimal(floatDecimalTxt.getText());
                } else if (colorTxt.getBackground() != null) {
                    bc.setColor("" + (colorTxt.getBackground().getRGB() & 0xFFFFFF));
                }

                // Set text fields with converted values
                decimalTxt.setText(bc.getDecimal());
                binaryTxt.setText(bc.getBinary());
                octalTxt.setText(bc.getOctal());
                hexadecimalTxt.setText(bc.getHexadecimal());
                charactersTxt.setText(bc.getCharacters());
                Color c = new Color(Integer.parseInt(bc.getColor()));
                colorTxt.setBackground(c);
                floatDecimalTxt.setText(bc.getFloatDecimal());
            }
        });


        // Choose color
        chooseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null, "Choose a color", colorTxt.getBackground());
                if (newColor != null) {
                    colorTxt.setBackground(newColor);
                }
            }
        });

        frm.setVisible(true);
        frm.setPreferredSize(new Dimension(1000, 500));
        frm.pack();
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    public static void main(String[] args) {
        NumberBaseConverterGUI nbc = new NumberBaseConverterGUI();
    }
}


