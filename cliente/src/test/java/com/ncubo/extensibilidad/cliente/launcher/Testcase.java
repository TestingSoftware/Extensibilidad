package com.ncubo.extensibilidad.cliente.launcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import com.ibatis.common.jdbc.ScriptRunner;


public class Testcase 
{
	
	private final String PATH_SCHEMA = "src/main/resources/schema.sql";

	public boolean executeDBScripts(String aSQLScriptFilePath, Connection con) throws IOException,SQLException
	{
		boolean isScriptExecuted = false;
		try
		{
			ScriptRunner sr = new ScriptRunner(con, false, false);
			Reader reader = new BufferedReader(new FileReader(aSQLScriptFilePath));
			sr.runScript(reader);
			isScriptExecuted = true;
		}
		catch (Exception e)
		{
			System.err.println("Error al ejecutar " + aSQLScriptFilePath +". El error es: "+ e.getMessage());
		}
		return isScriptExecuted;
	}

	public boolean executeSchema(Connection con) throws IOException,SQLException
	{
		boolean isScriptExecuted = false;
		try {
			ScriptRunner sr = new ScriptRunner(con, false, false);
			Reader reader = new BufferedReader(new FileReader(PATH_SCHEMA));
			sr.runScript(reader);
			isScriptExecuted = true;
		}
		catch (Exception e)
		{
			System.err.println("Error al ejecutar " + PATH_SCHEMA +". El error es: "+ e.getMessage());
		}
		return isScriptExecuted;
	}

}
