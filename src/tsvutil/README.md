# tsvutil/src/tsvutil/
Program is an example of how to use TsvFile.
i.e. a utility for handling tsv files as dataset.
* Create Table (new dataset file) i.e. .tsv file
* List and Count columns
* CRUD + Find row(s)


 package tsvutil;

<h3>Constructor Summary</h3>
<table>
	<tr>
		<td><b>Constructor and Description</b></td>
	</tr>
	<tr>
		<td>
			<b>TsvFile(String dataPath, String tableName)</b><br/>
			Creates a TsvFile instance and reads {dataPath}/{tableName}/data.tsv.
		</td>
	</tr>
</table>

<h3>Method Summary</h3>
 <table>
	<tr>
		<td><b>Modifiers and Type</b></td>
		<td><b>Method and Description</b></td>
	</tr>
	<tr>
		<td>String</td>
		<td>
			<b>getTableName()</b><br/>
			Returns table name.
		</td>
	</tr>
	<tr>
		<td>String[]</td>
		<td>
			<b>getColumns()</b><br/>
			Returns table column names.
		</td>
	</tr>
	<tr>
		<td>int</td>
		<td>
			<b>getColumnCount()</b><br/>
			Returns the column count.
		</td>
	</tr>
	<tr>
		<td>static TsvFile</td>
		<td>
			<b>createTable(String dataPath, String tableName, String[] columnHeaders)</b><br/>
			Creates a .tsv dataset file {dataPath}/{tableName}/data.tsv with given columns.
		</td>
	</tr>
	<tr>
		<td>String[]</td>
		<td>
			<b>listRows()</b><br/>
			Returns all rows.
		</td>
	</tr>
	<tr>
		<td>boolean</td>
		<td>
			<b>createRow(String[] row)</b><br/>
			Tests whether the given row was written to dataset file.
		</td>
	</tr>
	<tr>
		<td>String[]</td>
		<td>
			<b>getRow(String primaryKey)</b><br/>
			Returns a specific row in dataset table, as fields, from given primary key.
		</td>
	</tr>
	<tr>
		<td>String[]</td>
		<td>
			<b>findRow(String[] criterion)</b><br/>
			Returns specific row, as fields, in dataset table from given criterion.
		</td>
	</tr>
	<tr>
		<td>String[]</td>
		<td>
			<b>findRows(String[] criterion)</b><br/>
			Returns specific rows in dataset table from given criterion.
		</td>
	</tr>
	<tr>
		<td>boolean</td>
		<td>
			<b>updateRow(String[] row)</b><br/>
			Tests whetger given row was updated in dataset table.
		</td>
	</tr>
	<tr>
		<td>boolean</td>
		<td>
			<b>removeRow(String primaryKey)</b><br/>
			Tests whetger given row was deleted from dataset table.
		</td>
	</tr>
	<tr>
		<td>static String</td>
		<td>
			<b>processRow(String[] row)</b><br/>
			Translates fields to row by concatenating values with {tab delimiter} as fencing.
		</td>
	</tr>
	<tr>
		<td>static String[]</td>
		<td>
			<b>processRow(String row)</b><br/>
			Translates row to fields by splitting values by tab delimiter.
		</td>
	</tr>
</table>