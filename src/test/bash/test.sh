#!/bin/bash
# test 测试条件是否成立
set -e

num1=10
num2=20
#if test $[num1] -eq $[num2]
if test $num1 -eq $num2
 then
   echo "the two num is equal"
   else
    echo "the two num is not equal"
fi

#目录不存在创建
#if [ ! -d "shtest" ]
#  then mkdir shtest
#  else
#    echo "文件夹已存在"
#fi

#目录不存在创建
if test ! -d "shtest"
  then mkdir shtest
  else
    echo "文件夹已存在"
fi

#文件是否存在
if test ! -f "shtest/aa"
  then
  echo "文件不存在"
  else
    echo "文件已存在"
fi