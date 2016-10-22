package eightBit.js;
import java.io.*;
public class JSOperation implements  JSAst{
   protected JSAst oper;
   protected JSAst left, right;
   public JSAst getOper(){return this.oper;}
   public JSAst getLeft(){return this.left;}
   public JSAst getRight(){return this.right;}
   public JSOperation(JSAst oper, JSAst left, JSAst right){
      this.oper = oper;
	  this.left = left;
	  this.right = right;
   }
   public void genCode(PrintStream out){
	   
	   //String op = (((JSId)oper).getValue().compareTo("+") == 0) ? "ADD " : "MUL ";
	   
	   //out.println((((JSId)oper).getValue().compareTo("+") == 0) ? "ADD A, B" : "MUL B" + "\nPUSH A");
	   switch(((JSId)oper).getValue())
	   {
		   case "+":
				//out.print("MOV A, " + ((JSId.class.isInstance(left))?"[":""));
				out.print("PUSH " + ((JSId.class.isInstance(left))?"[":""));
				left.genCode(out);
				out.println((JSId.class.isInstance(left))?"]":"");
				
				//out.print("MOV B, " + ((JSId.class.isInstance(right))?"[":""));
				out.print("PUSH " + ((JSId.class.isInstance(right))?"[":""));
				right.genCode(out);
				out.println(((JSId.class.isInstance(right))?"]":""));
				out.println("POP A\nPOP B");
				out.println("ADD A, B\nPUSH A");
				break;
		   case "-":
				//out.println("SUB A, B");
				out.print("PUSH " + ((JSId.class.isInstance(right))?"[":""));
				right.genCode(out);
				out.println((JSId.class.isInstance(right))?"]":"");
				
				//out.print("MOV B, " + ((JSId.class.isInstance(right))?"[":""));
				out.print("PUSH " + ((JSId.class.isInstance(left))?"[":""));
				left.genCode(out);
				out.println(((JSId.class.isInstance(left))?"]":""));
				out.println("POP A\nPOP B");
				out.println("SUB A, B\nPUSH A");
				break;
		   case "*":
				//out.println("MUL B");
				out.print("PUSH " + ((JSId.class.isInstance(left))?"[":""));
				left.genCode(out);
				out.println((JSId.class.isInstance(left))?"]":"");
				
				//out.print("MOV B, " + ((JSId.class.isInstance(right))?"[":""));
				if(((JSCall.class.isInstance(right))))
				{
					right.genCode(out);
				}
				else
				{
				out.print("PUSH " + ((JSId.class.isInstance(right))?"[":""));
				right.genCode(out);
				out.println(((JSId.class.isInstance(right))?"]":""));
	   }
				out.println("POP A\nPOP B");
				out.println("MUL B\nPUSH A");
				break;
		   case "/":
				out.println("DIV B");
				break;
		   case ">":
		   case "==":
				//out.println("PUSH A\nPUSH B"); // se guardan los valores anteriores de los registros
				
				out.print("MOV A, ");
				if((JSId.class.isInstance(left)))
					out.print("[");
					//((JSBlock)body).setOwner(id);
				left.genCode(out);
				if((JSId.class.isInstance(left)))
					out.println("]");
				//out.print(", A");
				out.print("MOV B, ");
				if((JSId.class.isInstance(right)))
					out.print("[");
				right.genCode(out);
				if((JSId.class.isInstance(right)))
					out.println("]");
				out.println("\nCMP A, B");
				//out.println("POP B\nPOP A");	// se devuelven los valores a los registros
				//out.println();
				break;
	   }
	   //out.println(op + "A, B\nPUSH A");
      //left.genCode(out);
	 // out.print(", ");
	  //oper.genCode(out);
	  //right.genCode(out);
   }
}