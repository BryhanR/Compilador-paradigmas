

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
import java.io.PrintStream;

public class ASMIf implements ASMAst{
  
   private ASMAst c, t, e;
   public ASMIf(ASMAst c, ASMAst t, ASMAst e){
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
	   String op = ((ASMId)((ASMOperation)c).getOper()).getValue();
	   switch(((ASMId)((ASMOperation)c).getOper()).getValue())
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