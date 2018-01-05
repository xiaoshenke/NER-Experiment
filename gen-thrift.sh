#!/bin/bash

thrift --gen java:beans,hashcode,generated_annotations=undated Ner.thrift
#,-o=src/main/java Fixme: -o not working?



