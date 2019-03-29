#!/bin/bash
PATH_FI=/Users/matheus/eclipse-workspace/H-SwitchCover/src/SwitchCover/file/*
folder=/Users/matheus/eclipse-workspace/H-SwitchCover/src/SwitchCover/file/

#head="Folder,File,Mutants total,Mutants dead total,Mutants alive,Mutantion score"
#echo $head >> "result/txt/mefs_aleatorias/"result_16_4_4_BreadthAlltrans.txt
#echo $head >> "result/txt/mefs_aleatorias/"result16_4_4_BreadthAlltranspair.txt

#echo $head >> "result/txt/mefs_aleatorias/"result16_4_4_DepthAlltrans.txt
#echo $head >> "result/txt/mefs_aleatorias/"result16_4_4_DepthAlltranspair.txt

#echo $head >> "result/txt/mefs_aleatorias/"result16_4_4_EulerAlltrans.txt
#echo $head >> "result/txt/mefs_aleatorias/"result16_4_4_EulerAlltranspair.txt

#echo $head >> "result/txt/mefs_aleatorias/"result16_4_4_PCCAlltrans.txt
#echo $head >> "result/txt/mefs_aleatorias/"result16_4_4_PCCAlltranspair.txt

for path in $PATH_FI
do
	FOLDER_NAME=$(basename $path)
	path=$path"/*"

	if [ "$FOLDER_NAME" = "18-4-4" ] || [ "$FOLDER_NAME" = "20-4-4" ] || [ "$FOLDER_NAME" = "4-4-4" ] || [ "$FOLDER_NAME" = "6-4-4" ] || [ "$FOLDER_NAME" = "8-4-4" ]
	then
		for file in $path
		do
			FILE_NAME=$(basename $file)
			fsm1=$file"/fsm1.txt"

			#Breadth alltrans / alltranspair
			breadthAlltrans=$file"/tsbreadthAlltrans.txt"
			resultbreadthAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants.jar $fsm1 $breadthAlltrans)"
			echo $resultbreadthAlltrans >> "result/txt/mefs_aleatorias/"resultBreadthAlltrans.txt
			
			breadthAlltranspair=$file"/tsbreadthAlltranspair.txt"
			resultbreadthAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants.jar $fsm1 $breadthAlltranspair)"
			echo $resultbreadthAlltranspair >> "result/txt/mefs_aleatorias/"resultBreadthAlltranspair.txt

			#Depth alltrans / alltranspair
			depthAlltrans=$file"/tsdepthAlltrans.txt"
			resultdepthAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants.jar $fsm1 $depthAlltrans)"
			echo $resultdepthAlltrans >> "result/txt/mefs_aleatorias/"resultDepthAlltrans.txt
			
			depthAlltranspair=$file"/tsdepthAlltranspair.txt"
			resultdepthAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants.jar $fsm1 $depthAlltranspair)"
			echo $resultdepthAlltranspair >> "result/txt/mefs_aleatorias/"resultDepthAlltranspair.txt

			#H-Switch Cover alltrans / alltranspair
			eulerAlltrans=$file"/tseulerAlltrans.txt"
			resulteulerAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants.jar $fsm1 $eulerAlltrans)"
			echo $resulteulerAlltrans >> "result/txt/mefs_aleatorias/"resultEulerAlltrans.txt
			
			eulerAlltranspair=$file"/tseulerAlltranspair.txt"
			resulteulerAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants.jar $fsm1 $eulerAlltranspair)"
			echo $resulteulerAlltranspair >> "result/txt/mefs_aleatorias/"resultEulerAlltranspair.txt

			#PCC alltrans / alltranspair
			pccAlltrans=$file"/tspccAlltrans.txt"
			resultpccAlltrans="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants.jar $fsm1 $pccAlltrans)"
			echo $resultpccAlltrans >> "result/txt/mefs_aleatorias/"resultPCCAlltrans.txt
			
			pccAlltranspair=$file"/tspccAlltranspair.txt"
			resultpccAlltranspair="$FOLDER_NAME,$FILE_NAME,$(java -jar HighOrderMutants.jar $fsm1 $pccAlltranspair)"
			echo $resultpccAlltranspair >> "result/txt/mefs_aleatorias/"resultPCCAlltranspair.txt
		done
	fi
done