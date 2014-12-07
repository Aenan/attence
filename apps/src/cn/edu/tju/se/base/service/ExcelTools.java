package cn.edu.tju.se.base.service;

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
public interface ExcelTools {

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

	public ArrayList<ArrayList<String>> readExcel(String strSource);
	
	public void writeExcel(String strDest,ArrayList<ArrayList<String>> excelData);
	
}