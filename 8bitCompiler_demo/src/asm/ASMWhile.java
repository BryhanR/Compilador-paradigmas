

/*
* 			INTEGRANTES:
*
*	ALEXANDRA AGUILAR NAJERA	304780037	1 pm
*	MASIEL MORA RODRIGUEZ		604190071	8 am
* 	BRYHAN RODRIGUEZ MORA		115420325	1 pm
*	JEAN CARLO VARGAS ZUÑIGA	402220474	1 pm
*
*/



package eightBit.asm;
import java.util.*;
import java.io.*;
public class ASMWhile implements ASMAst {
	
	private ASMAst expression;
	private ASMAst statements;
	
   public ASMWhile(ASMAst ex, ASMAst stm){
      this.expression = ex;
	  this.statements = stm;
   }
   
   public void genCode(PrintStream out){
	   
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
	   switch(((ASMId)((ASMOperation)expression).getOper()).getValue())
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