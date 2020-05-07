#!/bin/bash
function hello(){
  echo "hello word!"
}

hello

 hello1(){
  echo "hello word!1111"
}

hello1

# test function with return
funReturn(){
  echo "two num sum"
  echo "input num one "
  read num1
  echo "input num two"
  read num2
  echo "the two nums are $num1 $num2"
  return  $(($num1+$num2))
}

funReturn
echo "sum of two num is $?"

# test function with param
funParam(){
  echo "param count: $#"
  echo "first param: $1"
  echo "tenth param: ${10}"
  echo "total param: $*"
}

funParam 1 2

# $#	传递到脚本或函数的参数个数
# $*	以一个单字符串显示所有向脚本传递的参数
# $$	脚本运行的当前进程ID号
# $!	后台运行的最后一个进程的ID号
# $@	与$*相同，但是使用时加引号，并在引号中返回每个参数。
# $-	显示Shell使用的当前选项，与set命令功能相同。
# $?	显示最后命令的退出状态。0表示没有错误，其他任何值表明有错误