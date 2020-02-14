package com.joneshsu.great.Components;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.joneshsu.great.utilites.Observer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar implements ActionListener {

    private static final long serialVersionUID = 1L;

    static final String MENU_EXPORT = "轉檔";
    static final String MENU_ITEM_OMRON = "歐姆龍";

    JMenu jMenuExport = null;

    JMenuItem jMenuItemOmron = null;

    Observer observer = null;

    public MenuBar(Observer observer) {
        super();

        this.observer = observer;

        jMenuExport = new JMenu(MENU_EXPORT);

        jMenuItemOmron = new JMenuItem(MENU_ITEM_OMRON);
        jMenuItemOmron.addActionListener(this);

        jMenuExport.add(jMenuItemOmron);

        this.add(jMenuExport);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jMenuItemOmron) {
            System.out.println(MENU_ITEM_OMRON);
            observer.update(new PanelOmron());
        }
    }

}