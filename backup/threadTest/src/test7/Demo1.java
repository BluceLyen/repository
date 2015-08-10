package test7;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Demo1 extends KeyAdapter{

	public static void main(String[] args) {
	    JFrame jf=new JFrame();
	    jf.addKeyListener(new Demo1());
	    jf.setVisible(true);
	    
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyChar());
	}
	
	

}
