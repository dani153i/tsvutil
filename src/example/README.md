# tsvutil/src/example/
Program is an example of how to use TsvFile.
i.e. a utility for handling tsv files as dataset.
* Create Table (new dataset file) i.e. .tsv file
* List and Count columns
* CRUD + Find row(s)


 package tsvutil;

<h3>Create Table (dataset .tsv file)</h3>

```java
TsvFile dataset_school = TsvFile.createTable(dataPath, "school.basics", new String[] { "CVR", "EAN", "Name", "AKA", "Address", "ZipCode" });
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
// get column names contatonated with tabstop (\t) as fencing. Ex. {"X1\tX2\t..Xn"}
String schl_bscs_clmns = TsvFile.processRow(dataset_school.getColumns());
```

<h3>List Rows</h3>

```java
// fetch rows
String[] schl_bscs_rws = dataset_school.listRows();

// print columns and rows
System.out.println(columns);	// print columns
for(String row : rows)			// foreach row in rows
	System.out.println(row);	// print row
```