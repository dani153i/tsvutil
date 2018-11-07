/**
 * @author Daniel Blom & Azan Munir
 * Copyright 2018
 *
 * Utility for handling tsv files as dataset
 * Create Table (new dataset file) i.e. .tsv file
 * List and Count columns
 * CRUD + Find row(s)
 */

package tsvutil;

import java.io.IOException;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class TsvFile {
	private String _dataPath;
	private File _dataset;
	private String _tableName;
	private String[] _columns;

	public String getTableName() {
		return _tableName;
	}

	public String[] getColumns() {
		return _columns.clone();	// return a shallow copy
	}

	// returns how many columns this dataset has
	public int getColumnCount() {
		return _columns.length;
	}

	// reads {dataPath}/{tableName}/data.tsv
	public TsvFile(String dataPath, String tableName)
	 throws Exception, FileNotFoundException	{
	 	_tableName = tableName;											// set table name
	 	_dataPath = dataPath + "/"+ _tableName;							// set data path
		_dataset = new File(_dataPath, "data.tsv");						// new File instance
		Scanner scanner = new Scanner(_dataset);						// new stream reader instance

		// process column headers
		if(scanner.hasNextLine()) {										// if dataset has columns
				_columns = processRow(scanner.nextLine());				// read line, process row and init columns
		}
		if(_columns == null || _columns.length < 1) {					// if no columns
			throw new Exception("Table: "+ tableName +" has no columns...");		// throw exception
		}
		scanner.close(); 												// close stream reader
	}

	// creates {dataPath}/{tableName}/data.tsv
	// with given columns
	public static TsvFile createTable(String dataPath, String tableName, String[] columnHeaders)
	{
		TsvFile newInstance = null;										// return value
		File dataset = new File(dataPath +"/"+ tableName, "data.tsv");	// file instance

		if(dataset.exists())											// if file already exists
			dataset.delete();											// remove file

		PrintStream streamWriter = null;								// stream writer for writing to file
		String headerRow = null;										// string to be written

		try {
			dataset.getParentFile().mkdirs();							// create parent directories
			dataset.createNewFile();									// create file for the dataset
			headerRow = processRow(columnHeaders);						// concatenate headers using fencing

			// write to file
			streamWriter = new PrintStream(dataset);					// new stream writer instance
			streamWriter.println(headerRow);							// write headers to file

			// set return value
			newInstance = new TsvFile(dataPath, tableName);				// new TsvFile instance

		} catch(Exception e) {
			System.out.println("~~ Could not write to file: "+ dataset.getName() +" ~~");
			System.out.println(e.getMessage() +"\n");
		} finally {
			streamWriter.close();										// close output stream
		}

		return newInstance;												// return instance
	}

	// list all rows
	public String[] listRows()
	{
		String token = String.valueOf(System.nanoTime());				// token for unique dataset file name
		File dataset = copyDataset(token);								// copy dataset to a new dataset with unique token
		Scanner scanner = null;

		List<String> out = new ArrayList<String>(); 					// list to store found rows
		try {
			scanner = new Scanner(dataset);								// use new scanner instance as input stream
			if(scanner.hasNextLine()) 									// skip first row. i.e. column headers
				scanner.nextLine();										//
			while(scanner.hasNextLine()) {								// while row hasn't been found and dataset has more rows
				out.add(scanner.nextLine());							// add next line to output
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			scanner.close();											// close input stream
			if(dataset.exists()) dataset.delete();						// remove temp dataset file from disk
		}
															
		return out.toArray(new String[0]);								// return rows as array of string
	}

	// insert new row to dataset table
	public boolean createRow(String[] row)
	{
		boolean out = false;
		// row column count cannot exceed table column count
		if(row.length != _columns.length) {
			System.out.println("~~ Row column count must match table column count ~~\n");
			return false; // row not created
		}

		String token = String.valueOf(System.nanoTime());				// token for unique dataset file name
		File dataset = copyDataset(token);								// copy dataset to a new dataset with unique token

		// because we don't want to read anything, only append
		// PrintStream -> BufferedBriter -> File Friter
		try(PrintWriter outStream = new PrintWriter(				// new output stream -> this will close auto, no worries :)
			new BufferedWriter(new FileWriter(dataset, true)))) {	// (File file, boolean append)
			outStream.println(processRow(row));						// write row to temp dataset file
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}

		_dataset.delete();											// remove original dataset from disk
		dataset.renameTo(_dataset);									// rename temp dataset to original dataset name

		return out; // row created
	}

	// get specific row in dataset table from given primary key
	public String[] getRow(String primaryKey)
	{
		String[] out = null;
		String token = String.valueOf(System.nanoTime());				// token for unique dataset file name
		File dataset = copyDataset(token);								// copy dataset to a new dataset with unique token
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(dataset);								// use scanner for reading dataset file
			if(scanner.hasNextLine()) 									// skip first row. i.e. column headers
				scanner.nextLine();										//
			while(scanner.hasNextLine()) {								// while row hasn't been found and dataset has more rows
				String[] fields = processRow(scanner.nextLine());		// get next row's fields
				if(fields[0].equals(primaryKey)) {						// if primary keys match
					out = fields;										// set found row as return value
					break;												// and break the loop
				}
			}
		} catch(FileNotFoundException e) {
			System.out.println("~~ Table: "+ dataset.getName() +" was not found ~~");
			System.out.println(e.getMessage() +"\n");
		} finally {
			scanner.close();											// close input stream
			if(dataset.exists()) dataset.delete();						// remove temp dataset file from disk
		}

		return out;														// return row
	}

	// find specific row in dataset table from given criterion
	public String[] findRow(String[] criterion)
	{
		String[] out = null;
		String token = String.valueOf(System.nanoTime());				// token for unique dataset file name
		File dataset = copyDataset(token);								// copy dataset to a new dataset with unique token
		Scanner scanner = null;

		try {
			scanner = new Scanner(dataset);								// use scanner for reading temp dataset file
			if(scanner.hasNextLine()) 									// skip first row. i.e. column headers
				scanner.nextLine();										//
			while(out == null && scanner.hasNextLine()) {				// while row hasn't been found and dataset has more rows
				String[] fields = processRow(scanner.nextLine());		// get next row's fields
				int criterion_c = criterion.length;
				for(int i=0; i < criterion.length &&					// foreach criteria
						i < getColumnCount(); i++) {					// 	[within field range]
					String cri = criterion[i];							// placeholder for readability
					if(cri == null 										// if criteria is null
					 || cri.equals("")									// if criteria is not set
					 || cri.equals(fields[i])){							// if criteria equals corresponding field
						criterion_c--;									// subtract 1 criteria from criterion that needs to be met
					} else {											// else
						break;											// skip rest of the criterion
					}
				}
				if(criterion_c == 0)
				{
					out = fields;
				}
			}
		} catch(FileNotFoundException e) {
			System.out.println("~~ Table: "+ dataset.getName() +" was not found ~~");
			System.out.println(e.getMessage() +"\n");
		} finally {
			scanner.close();											// close input stream
			if(dataset.exists()) dataset.delete();						// remove temp dataset file from disk
		}

		return out;														// return row
	}

	// find specific rows in dataset table from given criterion
	public String[] findRows(String[] criterion)
	{
		List<String> out = new ArrayList<String>();
		String token = String.valueOf(System.nanoTime());				// token for unique dataset file name
		File dataset = copyDataset(token);								// copy dataset to a new dataset with unique token
		Scanner scanner = null;

		try {
			scanner = new Scanner(dataset);								// use scanner for reading temp dataset file
			if(scanner.hasNextLine()) 									// skip first row. i.e. column headers
				scanner.nextLine();										//
			while(scanner.hasNextLine()) {								// while row hasn't been found and dataset has more rows
				String row = scanner.nextLine();						// get next row
				String[] fields = processRow(row);						// get row's fields
				int criterion_c = criterion.length;
				for(int i=0; i < criterion.length &&					// foreach criteria
						i < getColumnCount(); i++) {					// 	[within field range]
					String cri = criterion[i];							// placeholder for readability
					if(cri == null 										// if criteria is null
					 || cri.equals("")									// if criteria is not set
					 || cri.equals(fields[i])){							// if criteria equals corresponding field
						criterion_c--;									// subtract 1 criteria from criterion that needs to be met
					} else {											// else
						break;											// skip rest of the criterion
					}
				}
				if(criterion_c == 0)
				{
					out.add(row);
				}
			}
		} catch(FileNotFoundException e) {
			System.out.println("~~ Table: "+ dataset.getName() +" was not found ~~");
			System.out.println(e.getMessage() +"\n");
		} finally {
			scanner.close();											// close input stream
			if(dataset.exists()) dataset.delete();						// remove temp dataset file from disk
		}

		return out.toArray(new String[0]);								// return row
	}

	// update row in dataset table
	public boolean updateRow(String[] row)
	{
		boolean out = false;
		String datasetContent = null;
		String token = String.valueOf(System.nanoTime());				// token for unique dataset file name
		String token2 = String.valueOf(System.nanoTime());				// token for unique dataset file name
		File dataset = copyDataset(token);								// copy dataset to a new dataset with unique token
		File datasetFinal = writeTempDataset(token2);					// create new final dataset to to replace original after update
		Scanner scanner = null;
		PrintStream outStream = null;

		try {
			scanner = new Scanner(dataset);								// stream reader reading temp dataset file
			outStream = new PrintStream(datasetFinal);					// stream writer writing to final dataset file

			if(scanner.hasNextLine()) 									// skip first row. i.e. column headers
				outStream.println(scanner.nextLine());					//
			while(scanner.hasNextLine()) {								// while given row hasn't been found and dataset has more rows
				String line = scanner.nextLine();						// get row
				String[] fields = processRow(line);						// translate row to fields
				if(fields[0].equals(row[0])) {							// if row's primary key match given row's primary key
					outStream.println(processRow(row));					// write given row to final dataset
				} else {												// if row's primary key doesnt match given row's primary key
					outStream.println(line);							// write row to final dataset
				}
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			scanner.close();											// close input stream
			outStream.close();											// close output stream

			_dataset.delete();											// remove original dataset from disk
			datasetFinal.renameTo(_dataset);							// rename final dataset to original dataset name

			if(dataset.exists()) dataset.delete();						// remove temp dataset file from disk
		}
			
		return out; // row updated
	}

	// remove row from dataset table
	public boolean removeRow(String primaryKey)
	{
		boolean out = false;
		String datasetContent = null;
		String token = String.valueOf(System.nanoTime());				// token for unique dataset file name
		String token2 = String.valueOf(System.nanoTime());				// token for unique dataset file name
		File dataset = copyDataset(token);								// copy dataset to a new dataset with unique token
		File datasetFinal = writeTempDataset(token2);					// create new final dataset to to replace original after update
		Scanner scanner = null;
		PrintStream outStream = null;

		try {
			scanner = new Scanner(dataset);								// stream reader reading temp dataset file
			outStream = new PrintStream(datasetFinal);						// stream writer writing to final dataset file

			if(scanner.hasNextLine()) 									// skip first row. i.e. column headers
				outStream.println(scanner.nextLine());					//
			while(scanner.hasNextLine()) {								// while row hasn't been found and dataset has more rows
				String row = scanner.nextLine();						// get row
				String[] fields = processRow(row);						// translate row to fields
				if(!fields[0].equals(primaryKey)) {						// if row's primary key doesn't match given primary key
					outStream.println(row);									// write row to final dataset
				}
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			scanner.close();											// close input stream
			outStream.close();											// close output stream

			_dataset.delete();											// remove original dataset from disk
			datasetFinal.renameTo(_dataset);							// rename final dataset to original dataset name

			if(dataset.exists()) dataset.delete();						// remove temp dataset file from disk
		}

		return out; // row removed
	}

	// translate fields to row
	// concatenate values with fencing
	public static String processRow(String[] fields) {
		String outString = fields[0];
		for(int i=1; i < fields.length; i++) {
			outString += "\t"+ fields[i];
		}
		return outString;
	}

	// translate row to fields
	// split values by tab delimiter to get fields
	public static String[] processRow(String row) {
		return row.split("\t+"); 	// split row by tab delimiter
	}

	// copies a tsv dataset file on disk
	// this file should have a more or less unique token to it's name
	private File copyDataset(String token) {
		File dataset = new File(_dataPath, "data_"+ token +".tsv");		// new file instance
		try {
			Files.copy(_dataset.toPath(), dataset.toPath());			// copy file to new path
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return dataset;
	}

	// writes a temporary tsv dataset file to disk
	// this file should have a more or less unique token to it's name
	private File writeTempDataset(String token) {
		File file = new File(_dataPath, "data_"+ token +".tsv");
		try {
			file.createNewFile();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return file;
	}
}