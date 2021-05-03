package concentration.processor.ui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class FileSelectionPanel extends JPanel {

	private static final long serialVersionUID = 7779807929741305212L;
	private JLabel lblTitle;
	private JLabel lblSelectFile;
	private JButton btnChooseFile;
	private Component verticalStrut;
	private Component verticalStrut_1;

	public FileSelectionPanel() {
		initComponents();
	}

	private void initComponents() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		lblTitle = new JLabel("Concentration Processor");
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitle);

		verticalStrut = Box.createVerticalStrut(20);
		add(verticalStrut);

		lblSelectFile = new JLabel("Please select a data file:");
		add(lblSelectFile);
		lblSelectFile.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblSelectFile.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblSelectFile.setHorizontalAlignment(SwingConstants.CENTER);

		verticalStrut_1 = Box.createVerticalStrut(5);
		add(verticalStrut_1);

		btnChooseFile = new JButton("Choose File");
		btnChooseFile.setFont(new Font("Segoe UI", Font.BOLD, 12));
		add(btnChooseFile);
		btnChooseFile.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	public JButton getBtnChooseFile() {
		return btnChooseFile;
	}
}
