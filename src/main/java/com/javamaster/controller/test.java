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

public class test {
    public static void main(String[] args) {

        String source = "public class Solution{public int run(){return 1 + 5;}}";
        File folder = new File("./src/main/java/com/javamaster/controller");
        File sourceFile = new File(folder, "Solution.java");

        try {
            Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(sourceFile.getPath());
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            //System.out.println("JDK required (running inside of JRE)");
        } else {
            //System.out.println("you got it!");
        }

        int compilationResult = compiler.run(null, null, null, sourceFile.getPath());
        if (compilationResult == 0) {
            //System.out.println("Compilation is successful");
        } else {
            //.out.println("Compilation Failed");
        }

        try {
            // Create a stream to hold the output
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            // IMPORTANT: Save the old System.out!
            PrintStream old = System.out;
            // Tell Java to use your special stream
            System.setOut(ps);

            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {folder.toURI().toURL() });
            Class<?> cls = Class.forName("Solution", true, classLoader);
            Object instance = cls.newInstance();
            Method method = cls.getDeclaredMethod("run", null);
            System.out.println(method.invoke(instance, null));

            // Put things back
            System.out.flush();
            System.setOut(old);
            // Show what happened
            System.out.println("Here: " + baos.toString());

            sourceFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("something wrong");
        }
    }
}
