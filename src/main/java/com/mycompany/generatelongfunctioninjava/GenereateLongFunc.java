package com.mycompany.generatelongfunctioninjava;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.Log;
import com.github.javaparser.utils.SourceRoot;
import java.io.FileInputStream;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.expr.MethodCallExpr;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GenereateLongFunc {

    public static HashMap<String, String> methodsBody = new HashMap<String, String>();
    public static HashMap<String, String> methodsCall = new HashMap<String, String>();

    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("Animal.java");
        Scanner out = new Scanner(new File("Animal.java"));
        String classContents = out.useDelimiter("\\Z").next().trim();
        CompilationUnit cu;
        try {
            cu = StaticJavaParser.parse(in);
        } finally {
            in.close();
        }
        new MethodVisitor().visit(cu, null);
        new MethodVisitorDec().visit(cu, null);

        String outputCode = "";

        for (Map.Entry<String, String> entry : methodsCall.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            outputCode = classContents.replace(methodsCall.get(key), methodsBody.get(key));
            classContents = outputCode;
        }
          FileWriter myWriter = new FileWriter("AnimalLongFunc.java");

            myWriter.write(outputCode); 
            myWriter.close(); 
            System.out.println(outputCode);
    }

    private static class MethodVisitor extends VoidVisitorAdapter {

        @Override
        public void visit(MethodCallExpr methodCall, Object arg) {
            if (methodCall.getScope().isEmpty()) {
                methodsCall.put(methodCall.getName().asString(), methodCall.getName() + "();");

            }

            List<Expression> args = methodCall.getArguments();
            if (args != null) {
                handleExpressions(args);
            }
        }

        private void handleExpressions(List<Expression> expressions) {
            for (Expression expr : expressions) {
                if (expr instanceof MethodCallExpr) {
                    visit((MethodCallExpr) expr, null);
                } else if (expr instanceof BinaryExpr) {

                    BinaryExpr binExpr = (BinaryExpr) expr;
                    handleExpressions(Arrays.asList(binExpr.getLeft(), binExpr.getRight()));
                }
            }
        }
    }

    private static void getMethodLineNumbers(FileInputStream src) throws ParseException, IOException {
        CompilationUnit cu = StaticJavaParser.parse(src);
        new MethodVisitor().visit(cu, null);

    }

    private static class MethodVisitorDec extends VoidVisitorAdapter {

        @Override
        public void visit(MethodDeclaration m, Object arg) {
            methodsBody.put(m.getName().toString(), m.getBody().get().toString().substring(1, m.getBody().get().toString().length() - 1));

        }
    }
}
