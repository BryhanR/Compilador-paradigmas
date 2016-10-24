


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
public class ASMAssign extends  ASMOperation{
   final public static ASMId EQ = new ASMId("=");
   public ASMAssign(ASMAst left, ASMAst right){
      super(EQ, left, right);
   }
   public void genCode(PrintStream out){
	  out.print(((ASMId.class.isInstance(right))?"PUSH [":""));
	  out.print(((ASMNum.class.isInstance(right))?"PUSH ":""));
	  right.genCode(out);
	  out.println(((ASMId.class.isInstance(right))?"]":""));
   
	  out.println("POP A\nMOV [" + ((ASMId)left).getValue() + "], A");
   }
}