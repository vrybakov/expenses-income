package ru.ryb.budget.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ryb.budget.dto.PeriodExpenses;
import ru.ryb.budget.service.UnloadService;

@RestController
@RequestMapping("v1/unload")
public class UnloadController {

    private final UnloadService unloadService;

    public UnloadController(UnloadService unloadService) {
        this.unloadService = unloadService;
    }

    @GetMapping
    public ResponseEntity<ByteArrayResource> getUnloadFile(@RequestBody PeriodExpenses request) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Expenses-" + request.getBeginDate() + "-" + request.getEndDate() + ".xlsx");
        return new ResponseEntity<>(unloadService.getUnloadFile(request),
                header, HttpStatus.CREATED);
    }

}
