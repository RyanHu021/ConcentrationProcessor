package concentration.processor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import concentration.processor.data.CellManager;
import concentration.processor.data.FilterType;
import concentration.processor.data.WaterCell;

public class MapFrame extends JFrame {

	private static final long serialVersionUID = 5368301058039099270L;
	private static final int WINDOW_WIDTH = 1600;
	private static final int WINDOW_HEIGHT = 900;
	private static final int MAP_WIDTH = 15360;
	private static final int MAP_HEIGHT = 8640;

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private ScrollPanel scrollPanel;
	private JPanel panel;
	private JButton buttonPlus;
	private JButton button_1;
	private JLabel lblTime;
	private JComboBox<String> comboBox;
	private JLabel lblZoom;
	private JLabel lblLayer;
	private JComboBox<String> comboBox_1;
	private Component horizontalStrut;
	private JLabel lblKey;
	private JPanel panel_1;
	private JLabel label;
	private JLabel label_7;
	private Component rigidArea;
	private Component horizontalStrut_1;
	private JButton btnSaveImage;
	private JFileChooser fc;
	private Component horizontalStrut_2;
	private Component horizontalStrut_3;

	private CellManager cm;
	private BufferedImage img;
	private double time;
	private int layer;

	public MapFrame(CellManager cm) {
		super("Map");
		this.cm = cm;
		time = cm.getTimes()[0];
		layer = 0;
		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		scrollPanel = new ScrollPanel();
		contentPane.add(scrollPanel);

		scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		contentPane.add(scrollPane, BorderLayout.CENTER);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		lblTime = new JLabel("Time:");
		lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel.add(lblTime);

		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		comboBox.addActionListener(new ComboBoxActionListener());
		for (double d : cm.getTimes()) {
			comboBox.addItem(String.valueOf(d));
		}
		time = Double.parseDouble(comboBox.getItemAt(0));
		panel.add(comboBox);

		horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_2);

		lblLayer = new JLabel("Layer:");
		lblLayer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel.add(lblLayer);

		comboBox_1 = new JComboBox<String>();
		comboBox_1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		comboBox_1.addActionListener(new ComboBox_1ActionListener());
		for (int i = 0; i < cm.getLayers(); i++) {
			comboBox_1.addItem(String.valueOf(i));
		}
		layer = Integer.parseInt(comboBox_1.getItemAt(0));
		panel.add(comboBox_1);

		horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_3);

		lblZoom = new JLabel("Zoom:");
		lblZoom.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel.add(lblZoom);

		buttonPlus = new JButton("+");
		buttonPlus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		buttonPlus.addActionListener(new ButtonPlusActionListener());
		panel.add(buttonPlus);

		button_1 = new JButton("-");
		button_1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		button_1.addActionListener(new Button_1ActionListener());
		panel.add(button_1);

		horizontalStrut = Box.createHorizontalStrut(80);
		panel.add(horizontalStrut);

		lblKey = new JLabel("Key:");
		lblKey.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel.add(lblKey);

		label = new JLabel("0 mg/L");
		label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel.add(label);

		panel_1 = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3771344128027407102L;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Graphics g2d = (Graphics2D) g;
				for (int i = 0; i < 256; i++) {
					g2d.setColor(new Color(i, 255 - i, 0));
					g2d.drawLine(i, 0, i, 10);
				}
			}
		};
		panel_1.setSize(new Dimension(255, 10));
		panel.add(panel_1);

		rigidArea = Box.createRigidArea(new Dimension(256, 10));
		panel_1.add(rigidArea);

		label_7 = new JLabel("10 mg/L");
		label_7.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel.add(label_7);

		horizontalStrut_1 = Box.createHorizontalStrut(80);
		panel.add(horizontalStrut_1);

		btnSaveImage = new JButton("Save as PNG Image");
		btnSaveImage.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnSaveImage.addActionListener(new BtnSaveImageActionListener());
		panel.add(btnSaveImage);

		fc = new JFileChooser();
	}

	private class ScrollPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3306096968681254846L;
		double scale = 1;
		Point origin;
		Point viewOrigin;

		public ScrollPanel() {
			this.addMouseWheelListener(new MouseWheelListener() {
				@Override
				public void mouseWheelMoved(MouseWheelEvent e) {
					double delta = -0.05 * e.getPreciseWheelRotation();
					zoom(delta, e.getPoint());
				}
			});

			this.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					origin = e.getPoint();
					viewOrigin = scrollPane.getViewport().getViewPosition();
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}
			});

			this.addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseDragged(MouseEvent e) {
					Point p = viewOrigin;
					p.x -= e.getX() - origin.x;
					p.y -= e.getY() - origin.y;
					p.x = Math.min(Math.max(p.x, 0), scrollPane.getViewport().getViewSize().width
							- scrollPane.getViewport().getViewRect().width);
					p.y = Math.min(Math.max(p.y, 0), scrollPane.getViewport().getViewSize().height
							- scrollPane.getViewport().getViewRect().height);
					scrollPane.getViewport().setViewPosition(p);
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					scrollPanel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				}
			});
		}

		public void zoom(double delta, Point point) {
			delta += 1;
			Point pos = scrollPane.getViewport().getViewPosition();
			int x = (int) (point.x * delta - (point.x - pos.x));
			int y = (int) (point.y * delta - (point.y - pos.y));
			scrollPane.getViewport().setViewPosition(new Point(x, y));
			scale *= delta;
			revalidate();
			repaint();
		}

		@Override
		public Dimension getPreferredSize() {
			Dimension size = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
			size.width = (int) Math.round(WINDOW_WIDTH * scale);
			size.height = (int) Math.round(WINDOW_HEIGHT * scale);
			return size;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.scale(0.1, 0.1);
			AffineTransform at = new AffineTransform();
			if (img == null) {
				img = new BufferedImage(MAP_WIDTH, MAP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
				Graphics2D img2d = img.createGraphics();
				cm.sort(FilterType.time);
				WaterCell[] cells = cm.filter(cm.filter(cm.getCells(), time, FilterType.time), layer, FilterType.layer);

				for (WaterCell c : cells) {
					img2d.setColor(new Color((int) (Math.min(c.getConcentration(), 10) * (255 / 10)),
							(int) (255 - (Math.min(c.getConcentration(), 10) * (255 / 10))), 0));
					img2d.fillPolygon(c.getPolygon());
					img2d.setColor(new Color(0, 0, 0, 60));
					img2d.drawPolygon(c.getPolygon());
				}
				at.scale(scale, scale);
				g2d.drawImage(img, at, this);
			} else {
				at.scale(scale, scale);
				g2d.drawImage(img, at, this);
			}
		}
	}

	private class ComboBoxActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			img = null;
			time = Double.parseDouble((String) comboBox.getSelectedItem());
			scrollPanel.revalidate();
			scrollPanel.repaint();
		}
	}

	private class ButtonPlusActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Point point = new Point(
					scrollPane.getViewport().getViewPosition().x + (scrollPane.getViewport().getViewRect().width) / 2,
					scrollPane.getViewport().getViewPosition().y + (scrollPane.getViewport().getViewRect().height) / 2);
			scrollPanel.zoom(0.1, point);
		}
	}

	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Point point = new Point(
					scrollPane.getViewport().getViewPosition().x + (scrollPane.getViewport().getViewRect().width) / 2,
					scrollPane.getViewport().getViewPosition().y + (scrollPane.getViewport().getViewRect().height) / 2);
			scrollPanel.zoom(-0.1, point);
		}
	}

	private class ComboBox_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			img = null;
			layer = Integer.parseInt((String) comboBox_1.getSelectedItem());
			scrollPanel.revalidate();
			scrollPanel.repaint();
		}
	}

	private class BtnSaveImageActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (img != null) {
				fc.setSelectedFile(new File(System.getProperty("user.home") + System.getProperty("file.separator")
						+ "Documents" + System.getProperty("file.separator") + time + "_" + layer + ".png"));
				fc.setDialogTitle("Save as PNG");
				fc.setAcceptAllFileFilterUsed(false);
				fc.resetChoosableFileFilters();
				fc.addChoosableFileFilter(new FileNameExtensionFilter("PNG", "png"));
				System.out.println();
				if (fc.showSaveDialog(contentPane) == JFileChooser.APPROVE_OPTION) {
					try {
						ImageIO.write(img, "png", fc.getSelectedFile());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public static int getMapWidth() {
		return MAP_WIDTH;
	}

	public static int getMapHeight() {
		return MAP_HEIGHT;
	}
}
