/*
Navicat PGSQL Data Transfer

Source Server         : pgLocalhost
Source Server Version : 90314
Source Host           : localhost:5432
Source Database       : salasdereuniaodb
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90314
File Encoding         : 65001

Date: 2016-10-06 09:33:25
*/


-- ----------------------------
-- Sequence structure for agendamentos_id_agendamento_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "agendamentos_id_agendamento_seq";
CREATE SEQUENCE "agendamentos_id_agendamento_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for hibernate_sequence
-- ----------------------------
DROP SEQUENCE IF EXISTS "hibernate_sequence";
CREATE SEQUENCE "hibernate_sequence"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for salas_de_reuniao_id_sala_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "salas_de_reuniao_id_sala_seq";
CREATE SEQUENCE "salas_de_reuniao_id_sala_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for usuarios_id_usuario_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "usuarios_id_usuario_seq";
CREATE SEQUENCE "usuarios_id_usuario_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."usuarios_id_usuario_seq"', 1, true);

-- ----------------------------
-- Table structure for agendamentos
-- ----------------------------
DROP TABLE IF EXISTS "agendamentos";
CREATE TABLE "agendamentos" (
"id_agendamento" int4 DEFAULT nextval('agendamentos_id_agendamento_seq'::regclass) NOT NULL,
"data" timestamp(6) NOT NULL,
"duracao" int4 NOT NULL,
"hora" timestamp(6) NOT NULL,
"nome_agendamento" varchar(100) COLLATE "default" NOT NULL,
"url_arquivo" varchar(255) COLLATE "default",
"saladereuniao_id_sala" int4 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of agendamentos
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for agendamentos_aud
-- ----------------------------
DROP TABLE IF EXISTS "agendamentos_aud";
CREATE TABLE "agendamentos_aud" (
"id_agendamento" int4 NOT NULL,
"rev" int4 NOT NULL,
"revtype" int2,
"data" timestamp(6),
"duracao" int4,
"hora" timestamp(6),
"nome_agendamento" varchar(100) COLLATE "default",
"url_arquivo" varchar(255) COLLATE "default",
"saladereuniao_id_sala" int4
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of agendamentos_aud
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for agendamentos_usuarios
-- ----------------------------
DROP TABLE IF EXISTS "agendamentos_usuarios";
CREATE TABLE "agendamentos_usuarios" (
"agendamento_id_agendamento" int4 NOT NULL,
"participantes_id_usuario" int4 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of agendamentos_usuarios
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for agendamentos_usuarios_aud
-- ----------------------------
DROP TABLE IF EXISTS "agendamentos_usuarios_aud";
CREATE TABLE "agendamentos_usuarios_aud" (
"rev" int4 NOT NULL,
"agendamento_id_agendamento" int4 NOT NULL,
"participantes_id_usuario" int4 NOT NULL,
"revtype" int2
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of agendamentos_usuarios_aud
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for revinfo
-- ----------------------------
DROP TABLE IF EXISTS "revinfo";
CREATE TABLE "revinfo" (
"rev" int4 NOT NULL,
"revtstmp" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of revinfo
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for salas_de_reuniao
-- ----------------------------
DROP TABLE IF EXISTS "salas_de_reuniao";
CREATE TABLE "salas_de_reuniao" (
"id_sala" int4 DEFAULT nextval('salas_de_reuniao_id_sala_seq'::regclass) NOT NULL,
"capacidade" int4 NOT NULL,
"flg_excluida" bool,
"nome_sala" varchar(100) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of salas_de_reuniao
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for salas_de_reuniao_aud
-- ----------------------------
DROP TABLE IF EXISTS "salas_de_reuniao_aud";
CREATE TABLE "salas_de_reuniao_aud" (
"id_sala" int4 NOT NULL,
"rev" int4 NOT NULL,
"revtype" int2,
"capacidade" int4,
"flg_excluida" bool,
"nome_sala" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of salas_de_reuniao_aud
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for usuarios
-- ----------------------------
DROP TABLE IF EXISTS "usuarios";
CREATE TABLE "usuarios" (
"id_usuario" int4 DEFAULT nextval('usuarios_id_usuario_seq'::regclass) NOT NULL,
"ativo" bool,
"email" varchar(140) COLLATE "default" NOT NULL,
"nome_usuario" varchar(140) COLLATE "default" NOT NULL,
"senha" varchar(100) COLLATE "default" NOT NULL,
"tipo_usuario" int4 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of usuarios
-- ----------------------------
BEGIN;
INSERT INTO "usuarios" VALUES ('1', 't', 'duzao7667@gmail.com', 'Eduardo Ayres', '522d7ee4d28e5ab11e54f061d1a020190198be27', '0');
COMMIT;

-- ----------------------------
-- Table structure for usuarios_aud
-- ----------------------------
DROP TABLE IF EXISTS "usuarios_aud";
CREATE TABLE "usuarios_aud" (
"id_usuario" int4 NOT NULL,
"rev" int4 NOT NULL,
"revtype" int2,
"ativo" bool,
"email" varchar(140) COLLATE "default",
"nome_usuario" varchar(140) COLLATE "default",
"senha" varchar(100) COLLATE "default",
"tipo_usuario" int4
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of usuarios_aud
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------
ALTER SEQUENCE "agendamentos_id_agendamento_seq" OWNED BY "agendamentos"."id_agendamento";
ALTER SEQUENCE "salas_de_reuniao_id_sala_seq" OWNED BY "salas_de_reuniao"."id_sala";
ALTER SEQUENCE "usuarios_id_usuario_seq" OWNED BY "usuarios"."id_usuario";

-- ----------------------------
-- Primary Key structure for table agendamentos
-- ----------------------------
ALTER TABLE "agendamentos" ADD PRIMARY KEY ("id_agendamento");

-- ----------------------------
-- Primary Key structure for table agendamentos_aud
-- ----------------------------
ALTER TABLE "agendamentos_aud" ADD PRIMARY KEY ("id_agendamento", "rev");

-- ----------------------------
-- Primary Key structure for table agendamentos_usuarios_aud
-- ----------------------------
ALTER TABLE "agendamentos_usuarios_aud" ADD PRIMARY KEY ("rev", "agendamento_id_agendamento", "participantes_id_usuario");

-- ----------------------------
-- Primary Key structure for table revinfo
-- ----------------------------
ALTER TABLE "revinfo" ADD PRIMARY KEY ("rev");

-- ----------------------------
-- Primary Key structure for table salas_de_reuniao
-- ----------------------------
ALTER TABLE "salas_de_reuniao" ADD PRIMARY KEY ("id_sala");

-- ----------------------------
-- Primary Key structure for table salas_de_reuniao_aud
-- ----------------------------
ALTER TABLE "salas_de_reuniao_aud" ADD PRIMARY KEY ("id_sala", "rev");

-- ----------------------------
-- Uniques structure for table usuarios
-- ----------------------------
ALTER TABLE "usuarios" ADD UNIQUE ("email");

-- ----------------------------
-- Primary Key structure for table usuarios
-- ----------------------------
ALTER TABLE "usuarios" ADD PRIMARY KEY ("id_usuario");

-- ----------------------------
-- Primary Key structure for table usuarios_aud
-- ----------------------------
ALTER TABLE "usuarios_aud" ADD PRIMARY KEY ("id_usuario", "rev");

-- ----------------------------
-- Foreign Key structure for table "agendamentos"
-- ----------------------------
ALTER TABLE "agendamentos" ADD FOREIGN KEY ("saladereuniao_id_sala") REFERENCES "salas_de_reuniao" ("id_sala") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "agendamentos_aud"
-- ----------------------------
ALTER TABLE "agendamentos_aud" ADD FOREIGN KEY ("rev") REFERENCES "revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "agendamentos_usuarios"
-- ----------------------------
ALTER TABLE "agendamentos_usuarios" ADD FOREIGN KEY ("agendamento_id_agendamento") REFERENCES "agendamentos" ("id_agendamento") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "agendamentos_usuarios" ADD FOREIGN KEY ("participantes_id_usuario") REFERENCES "usuarios" ("id_usuario") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "agendamentos_usuarios_aud"
-- ----------------------------
ALTER TABLE "agendamentos_usuarios_aud" ADD FOREIGN KEY ("rev") REFERENCES "revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "salas_de_reuniao_aud"
-- ----------------------------
ALTER TABLE "salas_de_reuniao_aud" ADD FOREIGN KEY ("rev") REFERENCES "revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "usuarios_aud"
-- ----------------------------
ALTER TABLE "usuarios_aud" ADD FOREIGN KEY ("rev") REFERENCES "revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;
