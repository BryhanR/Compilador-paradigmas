/*
 loriacarlos@gmail.com EIF400 II-2016
 EightBit starting compiler
*/

/*
* 			INTEGRANTES:
*
*	ALEXANDRA AGUILAR NAJERA	304780037	1 pm
*	MASIEL MORA RODRIGUEZ		604190071	8 am
* 	BRYHAN RODRIGUEZ MORA		115420325	1 pm
*	JEAN CARLO VARGAS ZUÑIGA	402220474	1 pm
*
*/



package eightBit.compile;


import eightBit.asm.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;
import java.util.stream.*;


public class Compiler extends EightBitBaseVisitor<ASMAst> implements ASMEmiter{
   protected ASMAst program;
   protected Scope symbolTable = new Scope();
   public ASMAst getProgram(){
	   return this.program;
   }
   protected List<ASMAst> statements = new ArrayList<>();
   
   public void genCode(){
		((ASMProgram)this.program).genData();
	   System.out.println(";.codeArea:");
		this.program.genCode();
		
		
		//Corregir esto, es para poder imprimir numeros
	/*
		System.out.println(".print_number:\nPOP C\nPOP B\nPUSH C\nMOV C, 0\n.print_number_loop_01:\nINC C\nMOV A, B\nDIV 10\n");
		System.out.println("PUSH A\nMUL 10\nSUB B, A\nMOV A, B\nPOP B\nPUSH A\nCMP B, 0\nJNZ .print_number_loop_01\n.print_number_loop_02:\n");
		System.out.println("POP B\nADD B, 48\nMOV [D], B\nINC D\nDEC C\nCMP C, 0\nJNZ .print_number_loop_02\n.print_number_exit:\nPOP C\nPUSH .UNDEF\nPUSH C\nRET\n");
		
		// y esta es para imprimir texto
		System.out.println(".print_string:\nPOP C\nPOP B\nPUSH C\n.print_string_loop_01:\nMOV C, [B]\nCMP C, 0\nJE .print_string_exit\nMOV [D], C\n");
		System.out.println("INC D\nINC B\nJMP .print_string_loop_01\n.print_string_exit:\nPOP C\nPUSH .UNDEF\nPUSH C\nRET");
		// y esta para imprimir boolean
		System.out.println(".print_boolean:\nPOP C\nPOP A\nPUSH C\nCMP A, 1\nJE .print_boolean_true\nJNE .print_boolean_false");
		System.out.println(".print_boolean_true:\n PUSH .boolean_true\nJMP .end_print_boolean\n.print_boolean_false:\nPUSH .boolean_false\nJMP .end_print_boolean");
		System.out.println(".end_print_boolean:\nCALL .print_string\nPOP A\nPOP C\nPUSH .UNDEF\nPUSH C\nRET");
	*/
 }
   public ASMAst compile(ParseTree tree){
      return visit(tree);
   }
   @Override
   public ASMAst visitEightProgram(EightBitParser.EightProgramContext ctx){
	   ctx.eightFunction().stream()
	                      .forEach( fun -> visit(fun) );
						  
		ASMProgram p = ((ASMProgram)PROGRAM(this.statements));
		p.setDataArea(symbolTable.getCollectedData());
		this.program = p;
	   return this.program;
   }
   
   @Override
   public ASMAst visitEightFunction(EightBitParser.EightFunctionContext ctx){
      
      symbolTable.newScope(ctx.id().getText());	// se agrega la funcion al scope	  
	  ASMId id = (ASMId)visit(ctx.id());	  
	  ASMAst f = visit(ctx.formals());	  
	  List<ASMAst> fb = ((ASMFormals)f).getMembers();	  
	  fb.stream().forEach(e -> {
									ASMId idf = ((ASMId)e);
									String v = idf.getValue();
									idf.setValue(symbolTable.add(v));
								});	  
	  ASMAst body = visit(ctx.funBody());
	  ASMAst function = FUNCTION(id, f, body);
	  this.statements.add(function);
	  return function;
   }
   
   @Override
   public ASMAst visitEmptyStatement(EightBitParser.EmptyStatementContext ctx){
      return EMPTY();
	                
   }
   
   @Override
   public ASMAst visitReturnStatement(EightBitParser.ReturnStatementContext ctx){
	   ASMAst ex = visit(ctx.expr());
      return RET(ex,symbolTable.getCurrent());
	                
   }
   
   @Override
   public ASMAst visitAssignStatement(EightBitParser.AssignStatementContext ctx){
	   ASMAst id = visit(ctx.id());
	  return ASSIGN(id, visit(ctx.expr()));
	                
   }
   @Override
   public ASMAst visitBlockStatement(EightBitParser.BlockStatementContext ctx){
	  EightBitParser.ClosedListContext closedList = ctx.closedList();
      return (closedList == null ) ? BLOCK() 
	                               : visit(closedList);
   }
   @Override
   public ASMAst visitClosedList(EightBitParser.ClosedListContext ctx){					  
					   return  BLOCK(ctx.closedStatement().stream()
	                                                      .map( c -> 
																	{
																		ASMAst e = visit(c);
																		if(ASMAssign.class.isInstance(e))
																		{																			
																			ASMId id = ((ASMId)((ASMAssign)e).getLeft());
																			id.setValue(symbolTable.getValueCurrent(id.getValue()));																			
																		}
																		return e;
																	}
																	)
										                  .collect(Collectors.toList()));
	                
   }
   @Override
   public ASMAst visitFormals(EightBitParser.FormalsContext ctx){
	   EightBitParser.IdListContext idList = ctx.idList();
	   return (idList == null ) ? FORMALS()
	                            : visit(idList);
   }
   
   @Override
   public ASMAst visitWhileStatement(EightBitParser.WhileStatementContext ctx){
	   ASMAst exp = visit(ctx.expr());
	   ASMAst stmt = visit(ctx.closedStatement());
		  
	   return WHILE(exp, stmt);
   }
   
      @Override
   public ASMAst visitLetStatement(EightBitParser.LetStatementContext ctx){
	  ASMAst st = visit(ctx.assignStmtList());
	  

	  
	  List<ASMAst> s = ((ASMBlock)st).getMembers();
	  s.stream().forEach(e -> 
							{
								ASMAssign ass = ((ASMAssign)e);
											
								ASMAtom at = ((ASMAtom)ass.getLeft());
								String val = ((String)at.getValue());
								at.setValue(((String)symbolTable.add(val)));
							});
							
			  ASMAst stat = visit(ctx.closedStatement());
	  return LET(s,stat);
   }
   
    @Override
   public ASMAst visitAssignStmtList(EightBitParser.AssignStmtListContext ctx){
		return  BLOCK(ctx.assignStatement().stream()
						     .map( c ->	visit(c))
						     .collect(Collectors.toList()));
	
   } 
   
    @Override
   public ASMAst visitIfStatement(EightBitParser.IfStatementContext ctx){
	   ASMAst exp = visit(ctx.expr());	   
	   List<ASMAst> ar = ctx.closedStatement().stream()
	                                                .map( c -> visit(c))
										            .collect(Collectors.toList());													
		ASMAst a = ar.get(0);	  
		ASMAst b = (ar.size()>1)? ar.get(1): null;	  
	   return IF(exp, a, b);
   }
   
   
   
   @Override
   public ASMAst visitCallStatement(EightBitParser.CallStatementContext ctx){
	   ASMAst id = ID(ctx.ID().getText());
	   ASMAst tmp = visit(ctx.arguments());
	   List<ASMAst> arg = ((ASMBlock)tmp).getMembers();
	   return CALL(id, arg);
   }
   
   
   @Override
   public ASMAst visitArguments(EightBitParser.ArgumentsContext ctx){
	   return  (ctx.args() != null)? visit(ctx.args()) : BLOCK();	
   } 
   
   @Override
   public ASMAst visitArgs(EightBitParser.ArgsContext ctx){
	   return  BLOCK(ctx.expr().stream()
						     .map( c -> visit(c))
						     .collect(Collectors.toList()));
	
   } 
   
   @Override
   public ASMAst visitIdList(EightBitParser.IdListContext ctx){
	   return  FORMALS(ctx.id().stream()
						     .map( c -> visit(c))
						     .collect(Collectors.toList()));
	
   } 
   @Override
   public ASMAst visitId(EightBitParser.IdContext ctx){
	  return  ID(ctx.ID().getText());
   }
   @Override
    public ASMAst visitArithOperation(EightBitParser.ArithOperationContext ctx) {
	   if (ctx.oper == null)
		   return visit(ctx.arithMonom().get(0));
	   ASMAst oper = ( ctx.oper.getType() == EightBitParser.ADD ) ? ADD : MINUS;
       List<ASMAst> exprs = ctx.arithMonom().stream()
	                                       .map( c -> visit(c) )
										   .collect(Collectors.toList());
										   
										   
		
	   return exprs.stream()
	               .skip(1)
				   .reduce(exprs.get(0), (opers, expr) ->
	                              OPERATION(oper, opers , expr));
	   
    }
    @Override
    public ASMAst visitArithMonom(EightBitParser.ArithMonomContext ctx){
		ASMAst left = visit(ctx.arithSingle());
		return (ctx.operTDArithSingle() == null) 
		       ? left
		       :ctx.operTDArithSingle().stream()
	                                   .map( c -> visit(c) )
									   .reduce(left, (opers, expr) 
									                      -> FOLD_LEFT(opers , expr));
   } 
   @Override
   public ASMAst visitOperTDArithSingle(EightBitParser.OperTDArithSingleContext ctx){
	   ASMAst oper = ( ctx.oper.getType() == EightBitParser.MUL ) ? MUL : DIV;
	   ASMAst right = visit(ctx.arithSingle());
	   return OPERATION(oper, NULL, right);
   }
   @Override
   public ASMAst visitArithIdSingle(EightBitParser.ArithIdSingleContext ctx){
	   if(ctx.id() != null && ctx.arguments() != null)
	   {
			ASMAst id = visit(ctx.id());
			ASMAst ar =  visit(ctx.arguments());
			List<ASMAst> args =  ((ASMBlock)ar).getMembers();
			return CALL(id ,args);
	   }
	   else  
	   {
		   ASMAst ids = visit(ctx.id());
		   ((ASMId)ids).setValue(symbolTable.getValueCurrent(((ASMId)ids).getValue()));
			return ids;
	   }
   }
   
   
   @Override
    public ASMAst visitRelOperation(EightBitParser.RelOperationContext ctx) {
	   if (ctx.relOperator() == null)
		   return visit(ctx.arithOperation().get(0));
	   else
	   {
		    List<ASMAst> arr = ctx.relOperator().stream()
								.map( c -> visit(c))
								.collect(Collectors.toList());
		ASMAst oper = (arr.size()>0)? arr.get(0): ID(">"); 
		List<ASMAst> exprs = ctx.arithOperation().stream()
	                                       .map( c -> visit(c))
										   .collect(Collectors.toList());							  
	   return exprs.stream()
	               .skip(1)
				   .reduce(exprs.get(0), (opers, expr) ->
	                              OPERATION(oper, opers , expr));
	   }
	 
	   
    }
   
   
     @Override
   public ASMAst visitRelOperator(EightBitParser.RelOperatorContext ctx){
      return ID(ctx.getText());
   }
   
   
   
   @Override
   public ASMAst visitExprNum(EightBitParser.ExprNumContext ctx){
      return NUM(Integer.valueOf(ctx.NUMBER().getText()));
   }
     
   @Override
   public ASMAst visitExprString(EightBitParser.ExprStringContext ctx){
	  return STRING(symbolTable.addString(ctx.STRING().getText()));
   }
   
   @Override
   public ASMAst visitExprTrue(EightBitParser.ExprTrueContext ctx){
      return TRUE;
   }
   
   @Override
   public ASMAst visitExprFalse(EightBitParser.ExprFalseContext ctx){
      return FALSE;
   }
   
}
  