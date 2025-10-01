package com.overflow.toy_project.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class CodeService {
    
    public String execute(String language, String codeContent) {
        try {
            Path tempDir = Files.createTempDirectory("code-exec");
            Path codeFile;

            ProcessBuilder pb;

            switch (language) {
                case "java":
                    codeFile = tempDir.resolve("Main.java");
                    
                    Files.writeString(codeFile, codeContent);

                    pb = new ProcessBuilder("javac", codeFile.toString());

                    Process compile = pb.start();
                    compile.waitFor();

                    if (compile.exitValue() != 0) {
                        return new String(compile.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                    }

                    pb = new ProcessBuilder("java", "-cp", tempDir.toString(), "Main");
                    break;
                case "python":
                    codeFile = tempDir.resolve("main.py");

                    Files.writeString(codeFile, codeContent);

                    pb = new ProcessBuilder("python", codeFile.toString());
                    break;
                default:
                    return "지원하지 않는 언어입니다.";
            }

            pb.redirectErrorStream(true);

            Process process = pb.start();

            if (!process.waitFor(5, TimeUnit.SECONDS)) {
                process.destroyForcibly();

                return "실행 시간 초과입니다.";
            }

            return new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8); //
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}