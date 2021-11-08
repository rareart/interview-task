DROP TABLE IF EXISTS entity;

CREATE TABLE IF NOT EXISTS entity(
    id bigserial,
    code int,
    sysname varchar,
    watched_dttm timestamp,
    description varchar,
    data varchar
);

CREATE INDEX IF NOT EXISTS ENTITY_CODE ON entity(code);
CREATE INDEX IF NOT EXISTS ENTITY_SYSNAME on entity(sysname);
CREATE INDEX IF NOT EXISTS ENTITY_CODE_SYSNAME on entity(code, sysname);