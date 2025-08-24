package ru.ryb.budget.service;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import ru.ryb.budget.dto.PeriodExpenses;
import ru.ryb.budget.model.Category;
import ru.ryb.budget.model.IExpensesForUnload;
import ru.ryb.budget.repository.CategoryRepository;
import ru.ryb.budget.repository.ExpensesRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UnloadService {

    private final CategoryRepository categoryRepository;
    private final ExpensesRepository expensesRepository;

    public UnloadService(CategoryRepository categoryRepository, ExpensesRepository expensesRepository) {
        this.categoryRepository = categoryRepository;
        this.expensesRepository = expensesRepository;
    }

    public ByteArrayResource getUnloadFile(PeriodExpenses period) {
        List<Category> categories = categoryRepository.findAll();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            for (Category category : categories) {
                XSSFSheet sheet = workbook.createSheet(category.getName());

                period.setCategoryId(category.getId());
                List<IExpensesForUnload> dataList = fillExpensesModel(period);

                int rowNum = 0;

                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue("Дата");
                row.createCell(1).setCellValue("Сумма");

                List<BigDecimal> sumFinal = new ArrayList<>();

                for (IExpensesForUnload dataModel : dataList) {
                    createSheetHeaderExpenses(sheet, ++rowNum, workbook, dataModel);
                    sumFinal.add(dataModel.getAmount());
                }

                Row rowFinal = sheet.createRow(++rowNum);
                rowFinal.createCell(0).setCellValue("Всего");
                PeriodExpenses periodExpenses = new PeriodExpenses();
                periodExpenses.setCategoryId(category.getId());
                rowFinal.createCell(1).setCellValue(sumFinal.stream().reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
            }
            workbook.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Excel файл успешно создан!");

        return new ByteArrayResource(stream.toByteArray());
    }

    private void createSheetHeaderExpenses(XSSFSheet sheet, int rowNum, XSSFWorkbook workbook, IExpensesForUnload dataModel) {
        Row row = sheet.createRow(rowNum);

        CreationHelper creationHelper = workbook.getCreationHelper();
        CellStyle cellStyleDate = workbook.createCellStyle();

        cellStyleDate.setDataFormat(creationHelper.createDataFormat().getFormat("dd.mm.yyyy"));
        Date date = Date.from(dataModel.getCreateDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        row.createCell(0).setCellValue(date);
        row.getCell(0).setCellStyle(cellStyleDate);
        sheet.autoSizeColumn(0);
        row.createCell(1).setCellValue(dataModel.getAmount().doubleValue());
    }

    private List<IExpensesForUnload> fillExpensesModel(PeriodExpenses period) {
        return expensesRepository.findAllUnloadByCategoryIdAndCreateDateBetween(period.getCategoryId(), period.getBeginDate(), period.getEndDate());
    }

}
