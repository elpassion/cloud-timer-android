#!/usr/bin/env bash
./gradlew createToTestsDebugCoverageReport
mkdir -p app/build/reports/jacoco/test/
cp app/build/reports/coverage/toTests/debug/report.xml app/build/reports/jacoco/test/jacocoTestReport.xml
./gradlew coveralls
