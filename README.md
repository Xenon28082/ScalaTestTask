# ScalaTestTask
Test task for iTechArt
To run this project you need to instal sbt server
Command to run - ~run <input file path> <output file path>
File that you'll try to read must be a table? the row elements of wich are separated by tabs
First line of file consist of two integer numbers separated by tabs - width and height of table
Second parameter is optional, if it will be apsent result will be saved in defaultOutput.txt
Forseen error of recursion when one cell refer to the second, while second refer to the first
