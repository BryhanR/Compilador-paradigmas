

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
public class ASMAtom<T> implements ASMAst{
   private T value;
   public T getValue(){return this.value;}
   public void setValue(T v){this.value = v;}
   public ASMAtom(T value){
      this.value = value;
   }
   public void genCode(PrintStream out){
      out.print(this.value);
   }
}