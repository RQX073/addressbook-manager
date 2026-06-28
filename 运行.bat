@echo off
chcp 65001 >nul
echo ============================================
echo        地址簿管理器 - 编译 & 运行
echo ============================================
echo.

REM 创建 bin 目录
if not exist bin mkdir bin

REM 编译
echo [1/2] 正在编译源文件...
javac -d bin -encoding UTF-8 src/addressbook/*.java
if errorlevel 1 (
    echo 编译失败！请检查错误信息。
    pause
    exit /b 1
)
echo 编译成功！
echo.

REM 运行
echo [2/2] 启动程序...
echo.
java -cp bin addressbook.App

pause
