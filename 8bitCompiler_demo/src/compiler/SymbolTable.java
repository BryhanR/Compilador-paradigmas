package eightBit.compile;

import java.util.*;
import java.io.*;

public class SymbolTable
{
	private HashMap<String, Entry> scopes;
	
	private String current;
	
	public SymbolTable()
	{
		scopes = new HashMap<>();
		current = "";
	}
	
	public String getCurrent()
	{
		return this.current;
	}
	public void add(String k)
	{
		//scopes.put(k, new Entry(k));
		if(k.compareTo(current) != 0)
			scopes.get(current).add(k,current);
	}
	
	public void newScope(String c)
	{
		current = c;
		scopes.put(current, new Entry(current));
	}
	
	
	public boolean has(String k)
	{
		Entry e = scopes.get(current);
		return (e != null)?e.has(k) : false;
	}
	
	public void genDataArea(PrintStream out)
	{
		out.println(";.data_Area:\n.UNDEF: DB 255\n");
		scopes.forEach( (k,v) -> v.genCode(out));
	}
	public void genDataArea()
	{
		this.genDataArea(System.out);
	}
	public class Entry
	{
		private HashMap<String, String> symbolTable; // value propio y el nombre del scope(padre)
		//private List<String> symbols;
		private String id;
		public Entry(String id)
		{
			this.id = id;
			symbolTable = new HashMap<>();
			//symbols = new ArrayList<>();
		}
		
		public void add(String k, String v)
		{
			symbolTable.put(k, v);
			//symbols.add(v);
		}	
		
		public boolean has(String k)
		{
			return true;
		}
		
		
		public String get(String k)
		{
			return symbolTable.get(k);
			
		}
		
		public void genCode(PrintStream out)
		{
			/*Enumeration<String> e = symbolTable.keys();
			Object k;
			while(e.hasMoreElements())
			{
				k = e.nextElement();
				out.println(id + "_" + k);
			}*/
			symbolTable.forEach( (k,v) -> out.println("." + v + "_" + k + ": DB 0"));
		}
	}
}


