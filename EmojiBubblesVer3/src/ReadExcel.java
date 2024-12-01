import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class ReadExcel {

	public static EmotionData readEmotionResultNames(String fileName, EmotionData emotionData) throws IOException {
		File file = new File(fileName);
		try (FileInputStream fis = new FileInputStream(file)) {
			Workbook workbook = null;

			workbook = new HSSFWorkbook(fis);

			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				emotionData.addEmotionResult(new EmotionResult(workbook.getSheetName(i)));
			}

			workbook.close();

			return emotionData;
		}
	}

	public static EmotionData ReadEmotionData(String filename, EmotionData emotionData) {
		String[][] dataTable = null;
		File file = new File(filename);
		try {
			// Create a file input stream to read Excel workbook and worksheet
			FileInputStream xlfile = new FileInputStream(file);
			HSSFWorkbook xlwb = new HSSFWorkbook(xlfile);

			for (int x = 0; x < emotionData.getEmotionResultArraylist().size(); x++) {
				HSSFSheet xlSheet = xlwb.getSheetAt(x);

				// Get the number of rows and columns
				int numRows = xlSheet.getLastRowNum() + 1;
				int numCols = 0;

				// Create double array data table - rows x cols
				// We will return this data table
				dataTable = new String[numRows][numCols];

				// For each row, create a HSSFRow, then iterate through the "columns"
				// For each "column" create an HSSFCell to grab the value at the (i,j) cell
				// i=1 -> to skip first row with columns names
				for (int i = 1; i < numRows; i++) {

					// count columns
					numCols = xlSheet.getRow(i).getLastCellNum();

					HSSFRow xlRow = xlSheet.getRow(i);

					for (int j = 0; j < numCols; j++) {
						HSSFCell xlCell = xlRow.getCell(j);
						if (j == 0) // emotion name
							emotionData.getEmotionResultArraylist().get(x).getEmotionsArraylist()
									.add(new Emotion(xlCell.toString()));
						else if (j == 1) // emotion definition
							emotionData.getEmotionResultArraylist().get(x).getEmotionsArraylist().get(i - 1)
									.setDefinition(xlCell.toString());
						else // emotion questions
							emotionData.getEmotionResultArraylist().get(x).getEmotionsArraylist().get(i - 1)
									.addQuestion(xlCell.toString());
						// dataTable[i][j] = xlCell.toString();
					} // end for of columns

				} // end for of rows

			} // end for of sheet
		} catch (IOException e) {
			System.out.println("ERROR FILE HANDLING " + e.toString());
		}
		return emotionData;
	}

	public static void main(String args[]) {

		EmotionData emData = new EmotionData();
		// display sheets names
		try {
			emData = readEmotionResultNames("excelEmotionFolder//emotions.xls", emData);
			// display sheet data
			emData = ReadEmotionData("excelEmotionFolder//emotions.xls", emData);
			System.out.println(emData);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
