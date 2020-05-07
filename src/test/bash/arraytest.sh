#!/bin/bash

my_array=(a b "c" d)

echo "第一个元素为:${my_array[0]}"
echo "第二个元素为:${my_array[1]}"
echo "第三个元素为:${my_array[2]}"

echo "数组所有元素:${my_array[*]}"
echo "数组所有元素:${my_array[@]}"

echo "数组长度 :${#my_array[@]}"
echo "数组长度 :${#my_array[*]}"

echo "遍历数组"
for e in ${my_array[*]}
  do echo $e
  done