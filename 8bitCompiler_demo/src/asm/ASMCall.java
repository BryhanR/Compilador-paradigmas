

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

public class ASMCall implements ASMAst{
   
   private ASMAst f;
   private List<ASMAst> args;
   
   public ASMCall(ASMAst f, ASMAst e){
      this(f, Arrays.asList(e));
   }
   public ASMCall(ASMAst f, List<ASMAst> args){
      this.f = f;
      this.args = args;
	  
   }
   
   public void genCode(PrintStream out)
   {
	   args.stream().forEach(e -> {
									if((ASMCall.class.isInstance(e)))
									{
										e.genCode(out);
										out.println("PUSH A");
									}
									else if((ASMOperation.class.isInstance(e))) 
									{
										e.genCode(out);
									}
									else // se asume que es un parametro constante ej(5) 
									{
										out.print("PUSH ");
										out.print((ASMId.class.isInstance(e)) ? "[" : "");
										e.genCode(out);
										//out.println();
										out.println((ASMId.class.isInstance(e)) ? "]" : "");
									}
									
									
									});	

			switch(((ASMId)f).getValue())
			{
				case "print_number":
					out.println("\nPOP A\nPN A");
				break;
				case "print_string":
					out.println("\nPOP A\nPS A");
				break;
				case "print_boolean":
					out.println("\nPOP A\nPB A");
				break;
				default:
					out.print("CALL .");
					f.genCode();
					out.println("\nPOP A");
					out.println();
					break;
			}
   }
}