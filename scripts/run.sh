#!/usr/bin/env bash
set -euo pipefail

usage(){ echo "Использование: $0 <labN|N> <taskM|M> <java|cs|cpp>"; exit 1; }
[ $# -eq 3 ] || usage

lab="$1"; task="$2"; lang="$3"
[[ "$lab"  =~ ^[0-9]+$ ]]  && lab="lab${lab}"
[[ "$task" =~ ^[0-9]+$ ]] && task="task${task}"

base_dir="$(cd "$(dirname "$0")/.." && pwd)"
task_dir="$base_dir/$lab/$task"
[ -d "$task_dir" ] || { echo "Не найдено: $task_dir"; exit 1; }

case "$lang" in
  java)
    src="$task_dir/java"; main="$src/Main.java"
    [ -f "$main" ] || { echo "Не найдено: $main"; exit 1; }
    cd "$src"
    javac -d . Main.java
    pkg="$(sed -n 's/^[[:space:]]*package[[:space:]]\+//p' Main.java | head -n1 | tr -d ';' | tr -d '\r')"
    if [ -n "$pkg" ]; then
      exec java "$pkg.Main"
    else
      exec java Main
    fi
    ;;

  cs|csharp)
    src="$task_dir/cs"
    [ -d "$src" ] || { echo "Не найдено: $src"; exit 1; }
    cd "$src"
    if ! ls *.csproj >/dev/null 2>&1; then
      dotnet new console --force >/dev/null
    fi
    exec dotnet run
    ;;

  cpp|c++)
    src="$task_dir/cpp"
    [ -d "$src" ] || { echo "Не найдено: $src"; exit 1; }
    cd "$src"
    shopt -s nullglob
    files=( *.cpp )
    shopt -u nullglob
    [ ${#files[@]} -gt 0 ] || { echo "No .cpp files in $src"; exit 1; }
    mkdir -p build
    g++ -std=c++17 -O2 -Wall -Wextra -Wpedantic -pipe -pthread "${files[@]}" -o build/app
    exec ./build/app
    ;;

  *)
    echo "Неизвестный язык: $lang"; exit 1 ;;
esac
