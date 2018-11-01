#!/bin/bash
PATH_FI=/Users/matheus/eclipse-workspace/H-SwitchCover/src/SwitchCover/file/*

for path in $PATH_FI
do
	path=$path"/*"
	for file in $path
	do
		breathAlltrans=$file"/tsbreadthAlltrans.txt"
		java -jar TestSuiteStats.jar $breathAlltrans > breadthAlltransResult.txt
		breathAlltranspair=$file"/tsbreadthAlltranspair.txt"
		java -jar TestSuiteStats.jar $breathAlltranspair > breadthAlltranspairResult.txt

		depthAlltrans=$file"/tsdepthAlltrans.txt"
		java -jar TestSuiteStats.jar $depthAlltrans > depthAlltransResult.txt
		depthAlltranspair=$file"/tsdepthAlltranspair.txt"
		java -jar TestSuiteStats.jar $depthAlltranspair > depthAlltranspairResult.txt

		eulerAlltrans=$file"/tseulerAlltrans.txt"
		java -jar TestSuiteStats.jar $eulerAlltrans > eulerAlltransResult.txt
		eulerAlltranspair=$file"/tseulerAlltranspair.txt"
		java -jar TestSuiteStats.jar $eulerAlltranspair > eulerAlltranspairResult.txt

		pccAlltrans=$file"/tspccAlltrans.txt"
		java -jar TestSuiteStats.jar $pccAlltrans > pccAlltransResult.txt
		pccAlltranspair=$file"/tspccAlltranspair.txt"
		java -jar TestSuiteStats.jar $pccAlltranspair > pccAlltranspairResult.txt
	done
done