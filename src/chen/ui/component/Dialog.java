package chen.ui.component;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Dialog {

	public static Object input(Component parent, Object message, String title, Object[] options, Object initVal) {
		return JOptionPane.showInputDialog(parent, message, title, JOptionPane.QUESTION_MESSAGE, null, options, initVal);
	}

	public static void main(String[] args) {
		final JFrame f = new JFrame();
		f.setBounds(100, 100, 500, 400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new FlowLayout());

		final Object[] options = new Object[] { "11111111111111111111111111111111111111111111111111111111111111111", "2", "3" };

		JButton button = new JButton("option dialog");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object select = input(f, "message", "title", options, options[0]);
				System.out.println(select);
			}
		});
		f.add(button);
		f.setVisible(true);
	}

}
