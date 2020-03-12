#!/bin/bash
num=1
# [ ] 代表 test , 逻辑判断
# 引用表达式计算的值要使用 反引用将表达式包起来.`expr $num \* $num`
while [ $num -le 10 ]
  do
  echo "$num * $num is `expr $num \* $num`"
  num=`expr $num + 1`
done