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

public class DataFileTask extends SwingWorker<Void, Void> {
		
	private CellManager cm;
	private File file;
	
	public int progress;
		
	public DataFileTask(CellManager cm, File file) {
		this.cm = cm;
		this.file = file;
	}
	
	@Override
	public Void doInBackground() throws IOException, NumberFormatException {
		int bufferSize = ((int) file.length() + (1024 - ((int) file.length() % 1024)));
		BufferedReader br = new BufferedReader(new FileReader(file), bufferSize);
		StringTokenizer st;
		String readLine = "";
		double time = 0;
		int id = 0, layer = 0;
		int count = 0;
		
		progress = 0;
		setProgress(0);
		
		cm.setDataFileLength(cm.getFileLength(br));
		cm.createNewCellArray(cm.getDataFileLength());
		br = new BufferedReader(new FileReader(file), bufferSize);
		while((readLine = br.readLine()) != null && !isCancelled()) {
			st = new StringTokenizer(readLine.strip(), "  ");
			if (st.countTokens() == 1) {
				time = Double.parseDouble(readLine.strip());
				cm.addTime(time);;
				cm.setIds(id);
				id = 0;
			} else {
				while (st.hasMoreTokens()) {
					cm.addCell(new WaterCell(time, id, layer, Double.parseDouble(st.nextToken())));
					layer++;
				}
				id++;
				cm.setLayers(layer);
				layer = 0;
			}
			count++;
			progress = (int) ((100.0 / cm.getDataFileLength()) * count);
			setProgress(progress);
		}
		
		if(isCancelled()) {
			cm.createNewCellArray(0);
			cm.deleteTimes();
			cm.setIds(0);
			cm.setLayers(0);
		}
		progress = 101;
		setProgress(progress);
		br.close();
		cm.sort(FilterType.time);
		return null;
	}
}
