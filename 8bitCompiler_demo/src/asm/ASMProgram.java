

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
public class ASMProgram implements ASMAst{
   static private ASMId UNK = new ASMId("");
   private ASMId name;
   private List<ASMAst> functions;
   private String dataArea;
   private ASMAst body;
   public ASMProgram(List<ASMAst> functions){
      this(UNK, functions);
   }
   public ASMProgram(ASMId name, List<ASMAst> functions){
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