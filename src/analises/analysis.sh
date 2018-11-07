#!/bin/bash
PATH_FI=/Users/matheus/eclipse-workspace/H-SwitchCover/src/SwitchCover/file/*
folder=/Users/matheus/eclipse-workspace/H-SwitchCover/src/SwitchCover/file/

head="Folder,File,Number of test cases,Number of inputs (with resets),Number of inputs (no resets),Length of less test case,Length of largest test case,Average length,Standard deviation,Variance"
echo $head >> "result/"resultBreadthAlltrans.txt
echo $head >> "result/"resultBreadthAlltranspair.txt

echo $head >> "result/"resultDepthAlltrans.txt
echo $head >> "result/"resultDepthAlltranspair.txt

echo $head >> "result/"resultEulerAlltrans.txt
echo $head >> "result/"resultEulerAlltranspair.txt

echo $head >> "result/"resultPCCAlltrans.txt
echo $head >> "result/"resultPCCAlltranspair.txt

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
		echo $resultbreadthAlltrans >> "result/"resultBreadthAlltrans.txt
		
		breadthAlltranspair=$file"/tsbreadthAlltranspair.txt"
		resultbreadthAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $breadthAlltranspair)"
		echo $resultbreadthAlltranspair >> "result/"resultBreadthAlltranspair.txt

		#Depth alltrans / alltranspair
		depthAlltrans=$file"/tsdepthAlltrans.txt"
		resultdepthAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $depthAlltrans)"
		echo $resultdepthAlltrans >> "result/"resultDepthAlltrans.txt
		
		depthAlltranspair=$file"/tsdepthAlltranspair.txt"
		resultdepthAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $depthAlltranspair)"
		echo $resultdepthAlltranspair >> "result/"resultDepthAlltranspair.txt

		#H-Switch Cover alltrans / alltranspair
		eulerAlltrans=$file"/tseulerAlltrans.txt"
		resulteulerAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $eulerAlltrans)"
		echo $resulteulerAlltrans >> "result/"resultEulerAlltrans.txt
		
		eulerAlltranspair=$file"/tseulerAlltranspair.txt"
		resulteulerAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $eulerAlltranspair)"
		echo $resulteulerAlltranspair >> "result/"resultEulerAlltranspair.txt

		#PCC alltrans / alltranspair
		pccAlltrans=$file"/tspccAlltrans.txt"
		resultpccAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $pccAlltrans)"
		echo $resultpccAlltrans >> "result/"resultPCCAlltrans.txt
		
		pccAlltranspair=$file"/tspccAlltranspair.txt"
		resultpccAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar TestSuiteStats.jar $pccAlltranspair)"
		echo $resultpccAlltranspair >> "result/"resultPCCAlltranspair.txt


		#java -jar TestSuiteStats.jar $tsbreadthAlltrans >> $folder"/analise/"$FOLDER_NAME"/rsbreadthAlltrans.txt"
		#tsbreadthAlltranspair=$file"/tsbreadthAlltranspair.txt"
		#java -jar TestSuiteStats.jar $tsbreadthAlltranspair >> $folder"/analise/"$FOLDER_NAME"/rsbreadthAlltranspair.txt"
		
		#tsdepthAlltrans=$file"/tsdepthAlltrans.txt"
		#java -jar TestSuiteStats.jar $tsdepthAlltrans >> $folder"/analise/"$FOLDER_NAME"/rsdepthAlltrans.txt"
		#tsdepthAlltranspair=$file"/tsdepthAlltranspair.txt"
		#java -jar TestSuiteStats.jar $tsdepthAlltranspair >> $folder"/analise/"$FOLDER_NAME"/rsdepthAlltranspair.txt"

		#tseulerAlltrans=$file"/tseulerAlltrans.txt"
		#java -jar TestSuiteStats.jar $tseulerAlltrans >> $folder"/analise/"$FOLDER_NAME"/rseulerAlltrans.txt"
		#tseulerAlltranspair=$file"/tseulerAlltranspair.txt"
		#java -jar TestSuiteStats.jar $tseulerAlltranspair >> $folder"/analise/"$FOLDER_NAME"/rseulerAlltranspair.txt"

		#tspccAlltrans=$file"/tspccAlltrans.txt"
		#java -jar TestSuiteStats.jar $tspccAlltrans >> $folder"/analise/"$FOLDER_NAME"/rspccAlltrans.txt"
		#tspccAlltranspair=$file"/tspccAlltranspair.txt"
		#java -jar TestSuiteStats.jar $tspccAlltranspair >> $folder"/analise/"$FOLDER_NAME"/rspccAlltranspair.txt"
	done
done