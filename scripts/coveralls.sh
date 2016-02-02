#!/usr/bin/env bash
./gradlew connectedCheck
mkdir -p app/build/reports/jacoco/test/
cp app/build/reports/coverage/debug/report.xml app/build/reports/jacoco/test/jacocoTestReport.xml
./gradlew crashlyticsUploadDistributionDebug