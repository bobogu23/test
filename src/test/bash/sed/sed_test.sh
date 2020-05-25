#!/bin/sh
#第二行后追加 106,dandan,CSO
sed '2i\
106,dandan,CSO' person.txt

echo "------"
sed '2d' person.txt
echo "------"
sed '/feixue/d' person.txt
echo -e "------\n"
sed '2c\
 109,ffd,CSSS' person.txt
 echo -e "------\n"
 sed 's#yy#dandan#g' person.txt
echo -e "------\n"
sed -n '2,3p' person.txt
