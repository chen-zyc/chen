package chen.ui.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.HeadlessException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class ScrolledFrame extends JFrame {

	private JPanel	content;

	public ScrolledFrame() throws HeadlessException {
		super();
		init();
	}

	public ScrolledFrame(String title) throws HeadlessException {
		super(title);
		init();
	}

	private void init() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel panel = new JPanel(true);
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		scrollPane.setViewportView(panel);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);

		this.content = panel;
	}

	@Override
	public Component add(Component comp) {
		return content.add(comp);
	}

	@Override
	public Component add(Component comp, int index) {
		return content.add(comp, index);
	}

	@Override
	public void add(Component comp, Object constraints, int index) {
		content.add(comp, constraints, index);
	}

	@Override
	public Component add(String name, Component comp) {
		return content.add(name, comp);
	}

}
