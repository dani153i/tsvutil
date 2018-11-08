# tsvutil/src/tsvutil/
TsvFile is a utility for handling tsv files as dataset.
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
			Returns <u>table name</u>.
		</td>
	</tr>
	<tr>
		<td>String[]</td>
		<td>
			<b>getColumns()</b><br/>
			Returns table <u>column names</u>.
		</td>
	</tr>
	<tr>
		<td>int</td>
		<td>
			<b>getColumnCount()</b><br/>
			Returns the <u>column count</u>.
		</td>
	</tr>
	<tr>
		<td>static TsvFile</td>
		<td>
			<b>createTable(<i>String dataPath</i>, <i>String tableName</i>, <i>String[] columnHeaders</i>)</b><br/>
			Creates a .tsv dataset file {<i>dataPath</i>}/{<i>tableName</i>}/data.tsv with given columns.
		</td>
	</tr>
	<tr>
		<td>String[]</td>
		<td>
			<b>listRows()</b><br/>
			Returns <u>all rows</u>.
		</td>
	</tr>
	<tr>
		<td>String[]</td>
		<td>
			<b>listRows(<i>int page</i>, <i>int results</i>)</b><br/>
			Returns <u>all rows</u> within range [(<i>page</i> - 1) * <i>results</i> + 1 ; <i>page</i> * <i>results</i>].
		</td>
	</tr>
	<tr>
		<td>boolean</td>
		<td>
			<b>createRow(<i>String[] row</i>)</b><br/>
			Tests whether the given <i>row</i> was written to dataset file.
		</td>
	</tr>
	<tr>
		<td>String[]</td>
		<td>
			<b>getRow(<i>String primaryKey</i>)</b><br/>
			Returns a specific <u>row, as fields</u>, in dataset table from given <i>primary key</i>.
		</td>
	</tr>
	<tr>
		<td>String[]</td>
		<td>
			<b>findRow(<i>String[] criterion</i>)</b><br/>
			Returns specific <u>row, as fields</u>, in dataset table from given <i>criterion</i>.
		</td>
	</tr>
	<tr>
		<td>String[]</td>
		<td>
			<b>findRows(<i>String[] criterion</i>)</b><br/>
			Returns specific <u>rows, as fields</u>, in dataset table from given <i>criterion</i>.
		</td>
	</tr>
	<tr>
		<td>boolean</td>
		<td>
			<b>updateRow(<i>String[] row</i>)</b><br/>
			Tests whetger given <i>row</i> was updated in dataset table.
		</td>
	</tr>
	<tr>
		<td>boolean</td>
		<td>
			<b>removeRow(<i>String primaryKey</i>)</b><br/>
			Tests whetger row with given <i>primaryKey</i> was deleted from dataset table.
		</td>
	</tr>
	<tr>
		<td>static String</td>
		<td>
			<b>processRow(<i>String[] row</i>)</b><br/>
			Translates <i>fields</i> to <u>row</u> by concatenating values with {tab delimiter} as fencing.
		</td>
	</tr>
	<tr>
		<td>static String[]</td>
		<td>
			<b>processRow(<i>String row</i>)</b><br/>
			Translates <i>row</i> to <u>fields</u> by splitting values by tab delimiter.
		</td>
	</tr>
</table>