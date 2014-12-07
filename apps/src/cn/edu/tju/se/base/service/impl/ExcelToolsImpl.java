package cn.edu.tju.se.base.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import cn.edu.tju.se.base.service.Debug;
import cn.edu.tju.se.base.service.ExcelTools;

/**
 * Demonstrates <em>one</em> way to read from and write to an Excel spreadsheet
 * if the worksheet looked like this;
 * 
 * <pre>
 *  ___________________________________________
 *     |       |       |       |       |       |
 *     |   A   |   B   |   C   |   D   |   E   |
 *  ___|_______|_______|_______|_______|_______|
 *     |       |       |       |       |       |
 *   1 |   1   |   2   |   3   |   4   |   5   |
 *  ___|_______|_______|_______|_______|_______|
 *     |       |       |       |       |       |
 *   2 |       |       |       |       |       |
 *  ___|_______|_______|_______|_______|_______|
 *     |       |       |       |       |       |
 *   3 |       |   A   |       |   B   |       |
 *  ___|_______|_______|_______|_______|_______|
 *     |       |       |       |       |       |
 *   4 |       |       |       |       |   Z   |
 *  ___|_______|_______|_______|_______|_______|
 *     |       |       |       |       |       |
 *   5 | 1,400 |       |  250  |       |       |
 *  ___|_______|_______|_______|_______|_______|
 * 
 * </pre>
 * 
 * <p>
 * If a field contains the separator then it will be escaped. If the file should
 * obey Excel's CSV formatting rules, then the field will be surrounded with
 * speech marks whilst if it should obey UNIX conventions, each occurrence of
 * the separator will be preceded by the backslash character.
 * </p>
 * <p>
 * If a field contains an end of line (EOL) character then it too will be
 * escaped. If the file should obey Excel's CSV formatting rules then the field
 * will again be surrounded by speech marks. On the other hand, if the file
 * should follow UNIX conventions then a single backslash will precede the EOL
 * character. There is no single applicable standard for UNIX and some
 * appications replace the CR with \r and the LF with \n but this class will not
 * do so.
 * </p>
 * <p>
 * If the field contains double quotes then that character will be escaped. It
 * seems as though UNIX does not define a standard for this whilst Excel does.
 * Should the CSV file have to obey Excel's formmating rules then the speech
 * mark character will be escaped with a second set of speech marks. Finally, an
 * enclosing set of speah marks will also surround the entire field. Thus, if
 * the following line of text appeared in a cell - "Hello" he said - it would
 * look like this when converted into a field within a CSV file - """Hello"" he
 * said".
 * </p>
 * 
 * @author Li Gang
 * @version 1.00 2014/5/7
 */
@Service
public class ExcelToolsImpl implements ExcelTools {

	/**
	 * Process the contents of a folder, convert the contents of each Excel
	 * workbook into CSV format and save the resulting file to the specified
	 * folder using the same name as the original workbook with the .xls or
	 * .xlsx extension replaced by .csv
	 * 
	 * @param strSource
	 *            An instance of the String class that encapsulates the name of
	 *            and path to either a folder containing those Excel workbook(s)
	 *            or the name of and path to an individual Excel workbook that
	 *            is/are to be converted.
	 * @param strDestination
	 *            An instance of the String class encapsulating the name of and
	 *            path to a folder that will contain the resulting CSV files.
	 * @param formattingConvention
	 *            A primitive int whose value will determine whether certain
	 *            embedded characters should be escaped in accordance with
	 *            Excel's or UNIX formatting conventions. Two constants are
	 *            defined to support this option; ToCSV.EXCEL_STYLE_ESCAPING and
	 *            ToCSV.UNIX_STYLE_ESCAPING
	 * @param separator
	 *            An instance of the String class encapsulating the characters
	 *            or characters that should be used to separate items on a line
	 *            within the CSV file.
	 * @throws java.io.FileNotFoundException
	 *             Thrown if any file cannot be located on the filesystem during
	 *             processing.
	 * @throws java.io.IOException
	 *             Thrown if the filesystem encounters any problems during
	 *             processing.
	 * @throws java.lang.IllegalArgumentException
	 *             Thrown if the values passed to the strSource parameter refers
	 *             to a file or folder that does not exist, if the value passed
	 *             to the strDestination paramater refers to a folder that does
	 *             not exist, if the value passed to the strDestination
	 *             parameter does not refer to a folder or if the value passed
	 *             to the formattingConvention parameter is other than one of
	 *             the values defined by the constants
	 *             ToCSV.EXCEL_STYLE_ESCAPING and ToCSV.UNIX_STYLE_ESCAPING.
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
	 *             Thrown if the xml markup encounetered whilst parsing a
	 *             SpreadsheetML file (.xlsx) is invalid.
	 */

	private File[] checkSource(String strSource) {
		File source = new File(strSource);
		File[] filesList = null;

		// Check that the source file/folder exists.
		if (!source.exists()) {
			throw new IllegalArgumentException("The source for the Excel "
					+ "file(s) cannot be found.");
		}
		// Check to see if the sourceFolder variable holds a reference to
		// a file or a folder full of files.
		if (source.isDirectory()) {
			// Get a list of all of the Excel spreadsheet files (workbooks) in
			// the source folder/directory
			filesList = source.listFiles(new ExcelFilenameFilter());
		} else {
			// Assume that it must be a file handle - although there are other
			// options the code should perhaps check - and store the reference
			// into the filesList variable.
			filesList = new File[] { source };
		}
		return filesList;
	}

	private File checkDest(String strDestination) throws FileNotFoundException,
			IOException, IllegalArgumentException, InvalidFormatException {

		File destination = new File(strDestination);
		// Ensure that the folder the user has chosen to save the CSV files
		// away into firstly exists and secondly is a folder rather than, for
		// instance, a data file.
		if (!destination.exists()) {
			throw new IllegalArgumentException(
					"The output folder/directory for the "
							+ "Excel file(s) does not exist.");
		}
		if (!destination.isDirectory()) {
			throw new IllegalArgumentException("The destination for the Excel "
					+ "file(s) is not a directory/folder.");
		}
		return destination;
	}

	/**
	 * Open an Excel workbook ready for conversion.
	 * 
	 * @param file
	 *            An instance of the File class that encapsulates a handle to a
	 *            valid Excel workbook. Note that the workbook can be in either
	 *            binary (.xls) or SpreadsheetML (.xlsx) format.
	 * @throws java.io.FileNotFoundException
	 *             Thrown if the file cannot be located.
	 * @throws java.io.IOException
	 *             Thrown if a problem occurs in the file system.
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
	 *             Thrown if invalid xml is found whilst parsing an input
	 *             SpreadsheetML file.
	 */
	private Workbook openWorkbook(String strSource) {
		Workbook workbook = null;
		File file = new File(strSource);

		// Check that the source file/folder exists.
		if (!file.exists()) {
			throw new IllegalArgumentException("The source for the Excel "
					+ "file(s) cannot be found.");
		} else if (file.isDirectory()) {
			throw new IllegalArgumentException("The source is a directory.");
		} else {
			FileInputStream fis = null;
			try {
				Debug.out("Opening workbook [" + file.getName() + "] to read.");
				fis = new FileInputStream(file);

				// Open the workbook and then create the FormulaEvaluator and
				// DataFormatter instances that will be needed to, respectively,
				// force evaluation of formula found in cells and create a
				// formatted String encapsulating the cells contents.
				workbook = WorkbookFactory.create(fis);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return workbook;
	}

	/**
	 * Called to convert the contents of the currently opened workbook into a
	 * CSV file.
	 */
	public ArrayList<ArrayList<String>> readExcel(String strSource) {
		Workbook workbook = null;
		ArrayList<ArrayList<String>> excelData = null;

		DataFormatter formatter = null;
		FormulaEvaluator evaluator = null;

		Sheet sheet = null;
		Row row = null;
		int lastRowNum = 0;

		int maxRowWidth = 0;
		Cell cell = null;
		int lastCellNum = 0;
		ArrayList<String> excelLine = null;

		workbook = this.openWorkbook(strSource);
		if (workbook == null) {
			Debug.error("Error opening the workbook!");
			return null;
		} 
		
		evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		formatter = new DataFormatter(true);

		Debug.out("Reading excel file...");
		excelData = new ArrayList<ArrayList<String>>();
		
		// Discover how many sheets there are in the workbook....
		int numSheets = workbook.getNumberOfSheets();
		
		// and then iterate through them.
		for (int i = 0; i < numSheets; i++) {

			// Get a reference to a sheet and check to see if it contains
			// any rows.
			sheet = workbook.getSheetAt(i);
			if (sheet.getPhysicalNumberOfRows() > 0) {

				// Note down the index number of the bottom-most row and
				// then iterate through all of the rows on the sheet starting
				// from the very first row - number 1 - even if it is missing.
				// Recover a reference to the row and then call another method
				// which will strip the data from the cells and build lines
				// for inclusion in the resulting CSV file.
				lastRowNum = sheet.getLastRowNum();
				for (int j = 0; j <= lastRowNum; j++) {
					row = sheet.getRow(j);

					// Check to ensure that a row was recovered from the sheet
					// as it is
					// possible that one or more rows between other populated
					// rows could be
					// missing - blank. If the row does contain cells then...
					
					if (row != null) {
						excelLine = new ArrayList<String>();
						// Get the index for the right most cell on the row and
						// then
						// step along the row from left to right recovering the
						// contents
						// of each cell, converting that into a formatted String
						// and
						// then storing the String into the excelLine ArrayList.
						lastCellNum = row.getLastCellNum();
						
						for (int k = 0; k <= lastCellNum; k++) {
							cell = row.getCell(k);
							if (cell == null) {
								excelLine.add("");
							} else {
								if (cell.getCellType() != Cell.CELL_TYPE_FORMULA) {
									excelLine.add(formatter
											.formatCellValue(cell));
								} else {
									excelLine.add(formatter.formatCellValue(
											cell, evaluator));
								}
							}
						}
						// Make a note of the index number of the right most
						// cell. This value
						// will later be used to ensure that the matrix of data
						// in the CSV file is square.
						if (lastCellNum > maxRowWidth) {
							maxRowWidth = lastCellNum;
						}
					}
					excelData.add(excelLine);
				}
			}
		}
		return excelData;
	}

	public void writeExcel(String strDest,ArrayList<ArrayList<String>> excelData){
		Debug.out("Preparing data for writing excel file...");
		//创建excel工作簿 
        Workbook wb = new XSSFWorkbook();//HSSFWorkbook(); 
        //创建第一个sheet（页），命名为 new sheet 
        Sheet sheet = wb.createSheet("sheet1"); 
        //Row 行 
        //Cell 方格 
        // Row 和 Cell 都是从0开始计数的 
        
        int rowNo=0,cellNo=0;
        for (ArrayList<String> line: excelData){
        	// 创建一行，在页sheet上 
            Row row = sheet.createRow((short) rowNo++);
            cellNo=0;
            for(String text:line){
            	// 在row行上创建一个方格 
                Cell cell = row.createCell(cellNo++);
                //设置方格的显示 
                cell.setCellValue(text);
            }
        }
        
		try {     
			Debug.out("Opening workbook [" + strDest + "] to write.");
			//创建一个文件 命名为workbook.xls
			FileOutputStream fileOut = new FileOutputStream(strDest);
			// 把上面创建的工作簿输出到文件中 
	        wb.write(fileOut); 
	        //关闭输出流 
	        fileOut.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
	}
	
	public static void main(String[] args) {
		ExcelTools excelTools = new ExcelToolsImpl();
		ArrayList<ArrayList<String>> data = excelTools.readExcel("c:\\temp\\data.xlsx");
		for (ArrayList<String> line : data) {
			System.out.print("Cell:"+line.size());
			for (String text : line) {
				System.out.print("|" + text);
			}
			System.out.println();
		}
		excelTools.writeExcel("c:\\temp\\new.xlsx", data);

	}

	/**
	 * An instance of this class can be used to control the files returned be a
	 * call to the listFiles() method when made on an instance of the File class
	 * and that object refers to a folder/directory
	 */
	class ExcelFilenameFilter implements FilenameFilter {

		/**
		 * Determine those files that will be returned by a call to the
		 * listFiles() method. In this case, the name of the file must end with
		 * either of the following two extension; '.xls' or '.xlsx'. For the
		 * future, it is very possible to parameterise this and allow the
		 * containing class to pass, for example, an array of Strings to this
		 * class on instantiation. Each element in that array could encapsulate
		 * a valid file extension - '.xls', '.xlsx', '.xlt', '.xlst', etc. These
		 * could then be used to control which files were returned by the call
		 * to the listFiles() method.
		 * 
		 * @param file
		 *            An instance of the File class that encapsulates a handle
		 *            referring to the folder/directory that contains the file.
		 * @param name
		 *            An instance of the String class that encapsulates the name
		 *            of the file.
		 * @return A boolean value that indicates whether the file should be
		 *         included in the array retirned by the call to the listFiles()
		 *         method. In this case true will be returned if the name of the
		 *         file ends with either '.xls' or '.xlsx' and false will be
		 *         returned in all other instances.
		 */
		public boolean accept(File file, String name) {
			return (name.endsWith(".xls") || name.endsWith(".xlsx"));
		}
	}
}