 #!/bin/bash
##############################################################################################################################
###CONFIGURATION VARIABLES

DIR=/home/andre/ifiles/doctoral/FSM-p-complete/experiments-v2/states

for STATES in 4 6 8 10 12 14 16 18 20 22 24 26 28 30;
do
    for INPUTS in `seq 4 4`;
    do
        for OUTPUTS in `seq 4 4`;
        do
            TRANSITIONS=$((STATES * INPUTS))
            
            mkdir $DIR/results/$STATES"-"$INPUTS"-"$OUTPUTS
            
            for SEED in `seq 1 100`;
            do
                ######################################
                printf "$STATES\n$INPUTS\n$OUTPUTS\n$STATES" > HInput.txt
                
                wine h.exe < HInput.txt > logH.txt
                java -jar HMethodExtract.jar FSM.fsm > tshnina.txt
                java -jar HFsmExtractor.jar FSM.fsm > fsm1.txt

                $DIR/spy 0 $STATES < fsm1.txt > tsspy.txt
                $DIR/fsm-hsi < fsm1.txt > tshsi.txt
                java -jar w.jar fsm1.txt > tsw.txt
                java -jar pcomplete3.jar fsm1.txt > tsp3.txt
                
                java -jar size.jar tsspy.txt > size.txt
                SPY_SIZE=`cat size.txt`

                java -jar size.jar tsp3.txt > size.txt
                P_SIZE3=`cat size.txt`
                
                java -jar size.jar tsw.txt > size.txt
                W_SIZE=`cat size.txt`
                
                java -jar size.jar tshsi.txt > size.txt
                HSI_SIZE=`cat size.txt`

                java -jar size.jar tshnina.txt > size.txt
                HNINA_SIZE=`cat size.txt`

                echo $STATES","$INPUTS","$OUTPUTS","$W_SIZE","$HSI_SIZE","$SPY_SIZE","$HNINA_SIZE","$P_SIZE3
                
                mkdir $DIR/results/$STATES"-"$INPUTS"-"$OUTPUTS/$SEED
                
                cp *.txt $DIR/results/$STATES"-"$INPUTS"-"$OUTPUTS/$SEED
                
                rm *.txt
                ######################################
            done
        done        
    done    
done

