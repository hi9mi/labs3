#!/usr/bin/env bash
set -euo pipefail

usage(){ echo "Использование: $0 <labN|N> <taskM|M> [java|cs|cpp|all]"; exit 1; }
[ $# -ge 2 ] && [ $# -le 3 ] || usage

lab="$1"; task="$2"; lang="${3:-all}"
[[ "$lab"  =~ ^[0-9]+$ ]]  && lab="lab${lab}"
[[ "$task" =~ ^[0-9]+$ ]] && task="task${task}"

base_dir="$(cd "$(dirname "$0")/.." && pwd)"
task_dir="$base_dir/$lab/$task"
mkdir -p "$task_dir"

create_java() {
  dir="$task_dir/java"; mkdir -p "$dir"
  f="$dir/Main.java"
  if [ ! -f "$f" ]; then
    cat >"$f" <<'JAVA'
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello from Java!");
    }
}
JAVA
  fi
}

create_cs() {
  dir="$task_dir/cs"; mkdir -p "$dir"
  if ! ls "$dir"/*.csproj >/dev/null 2>&1; then
    (cd "$dir" && dotnet new console --force >/dev/null)
  fi
}

create_cpp() {
  dir="$task_dir/cpp"; mkdir -p "$dir"
  f="$dir/main.cpp"
  if [ ! -f "$f" ]; then
    cat >"$f" <<'CPP'
#include <bits/stdc++.h>
int main() {
    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout << "Hello from C++!\n";
    return 0;
}
CPP
  fi
}

case "$lang" in
  all)  create_java; create_cs; create_cpp ;;
  java) create_java ;;
  cs|csharp) create_cs ;;
  cpp|c++) create_cpp ;;
  *) echo "Неизвестный язык: $lang"; exit 1 ;;
esac

echo "Создана $lab/$task в $task_dir"
