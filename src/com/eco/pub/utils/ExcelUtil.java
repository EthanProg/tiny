package com.eco.pub.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @Description：EXCEL导出工具类
 *
 * @author Ethan
 * @date 2016年3月7日
 * 
 * @Description：
 * @modifier 
 */
public class ExcelUtil {
	
	public static final String HEADERINFO = "headInfo";
	public static final String DATAINFON = "dataInfo";
	
	/**
	 * 
	 * @Title: 
	 * @Description: 创建一个webbook，对应一个Excel文件,默认创建xls格式
	 * @return Workbook
	 * @author Ethan 2016年3月7日
	 *
	 * @Description:
	 * @modifier
	 */
	public static Workbook getWebBook() {
		return new HSSFWorkbook();
	}
	
	
	/**
	 * 
	 * @Title: 
	 * @Description: 创建一个webbook，对应一个Excel文件,默认创建xlsx格式
	 * @return Workbook
	 * @author Ethan 2016年3月7日
	 *
	 * @Description:
	 * @modifier
	 */
	public static Workbook getWebBookXlsx() {
		return new XSSFWorkbook();
	}
	
	
	/**
	 * 
	 * @Title: 
	 * @Description: 输出Excel流
	 * @param fileName 导出Excel表的文件路径
	 * @param map 封装需要导出的数据(HEADERINFO封装表头信息，DATAINFON：封装要导出的数据信息,此处需要使用TreeMap)
	 * 例如 : map.put(ExcelUtil.HEADERINFO,List<String> headList);
     *       map.put(ExcelUtil.DATAINFON,List<TreeMap<String,Object>>  dataList);
	 * @param wb
	 * @param req
	 * @param rep
	 * @throws IOException void
	 * @author Ethan 2016年3月28日
	 *
	 * @Description: 
	 * @modifier
	 */
	@SuppressWarnings("unchecked")
	public static void writeExcel(String fileName,Map<String,Object> map,Workbook wb,HttpServletRequest req, HttpServletResponse rep){
		if (null != map && null != wb) {
		    fileName = fileName == null ? PubTool.uuid() : fileName;
		    
			List headList = (List) map.get(ExcelUtil.HEADERINFO);
			List dataList = (List) map.get(ExcelUtil.DATAINFON);
			
			CellStyle style = getCellStyle(wb);
			Sheet sheet = wb.createSheet();
			
			Row row = sheet.createRow(0);
			int size = headList.size();
			for (int i = 0; i < size; i++) {
				sheet.setColumnWidth(i, 3766);
				Cell headCell = row.createCell(i);
				headCell.setCellType(Cell.CELL_TYPE_STRING);
				headCell.setCellStyle(style);
				headCell.setCellValue(String.valueOf(headList.get(i)));
			}
			
			if (PubTool.isListHasData(dataList)) {
				size = dataList.size();
				for (int i = 0; i < size; i++) {
					Row rowdata = sheet.createRow(i + 1);
					TreeMap<String, Object> mapdata = (TreeMap<String, Object>) dataList.get(i);
					Iterator<String> it = mapdata.keySet().iterator();
					int j = 0;
					while (it.hasNext()) {
						String strdata = String.valueOf(mapdata.get(it.next()));
						Cell celldata = rowdata.createCell(j);
						celldata.setCellType(Cell.CELL_TYPE_STRING);
						celldata.setCellValue(strdata);
						j++;
					}
				}
			}
			
			try {
			    String user_agent = req.getHeader("user-agent");
			    if (user_agent.indexOf("MSIE") != -1 || user_agent.indexOf("rv:11") != -1) {
	                fileName = java.net.URLEncoder.encode(fileName, "utf-8") + ".xls";
	            } else {
	                fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1") + ".xls";
	            }
			} catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
			
			ServletOutputStream servletoutputstream;
            try {
                servletoutputstream = rep.getOutputStream();
                rep.setHeader("Content-disposition", "attachment; filename=" + fileName);
                rep.setDateHeader("Expires", 0);
                rep.setContentType("application/vnd.ms-excel;charset=utf-8");
                wb.write(servletoutputstream);
                servletoutputstream.flush();
                servletoutputstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
	}
	
	
	/**
	 * 
	 * @Title: getCellStyle
	 * @Description: 设置表头样式
	 * @param wb
	 * @return CellStyle
	 * @author Ethan 2016年3月7日
	 *
	 * @Description: 
	 * @modifier
	 */
	public static CellStyle getCellStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(HSSFCellStyle.SOLID_FOREGROUND);// 让单元格居中
		style.setFont(font);
		return style;
	}
	
	/**
	 * 
	 * @Title: 
	 * @Description: 获取表格数据
	 * @param cell
	 * @return String
	 * @author Ethan 2016年3月28日
	 *
	 * @Description:
	 * @modifier
	 */
	public static String getCellValue(HSSFCell cell) {
		if (null == cell) {
			return null;
		}
		String value = "";
		int cellType = cell.getCellType();
		switch (cellType) {
		case HSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			value = cell.getNumericCellValue() + "";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue() + "";
			break;
		default:
		    break;
		}
		return value;
	}
}
