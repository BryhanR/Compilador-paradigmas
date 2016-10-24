
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
import java.io.*;
public class ASMBool extends ASMAtom<Boolean>{
   public ASMBool(boolean value){
      super(value);
   }
    public void genCode(PrintStream out){
		
      out.print(((boolean)getValue())?"1":"0");
   }
}