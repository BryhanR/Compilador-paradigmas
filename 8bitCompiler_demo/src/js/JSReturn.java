package eightBit.js;
import java.util.*;
import java.io.*;
public class JSReturn implements JSAst{
  
   private JSAst e;
   public JSReturn(JSAst e){
      this.e = e;
   }
   public void genCode(PrintStream out){
	  if((JSId.class.isInstance(e)))
	  {
		  out.print("MOV A, [");
		  e.genCode();
		  out.println("]");
	  }
	  else if((JSOperation.class.isInstance(e)))
		{
			this.e.genCode();
			out.println("POP A");
		}
		else if((JSString.class.isInstance(e)))
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
	  out.print("JMP ");
   }
}