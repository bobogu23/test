#!/bin/bash
#按单词出现频率降序排序（计算文件中每个单词的重复数量）

awk '{for(i=1;i<=NF;i++) print $i}' count.txt | sort|uniq -c |sort -r |awk '{print $2" "$1}'
echo -e "\n"
#cat count.txt  | xargs -n 1 | sort | uniq -c | sort -nr | awk '{print $2" "$1}'

#取出网卡eth0 的IP地址
#lo0: flags=8049<UP,LOOPBACK,RUNNING,MULTICAST> mtu 16384
#	options=1203<RXCSUM,TXCSUM,TXSTATUS,SW_TIMESTAMP>
#	inet 127.0.0.1 netmask 0xff000000
#	inet6 ::1 prefixlen 128
#	inet6 fe80::1%lo0 prefixlen 64 scopeid 0x1
#	nd6 options=201<PERFORMNUD,DAD>
#gif0: flags=8010<POINTOPOINT,MULTICAST> mtu 1280
#stf0: flags=0<> mtu 1280
#XHC0: flags=0<> mtu 0
#XHC20: flags=0<> mtu 0
#en0: flags=8863<UP,BROADCAST,SMART,RUNNING,SIMPLEX,MULTICAST> mtu 1500
#	ether 88:e9:fe:4d:fe:b5
#	inet6 fe80::1035:e1b5:65e5:ff8f%en0 prefixlen 64 secured scopeid 0x7
#	inet6 240e:e0:f18e:f026:109f:30b1:3844:e069 prefixlen 64 autoconf secured
#	inet6 240e:e0:f18e:f026:f1b7:bd7c:ccc1:af9e prefixlen 64 autoconf temporary
#	inet 192.168.43.83 netmask 0xffffff00 broadcast 192.168.43.255
#	nd6 options=201<PERFORMNUD,DAD>
#	media: autoselect
#	status: active
ifconfig en0 | awk  '$1~/^inet$/{print $2}'
echo -e "\n"

#取出文件/etc/services的23～30行
awk 'NR>22 && NR<31' /etc/services
echo -e "------------\n"
#处理以下文件内容，将域名取出并根据域名进行计数排序处理

#http://www.etiantian.org/index.html
#
#http://www.etiantian.org/1.html
#
#http://post.etiantian.org/index.html
#
#http://mp3.etiantian.org/index.html
#
#http://www.etiantian.org/3.html
#
#http://post.etiantian.org/2.html

# 思路, 以/ 为分隔符取出第二列 ,域名做为数组的下标，i++统计次数，遍历
#awk -F "[/]+" '{i++;print $2,i}' url_content.txt

awk -F "[/]+" '{h[$2]++;print $2,h["www.etiantian.org"]}'  url_content.txt
echo -e "\n"

awk -F "/+" '{h[$2]++}END{for(i in h)print i,h[i]}' url_content.txt