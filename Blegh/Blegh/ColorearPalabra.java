package Blegh;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ColorearPalabra extends JFrame {
  private Color color = Color.black;
  private final JTextPane textPane;
  private Font defaultFont = new Font("Agency FB", Font.BOLD, 30);		
  private boolean cambio = true;

  public static void main(String[] args) {
    new ColorearPalabra();
  }

  public ColorearPalabra() {
    this.setTitle("Colores");
    this.setSize(400, 300);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    textPane = new JTextPane();
	textPane.setFont(defaultFont);			

    textPane.addKeyListener(new KeyListener() {
      @Override
      public void keyPressed(KeyEvent e) {}

      @Override
      public void keyTyped(KeyEvent e) {
        isVocal(e.getKeyChar());
      }

      @Override
      public void keyReleased(KeyEvent e) {
        isVocal(e.getKeyChar());
      }
    });
    JScrollPane scrollPane = new JScrollPane(textPane);
    this.getContentPane().add(scrollPane);
	this.setLocationRelativeTo(null);			
    this.setVisible(true);
  }

  private void cambiarColorTexto(Color color, int num) {
    StyledDocument dc = textPane.getStyledDocument();
    SimpleAttributeSet attrs = new SimpleAttributeSet();
    StyleConstants.setForeground(attrs, color);
	StyleConstants.setForeground(attrs, color);
    StyleConstants.setFontSize(attrs, defaultFont.getSize()); 
    StyleConstants.setFontFamily(attrs, defaultFont.getFamily()); 
    StyleConstants.setBold(attrs, defaultFont.isBold()); 
    StyleConstants.setItalic(attrs, defaultFont.isItalic()); 

    int inicio = dc.getLength() -  num;

    dc.setCharacterAttributes(inicio, 1, attrs, true);
  }

  private void isVocal(char c) {
	  c = Character.toLowerCase(c);

    if (cambio) {
      switch (c) {
      case 'a': {
        color = Color.RED;
        cambio = false;
      }
      break;
      case 'e': {
        color = Color.BLUE;
        cambio = false;
      }
      break;
      case 'i': {
        color = Color.YELLOW;
        cambio = false;
      }
      break;
      case 'o': {
        color = Color.GREEN;
        cambio = false;
      }
      break;
      case 'u': {
        color = Color.MAGENTA;
        cambio = false;
      }
      break;
      }
    }
    if (c == '#') {
      color = Color.black;
      cambio = true;
      cambiarColorTexto(color,  0);
    } else {
      cambiarColorTexto(color, 1);
    }
  }

  public String writtenText() {
    return textPane.getText();
  }



}

