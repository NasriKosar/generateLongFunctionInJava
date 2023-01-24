
package com.mycompany.generatelongfunctioninjava;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
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
import java.io.FileInputStream;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.expr.MethodCallExpr;
import java.util.Arrays;
import java.util.List;


public class MethodCallPrinter {
       public static void main(String[] args) throws Exception
    {
        FileInputStream in = new FileInputStream("Dog.java");

        CompilationUnit cu;
        try
        {
            cu = StaticJavaParser.parse(in);
        }
        finally
        {
            in.close();
        }
        new MethodVisitor().visit(cu, null);
    }

    private static class MethodVisitor extends VoidVisitorAdapter
    {
        @Override
        public void visit(MethodCallExpr methodCall, Object arg)
        {
            System.out.print("Method call: " + methodCall.getName() + "\n");
            List<Expression> args = methodCall.getArguments();
            if (args != null)
                handleExpressions(args);
        }

        private void handleExpressions(List<Expression> expressions)
        {
            for (Expression expr : expressions)
            {
                if (expr instanceof MethodCallExpr)
                    visit((MethodCallExpr) expr, null);
                else if (expr instanceof BinaryExpr)
                {
                   
                    BinaryExpr binExpr = (BinaryExpr)expr;
                    handleExpressions(Arrays.asList(binExpr.getLeft(), binExpr.getRight()));
                }
            }
        }
    }
}
