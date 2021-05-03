package concentration.processor.task;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.SwingWorker;

import concentration.processor.data.CellManager;
import concentration.processor.data.FilterType;
import concentration.processor.data.WaterCell;
import concentration.processor.ui.MapFrame;

public class CoordinateFileTask extends SwingWorker<Void, Void> {
	
	private CellManager cm;
	private File file;
	private double[][][] coordinates;
	
	public int progress;
		
	public CoordinateFileTask(CellManager cm, File file) {
		this.cm = cm;
		this.file = file;
	}
	
	@Override
	public Void doInBackground() throws NumberFormatException, IOException {
		int bufferSize = ((int) file.length() + (1024 - ((int) file.length() % 1024)));
		BufferedReader br = new BufferedReader(new FileReader(file), bufferSize);
		StringTokenizer st;
		String readLine = "";
		int i = 0, j = 0;
		int maxI = -1, maxJ = -1;
		double x = 0, y = 0;
		double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
		int count = 0;
		
		progress = 0;
		setProgress(0);
		
		while((readLine = br.readLine()) != null && !isCancelled()) {
			st = new StringTokenizer(readLine.substring(0, readLine.length()).strip(), "   ");
			i = Integer.parseInt(st.nextToken().strip());
			if (i > maxI)
				maxI = i;
			j = Integer.parseInt(st.nextToken().strip());
			if (j > maxJ)
				maxJ = j;
			x = Double.parseDouble(st.nextToken().strip());
			if (x > maxX)
				maxX = x;
			if (x < minX)
				minX = x;
			y = Double.parseDouble(st.nextToken().strip());
			if (y > maxY)
				maxY = y;
			if (y < minY)
				minY = y;
		}
		br.close();
		
		br = new BufferedReader(new FileReader(file), bufferSize);
		cm.sort(FilterType.id);
		cm.setCoordinateFileLength(cm.getFileLength(br));
		WaterCell[] cells = cm.getCells();
		coordinates = new double[maxI + 1][maxJ + 1][2];
		br = new BufferedReader(new FileReader(file), bufferSize);
		while((readLine = br.readLine()) != null && !isCancelled()) {
			st = new StringTokenizer(readLine.strip(), "   ");
			i = Integer.parseInt(st.nextToken().strip());
			j = Integer.parseInt(st.nextToken().strip());
			x = Double.parseDouble(st.nextToken().strip());
			y = Double.parseDouble(st.nextToken().strip());
			
			double scale = Math.min(MapFrame.getMapWidth() / (maxX - minX), MapFrame.getMapHeight() / (maxY - minY));
			coordinates[i][j][0] = (x - minX) * scale;
			coordinates[i][j][1] = MapFrame.getMapHeight() - ((y - minY) * scale);
			
			count++;
			progress = (int) ((50.0 / cm.getIndexFileLength()) * count);
			setProgress(progress);
		}
		
		count = 0;
		for (WaterCell c: cells) {
			for (int k = 0; k < c.getPolygon().npoints; k++) {
				try {
					i = c.getPolygon().xpoints[k];
					j = c.getPolygon().ypoints[k];
					c.getPolygon().xpoints[k] = (int) coordinates[i][j][0];
					c.getPolygon().ypoints[k] = (int) coordinates[i][j][1];
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			count++;
			progress = (int) (50.0 + ((50.0 / cells.length) * count));
			setProgress(progress);
		}
		progress = 101;
		setProgress(progress);
		progress = 102;
		br.close();
		progress = 103;
		cm.sort(FilterType.time);
		progress = 104;
		return null;
	}
}
