package eightBit.js;
import java.util.*;
import java.io.*;

public class JSCall implements JSAst{
   
   private JSAst f;
   private List<JSAst> args;
   
   public JSCall(JSAst f, JSAst e){
      this(f, Arrays.asList(e));
   }
   public JSCall(JSAst f, List<JSAst> args){
      this.f = f;
      this.args = args;
	  
   }
   
   public void genCode(PrintStream out)
   {
	   args.stream().forEach(e -> {
									if((JSCall.class.isInstance(e)))
									{
										e.genCode(out);
										out.println("PUSH A");
									}
									else if((JSOperation.class.isInstance(e))) 
									{
										e.genCode(out);
									}
									else // se asume que es un parametro constante ej(5) 
									{
										out.print("PUSH ");
										out.print((JSId.class.isInstance(e)) ? "[" : "");
										e.genCode(out);
										//out.println();
										out.println((JSId.class.isInstance(e)) ? "]" : "");
									}
									
									
									});
									
		//System.err.println("Call " + ((JSId)f).getValue());	

			switch(((JSId)f).getValue())
			{
				case "PrintN":
					out.println("\nPOP A\nPN A");
				break;
				case "PrintS":
					out.println("\nPOP A\nPS A");
				break;
				case "PrintB":
					out.println("\nPOP A\nPB A");
				break;
				default:
					out.print("CALL .");
					f.genCode();
					out.println("\nPOP A");
					out.println();
					break;
			}
		/*if(((JSId)f).getValue().compareTo("Print") == 0)
		{
			out.println("\nPOP A\nPN A");
		}
			
	   
		else
		{
			out.print("CALL .");
			f.genCode();
			out.println("\nPOP A");
			out.println();
		}		*/	
   }
}