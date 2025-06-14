@echo off
cd /d %~dp0

echo 启动 edu-app.jar ...
start "JavaApp" cmd /k java -jar ../../springboot/target/edu-app.jar

echo 启动 natapp.exe ...
start "NatApp" cmd /k natapp.exe

echo 所有服务已启动。
