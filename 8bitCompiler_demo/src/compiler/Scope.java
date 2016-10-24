

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

import java.util.*;
import java.io.*;



public class Scope
{
	private HashMap<String, Entry> scopes;
	
	private String current;
	
	public Scope()
	{
		scopes = new HashMap<>();
		current = "";
	}
	
	public String getCurrent()
	{
		return this.current;
	}
	public String add(String k)
	{
		//System.err.println("Tratando de ingresar " + k + " En Scope");
		if(k.compareTo(current) != 0)
			return scopes.get(current).add(k,current);
		return "";
	}
	
	public String getCollectedData()
	{
		StringBuilder data = new StringBuilder("");
		scopes.forEach( (k,v) -> {data.append(";"+v.getId()+"_Data_Area"+"\n"+v.getId() + "_Data_Area:\n" + v.getData()+"\n");/*out.println(";"+v.getId()+"_Data_Area"+"\n"+v.getId() + "_Data_Area:");v.genCode(out);*/});
		return data.toString();
	}	
	
	public String addString(String v)
	{
			return scopes.get(current).addString(v);
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
	
	/*public void genDataArea(PrintStream out)
	{
		out.println(";.data_Area:\n.UNDEF: DB 255\n.boolean_true: DB \"TRUE\" \n\tDB 0\n.boolean_false: DB \"FALSE\"\n\tDB 0");
		scopes.forEach( (k,v) -> {out.println(";"+v.getId()+"_Data_Area"+"\n"+v.getId() + "_Data_Area:");v.genCode(out);});
	}*/
	
	/*public void genDataArea()
	{
		this.genDataArea(System.out);
	}*/
	
	public String getValueCurrent(String k)
	{
		//System.err.println("Solicitando " + k + " Current En Scope Con resultado: " + scopes.get(current).get(k));
		return scopes.get(current).get(k);
	}
	
	
	public class Entry
	{
		private HashMap<String, String> symbolTable; // value propio y el nombre del scope(padre)
		private HashMap<String,String> stringPool; // id generado, valor de string
		
		private String id;
		public Entry(String id)
		{
			this.id = id;
			symbolTable = new HashMap<>();
			stringPool = new HashMap<>();
		}
		public String getId()
		{
			return id;
		}
		
		public String addString(String v)
		{
			String code = id+"_string_"+(stringPool.size()+1);
			stringPool.put(code,v);
			return code;
		}
		public String add(String k, String v)
		{
			symbolTable.put(k, v);
			return "." + v + "_" + k;
		}	
		
		public boolean has(String k)
		{
			return true;
		}
		
		
		public String get(String k)
		{
			return "." + symbolTable.get(k) + "_" + k;			
		}
		
		
		public String getData()
		{
			StringBuilder data = new StringBuilder("");
			symbolTable.forEach( (k,v) -> data.append("." + v + "_" + k + ": DB 0\n"));
			stringPool.forEach( (k,v) -> data.append( k +": DB "+v+"\n\t DB 0\n"));
			return data.toString();
		}
		
		
		/*public void genCode(PrintStream out)
		{			
			symbolTable.forEach( (k,v) -> out.println("." + v + "_" + k + ": DB 0"));
			stringPool.forEach( (k,v) -> out.println( k +": DB "+v+"\n\t DB 0"));
			}*/
	}
}


