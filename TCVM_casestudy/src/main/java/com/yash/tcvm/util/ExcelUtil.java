package com.yash.tcvm.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yash.tcvm.dao.ContainerDao;
import com.yash.tcvm.daoimpl.ContainerDaoImpl;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.literal.ExcelSheetConstants;
import com.yash.tcvm.model.Container;
import com.yash.tcvm.service.ContainerService;
import com.yash.tcvm.serviceimpl.ContainerServiceImpl;

/**
 * This class defines and implements the functionality associated with the excel
 * utility.
 * 
 * @author soumya.gupta
 *
 */
public class ExcelUtil {

	public static void main(String[] args) throws IOException, InvalidFormatException, EmptyException {

		ContainerDao containerDao = new ContainerDaoImpl();
		ContainerService containerService = new ContainerServiceImpl(containerDao);

		List<String> columns = new ArrayList<String>();
		columns.add("Ingredient");
		columns.add("Maximum capacity");
		columns.add("Current Availability");

		List<Container> containers = containerService.getContainers();
		createExcelSheet(columns, "Container's status", ExcelSheetConstants.CONTAINER_STATUS_REPORT, containers);

	}

	public static void createExcelSheet(List<String> columns, String sheetName, String fileName,
			List<Container> containers) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);

		for (int i = 0; i < columns.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns.get(i));
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		for (Container container : containers) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(container.getIngredient().toString());
			row.createCell(1).setCellValue(container.getMaxCapacity());
			row.createCell(2).setCellValue(container.getCurrentAvailability());
		}

		for (int i = 0; i < columns.size(); i++) {
			sheet.autoSizeColumn(i);
		}

		FileOutputStream fileOut = new FileOutputStream(ExcelSheetConstants.EXCEL_SHEET_FILE_PATH.concat(fileName));
		workbook.write(fileOut);
		fileOut.close();

		workbook.close();

	}
}
