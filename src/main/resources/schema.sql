DROP TABLE IF EXISTS public."Cities";
CREATE TABLE public."Cities"("Id" serial PRIMARY KEY, "Name" VARCHAR(255), "Population" integer);

DROP TABLE IF EXISTS public."Cities";
CREATE TABLE public."Cities"("Id" serial PRIMARY KEY, "Name" VARCHAR(255), "Population" integer);

CREATE TABLE public."AZLM_TGE_CALENDAR" (
	"CALENDAR_ID" serial PRIMARY KEY,
	"DATE" timestamptz(6) NULL,
	"COUNT" numeric NULL
)
;