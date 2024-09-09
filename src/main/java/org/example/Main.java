package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Optional;

public class Main extends JFrame {
    ArrayList<MyDoc> docs;
    InvoiceForm iForm;
    OrderForm oForm;
    PaymentForm pForm;
    DocInfo docInfo;

    DefaultListModel<JCheckBox> listModel;
    JCheckBoxList docList;
    JButton nB = new JButton("Накладная");
    JButton pB = new JButton("Платёжка");
    JButton zB = new JButton("Заявка на оплату");
    JButton saveB = new JButton("Сохранить");
    JButton loadB = new JButton("Загрузить");
    JButton vB = new JButton("Просмотр");
    JButton dB = new JButton("Удалить выбранные");
    JButton eB = new JButton("Выход");

    public Main()
    {
        super("DocCreator");
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        //setMinimumSize (new Dimension (500, 600));
        docs = new ArrayList<MyDoc>();
        iForm = new InvoiceForm(this);
        oForm = new OrderForm(this);
        pForm = new PaymentForm(this);
        docInfo = new DocInfo(this);

        listModel = new DefaultListModel<JCheckBox>();
       docList = new JCheckBoxList(listModel);

        nB.setName("n");
        pB.setName("p");
        zB.setName("z");
        saveB.setName("save");
        loadB.setName("load");
        vB.setName("v");
        dB.setName("d");
        eB.setName("e");

        nB.addActionListener(e -> bClicked(e.getSource()));
        pB.addActionListener(e -> bClicked(e.getSource()));
        zB.addActionListener(e -> bClicked(e.getSource()));
        saveB.addActionListener(e -> bClicked(e.getSource()));
        loadB.addActionListener(e -> bClicked(e.getSource()));
        vB.addActionListener(e -> bClicked(e.getSource()));
        dB.addActionListener(e -> bClicked(e.getSource()));
        eB.addActionListener(e -> bClicked(e.getSource()));



        Container container = getContentPane();
        JPanel lPanel = new JPanel();
        lPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        lPanel.setLayout(new GridLayout(0, 1));
        JPanel rPanel = new JPanel();
        rPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        rPanel.setLayout(new GridLayout(0, 1));



        //String str[] = {"Платежка","Платежка","Заявка", "Накладная"};
        lPanel.add(docList, BorderLayout.CENTER);

        rPanel.add(nB);
        rPanel.add(pB);
        rPanel.add(zB);
        rPanel.add(saveB);
        rPanel.add(loadB);
        rPanel.add(vB);
        rPanel.add(dB);
        rPanel.add(eB);

        container.add(lPanel, BorderLayout.WEST);
        container.add(rPanel, BorderLayout.EAST);

        container.add(new JScrollPane(docList));
        // Открываем окно
        pack();
        rPanel.setSize(300,400);
        setVisible(true);
    }

    private void bClicked(Object o) {
        JButton btn = (JButton) o;
        String str = btn.getName();

        switch (str) {
            case "n":
                try {
                    Invoice result = iForm.showForm();
                    if (result.date != "05.07.1979") {
                        docs.add(result);
                        String zzz = "Накладная от " + result.date + " номер " + result.number;
                        JCheckBox cb = new JCheckBox(zzz);
                        cb.setToolTipText(zzz);
                        listModel.addElement(cb);
                    }

                   pack();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;
            case "p":
                try {
                    Payment result = pForm.showForm();
                    if (result.date != "05.07.1979") {
                        docs.add(result);
                        String zzz = "Платёжка от " + result.date + " номер "+ result.number;
                        JCheckBox cb = new JCheckBox(zzz);
                        cb.setToolTipText(zzz);
                        listModel.addElement(cb);
                    }
                    pack();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;
            case "z":
                try {
                    Order result = oForm.showForm();
                    if (result.date != "05.07.1979") {
                        docs.add(result);
                        String zzz = "Заявка на оплату от " + result.date + " номер " + result.number;
                        JCheckBox cb = new JCheckBox(zzz);
                        cb.setToolTipText(zzz);
                        listModel.addElement(cb);
                    }
                    pack();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;
            case "save":
                if (docList.getSelectedIndex() != -1) {
                    JFileChooser fileChooser1 = new JFileChooser();
                    fileChooser1.setDialogTitle("Выберите путь и имя файла");


                    int userSelection = fileChooser1.showSaveDialog(this);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser1.getSelectedFile();
                        System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                        int ind = docList.getSelectedIndex();
                        MyDoc zzz = docs.get(ind);
                        String filename = fileToSave.getAbsolutePath();
                        if (!filename.endsWith(zzz.getExtension()))
                            filename += zzz.getExtension();
                        zzz.saveToFile(filename);

                    }
                }
                break;
            case "load":
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "INV, ORD, PAY", "inv", "ord","pay");
                fileChooser.setFileFilter(filter);
                int option = fileChooser.showOpenDialog(this);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    MyDoc myDoc = null;
                    String zzz = "";
                    Optional<String> ext = getExtensionByStringHandling(file.getName());
                    switch (ext.get()) {
                        case "inv":
                            myDoc = new Invoice();
                            Invoice inv = new Invoice();
                            inv.loadFromFile(file.getAbsolutePath());
                            myDoc.loadFromFile(file.getAbsolutePath());
                            zzz = "Накладная от " + inv.date + " номер "+ inv.number;
                            break;
                        case "ord":
                            myDoc = new Order();
                            Order ord = new Order();
                            ord.loadFromFile(file.getAbsolutePath());
                            myDoc.loadFromFile(file.getAbsolutePath());
                            zzz = "Заявка на оплату от " + ord.date + " номер "+ ord.number;

                            break;
                        case "pay":
                            myDoc = new Payment();
                            Payment pay = new Payment();
                            pay.loadFromFile(file.getAbsolutePath());
                            myDoc.loadFromFile(file.getAbsolutePath());
                            zzz = "Платёжка от " + pay.date + " номер "+ pay.number;
                            break;
                        default:
                            System.out.println("Can't detect type of document - " + file.getName() );
                            break;
                    }
                    System.out.println("File Selected: " + file.getName());

                    docs.add(myDoc);

                    JCheckBox cb = new JCheckBox(zzz);
                    cb.setToolTipText(zzz);
                    listModel.addElement(cb);


                }else{
                    System.out.println("Open command canceled");
                }
                break;
            case "v":
                try {
                    int ind = docList.getSelectedIndex();
                    String txt = docList.getSelectedValue().getToolTipText();
                    docInfo.showForm(docs.get(ind), txt);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;
            case "d":
                ArrayList<Integer> zzz = new ArrayList<>();
                for(int i=0; i < listModel.getSize(); i++){
                    JCheckBox cb =  listModel.getElementAt(i);
                    if (cb.isSelected()) {
                        System.out.println(cb.getToolTipText());
                        //listModel.removeElement(listModel.getElementAt(i));
                        zzz.add(i);
                    }

                }

                Collections.reverse(zzz);
                for (int i = 0; i < zzz.size(); i++) {
                    listModel.remove(zzz.get(i));
                    docs.remove (zzz.get(i).intValue());
                }
                //listModel.remove()
                //docs.removeAll(zzz);
                //docs.forEach(System.out::println);

                break;
            case "e":
                System.exit(0);
                break;
            default:
                throw new IllegalArgumentException("Что за кнопка ???");
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}