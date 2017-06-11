#!/usr/bin/env bash

# fail if no hostname is provided
REGISTRY_HOST=${REGISTRY_HOST:?no host}
REGISTRY_PORT=${REGISTRY_PORT:-5000}

# we are always listening on port 80
# https://github.com/nginxinc/docker-nginx/blob/43c112100750cbd1e9f2160324c64988e7920ac9/stable/jessie/Dockerfile#L25
PORT=${PORT:-80}

sed -e "s/%HOST%/$REGISTRY_HOST/g" \
    -e "s/%PORT%/$REGISTRY_PORT/g" \
    -e "s/%BIND_PORT%/$PORT/g" \
    </etc/nginx/conf.d/default.conf.in >/etc/nginx/conf.d/default.conf

# wait for registry to come online
while ! curl -sS "$REGISTRY_HOST:$REGISTRY_PORT" &>/dev/null; do
    printf "waiting for the registry (%s:%s) to come online...\n" "$REGISTRY_HOST" "$REGISTRY_PORT"
    sleep 1
done

printf "starting proxy...\n"
exec nginx -g "daemon off;" "$@"
