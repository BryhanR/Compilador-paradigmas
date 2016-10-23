package eightBit.js;
import java.io.*;
public class JSBool extends JSAtom<Boolean>{
   public JSBool(boolean value){
      super(value);
   }
    public void genCode(PrintStream out){
		
      out.print(((boolean)getValue())?"1":"0");
   }
}