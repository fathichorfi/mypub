#!/usr/bin/env bash

# wait-for-it.sh

set -e

TIMEOUT=15
QUIET=0
WAIT_FOR=0

while getopts "t:q" o; do
  case "${o}" in
    t)
      TIMEOUT="${OPTARG}"
      ;;
    q)
      QUIET=1
      ;;
    *)
      echo "Usage: wait-for-it.sh host:port [-t timeout] [-q]"
      exit 1
      ;;
  esac
done
shift $((OPTIND - 1))

if [ $# -ne 1 ]; then
  echo "Usage: wait-for-it.sh host:port [-t timeout] [-q]"
  exit 1
fi

host_port="$1"
host=$(echo "$host_port" | cut -d: -f1)
port=$(echo "$host_port" | cut -d: -f2)

for i in $(seq $TIMEOUT); do
  nc -z "$host" "$port" && WAIT_FOR=1 && break
  sleep 1
done

if [ "$WAIT_FOR" -eq 0 ]; then
  echo "Timeout of $TIMEOUT seconds exceeded for $host_port"
  exit 1
fi

if [ "$QUIET" -eq 0 ]; then
  echo "$host_port is available!"
fi

exec "$@"
