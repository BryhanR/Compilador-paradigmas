

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
	
public class ASMEmpty implements ASMAst{
   @Override
   public void genCode(PrintStream out){
	   out.println("/* empty statement! */");
   }
}