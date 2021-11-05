DROP TABLE IF EXISTS Entity;

CREATE TABLE IF NOT EXISTS Entity(
    id bigserial,
    code int,
    sysname varchar,
    watched_dttm date,
    description varchar,
    data varchar
);

CREATE INDEX IF NOT EXISTS ENTITY_CODE ON Entity(code);
CREATE INDEX IF NOT EXISTS ENTITY_SYSNAME on Entity(sysname);
CREATE INDEX IF NOT EXISTS ENTITY_CODE_SYSNAME on Entity(code, sysname);