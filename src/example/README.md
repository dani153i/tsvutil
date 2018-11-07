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
