#!/bin/bash
# use `kill -s SIGUSR1 PID` to backup log manually

# 1 argv - mongo path
# 2 argv - mongo config path
# 3 argv - "on" or "" for startup mongo
#           "off" - for shutdown mongo

echo "parameters # $#"

if [[ -z "$1" ]];then
    echo "mongo path is empty -> exit"
    #set error exit code
    exit 1
else
    echo "mongo path is: $1"
fi

if [[ -z "$2" ]]; then
	echo "mongo config path is empty -> exit"
    #set error exit code
    exit 1
else
	echo "mongo config path set: $2"
fi

echo "mongo startup/shutdown options: $3"
if [[ -z "$3" || "on" == "$3" ]];then
	echo "mongod stratup..."
	$1 --config $2
else
	echo "shutdown mongod..."
	$1 --config $2 --shutdown
fi
