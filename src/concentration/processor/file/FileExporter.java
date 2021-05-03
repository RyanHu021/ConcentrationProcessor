package concentration.processor.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import concentration.processor.data.CellManager;
import concentration.processor.data.FilterType;
import concentration.processor.data.StatsCalculator;
import concentration.processor.data.WaterCell;

public class FileExporter {
	
	private static final String[] COLUMNS = {"Time:", "Minimum:", "Maximum:", "Average:"};
	
	public File exportDataToExcel(CellManager cm, File file) throws IOException {
		FileOutputStream stream = new FileOutputStream(file);
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Concentration");
		double[][] results = calculate(cm);
		
		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < COLUMNS.length; i++) {
			headerRow.createCell(i).setCellValue(COLUMNS[i]);;
		}
		
		for (int i = 0; i < results.length; i++) {
			Row row = sheet.createRow(i + 1);
			for (int j = 0; j < results[0].length; j++) {
				row.createCell(j).setCellValue(results[i][j]);
			}
			System.out.println("row " + i);
		}
		
		for(int i = 0; i < COLUMNS.length; i++) {
            sheet.autoSizeColumn(i);;
        }
		
		workbook.write(stream);
		stream.close();
		workbook.close();
		return null;
	}
	
	private double[][] calculate(CellManager cm) {
		double[][] stats = new double[cm.getTimes().length][4];
		cm.sort(FilterType.time);
		for (int i = 0; i < cm.getTimes().length; i++) {
			WaterCell[] cells = cm.filter(cm.getCells(), cm.getTimes()[i], FilterType.time);
			double[] concentration = new double[cells.length];
			for (int j = 0; j < concentration.length; j++) {
				concentration[j] = cells[j].getConcentration();
			}
			stats[i][0] = cm.getTimes()[i];
			stats[i][1] = StatsCalculator.min(concentration);
			stats[i][2] = StatsCalculator.max(concentration);
			stats[i][3] = StatsCalculator.avg(concentration);
		}
		return stats;
	}
}
