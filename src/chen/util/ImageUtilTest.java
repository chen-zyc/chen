package chen.util;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import chen.ui.component.ScrolledFrame;

public class ImageUtilTest {
	
	public static void testGetInputStream() {
		String[] images = new String[] { "chen/ui/images/logo.gif", "chen/ui/images/ok.png"/*, "chen/ui/images/rain.jpg"*/
										, "files/images/logo.gif", "files/images/ok.png"/*, "/files/images/rain.jpg"*/
										, "http://e.hiphotos.baidu.com/image/pic/item/242dd42a2834349bd74626b5caea15ce36d3beba.jpg"};
		
		ScrolledFrame f = new ScrolledFrame();
		f.setBounds(100, 100, 800, 600);
		
		for(String image:images){
			JLabel label = null;
			byte[] bytes = ImageUtil.getBytes(image);
			if (bytes == null) {
				label = new JLabel();
				label.setText("获取图片失败");
			} else {
				System.out.println(image);
				label = new JLabel(new ImageIcon(bytes));
				label.setText(image);
			}
			label.setSize(200, 200);
			f.add(label);
		}
		f.setVisible(true);
	}

	public static void main(String[] args) {
		testGetInputStream();
	}
}
