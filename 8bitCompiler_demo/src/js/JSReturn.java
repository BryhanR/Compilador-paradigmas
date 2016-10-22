package eightBit.js;
import java.util.*;
import java.io.*;
public class JSReturn implements JSAst{
  
   private JSAst e;
   public JSReturn(JSAst e){
      this.e = e;
   }
   public void genCode(PrintStream out){
      //out.print("return ");
	  if((JSId.class.isInstance(e)))
	  {
		  out.print("MOV A, [");
		  e.genCode();
		  out.println("]");
	  }
	  else
	  {
		if((JSOperation.class.isInstance(e)))
		{
			this.e.genCode();
			out.println("POP A");
		}
		else
		{
			out.print("MOV A, ");
			this.e.genCode();
			out.println();	
		}
				 
	  }
	  
	  
	  out.print("JMP ");
	  //out.print(";");
   }
}