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
	   /*out.print("Call Assignment");
	   f.genCode(out);
	   out.print("(");
	   args.stream().forEach(e -> e.genCode(out));
	   out.print(")");*/
	   args.stream().forEach(e -> {
									if((JSCall.class.isInstance(e)))
									{
										//out.print((JSCall.class.isInstance(e))? "" : "PUSH ");
										//out.print((JSId.class.isInstance(e)) ? "[" : "");
										e.genCode(out);
										//out.println((JSId.class.isInstance(e)) ? "]" : "");
									}
									else if((JSOperation.class.isInstance(e))) 
									{
										//out.print(/*(JSCall.class.isInstance(e))? "" : */"PUSH ");
										//out.print((JSId.class.isInstance(e)) ? "[" : "");
										e.genCode(out);
										//out.println((JSId.class.isInstance(e)) ? "]" : "");
									}
									else // se asume que es un parametro constante ej(5) 
									{
										out.print(/*(JSCall.class.isInstance(e))? "" : */"PUSH ");
										out.print((JSId.class.isInstance(e)) ? "[" : "");
										e.genCode(out);
										out.println((JSId.class.isInstance(e)) ? "]" : "");
									}
									
									
									});
	   out.print("CALL .");
	   f.genCode();
	   //out.println("POP A");
	   out.println();
   }
}