


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
import java.io.*;
public class ASMOperation implements  ASMAst{
   protected ASMAst oper;
   protected ASMAst left, right;
   public ASMAst getOper(){return this.oper;}
   public ASMAst getLeft(){return this.left;}
   public ASMAst getRight(){return this.right;}
   public ASMOperation(ASMAst oper, ASMAst left, ASMAst right){
      this.oper = oper;
	  this.left = left;
	  this.right = right;
   }
   public void genCode(PrintStream out){
	   switch(((ASMId)oper).getValue())
	   {
		   case "+":
				out.print("PUSH " + ((ASMId.class.isInstance(left))?"[":""));
				left.genCode(out);
				out.println((ASMId.class.isInstance(left))?"]":"");
				
				out.print("PUSH " + ((ASMId.class.isInstance(right))?"[":""));
				right.genCode(out);
				out.println(((ASMId.class.isInstance(right))?"]":""));
				out.println("POP A\nPOP B");
				out.println("ADD A, B\nPUSH A");
				break;
		   case "-":
				out.print("PUSH " + ((ASMId.class.isInstance(right))?"[":""));
				right.genCode(out);
				out.println((ASMId.class.isInstance(right))?"]":"");
				
				out.print("PUSH " + ((ASMId.class.isInstance(left))?"[":""));
				left.genCode(out);
				out.println(((ASMId.class.isInstance(left))?"]":""));
				out.println("POP A\nPOP B");
				out.println("SUB A, B\nPUSH A");
				break;
		   case "*":
				out.print("PUSH " + ((ASMId.class.isInstance(left))?"[":""));
				left.genCode(out);
				out.println((ASMId.class.isInstance(left))?"]":"");
				
				if(((ASMCall.class.isInstance(right))))
				{
					right.genCode(out);
					out.println("PUSH A");
				}
				else
				{
					out.print("PUSH " + ((ASMId.class.isInstance(right))?"[":""));
					right.genCode(out);
					out.println(((ASMId.class.isInstance(right))?"]":""));
				}
				out.println("POP A\nPOP B");
				out.println("MUL B\nPUSH A");
				break;
		   case "/":
				out.println("DIV B");
				break;
		   case ">":
		   case "==":
				
				out.print("MOV A, ");
				if((ASMId.class.isInstance(left)))
					out.print("[");
				left.genCode(out);
				if((ASMId.class.isInstance(left)))
					out.println("]");
				out.print("MOV B, ");
				if((ASMId.class.isInstance(right)))
					out.print("[");
				right.genCode(out);
				if((ASMId.class.isInstance(right)))
					out.println("]");
				out.println("\nCMP A, B");
				break;
	   }
   }
}