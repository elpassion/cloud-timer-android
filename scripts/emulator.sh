#!/usr/bin/env bash
pwd
echo no | android create avd --force -n test -t android-16 --abi armeabi-v7a --skin 480x800
emulator -avd test -no-audio -no-window &
android-wait-for-emulator
sleep 30
adb shell input keyevent 82