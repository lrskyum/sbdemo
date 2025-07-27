#!/usr/bin/env bash
set -ee

export KC=keycloak

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE "keycloak";
    CREATE USER keycloak WITH PASSWORD 'keycloak';
    GRANT ALL PRIVILEGES ON DATABASE "keycloak" TO keycloak;
EOSQL
