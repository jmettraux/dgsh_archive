#!/bin/sh

FILENAME=dgsh-sample-session.txt

rm $FILENAME
tee -a $FILENAME | ./dgsh.sh | tee -a $FILENAME

