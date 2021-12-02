package com.javamaster.controller;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class complie {
    public String runComplie(String code, String path){

        String source = code;
        File folder = new File(path);
        File sourceFile = new File(folder, "Java.java");

        try {
            Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        //        System.out.println(sourceFile.getPath());
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            //System.out.println("JDK required (running inside of JRE)");
        } else {
            //System.out.println("you got it!");
        }

        try {
            // Create a stream to hold the output
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            // IMPORTANT: Save the old System.out!
            PrintStream old = System.out;
            // Tell Java to use your special stream
            System.setOut(ps);

            int compilationResult = compiler.run(null, null, null, sourceFile.getPath());
            if (compilationResult == 0) {
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {folder.toURI().toURL() });
                Class<?> cls = Class.forName("Java", true, classLoader);
                Method method = cls.getMethod("main", String[].class);

                Object[] args = new Object[1];
                args[0] = new String[] { "1", "2"};
                System.out.println(method.invoke(null, args));
            } else {
                System.out.println("Compilation Failed");
            }

            // Put things back
            System.out.flush();
            System.setOut(old);
            // Show what happened
            System.out.println("Here: " + baos.toString());

            return baos.toString();
        } catch (Exception e) {
//            e.printStackTrace();
            return "Lỗi biên dịch";
        }
    }
}
