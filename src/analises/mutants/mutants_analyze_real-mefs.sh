#!/bin/bash
PATH_FI=/Users/matheus/eclipse-workspace/H-SwitchCover/src/SwitchCover/xml/*
folder=/Users/matheus/eclipse-workspace/H-SwitchCover/src/SwitchCover/xml/

head="Folder,File,Mutants total,Mutants dead total,Mutants alive,Mutantion score"
echo $head >> "result/txt/mefs_reais/"resultBreadthAlltrans.txt
echo $head >> "result/txt/mefs_reais/"resultBreadthAlltranspair.txt
echo $head >> "result/txt/mefs_reais/"resultDepthAlltrans.txt
echo $head >> "result/txt/mefs_reais/"resultDepthAlltranspair.txt
echo $head >> "result/txt/mefs_reais/"resultEulerAlltrans.txt
echo $head >> "result/txt/mefs_reais/"resultEulerAlltranspair.txt
echo $head >> "result/txt/mefs_reais/"resultPCCAlltrans.txt
echo $head >> "result/txt/mefs_reais/"resultPCCAlltranspair.txt

for path in $PATH_FI
do
	FOLDER_NAME=$(basename $path)
	path=$path"/*"

	for file in $path
	do
		FILE_NAME=$(basename $file)
		fsm1=$file"/fsm1.txt"
		
		#Breadth alltrans / alltranspair
		breadthAlltrans=$file"/tsbreadthAlltrans.txt"
		resultbreadthAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants-v2.jar $fsm1 $breadthAlltrans)"
		echo $resultbreadthAlltrans >> "result/txt/mefs_reais/"resultBreadthAlltrans.txt
		
		breadthAlltranspair=$file"/tsbreadthAlltranspair.txt"
		resultbreadthAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants-v2.jar $fsm1 $breadthAlltranspair)"
		echo $resultbreadthAlltranspair >> "result/txt/mefs_reais/"resultBreadthAlltranspair.txt

		#Depth alltrans / alltranspair
		depthAlltrans=$file"/tsdepthAlltrans.txt"
		resultdepthAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants-v2.jar $fsm1 $depthAlltrans)"
		echo $resultdepthAlltrans >> "result/txt/mefs_reais/"resultDepthAlltrans.txt
		
		depthAlltranspair=$file"/tsdepthAlltranspair.txt"
		resultdepthAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants-v2.jar $fsm1 $depthAlltranspair)"
		echo $resultdepthAlltranspair >> "result/txt/mefs_reais/"resultDepthAlltranspair.txt

		#H-Switch Cover alltrans / alltranspair
		eulerAlltrans=$file"/tseulerAlltrans.txt"
		resulteulerAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants-v2.jar $fsm1 $eulerAlltrans)"
		echo $resulteulerAlltrans >> "result/txt/mefs_reais/"resultEulerAlltrans.txt
		
		eulerAlltranspair=$file"/tseulerAlltranspair.txt"
		resulteulerAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants-v2.jar $fsm1 $eulerAlltranspair)"
		echo $resulteulerAlltranspair >> "result/txt/mefs_reais/"resultEulerAlltranspair.txt

		#PCC alltrans / alltranspair
		pccAlltrans=$file"/tspccAlltrans.txt"
		resultpccAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants-v2.jar $fsm1 $pccAlltrans)"
		echo $resultpccAlltrans >> "result/txt/mefs_reais/"resultPCCAlltrans.txt
		
		pccAlltranspair=$file"/tspccAlltranspair.txt"
		resultpccAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants-v2.jar $fsm1 $pccAlltranspair)"
		echo $resultpccAlltranspair >> "result/txt/mefs_reais/"resultPCCAlltranspair.txt
	done
done

