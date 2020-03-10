package com.joneshsu.great;

import com.joneshsu.great.component.MainBoard;

/**
 * Hello world!
 */
public final class App {

    /**
     * Start the application.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        MainBoard mainBoard = MainBoard.getSingleton();
        mainBoard.setVisible(true);
    }
}
