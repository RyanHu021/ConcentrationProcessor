package concentration.processor.data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class CellManager {
	
	private ArrayList<WaterCell> cellArray;
	private int dataFileLength;
	private int indexFileLength;
	private int coordinateFileLength;

	Comparator<WaterCell> timeComparator;
	Comparator<WaterCell> idComparator;
	Comparator<WaterCell> layerComparator;
	
	private ArrayList<Double> times;
	private int ids;
	private int layers;
	
	public CellManager() {
		cellArray = new ArrayList<WaterCell>();
		dataFileLength = 0;
		indexFileLength = 0;
		
		timeComparator = Comparator.comparingDouble(WaterCell::getTime).thenComparingInt(WaterCell::getCellID).thenComparingInt(WaterCell::getLayer);
		idComparator = Comparator.comparingInt(WaterCell::getCellID).thenComparingDouble(WaterCell::getTime).thenComparingInt(WaterCell::getLayer);
		layerComparator = Comparator.comparingDouble(WaterCell::getLayer).thenComparingDouble(WaterCell::getTime).thenComparingInt(WaterCell::getCellID);
		
		times = new ArrayList<Double>();
		ids = 0;
		layers = 0;
	}
	
	public boolean verifyDataFile(File file) throws IOException {
		int bufferSize = ((int) file.length() + (1024 - ((int) file.length() % 1024)));
		BufferedReader br = new BufferedReader(new FileReader(file), bufferSize);
		StringTokenizer st;
		String readLine = "";
		int tokens;
		int idCount = 0, lCount = 0;
		int lastIdCount = 0;
		
		try {
			while ((readLine = br.readLine()) != null) {
				st = new StringTokenizer(readLine.strip(), "  ");
				tokens = st.countTokens();
				if (tokens == 1) {
					Double.parseDouble(st.nextToken());
					if (idCount == 0 && lastIdCount != 0 ) {
						idCount = lastIdCount;
					} else {
						if (idCount != lastIdCount) {
							br.close();
							return false;
						}
					}
					lastIdCount = 0;
				} else if (tokens > 1) {
					while (st.hasMoreTokens()) {
						Double.parseDouble(st.nextToken());
					}
					if (lCount == 0) {
						lCount = tokens;
					} else {
						if (lCount != tokens) {
							br.close();
							return false;
						}
					}
					lastIdCount++;
				} else {
					br.close();
					return false;
				}
			}
		} catch (Exception e) {
			br.close();
			return false;
		}
		
		if (idCount != lastIdCount) {
			br.close();
			return false;
		}
		br.close();
		return true;
	}
	
	public boolean verifyIndexFile(File file) throws IOException {
		int bufferSize = ((int) file.length() + (1024 - ((int) file.length() % 1024)));
		BufferedReader br = new BufferedReader(new FileReader(file), bufferSize);
		StringTokenizer st;
		String readLine = "";
		int tokens;
		
		br.readLine();
		try {
			while ((readLine = br.readLine()) != null) {
				st = new StringTokenizer(readLine.substring(0, readLine.length() - 1).strip(), "     ");
				tokens = st.countTokens();
				while (st.hasMoreTokens()) {
					Integer.parseInt(st.nextToken().strip());
				}
				if (tokens < 4) {
					br.close();
					return false;
				}
				if (tokens % 2 != 0) {
					br.close();
					return false;
				}
			}
		} catch (Exception e) {
			br.close();
			return false;
		}
		br.close();
		return true;
	}
	
	public boolean verifyCoordinateFile(File file) throws IOException {
		int bufferSize = ((int) file.length() + (1024 - ((int) file.length() % 1024)));
		BufferedReader br = new BufferedReader(new FileReader(file), bufferSize);
		StringTokenizer st;
		String readLine = "";
		
		try {
			while ((readLine = br.readLine()) != null) {
				st = new StringTokenizer(readLine.strip(), "   ");
				Integer.parseInt(st.nextToken().strip());
				Integer.parseInt(st.nextToken().strip());
				Double.parseDouble(st.nextToken().strip());
				Double.parseDouble(st.nextToken().strip());
			}
		} catch (Exception e) {
			br.close();
			return false;
		}
		br.close();
		return true;
	}
	
	public int getFileLength(BufferedReader br) throws IOException {
		int count = 0;
		while (br.readLine() != null) count++;
		return count;
	}
		
	public void sort(FilterType t) {
		switch (t) {
		case time:
			Collections.sort(cellArray, timeComparator);
			break;
		case id:
			Collections.sort(cellArray, idComparator);
			break;
		case layer:
			Collections.sort(cellArray, layerComparator);
			break;
		}
	}
	
	public void addCell(WaterCell cell) {
		cellArray.add(cell);
	}
	
	public WaterCell[] getCells() {
		WaterCell[] c = new WaterCell[cellArray.size()];
		return cellArray.toArray(c);
	}
	
	public void setCells(WaterCell[] cells) {
		this.cellArray = new ArrayList<WaterCell>(Arrays.asList(cells));
	}
	
	public WaterCell[] filter(WaterCell[] array, double value, FilterType t) {
		ArrayList<WaterCell> cells = new ArrayList<WaterCell>();
		switch (t) {
		case time:
			sort(FilterType.time);
			for(WaterCell c: array) {
				if(c.getTime() == value) {
					cells.add(c);
				}
			}
			break;
		case id:
			sort(FilterType.id);
			for(WaterCell c: array) {
				if(c.getCellID() == value) {
					cells.add(c);
				}
			}
			break;
		case layer:
			sort(FilterType.layer);
			for(WaterCell c: array) {
				if(c.getLayer() == value) {
					cells.add(c);
				}
			}
			break;
		}
		WaterCell[] c = new WaterCell[cells.size()];
		return cells.toArray(c);
	}
	
	public void createNewCellArray(int dataFileLength) {
		cellArray = new ArrayList<WaterCell>(dataFileLength);
	}
	
	public int getDataFileLength() {
		return dataFileLength;
	}

	public void setDataFileLength(int dataFileLength) {
		this.dataFileLength = dataFileLength;
	}
	
	public int getIndexFileLength() {
		return indexFileLength;
	}

	public void setIndexFileLength(int indexFileLength) {
		this.indexFileLength = indexFileLength;
	}
	
	public int getCoordinateFileLength() {
		return coordinateFileLength;
	}

	public void setCoordinateFileLength(int coordinateFileLength) {
		this.coordinateFileLength = coordinateFileLength;
	}

	public int getIds() {
		return ids;
	}

	public void setIds(int ids) {
		this.ids = ids;
	}
	
	public Double[] getTimes() {
		Double[] d = new Double[times.size()];
		return times.toArray(d);
	}

	public void addTime(double time) {
		times.add(time);
	}
	
	public void deleteTimes() {
		times.clear();
	}
	
	public int getLayers() {
		return layers;
	}
	
	public void setLayers(int layers) {
		this.layers = layers;
	}
}
