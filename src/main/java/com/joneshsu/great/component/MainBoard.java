package com.joneshsu.great.component;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.joneshsu.great.util.Observer;

public class MainBoard extends JFrame implements Observer {
    static final long serialVersionUID = 1L;

    static final int WIDTH_SIZE = 800;
    static final int HEIGHT_SIZE = 600;

    static final String APP_NAME = "Great Kits";

    static MainBoard mainBoard = null;

    MenuBar menuBar = null;

    public static MainBoard getSingleton() {
        if (mainBoard == null) {
            mainBoard = new MainBoard();
        }

        return mainBoard;
    }

    private MainBoard() {
        super(APP_NAME);
        this.setSize(WIDTH_SIZE, HEIGHT_SIZE);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        menuBar = new MenuBar(this);

        this.setJMenuBar(menuBar);
    }

    @Override
    public void update(JComponent component) {
        this.add(component);
        this.setVisible(true); 
    }

     
}