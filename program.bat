color 0a
ECHO off
cls
javac -g -sourcepath src;src/example src/example/Program.java -d bin
java -cp bin Program
PAUSE