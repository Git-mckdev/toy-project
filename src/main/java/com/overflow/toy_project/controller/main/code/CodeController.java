package com.overflow.toy_project.controller.main.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.overflow.toy_project.model.Code;
import com.overflow.toy_project.service.CodeService;

@Controller
@RequestMapping("/main/code")
public class CodeController {
    
    private final CodeService codeService;

    @Autowired
    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    // 코드 실행기 기능
    @GetMapping("/execution")
    public String viewCodeExecutionPage() {
        return "main/code/execution";
    }

    @PostMapping("/execute")
    public ResponseEntity<String> codeExecute(@RequestBody Code code) {
        String output = codeService.execute(code.getLanguage(), code.getCodeContent());

        return ResponseEntity.ok(output);
    }
}