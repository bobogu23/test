#!/bin/bash
if [ $(ps -ef | grep -c "ssh") -gt 1 ]; then echo "true"; fi

num1=$[1+2]
echo $num1

num2=`expr 2 + 2`
echo $num2