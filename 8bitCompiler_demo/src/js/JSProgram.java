package eightBit.js;
import java.util.*;
import java.io.*;
public class JSProgram implements JSAst{
   static private JSId UNK = new JSId("");
   private JSId name;
   private List<JSAst> functions;
   private String dataArea;
   private JSAst body;
   public JSProgram(List<JSAst> functions){
      this(UNK, functions);
   }
   public JSProgram(JSId name, List<JSAst> functions){
      this.functions = functions;
	  this.name = name;
	  this.dataArea = "";
   }
   
   public void setDataArea(String data)
   {
	   this.dataArea = data;
   }
   public void genData()
   {
	   System.out.println(".init:\nMOV D, 232\nJMP .main\n\n");
	   System.out.println(";.data_Area:\n.UNDEF: DB 255\n.boolean_true: DB \"TRUE\" \n\tDB 0\n.boolean_false: DB \"FALSE\"\n\tDB 0");
	   System.out.println(dataArea);
   }
   
   public void genCode(PrintStream out){
       functions.stream().forEach( t -> t.genCode(out));
   }
}