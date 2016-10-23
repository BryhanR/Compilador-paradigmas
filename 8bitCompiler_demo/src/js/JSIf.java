package eightBit.js;
import java.util.*;
import java.io.PrintStream;

public class JSIf implements JSAst{
  
   private JSAst c, t, e;
   public JSIf(JSAst c, JSAst t, JSAst e){
      this.c = c;
	  this.t = t;
	  this.e = e;
   }
   @Override
   public void genCode(PrintStream out){
	 this.c.genCode(out); 
	 genHead(out);
	if(t != null)
	{
		out.println(".then_if:");
		this.t.genCode(out);
	}
	out.println(".else_if:"); 
	 if(e != null)
	 {
		this.e.genCode(out);
	 }	
   }
   
   public void genHead(PrintStream out)
   {
	   String op = ((JSId)((JSOperation)c).getOper()).getValue();
	   switch(((JSId)((JSOperation)c).getOper()).getValue())
	   {
		   case ">":
				out.println("\nJA .then_if");
				out.println("JBE .else_if");
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