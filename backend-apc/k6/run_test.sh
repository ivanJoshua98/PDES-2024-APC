#!/bin/sh 

TEST_TO_RUN="./src/script_test_carga_multiples_url_paralelo.js"


k6 run -s 15s:10 -s 30s:500 -s 15s:5000  -s 15s:1000  -s 15s:5000 -s 15s:10  $TEST_TO_RUN




