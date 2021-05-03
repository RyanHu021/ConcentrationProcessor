package concentration.processor.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ProgressMonitor;
import javax.swing.filechooser.FileNameExtensionFilter;

import concentration.processor.data.CellManager;
import concentration.processor.data.FilterType;
import concentration.processor.data.StatsCalculator;
import concentration.processor.data.WaterCell;
import concentration.processor.file.FileExporter;
import concentration.processor.task.CoordinateFileTask;
import concentration.processor.task.DataFileTask;
import concentration.processor.task.IndexFileTask;

public class ProcessorFrame extends JFrame {

	private static final long serialVersionUID = -1725072791557839072L;

	private File file;
	private FileExporter fe;
	private CellManager cm;
	private WaterCell[] cells;
	private ProgressMonitor pm;
	private DataFileTask dataTask;
	private IndexFileTask indexTask;
	private CoordinateFileTask coordinateTask;

	private FileSelectionPanel fileSelectionPanel;
	private StatisticsPanel statisticsPanel;
	private JPanel defaultPanel;
	private JFileChooser fc;

	public ProcessorFrame() {
		super("Concentration Processor");
		fe = new FileExporter();
		cm = new CellManager();
		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);

		fc = new JFileChooser();

		fileSelectionPanel = new FileSelectionPanel();
		fileSelectionPanel.setOpaque(true);
		fileSelectionPanel.getBtnChooseFile().addActionListener(new BtnChooseFileActionListener());
		setContentPane(fileSelectionPanel);

		statisticsPanel = new StatisticsPanel();
		statisticsPanel.setOpaque(true);
		statisticsPanel.getBtnCalculate().addActionListener(new BtnCalculateActionListener());
		statisticsPanel.getBtnChooseIjIndex().addActionListener(new BtnIndexActionListener());
		statisticsPanel.getBtnChooseUtmCoordinate().addActionListener(new BtnCoordinateActionListener());
		statisticsPanel.getBtnClose().addActionListener(new BtnCloseActionListener());
		statisticsPanel.getBtnCreateMap().addActionListener(new BtnCreateMapActionListener());
		statisticsPanel.getBtnExportToExcel().addActionListener(new BtnExportExcelActionListener());
	}

	public class BtnChooseFileActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			fc.setDialogTitle("Open Data File");
			fc.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")
					+ "Documents" + System.getProperty("file.separator")));
			fc.setAcceptAllFileFilterUsed(true);
			fc.resetChoosableFileFilters();

			if (fc.showOpenDialog(defaultPanel) == JFileChooser.APPROVE_OPTION) {
				fileSelectionPanel.getBtnChooseFile().setEnabled(false);
				file = fc.getSelectedFile();
				try {
					if (cm.verifyDataFile(file)) {
						cm = new CellManager();
						pm = new ProgressMonitor(fileSelectionPanel, "Reading " + file.getPath(), "", 0, 100);
						pm.setProgress(0);
						dataTask = new DataFileTask(cm, file);
						dataTask.addPropertyChangeListener(new DataFileTaskPropertyChangeListener());
						dataTask.execute();
					} else {
						JOptionPane.showMessageDialog(fileSelectionPanel, "Invalid data file: " + file.getPath(),
								"Formatting Error", JOptionPane.ERROR_MESSAGE);
						fileSelectionPanel.getBtnChooseFile().setEnabled(true);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(fileSelectionPanel, "Failed to read " + file.getPath(),
							"File Reading Error", JOptionPane.ERROR_MESSAGE);
					fileSelectionPanel.getBtnChooseFile().setEnabled(true);
				}
			}
		}
	}

	private class BtnIndexActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			fc.setDialogTitle("Open I/J Index File");
			fc.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")
					+ "Documents" + System.getProperty("file.separator")));
			fc.setAcceptAllFileFilterUsed(true);
			fc.resetChoosableFileFilters();

			if (fc.showOpenDialog(defaultPanel) == JFileChooser.APPROVE_OPTION) {
				statisticsPanel.getBtnCalculate().setEnabled(false);
				statisticsPanel.getBtnChooseIjIndex().setEnabled(false);
				statisticsPanel.getBtnClose().setEnabled(false);
				statisticsPanel.getBtnExportToExcel().setEnabled(false);
				file = fc.getSelectedFile();

				try {
					if (cm.verifyIndexFile(file)) {
						pm = new ProgressMonitor(statisticsPanel, "Reading " + file.getPath(), "", 0, 100);
						pm.setProgress(0);
						indexTask = new IndexFileTask(cm, file);
						indexTask.addPropertyChangeListener(new IndexFileTaskPropertyChangeListener());
						indexTask.execute();
					} else {
						JOptionPane.showMessageDialog(fileSelectionPanel, "Invalid I/J Index File: " + file.getPath(),
								"Formatting Error", JOptionPane.ERROR_MESSAGE);
						statisticsPanel.getBtnCalculate().setEnabled(true);
						statisticsPanel.getBtnChooseIjIndex().setEnabled(true);
						statisticsPanel.getBtnClose().setEnabled(true);
						statisticsPanel.getBtnExportToExcel().setEnabled(true);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(fileSelectionPanel, "Failed to read " + file.getPath(),
							"File Reading Error", JOptionPane.ERROR_MESSAGE);
					statisticsPanel.getBtnCalculate().setEnabled(true);
					statisticsPanel.getBtnChooseIjIndex().setEnabled(true);
					statisticsPanel.getBtnClose().setEnabled(true);
					statisticsPanel.getBtnExportToExcel().setEnabled(true);
				}
			}
		}
	}

	private class BtnCoordinateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			fc.setDialogTitle("Open UTM Coordinate File");
			fc.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")
					+ "Documents" + System.getProperty("file.separator")));
			fc.setAcceptAllFileFilterUsed(true);
			fc.resetChoosableFileFilters();

			if (fc.showOpenDialog(defaultPanel) == JFileChooser.APPROVE_OPTION) {
				statisticsPanel.getBtnCalculate().setEnabled(false);
				statisticsPanel.getBtnChooseUtmCoordinate().setEnabled(false);
				statisticsPanel.getBtnClose().setEnabled(false);
				statisticsPanel.getBtnCreateMap().setEnabled(false);
				statisticsPanel.getBtnExportToExcel().setEnabled(false);
				file = fc.getSelectedFile();

				try {
					if (cm.verifyCoordinateFile(file)) {
						pm = new ProgressMonitor(statisticsPanel, "Reading " + file.getPath(), "", 0, 100);
						pm.setProgress(0);
						coordinateTask = new CoordinateFileTask(cm, file);
						coordinateTask.addPropertyChangeListener(new CoordinateFileTaskPropertyChangeListener());
						coordinateTask.execute();
					} else {
						JOptionPane.showMessageDialog(fileSelectionPanel,
								"Invalid UTM Coordinate file: " + file.getPath(), "Formatting Error",
								JOptionPane.ERROR_MESSAGE);
						statisticsPanel.getBtnCalculate().setEnabled(true);
						statisticsPanel.getBtnChooseUtmCoordinate().setEnabled(true);
						statisticsPanel.getBtnClose().setEnabled(true);
						statisticsPanel.getBtnCreateMap().setEnabled(true);
						statisticsPanel.getBtnExportToExcel().setEnabled(true);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(fileSelectionPanel, "Failed to read " + file.getPath(),
							"File Reading Error", JOptionPane.ERROR_MESSAGE);
					statisticsPanel.getBtnCalculate().setEnabled(true);
					statisticsPanel.getBtnChooseUtmCoordinate().setEnabled(true);
					statisticsPanel.getBtnClose().setEnabled(true);
					statisticsPanel.getBtnCreateMap().setEnabled(true);
					statisticsPanel.getBtnExportToExcel().setEnabled(true);
				}
			}
		}
	}

	private class BtnCalculateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cells = cm.getCells();
			double[] concentration;
			try {
				if (statisticsPanel.getComboBox().getSelectedIndex() > 0) {
					double d = Double.parseDouble((String) statisticsPanel.getComboBox().getSelectedItem());
					cells = cm.filter(cells, d, FilterType.time);
				}
				if (!statisticsPanel.getTxtID().getText().isBlank()) {
					int i = Integer.parseInt(statisticsPanel.getTxtID().getText().strip());
					if (i >= 0 && i < cm.getIds()) {
						cells = cm.filter(cells, i, FilterType.id);
					} else {
						JOptionPane.showMessageDialog(fileSelectionPanel,
								"ID filter must be between 0-" + (cm.getIds() - 1), "Filter Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				if (!statisticsPanel.getTxtLayer().getText().isBlank()) {
					int i = Integer.parseInt(statisticsPanel.getTxtLayer().getText().strip());
					if (i >= 0 && i < cm.getLayers()) {
						cells = cm.filter(cells, i, FilterType.layer);
					} else {
						JOptionPane.showMessageDialog(fileSelectionPanel,
								"Layer filter must be between 0-" + (cm.getLayers() - 1), "Filter Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				concentration = new double[cells.length];
				for (int i = 0; i < concentration.length; i++) {
					concentration[i] = cells[i].getConcentration();
				}
				statisticsPanel.getLblMinimum().setText("Minimum: " + StatsCalculator.min(concentration) + " mg/L");
				statisticsPanel.getLblMaximum().setText("Maximum: " + StatsCalculator.max(concentration) + " mg/L");
				statisticsPanel.getLblAverage().setText("Average: " + StatsCalculator.avg(concentration) + " mg/L");
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(fileSelectionPanel, "Invalid filters", "Filter Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class BtnExportExcelActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
			fc.setSelectedFile(new File(System.getProperty("user.home") + System.getProperty("file.separator")
					+ "Documents" + System.getProperty("file.separator") + date + ".xlsx"));
			fc.setDialogTitle("Export to Excel");
			fc.setAcceptAllFileFilterUsed(false);
			fc.resetChoosableFileFilters();
			fc.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Excel Spreadsheet", "xlsx"));
			System.out.println();
			if (fc.showSaveDialog(defaultPanel) == JFileChooser.APPROVE_OPTION) {
				try {
					fe.exportDataToExcel(cm, fc.getSelectedFile());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private class BtnCloseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			statisticsPanel.getBtnChooseUtmCoordinate().setEnabled(false);
			statisticsPanel.getBtnCreateMap().setEnabled(false);
			statisticsPanel.getLblSelectedDataFile().setText("Selected Data File:");
			statisticsPanel.getLblSelectedIjIndex().setText("Selected I/J Index File:");
			statisticsPanel.getLblSelectedUtmCoordinate().setText("Selected UTM Coordinate File:");
			statisticsPanel.getLblId().setText("ID:");
			statisticsPanel.getLblLayer().setText("Layer:");
			cm = new CellManager();
			file = null;
			setContentPane(fileSelectionPanel);
		}
	}

	private class BtnCreateMapActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MapFrame f = new MapFrame(cm);
			f.setVisible(true);
		}
	}

	private class DataFileTaskPropertyChangeListener implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent evt) {
			if ("progress" == evt.getPropertyName()) {
				int progress = (int) evt.getNewValue();
				pm.setProgress(progress);
				pm.setNote(progress + "%");
				if (progress >= 100) {
					fileSelectionPanel.getBtnChooseFile().setEnabled(true);
					statisticsPanel.getBtnChooseIjIndex().setEnabled(true);
					statisticsPanel.getLblSelectedDataFile()
							.setText("<html><p>Selected Data File: " + file.getPath() + "</p><html>");
					statisticsPanel.getLblId().setText("ID: (0-" + (cm.getIds() - 1) + ")");
					statisticsPanel.getLblLayer().setText("Layer: (0-" + (cm.getLayers() - 1) + ")");
					statisticsPanel.getComboBox().removeAllItems();
					statisticsPanel.getComboBox().addItem("All");
					setContentPane(statisticsPanel);
					for (double d : cm.getTimes()) {
						statisticsPanel.getComboBox().addItem(String.valueOf(d));
					}
				} else if (pm.isCanceled()) {
					dataTask.cancel(true);
					fileSelectionPanel.getBtnChooseFile().setEnabled(true);
				}
			}
		}
	}

	private class IndexFileTaskPropertyChangeListener implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent evt) {
			if ("progress" == evt.getPropertyName()) {
				int progress = (int) evt.getNewValue();
				pm.setProgress(progress);
				pm.setNote(progress + "%");
				if (progress >= 100) {
					statisticsPanel.getBtnCalculate().setEnabled(true);
					statisticsPanel.getBtnChooseUtmCoordinate().setEnabled(true);
					statisticsPanel.getBtnClose().setEnabled(true);
					statisticsPanel.getBtnExportToExcel().setEnabled(true);
					statisticsPanel.getLblSelectedIjIndex()
							.setText("<html><p>Selected I/J Index File: " + file.getPath() + "</p><html>");
				} else if (pm.isCanceled()) {
					indexTask.cancel(true);
					statisticsPanel.getBtnCalculate().setEnabled(true);
					statisticsPanel.getBtnChooseIjIndex().setEnabled(true);
					statisticsPanel.getBtnClose().setEnabled(true);
					statisticsPanel.getBtnExportToExcel().setEnabled(true);
				}
			}
		}
	}

	private class CoordinateFileTaskPropertyChangeListener implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent evt) {
			if ("progress" == evt.getPropertyName()) {
				int progress = (int) evt.getNewValue();
				pm.setProgress(progress);
				pm.setNote(progress + "%");
				if (progress >= 100) {
					statisticsPanel.getBtnCalculate().setEnabled(true);
					statisticsPanel.getBtnClose().setEnabled(true);
					statisticsPanel.getBtnCreateMap().setEnabled(true);
					statisticsPanel.getBtnExportToExcel().setEnabled(true);
					statisticsPanel.getLblSelectedUtmCoordinate()
							.setText("<html><p>Selected UTM Coordinate File: " + file.getPath() + "</p><html>");
				} else if (pm.isCanceled()) {
					statisticsPanel.getBtnCalculate().setEnabled(true);
					statisticsPanel.getBtnChooseUtmCoordinate().setEnabled(true);
					statisticsPanel.getBtnClose().setEnabled(true);
					statisticsPanel.getBtnExportToExcel().setEnabled(true);
				}
			}
		}
	}
}
