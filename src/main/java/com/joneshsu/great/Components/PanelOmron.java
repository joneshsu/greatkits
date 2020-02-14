package com.joneshsu.great.Components;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.joneshsu.great.Strategies.Omron;
import com.joneshsu.great.utilites.ExtensionFileFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelOmron extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    static final String BUTTON_FILE_BROWSER = "選擇PDF檔案";

    Omron omron; 

    JButton jButtonFileBrowser; 
    JTextArea jTextArea;
    JFrame jFrame = new JFrame();

    ExtensionFileFilter filter = new ExtensionFileFilter();
    JFileChooser fileChooser = new JFileChooser();

    public PanelOmron() {
        super();

        omron = new Omron();
        
        jButtonFileBrowser = new JButton(BUTTON_FILE_BROWSER);
        jButtonFileBrowser.addActionListener(this);

        jTextArea = new JTextArea(10, 50);
        jTextArea.setEditable(false);        

        this.add(jTextArea);
        this.add(jButtonFileBrowser);
       
        filter.addExtension("pdf");
        filter.setDescription("PDF檔案(*.pdf)");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
    }

    private void clickBrowserFileEvent(ActionEvent e) {
        int result = fileChooser.showOpenDialog(jFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            jTextArea.selectAll();
            jTextArea.replaceSelection("");

            String name = fileChooser.getSelectedFile().getPath();
            jTextArea.append(" 讀取檔案: " + name + "\n");

            String outputFilePath = omron.PDFToExcel(name);

            jTextArea.append(" 轉檔完成 \n");
            jTextArea.append(" 檔案位置: " + outputFilePath);
            
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jButtonFileBrowser) {
            clickBrowserFileEvent(e);         
        }
    }

}