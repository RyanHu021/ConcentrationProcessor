package concentration.processor.task;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.SwingWorker;

import concentration.processor.data.CellManager;
import concentration.processor.data.FilterType;
import concentration.processor.data.WaterCell;

public class IndexFileTask extends SwingWorker<Void, Void> {
	
	private CellManager cm;
	private File file;
	
	public int progress;
		
	public IndexFileTask(CellManager cm, File file) {
		this.cm = cm;
		this.file = file;
	}
	
	@Override
	public Void doInBackground() throws IOException, NumberFormatException {
		int bufferSize = ((int) file.length() + (1024 - ((int) file.length() % 1024)));
		BufferedReader br = new BufferedReader(new FileReader(file), bufferSize);
		StringTokenizer st;
		String readLine = "";
		int i = 0, j = 0;
		int[] indexI, indexJ;
		int points = 0, count = 1;
		int a = 0;
		
		progress = 0;
		setProgress(0);
		
		cm.sort(FilterType.id);
		cm.setIndexFileLength(cm.getFileLength(br));
		WaterCell[] cells = cm.getCells();
		br = new BufferedReader(new FileReader(file), bufferSize);
		br.readLine();
		
		while((readLine = br.readLine()) != null && !isCancelled()) {
			st = new StringTokenizer(readLine.substring(0, readLine.length() - 1).strip(), "     ");
			points = st.countTokens() / 2 - 1;
			indexI = new int[points];
			indexJ = new int[points];
			i = Integer.parseInt(st.nextToken().strip());
			j = Integer.parseInt(st.nextToken().strip());
			for (int k = 0; k < points; k++) {
				indexI[k] = Integer.parseInt(st.nextToken().strip());
				indexJ[k] = Integer.parseInt(st.nextToken().strip());
			}
			for (int l = 0; l < cm.getTimes().length * cm.getLayers(); l++) {
				cells[a + l].initIndex(i, j, new Polygon(indexI, indexJ, points));
			}
			a += cm.getTimes().length * cm.getLayers();
			count++;
			progress = (int) ((100.0 / cm.getIndexFileLength()) * count);
			setProgress(progress);
		}
		
		if(isCancelled()) {
			cells = null;
		} else {
			cm.setCells(cells);
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
