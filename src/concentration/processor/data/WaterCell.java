package concentration.processor.data;
import java.awt.Polygon;

public class WaterCell {
		
	private double time;
	private int cellID;
	private int layer;
	private double concentration;
	private int indexI;
	private int indexJ;
	private Polygon polygon;
	
	public WaterCell(double time, int cellID, int layer, double concentration) {
		this.time = time;
		this.cellID = cellID;
		this.layer = layer;
		this.concentration = concentration;
		indexI = 0;
		indexJ = 0;
		this.polygon = null;
	}
	
	public void initIndex(int i, int j, Polygon polygon) {
		setIndexI(i);
		setIndexJ(j);
		setPolygon(polygon);
	}
	
	public String toString() {
		return "time=" + time + " id=" + cellID + " layer=" + layer + " concentration=" + concentration;
	}
	
	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public int getCellID() {
		return cellID;
	}

	public void setCellID(int cellID) {
		this.cellID = cellID;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public double getConcentration() {
		return concentration;
	}

	public void setConcentration(double concentration) {
		this.concentration = concentration;
	}

	public int getIndexI() {
		return indexI;
	}

	public void setIndexI(int indexI) {
		this.indexI = indexI;
	}

	public int getIndexJ() {
		return indexJ;
	}

	public void setIndexJ(int indexJ) {
		this.indexJ = indexJ;
	}

	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}
}
