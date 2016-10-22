package eightBit.js;
import java.util.*;
import java.io.*;
public class JSWhile implements JSAst {
	
	private JSAst expression;
	private JSAst statements;
	
   public JSWhile(JSAst ex, JSAst stm){
      this.expression = ex;
	  this.statements = stm;
   }
   
   public void genCode(PrintStream out){
	   /*out.print("While(");
	   expression.genCode(out);
	   out.print("){");
	   statements.genCode(out);
	   out.print("}");*/
	   
	   
	   out.println(".while:");
	   expression.genCode(out);
	   genHead(out);
	   out.println(".continue_while:");
	   if(statements != null)
		   out.println("; Si hay statements");
	   statements.genCode(out);
	   expression.genCode(out);
	   genHead(out);
	   
	   out.println("\n.end_while:");
	   
   }
   
   public void genHead(PrintStream out)
   {
	   switch(((JSId)((JSOperation)expression).getOper()).getValue())
	   {
		   case ">":
				out.println("\nJA .continue_while");
				out.println("\nJBE .end_while");
				break;
				case "<":
				out.println("\nJB .then_if");
				out.println("JAE .else_if");
				break;
			case "==":
				out.println("\nJE .then_if");
				out.println("JNE .else_if");
				break;
			case "<=":
				out.println("\nJBE .then_if");
				out.println("JA .else_if");
				break;
			case "!=":
				out.println("\nJNE .then_if");
				out.println("JE .else_if");
				break;
	   }
   }
   
}