package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OrderForm extends JDialog {
    JTextField j1 = new JTextField();
    JTextField j2 = new JTextField();
    JTextField j3 = new JTextField();
    JTextField j4 = new JTextField();
    JTextField j5 = new JTextField();
    JTextField j6 = new JTextField();
    JTextField j7 = new JTextField();
    JTextField j8 = new JTextField();

    private Order result = new Order();

    JPanel content;

    public OrderForm(Frame parent) {
        super(parent, true);
        setTitle("Новая заявка на оплату");
        result.date = "05.07.1979";


        JPanel gui = new JPanel(new BorderLayout(3, 3));

        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        content = new JPanel(new GridLayout(0, 1));
        content.add(new Label("Номер"));
        content.add(j1);
        content.add(new Label("Дата"));
        content.add(j2);
        content.add(new Label("Пользователь"));
        content.add(j3);
        content.add(new Label("Контрагент"));
        content.add(j4);
        content.add(new Label("Сумма"));
        content.add(j5);
        content.add(new Label("Валюта"));
        content.add(j6);
        content.add(new Label("Курс валюты"));
        content.add(j7);
        content.add(new Label("Комиссия"));
        content.add(j8);
        gui.add(content, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(4));
        gui.add(buttons, BorderLayout.SOUTH);

        JButton ok = new JButton("OK");
        buttons.add(ok);
        ok.addActionListener(e->{
            result = new Order(j1.getText(),j2.getText(),j3.getText(),j4.getText(),j5.getText(),j6.getText(),j7.getText(),j8.getText());
            setVisible(false);
        });

        setContentPane(gui);

    }


    public Order showForm() {
        pack();
        setSize(200,400);
        setLocationRelativeTo(getParent());
        setVisible(true);
        return result;
    }
}
