#!/usr/bin/env bash
mkdir -p app/build/reports/jacoco/test/
cp app/build/reports/coverage/debug/report.xml app/build/reports/jacoco/test/jacocoTestReport.xml
./gradlew coveralls