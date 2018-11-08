/**
 * @author Daniel Blom & Azan Munir
 * Copyright 2018
 */

import tsvutil.TsvFile;

/**
 * Program is an example of how to use TsvFile
 * i.e a utility for handling tsv files as dataset
 * Create Table (new dataset file) i.e. .tsv file
 * List and Count columns
 * CRUD + Find row(s)
 */
public class Program {

	public static final String dataPath = "data";

	public static void main(String[] args) throws Exception {
		/**
		 * CREATE
		 */

		//create mock data
		createMockData();

		/**
		 * Instantiate & initialize repositories
		 */
		// datasets from mock data
		TsvFile dataset_school 			= 	new TsvFile(dataPath, "school.basics");
		/*TsvFile dataset_schoolLogins 	= 	new TsvFile(dataPath, "school.login");
		TsvFile dataset_courses 		= 	new TsvFile(dataPath, "school.course");
		TsvFile dataset_teachers 		= 	new TsvFile(dataPath, "teacher.basics");
		TsvFile dataset_students 		= 	new TsvFile(dataPath, "student.basics");*/

		/**
		 * READ
		 */

		// get school basics table data and rows
		String schl_bscs_clmns = TsvFile.processRow(dataset_school.getColumns());		// get school basics columns
		String[] schl_bscs_rws = dataset_school.listRows();								// get school basics rows

		System.out.println("\nList schools: ");
		// print school basics columns and rows
		printCustomTable(schl_bscs_clmns, schl_bscs_rws);

		/**
		 * UPDATE
		 */

		String[] school_kea = TsvFile.processRow(schl_bscs_rws[0]);						// translate first row to fields
		school_kea[2] = "<="+ school_kea[2] +"=>";										// change row's 3th field
		dataset_school.updateRow(school_kea);											// update row in db
		schl_bscs_rws = dataset_school.listRows();										// get school basics rows

		System.out.println("\nUpdate school name with primary key '"+ school_kea[0] +"': ");
		// print school basics columns and rows
		printCustomTable(schl_bscs_clmns, schl_bscs_rws);

		/**
		 * DELETE
		 */

		String primaryKey = TsvFile.processRow(schl_bscs_rws[2])[0];					// get first field of 3nd row
		dataset_school.removeRow(primaryKey);											// remove row from db
		schl_bscs_rws = dataset_school.listRows();										// get school basics rows

		System.out.println("\nDelete schools with primary key '"+ primaryKey +"': ");
		// print school basics columns and rows
		printCustomTable(schl_bscs_clmns, schl_bscs_rws);

		/**
		 * FIND
		 */

		String[] searchCriterion1 = new String[] { null, null, null, "KEA", null, "2200" };
		String[] searchCriterion2 = new String[] { null, null, null, "KEA", null };
		schl_bscs_rws = dataset_school.findRows(searchCriterion1);						// get school basics rows

		System.out.println("\nFind schools aka 'KEA' located in zip '2200': ");
		// print school basics columns and rows
		printCustomTable(schl_bscs_clmns, schl_bscs_rws);

		schl_bscs_rws = dataset_school.findRows(searchCriterion2);						// get school basics rows

		System.out.println("\nFind schools aka 'KEA': ");
		// print school basics columns and rows
		printCustomTable(schl_bscs_clmns, schl_bscs_rws);
	}

	// print dataset table (columns, rows)
	static void printCustomTable(String columns, String[] rows) {
		// print columns and rows
		System.out.println(columns);													// print columns
		for(String row : rows) {														// foreach row in rows
			System.out.println(row);													// print row
		}
	}

	// create and populate dataset
	static void createMockData() {
		/**
		 * Create Tables (datasets) i.e. tsv files
		 */
		TsvFile dataset_school 			= 	TsvFile.createTable(dataPath, "school.basics", new String[]		{ "CVR", "EAN", "Name", "AKA", "Address", "ZipCode" });
		TsvFile dataset_schoolLogins 	= 	TsvFile.createTable(dataPath, "school.login", new String[]		{ "CVR", "CPR", "Username", "Hash", "Token", "LastLogin" });
		TsvFile dataset_courses 		= 	TsvFile.createTable(dataPath, "school.course", new String[]		{ "Name", "Teachers", "Students" });
		TsvFile dataset_teachers 		= 	TsvFile.createTable(dataPath, "teacher.basics", new String[]	{ "CPR", "Name", "Address", "ZipCode", "Phone", "Email" });
		TsvFile dataset_students 		= 	TsvFile.createTable(dataPath, "student.basics", new String[]	{ "CPR", "Name", "Address", "ZipCode", "Phone", "Email" });

		/**
		 * Rows
		 */
		// create schools
		String[][] schools = new String[][] {
			new String[] { "31656206", "5798000560291", "Københavns Erhvervsakademi", "KEA", "Guldbergsgade 29N", "2200" },
			new String[] { "63372173", "5798000385844", "Karlslunde Erhvervsakademi", "KEA", "Skelvej 56", "2690" },
			new String[] { "19596915", "5798000417687", "Copenhagen Business School", "CBS", "Solbjerg Plads 3", "2000" },
			new String[] { "00000000", "0000000000000", "Skolen der slet ikke fandtes", "TstSkl", "Intetsted 0", "0000" }};
		// save schools to db
		for(String[] school : schools) dataset_school.createRow(school);
	}
}