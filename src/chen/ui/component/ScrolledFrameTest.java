package chen.ui.component;

import javax.swing.JButton;

public class ScrolledFrameTest {

	public static void main(String[] args) {
		new ScrolledFrameTest().test();
	}

	public void test() {
		ScrolledFrame f = new ScrolledFrame();

		for (int i = 0; i < 10; i++) {
			f.add(new JButton("test button " + i));
		}

		f.setBounds(100, 100, 300, 200);
		f.setVisible(true);
	}
	
}
