package eightBit.js;
import java.io.*;
public class JSAssign extends  JSOperation{
   final public static JSId EQ = new JSId("=");
   public JSAssign(JSAst left, JSAst right){
      super(EQ, left, right);
   }
   public void genCode(PrintStream out){
	  out.print(((JSId.class.isInstance(right))?"PUSH [":""));
	  out.print(((JSNum.class.isInstance(right))?"PUSH ":""));
	  right.genCode(out);
	  out.println(((JSId.class.isInstance(right))?"]":""));
   
	  out.println("POP A\nMOV [" + ((JSId)left).getValue() + "], A");
   }
}