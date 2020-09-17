 #!/bin/bash
##############################################################################################################################
###CONFIGURATION VARIABLES

DIR=/home/andre/ifiles/doctoral/FSM-p-complete/experiments-v2/processResults/output-results

for CONFDIR in `ls $DIR`;
do
   
    for FSMDIR in `ls $DIR/$CONFDIR`;
    do
        java -jar TestSuiteStats.jar $DIR/$CONFDIR/$FSMDIR/tsw.txt > temp.txt
        TSW=`cat temp.txt`
        
        java -jar TestSuiteStats.jar $DIR/$CONFDIR/$FSMDIR/tshsi.txt > temp.txt
        TSHSI=`cat temp.txt`
        
        java -jar TestSuiteStats.jar $DIR/$CONFDIR/$FSMDIR/tshnina.txt > temp.txt
        TSH=`cat temp.txt`
        
        java -jar TestSuiteStats.jar $DIR/$CONFDIR/$FSMDIR/tsspy.txt > temp.txt
        TSSPY=`cat temp.txt`
        
        java -jar TestSuiteStats.jar $DIR/$CONFDIR/$FSMDIR/tsp3.txt > temp.txt
        TSP=`cat temp.txt`
        
        #echo $CONFDIR","$FSMDIR
        #echo "W: "$TSW
        #echo "HSI: "$TSHSI
        #echo "H: "$TSH
        #echo "SPY: "$TSSPY
        #echo "P: "$TSP
        
        echo $CONFDIR","$FSMDIR";"$TSW";"$TSHSI";"$TSH";"$TSSPY";"$TSP
    done
    

done


