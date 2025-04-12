package org.example.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.Student;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUtil {

  // 处理 MultipartFile 的方法
  public static <T> List<T> readExcel(MultipartFile file, Class<T> targetType) {
    try (InputStream inputStream = file.getInputStream()) {
      return readExcelFromInputStream(inputStream, targetType);
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  // 处理 File 的方法
  public static <T> List<T> readExcel(File file, Class<T> targetType) {
    try (FileInputStream fis = new FileInputStream(file)) {
      return readExcelFromInputStream(fis, targetType);
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  // 通用的读取 Excel 的方法
  private static <T> List<T> readExcelFromInputStream(InputStream inputStream,
                                                      Class<T> targetType) {
    List<T> objectList = new ArrayList<>();

    try (Workbook workbook = new XSSFWorkbook(inputStream)) {
      Sheet sheet = workbook.getSheetAt(0);
      Iterator<Row> rowIterator = sheet.iterator();

      // 跳过表头
      if (rowIterator.hasNext()) {
        rowIterator.next();
      }

      Field[] fields = targetType.getDeclaredFields();

      while (rowIterator.hasNext()) {
        Row row = rowIterator.next();
        T obj = targetType.getDeclaredConstructor().newInstance();

        for (int i = 0; i < fields.length; i++) {
          Field field = fields[i];
          field.setAccessible(true);

          Cell cell = row.getCell(i);
          if (cell != null) {
            // 获取字段类型
            Class<?> fieldType = field.getType();

            // 获取单元格值
            Object value = getCellValue(cell, fieldType);

            // 如果字段是 Integer 类型，尝试从 String 转换为 Integer
            if (fieldType == Integer.class || fieldType == int.class) {
              if (value instanceof String) {
                try {
                  value = Integer.parseInt((String) value);
                } catch (NumberFormatException e) {
                  value = 0; // 默认值，或记录错误
                }
              }
            }

            // 设置对象的字段值
            field.set(obj, value);
          }
        }

        objectList.add(obj);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return objectList;
  }

  // 获取单元格值的方法
  // 获取单元格值的方法
  private static Object getCellValue(Cell cell, Class<?> fieldType) {
    switch (cell.getCellType()) {
      case STRING:
        return cell.getStringCellValue();
      case NUMERIC:
        if (fieldType == int.class || fieldType == Integer.class) {
          return (int) cell.getNumericCellValue();
        /*} else if (fieldType == double.class || fieldType == Double.class) {
          return cell.getNumericCellValue();*/
        } else if (fieldType == String.class) {
          return String.valueOf(cell.getNumericCellValue());
        }
        return cell.getNumericCellValue();
      case BOOLEAN:
        return cell.getBooleanCellValue();
      default:
        return null;
    }
  }


  public static void main(String[] args) {
    String filePath = "your_file_path";
    try (FileInputStream input = new FileInputStream(filePath)) {
//      MultipartFile file = new MockMultipartFile("file", "学生名单.xlsx",
//          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", input);
      MultipartFile file = new MockMultipartFile("student", input);

      List<Student> students = ExcelUtil.readExcel(file, Student.class);
      System.out.println(students);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}