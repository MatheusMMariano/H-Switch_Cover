#!/bin/bash
PATH_FI=/Users/matheus/eclipse-workspace/H-SwitchCover/src/SwitchCover/file/*
folder=/Users/matheus/eclipse-workspace/H-SwitchCover/src/SwitchCover/file/

head="Folder,File,Number of test cases,Number of inputs (with resets),Number of inputs (no resets),Length of less test case,Length of largest test case,Average length,Standard deviation,Variance"
echo $head >> "result/txt/mefs_aleatorias/"resultBreadthAlltrans.txt
echo $head >> "result/txt/mefs_aleatorias/"resultBreadthAlltranspair.txt

echo $head >> "result/txt/mefs_aleatorias/"resultDepthAlltrans.txt
echo $head >> "result/txt/mefs_aleatorias/"resultDepthAlltranspair.txt

echo $head >> "result/txt/mefs_aleatorias/"resultEulerAlltrans.txt
echo $head >> "result/txt/mefs_aleatorias/"resultEulerAlltranspair.txt

echo $head >> "result/txt/mefs_aleatorias/"resultPCCAlltrans.txt
echo $head >> "result/txt/mefs_aleatorias/"resultPCCAlltranspair.txt

for path in $PATH_FI
do
	FOLDER_NAME=$(basename $path)
	path=$path"/*"

	for file in $path
	do
		FILE_NAME=$(basename $file)
		
		#Breadth alltrans / alltranspair
		breadthAlltrans=$file"/tsbreadthAlltrans.txt"
		resultbreadthAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $breadthAlltrans)"
		echo $resultbreadthAlltrans >> "result/txt/mefs_aleatorias/"resultBreadthAlltrans.txt
		
		breadthAlltranspair=$file"/tsbreadthAlltranspair.txt"
		resultbreadthAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $breadthAlltranspair)"
		echo $resultbreadthAlltranspair >> "result/txt/mefs_aleatorias/"resultBreadthAlltranspair.txt

		#Depth alltrans / alltranspair
		depthAlltrans=$file"/tsdepthAlltrans.txt"
		resultdepthAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $depthAlltrans)"
		echo $resultdepthAlltrans >> "result/txt/mefs_aleatorias/"resultDepthAlltrans.txt
		
		depthAlltranspair=$file"/tsdepthAlltranspair.txt"
		resultdepthAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $depthAlltranspair)"
		echo $resultdepthAlltranspair >> "result/txt/mefs_aleatorias/"resultDepthAlltranspair.txt

		#H-Switch Cover alltrans / alltranspair
		eulerAlltrans=$file"/tseulerAlltrans.txt"
		resulteulerAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $eulerAlltrans)"
		echo $resulteulerAlltrans >> "result/txt/mefs_aleatorias/"resultEulerAlltrans.txt
		
		eulerAlltranspair=$file"/tseulerAlltranspair.txt"
		resulteulerAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $eulerAlltranspair)"
		echo $resulteulerAlltranspair >> "result/txt/mefs_aleatorias/"resultEulerAlltranspair.txt

		#PCC alltrans / alltranspair
		pccAlltrans=$file"/tspccAlltrans.txt"
		resultpccAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $pccAlltrans)"
		echo $resultpccAlltrans >> "result/txt/mefs_aleatorias/"resultPCCAlltrans.txt
		
		pccAlltranspair=$file"/tspccAlltranspair.txt"
		resultpccAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $pccAlltranspair)"
		echo $resultpccAlltranspair >> "result/txt/mefs_aleatorias/"resultPCCAlltranspair.txt
	done
done