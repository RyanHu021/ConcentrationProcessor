package concentration.processor.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class StatisticsPanel extends JPanel {

	private static final long serialVersionUID = -4930266572152627960L;
	private JLabel lblTitle;
	private JPanel panel;
	private JLabel lblSelectedDataFile;
	private JLabel lblSelectedIjIndex;
	private JButton btnChooseIjIndex;
	private JLabel lblSelectedUtmCoordinate;
	private JButton btnChooseUtmCoordinate;
	private JPanel panel_1;
	private JLabel lblFilters;
	private Component verticalStrut;
	private JLabel lblTime;
	private JComboBox<String> comboBox;
	private JLabel lblId;
	private JTextField txtID;
	private JLabel lblLayer;
	private JTextField txtLayer;
	private Component verticalStrut_2;
	private Component verticalStrut_3;
	private Component verticalStrut_4;
	private Component verticalStrut_5;
	private JButton btnCalculate;
	private JLabel lblMinimum;
	private JLabel lblMaximum;
	private JLabel lblAverage;
	private JButton btnExportToExcel;
	private Component verticalStrut_8;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JButton btnClose;
	private JButton btnCreateMap;
	private Component verticalStrut_1;
	private JLabel lblActions;
	private Component verticalStrut_6;

	public StatisticsPanel() {

		initComponents();
	}

	private void initComponents() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setSize(450, 300);
		setLayout(new BorderLayout(0, 0));

		lblTitle = new JLabel("Concentration Processor");
		lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitle, BorderLayout.NORTH);

		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 0, 0, 0));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		lblSelectedDataFile = new JLabel("Selected Data File:");
		lblSelectedDataFile.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblSelectedDataFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectedDataFile.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel.add(lblSelectedDataFile);

		verticalStrut_2 = Box.createVerticalStrut(5);
		panel.add(verticalStrut_2);

		lblSelectedIjIndex = new JLabel("Selected I/J Index File:");
		lblSelectedIjIndex.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblSelectedIjIndex.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblSelectedIjIndex);

		btnChooseIjIndex = new JButton("Choose I/J Index File");
		btnChooseIjIndex.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnChooseIjIndex.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnChooseIjIndex);

		verticalStrut_3 = Box.createVerticalStrut(5);
		panel.add(verticalStrut_3);

		lblSelectedUtmCoordinate = new JLabel("Selected UTM Coordinate File:");
		lblSelectedUtmCoordinate.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel.add(lblSelectedUtmCoordinate);
		lblSelectedUtmCoordinate.setAlignmentX(0.5f);

		btnChooseUtmCoordinate = new JButton("Choose UTM Coordinate File");
		btnChooseUtmCoordinate.setEnabled(false);
		btnChooseUtmCoordinate.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnChooseUtmCoordinate.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnChooseUtmCoordinate);

		panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(5, 0, 0, 0));
		add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		lblFilters = new JLabel("Filters:");
		lblFilters.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblFilters.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel_1.add(lblFilters);

		verticalStrut = Box.createVerticalStrut(20);
		panel_1.add(verticalStrut);

		lblTime = new JLabel("Time:");
		lblTime.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel_1.add(lblTime);

		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel_1.add(comboBox);

		verticalStrut_4 = Box.createVerticalStrut(5);
		panel_1.add(verticalStrut_4);

		lblId = new JLabel("ID:");
		lblId.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblId.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel_1.add(lblId);

		txtID = new JTextField();
		txtID.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel_1.add(txtID);
		txtID.setColumns(10);

		verticalStrut_5 = Box.createVerticalStrut(5);
		panel_1.add(verticalStrut_5);

		lblLayer = new JLabel("Layer:");
		lblLayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLayer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel_1.add(lblLayer);

		txtLayer = new JTextField();
		txtLayer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel_1.add(txtLayer);
		txtLayer.setColumns(10);

		verticalStrut_8 = Box.createVerticalStrut(10000);
		panel_1.add(verticalStrut_8);

		panel_2 = new JPanel();
		add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));

		btnCalculate = new JButton("Calculate");
		btnCalculate.setFont(new Font("Segoe UI", Font.BOLD, 12));
		panel_2.add(btnCalculate, BorderLayout.NORTH);

		panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(5, 10, 5, 10));
		panel_2.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));

		lblMinimum = new JLabel("Minimum:");
		lblMinimum.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblMinimum.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblMinimum, BorderLayout.WEST);

		lblMaximum = new JLabel("Maximum:");
		lblMaximum.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblMaximum.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblMaximum, BorderLayout.CENTER);

		lblAverage = new JLabel("Average:");
		lblAverage.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblAverage.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblAverage, BorderLayout.EAST);

		btnExportToExcel = new JButton("Export to Excel");
		btnExportToExcel.setFont(new Font("Segoe UI", Font.BOLD, 12));
		panel_2.add(btnExportToExcel, BorderLayout.SOUTH);

		panel_4 = new JPanel();
		panel_4.setBorder(new EmptyBorder(5, 0, 0, 0));
		add(panel_4, BorderLayout.EAST);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));

		lblActions = new JLabel("Actions:");
		lblActions.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblActions.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_4.add(lblActions);

		verticalStrut_6 = Box.createVerticalStrut(20);
		panel_4.add(verticalStrut_6);

		btnCreateMap = new JButton("Create Map");
		btnCreateMap.setEnabled(false);
		btnCreateMap.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnCreateMap.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_4.add(btnCreateMap);

		verticalStrut_1 = Box.createVerticalStrut(20);
		panel_4.add(verticalStrut_1);

		btnClose = new JButton("Close Files");
		btnClose.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnClose.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_4.add(btnClose);
	}

	public JLabel getLblSelectedDataFile() {
		return lblSelectedDataFile;
	}

	public JLabel getLblSelectedIjIndex() {
		return lblSelectedIjIndex;
	}

	public JButton getBtnChooseIjIndex() {
		return btnChooseIjIndex;
	}

	public JLabel getLblSelectedUtmCoordinate() {
		return lblSelectedUtmCoordinate;
	}

	public JButton getBtnChooseUtmCoordinate() {
		return btnChooseUtmCoordinate;
	}

	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	public JLabel getLblId() {
		return lblId;
	}

	public JLabel getLblLayer() {
		return lblLayer;
	}

	public JTextField getTxtID() {
		return txtID;
	}

	public JTextField getTxtLayer() {
		return txtLayer;
	}

	public JButton getBtnCalculate() {
		return btnCalculate;
	}

	public JLabel getLblMinimum() {
		return lblMinimum;
	}

	public JLabel getLblMaximum() {
		return lblMaximum;
	}

	public JLabel getLblAverage() {
		return lblAverage;
	}

	public JButton getBtnExportToExcel() {
		return btnExportToExcel;
	}

	public JButton getBtnCreateMap() {
		return btnCreateMap;
	}

	public JButton getBtnClose() {
		return btnClose;
	}
}
