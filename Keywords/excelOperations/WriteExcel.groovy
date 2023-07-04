package excelOperations
import java.util.Map.Entry;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import internal.GlobalVariable

public class WriteExcel {

	/*
	 * This keyword is used to store voicemail,fax attached file name
	 * writing filename in excel sheet
	 */
	@Keyword
	public void storeFileNametoExcel(String filePath,String filename,String sheetName) throws IOException{

		filePath=RunConfiguration.getProjectDir()+"//"+filePath;
		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		XSSFSheet sheet = workbook.getSheet(sheetName);

		Row headerRow = sheet.createRow(0)
		headerRow.createCell(0).setCellValue("FileName")

		//Data Present in Row 1
		Row dataRow = sheet.createRow(1)
		dataRow.createCell(0).setCellValue(filename)

		fis.close()
		FileOutputStream fos = new FileOutputStream(filePath);
		workbook.write(fos);
		fos.close();
	}

	@Keyword
	public void saveChromeWindow(String filePath,HashMap map) throws IOException{

		filePath=RunConfiguration.getProjectDir()+"/"+filePath;
		FileInputStream fis = new FileInputStream(new File(filePath));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Sheet1");

		int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
		for(Entry e:map.entrySet()){


			Row row = sheet.getRow(e.key);
			println(e)
			Cell cell = row.createCell(6,CellType.STRING);
			cell.setCellValue(e.value);
		}
		fis.close()
		FileOutputStream fos = new FileOutputStream(filePath);
		workbook.write(fos);
		fos.close();
	}


	/*
	 * Fetching Existing Number of Account and Number of Users
	 * Write into "Existing_Account_User_Count" data sheet in Agents excel
	 *
	 */

	@Keyword
	public void ExistingAccountsData(Number existingNumberOfAccounts,List<Number> existingNumberOfUsers){
		def excelSheetPath =RunConfiguration.getProjectDir()+"/"+GlobalVariable.DataFileSelection.get('DataFilePath')

		FileInputStream fileInput = new FileInputStream(excelSheetPath)

		XSSFWorkbook workbook = new XSSFWorkbook(fileInput)

		XSSFSheet sheet = workbook.getSheet("Existing_Account_User_Count")

		Row headerRow = sheet.createRow(0)

		headerRow.createCell(0).setCellValue("existingAccounts")

		//Headers of the Sheet
		for(def i=1; i<= existingNumberOfUsers.size(); i++){
			String noOfUsers = "ExistingNoOfUsers"+i
			headerRow.createCell(i).setCellValue(noOfUsers)
		}

		//Data Present in Row 1
		Row dataRow = sheet.createRow(1)
		dataRow.createCell(0).setCellValue(existingNumberOfAccounts)

		for(def j =1; j<= existingNumberOfUsers.size(); j++){
			dataRow.createCell(j).setCellValue(existingNumberOfUsers[j-1])
		}

		FileOutputStream fileOut = new FileOutputStream(excelSheetPath);
		workbook.write(fileOut)
		fileOut.close()

		// Closing the workbook
		workbook.close()
	}


	/*
	 * Provide Number of Account and Number of Users
	 * Write into "GenericTestData" data sheet in Agents excel
	 * 
	 */

	@Keyword
	public void writeToExcel(Number numberOfAccounts,List<Number> numberOfUsers){
		def excelSheetPath =RunConfiguration.getProjectDir()+"/"+GlobalVariable.DataFileSelection.get('DataFilePath')

		FileInputStream fileInput = new FileInputStream(excelSheetPath)

		XSSFWorkbook workbook = new XSSFWorkbook(fileInput)

		XSSFSheet sheet = workbook.getSheet("GenericTestData")

		Row headerRow = sheet.createRow(0)

		headerRow.createCell(0).setCellValue("NoOfAccounts")

		//Headers of the Sheet
		for(def i=1; i<= numberOfUsers.size(); i++){
			String noOfUsers = "NoOfUsers"+i
			headerRow.createCell(i).setCellValue(noOfUsers)
		}

		//Data Present in Row 1
		Row dataRow = sheet.createRow(1)
		dataRow.createCell(0).setCellValue(numberOfAccounts)

		for(def j =1; j<= numberOfUsers.size(); j++){
			dataRow.createCell(j).setCellValue(numberOfUsers[j-1])
		}

		FileOutputStream fileOut = new FileOutputStream(excelSheetPath);
		workbook.write(fileOut)
		fileOut.close()

		// Closing the workbook
		workbook.close()
	}
	@Keyword
	public void writeParameterToExcel(String filePath, HashMap dynamic_parameters, String sheetName, int keyCell, int valueCell) throws IOException{

		filePath=RunConfiguration.getProjectDir()+"//"+filePath;
		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		XSSFSheet sheet = workbook.getSheet(sheetName);

		Row headerRow = sheet.createRow(0)
		Row dataRow = sheet.createRow(1)

		for(Entry e:dynamic_parameters.entrySet()){
			//Create a header cell
			headerRow.createCell(keyCell).setCellValue(e.key)
			keyCell++

			//create a value of cell
			dataRow.createCell(valueCell).setCellValue(e.value)
			valueCell++
		}

		fis.close()
		FileOutputStream fos = new FileOutputStream(filePath);
		workbook.write(fos);
		fos.close();
	}
}
