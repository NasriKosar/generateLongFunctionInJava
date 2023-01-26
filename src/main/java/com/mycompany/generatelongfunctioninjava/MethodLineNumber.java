
package com.mycompany.generatelongfunctioninjava;




import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.Log;
import com.github.javaparser.utils.SourceRoot;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;


import java.io.File;
import java.io.IOException;
import java.util.Optional;
public class MethodLineNumber {
     public static void method1() {
        int i = 1;
        System.out.println(i);
    }

    public static void method2() {
        String s = "hello";
        System.out.println(s);
    }

    public static void main(String[] args) throws ParseException, IOException {
//        File f = new File(".").getAbsoluteFile();
//        File srcRoot = new File(f, "src/code");
//        String srcFilename = MethodLineNumber.class.getName().replaceAll("\\.", "/") + ".java";
//        File src = new File(srcRoot, srcFilename);
     FileInputStream in = new FileInputStream("Dog.java");
        System.out.println(in);
        System.out.println(in);
        System.out.println(in);
        getMethodLineNumbers(in);
    }

    private static void getMethodLineNumbers(FileInputStream src) throws ParseException, IOException {
        CompilationUnit cu = StaticJavaParser.parse(src);
        new MethodVisitor().visit(cu, null);
    }

    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodVisitor extends VoidVisitorAdapter {
        @Override
        public void visit(MethodDeclaration m, Object arg) {
//            System.out.println("From [" + m + "," + m.getBeginColumn() + "] to [" + m.getEndLine() + ","
//                    + m.getEndColumn() + "] is method:");
            
            System.out.println("full method: "+m);
            System.out.println(m.getBegin().get());
         
             System.out.println(m.getEnd().get());
             System.out.println( "Body: "+m.getBody().get());
             System.out.println( "Decleration: "+m.getDeclarationAsString(true, true, true));
        }
    }
}
