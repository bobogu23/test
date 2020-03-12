#!/bin/bash

# if elif elif else fi
# elif 表示 else if, else 其他情况， fi 表示结束

while [ $num -le 10 ]
  do
  echo "$num * $num is `expr $num \* $num`"
  num=`expr $num + 1`
done