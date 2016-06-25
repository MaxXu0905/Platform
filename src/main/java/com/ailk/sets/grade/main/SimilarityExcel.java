package com.ailk.sets.grade.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ailk.sets.grade.utils.Document;
import com.ailk.sets.grade.utils.Similarity;

public class SimilarityExcel {

	public static void main(String[] args) {
		InputStream in = null;

		try {
			in = new FileInputStream(
					new File(
							"/Users/xugq/Documents/QuestionBank/测试/1070401300-测试-xugq.xlsx"));
			SimilarityExcel.loadFile(in, true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	public static void loadFile(InputStream in, boolean isXSSF)
			throws Exception {
		Workbook workBook;
		if (isXSSF)
			workBook = new XSSFWorkbook(in);
		else
			workBook = new HSSFWorkbook(in);

		for (int sheetId = 0; sheetId < workBook.getNumberOfSheets(); sheetId++) {
			Sheet sheet = workBook.getSheetAt(sheetId);
			List<Integer> columnIndexes = new ArrayList<Integer>();

			// 获取标题对应的索引
			Row head = sheet.getRow(0);
			for (int i = head.getFirstCellNum(); i < head.getLastCellNum(); i++) {
				String columnName = head.getCell(i).getStringCellValue();
				if (columnName.startsWith("title")
						|| columnName.startsWith("option")) {
					columnIndexes.add(i);
					break;
				}
			}

			List<Pair> pairs = new ArrayList<Pair>();

			// 第一行为标题，去掉
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				StringBuilder builder = new StringBuilder();
				for (int columnIndex : columnIndexes) {
					Cell cell = row.getCell(columnIndex);
					if (cell == null)
						continue;

					String value;
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						value = Boolean.toString(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if ((double) (int) cell.getNumericCellValue() == cell
								.getNumericCellValue()) {
							value = Integer.toString((int) cell
									.getNumericCellValue());
						} else {
							value = Double.toString(cell.getNumericCellValue());
						}
						break;
					case Cell.CELL_TYPE_BLANK:
						value = null;
						break;
					default:
						continue;
					}

					builder.append(value);
				}

				Pair pair = new Pair();
				pair.setRowNum(i);
				pair.setDocument(new Document(builder.toString()));
				pairs.add(pair);
			}

			for (int i = 0; i < pairs.size(); i++) {
				Pair pair1 = pairs.get(i);
				Document doc1 = pair1.getDocument();

				for (int j = i + 1; j < pairs.size(); j++) {
					Pair pair2 = pairs.get(j);
					Document doc2 = pair2.getDocument();

					double similarity = Similarity
							.calculateSimilary(doc1, doc2);
					if (similarity >= 0.8) {
						System.out
								.println("row (" + pair1.getRowNum() + ", "
										+ pair2.getRowNum() + ") 相似，相似度为："
										+ similarity);
					}
				}
			}
		}
	}

	private static class Pair {
		private int rowNum;
		private Document document;

		public int getRowNum() {
			return rowNum;
		}

		public void setRowNum(int rowNum) {
			this.rowNum = rowNum;
		}

		public Document getDocument() {
			return document;
		}

		public void setDocument(Document document) {
			this.document = document;
		}
	}

}
