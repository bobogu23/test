#!/bin/bash
# operate on file.txt
#文件中包含名字，电话号码和过去三个月里的捐款
#1. 显示所有电话号码
#2. 显示Dan的电话号码
#3. 显示Susan的名字和电话号码
#4. 显示所有以D开头的姓
#5. 显示所有以一个C或E开头的名
#6. 显示所有只有四个字符的名
#7. 显示所有区号为916的人名
#8. 显示Mike的捐款.显示每个值时都有以$开头.如$250$100$175
#9. 显示姓,其后跟一个逗号和名,如Jody,Savage
#10.写一个awk的脚本,它的作用:显示Savage的全名和电话号码.显示Chet的捐款.显示所有头一个月捐款$250的人名.

#1
awk -F ":" '{print $2}' file.txt
echo -e "\n"

#2
echo "#2#2#2#2#2"
awk -F ":" '/^Dan/ {print $2}' file.txt
echo -e "\n"

#3
echo "#3#3#3#3#3"
awk -F ":" '/^Susan/ {  print "name: "$1",tel:"$2}' file.txt
echo -e "\n"

#4,字符串或者变量想用正则匹配，直接在字符串或者变量后面加上 ~ /regex/
echo "#4#4#4"
awk -F ":" '{print $1}' file.txt |awk '$2~/^D/{print $2}'
## -e 开启转义
echo -e "\n"


#5. 显示所有以一个C或E开头的名
awk '$1~/^(C|E)/{print $1}' file.txt
echo -e "\n"

#6. 显示所有只有四个字符的名
awk  ' {if (length($1)== 4) {print $1} } ' file.txt
echo -e "\n"

#7. 显示所有区号为916的人名
awk -F ":" '{print $1$2}' file.txt | awk -F "[" '$2~/^916/{print $1}'
echo -e "\n"
#8. 显示Mike的捐款.显示每个值时都有以$开头.如$250$100$175
awk -F ":" '$1~/^Mike/{print "\$"$3"\$"$4"\$"$5}' file.txt
echo -e "\n"
#9. 显示姓,其后跟一个逗号和名,如Jody,Savage
awk -F ":" '{print $1}' file.txt | awk '{print $1","$2}'
echo -e "\n"
#10.写一个awk的脚本,它的作用:显示Savage的全名和电话号码.显示Chet的捐款.
# 显示所有头一个月捐款$250的人名.
awk -F ":" '$1~/Savage$/{print $1","$2}' file.txt
awk -F ":" '$1~/^Chet/{print $1 "," $3","$4","$5}' file.txt
awk -F ":" '{if ($3==250) {print $1}}' file.txt
echo -e "\n"

#10.写一个awk的脚本,它的作用:显示Savage的全名和电话号码.显示Chet的捐款.
# 显示所有头一个月捐款$250的人名.
awk 'BEGIN {FS=":"}
{if($1~/Savage$/){print $1","$2}
if($1~/^Chet/){print $1 "," $3","$4","$5}
if($3==250){print $1}
}' file.txt
echo -e "\n"

awk -F ":" '{print NR,$1}' file.txt

