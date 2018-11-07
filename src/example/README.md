# tsvutil/src/example/
Program is an example of how to use TsvFile.
i.e. a utility for handling tsv files as dataset.
* Create Table (new dataset file) i.e. .tsv file
* List and Count columns
* CRUD + Find row(s)

<h3>Imports</h3>
```java
 import tsvutil;
```
<h3>Create Table (dataset .tsv file)</h3>

```java
String dataPath = "data";
TsvFile dataset_school = TsvFile.createTable(dataPath, "school.basics",
	new String[] { "CVR", "EAN", "Name", "AKA", "Address", "ZipCode" });
```

<h3>Get Dataset as <b>TsvFile</b></h3>

```java
TsvFile dataset_school = new TsvFile(dataPath, "school.basics");
```

<h3>Insert Row to Table</h3>

```java
// create schools
String[][] schools = new String[][] {
	new String[] { "31656206", "5798000560291", "Københavns Erhvervsakademi", "KEA", "Guldbergsgade 29N", "2200" },
	new String[] { "63372173", "5798000385844", "Karlslunde Erhvervsakademi", "KEA", "Skelvej 56", "2690" },
	new String[] { "19596915", "5798000417687", "Copenhagen Business School", "CBS", "Solbjerg Plads 3", "2000" },
	new String[] { "00000000", "0000000000000", "Skolen der slet ikke fandtes", "TstSkl", "Intetsted 0", "0000" }};

for(String[] school : schools)
	dataset_school.createRow(school);
```

<h3>List Column Names</h3>

```java
// get column names concatonated with tabstop (\t) as fencing. Ex. {"X1\tX2\t..Xn"}
String schl_bscs_clmns = TsvFile.processRow(dataset_school.getColumns());
```

<h3>List Rows</h3>

```java
// fetch rows
String[] schl_bscs_rws = dataset_school.listRows();

// print columns and rows
System.out.println(columns);		// print columns
for(String row : rows)			// foreach row in rows
	System.out.println(row);	// print row
```

<h3>Update Row</h3>

```java
String[] schl_bscs_rws = dataset_school.listRows();		// fetch rows
String[] school_kea = TsvFile.processRow(schl_bscs_rws[0]);	// translate first row to fields. i.e. String to arrray of String
school_kea[2] = "UPDATED VALUE";				// change row's 3th field
dataset_school.updateRow(school_kea);				// update row in dataset

System.out.println("\nUpdate school name with primary key '"+ school_kea[0] +"': ");

// before: "31656206", "5798000560291", "Københavns Erhvervsakademi", "KEA", "Guldbergsgade 29N", "2200"
// after: "31656206", "5798000560291", "UPDATED VALUE", "KEA", "Guldbergsgade 29N", "2200"
```

<h3>Delete Row</h3>

```java
String[] schl_bscs_rws = dataset_school.listRows();		// fetch rows
// primary key = first field of 3rd row
String primaryKey = TsvFile.processRow(schl_bscs_rws[2])[0];	// translates row to fields. i.e. String to arrray of String.
dataset_school.removeRow(primaryKey);				// remove row from dataset

System.out.println("\nDeleted schools with primary key '"+ primaryKey +"': ");
```


<h3>Find Rows</h3>

```java
String[] schl_bscs_rws = null;
// search criteria cannot exceed table column count. i.e. school.basics.columns = 6
String[] searchCriterion1 = new String[] { null, null, null, "KEA", null, "2200" };	// 6 criteria is fine, since 6 <= 6
String[] searchCriterion2 = new String[] { null, null, null, "KEA", null };		// 5 criteria is fine, since 5 <= 6

schl_bscs_rws = dataset_school.findRows(searchCriterion1);		// get school.basics rows from 1st criterion
System.out.println("\nFind schools aka 'KEA' located in zip '2200': ");
// "31656206", "5798000560291", "Københavns Erhvervsakademi", "KEA", "Guldbergsgade 29N", "2200"

schl_bscs_rws = dataset_school.findRows(searchCriterion2);		// get school.basics rows from 2nd criterion
System.out.println("\nFind schools aka 'KEA': ");
// "31656206", "5798000560291", "Københavns Erhvervsakademi", "KEA", "Guldbergsgade 29N", "2200"
// "63372173", "5798000385844", "Karlslunde Erhvervsakademi", "KEA", "Skelvej 56", "2690"
```