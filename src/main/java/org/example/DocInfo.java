package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DocInfo extends JDialog {
    JPanel content;
    JTextArea jt;

    public DocInfo(Frame parent) {
        super(parent, true);
        setTitle("Просмотр");


        JPanel gui = new JPanel(new BorderLayout(3, 3));

        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        content = new JPanel(new GridLayout(0, 1));
        jt = new JTextArea();
        content.add(jt);

        gui.add(content, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(4));
        gui.add(buttons, BorderLayout.SOUTH);

        JButton ok = new JButton("OK");
        buttons.add(ok);
        ok.addActionListener(e->{
            setVisible(false);
        });

        setContentPane(gui);

    }


    public void showForm(MyDoc doc,String title) {
        this.setTitle(title);
        jt.setText(doc.toString());
        pack();
        setSize(200,400);
        setLocationRelativeTo(getParent());
        setVisible(true);
    }
}
