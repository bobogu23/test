#!/bin/bash
#运算符测试
val=`expr 2 + 2`
echo "两数之和:$val"

a=10
b=20
if [ $a == $b ]
then echo "a ==b"
fi
if [ $a != $b ]
then  echo "a != b"
fi