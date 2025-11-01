# Labs

Лабораторные работы выполнялись в **Visual Studio Code** под **WSL (Ubuntu)**.

## Быстрый старт

### Установка необходимых пакетов

```bash
sudo apt update
sudo apt install -y build-essential gdb
sudo apt install -y dotnet-sdk-8.0
sudo apt install -y openjdk-21-jdk
```

### Рекомендуемые расширения VS Code

```bash
ms-vscode.cpptools-extension-pack
vscjava.vscode-java-pack
ms-dotnettools.csdevkit
```

После установки можно создавать и запускать лабораторные через `make`:

```bash
make init 1 1 all   # создать структуру lab1/task1
make run 1 1 cs     # запустить задачу на C#
```
