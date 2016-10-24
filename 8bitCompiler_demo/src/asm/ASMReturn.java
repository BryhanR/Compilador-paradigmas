

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
public class ASMReturn implements ASMAst{
  
   private ASMAst e;
   private String parent;
   public ASMReturn(ASMAst e,String p){
      this.e = e;
	  this.parent = p;
   }
   public void genCode(PrintStream out){
	  if((ASMId.class.isInstance(e)))
	  {
		  out.print("MOV A, [");
		  e.genCode();
		  out.println("]");
	  }
	  else if((ASMOperation.class.isInstance(e)))
		{
			this.e.genCode();
			out.println("POP A");
		}
		else if((ASMString.class.isInstance(e)))
		{
			out.print("MOV A, ");
			this.e.genCode();
			out.println();
		}
		else 
		{
			out.print("MOV A, ");
			this.e.genCode();
			out.println();	
		}
	  out.println("JMP " + parent + "_end");
   }
}