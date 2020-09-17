#!/bin/bash
PATH_FI=/Users/matheus/eclipse-workspace/H-SwitchCover/src/SwitchCover/xml/*
folder=/Users/matheus/eclipse-workspace/H-SwitchCover/src/SwitchCover/xml/

head="Folder,File,Breadth All-trans,Breadth All-transpair,Depth All-trans,Depth All-transpair,Euler All-trans,Euler All-transpair,CPP All-trans,CPP All-transpair"
echo $head >> "result/txt/mefs_reais/"time.txt

for path in $PATH_FI
do
	FOLDER_NAME=$(basename $path)
	path=$path"/*"

	for file in $path
	do
		FILE_NAME=$(basename $file)
		
		bAt=`cat $file/timeBreadthAlltrans.txt`
		bAp=`cat $file/timeBreadthAlltranspair.txt`
		dAt=`cat $file/timeDepthAlltrans.txt`
		dAp=`cat $file/timeDepthAlltranspair.txt`
		eAt=`cat $file/timeEulerianAlltrans.txt`
		eAp=`cat $file/timeEulerianAlltranspair.txt`
		cAt=`cat $file/timeCPPAlltrans.txt`
		cAp=`cat $file/timeCPPAlltranspair.txt`

		echo $FOLDER_NAME","$FILE_NAME","$bAt","$bAp","$dAt","$dAp","$eAt","$eAp","$cAt","$cAp >> "result/txt/mefs_reais/"time.txt
	done
done