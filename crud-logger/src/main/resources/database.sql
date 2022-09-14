CREATE TABLE "users" (
    id SERIAL NOT NULL,
    "password" varchar(300) NOT NULL,
    email varchar(300) NOT NULL,
    "type" varchar(300) NOT null,
    CONSTRAINT users_pk PRIMARY KEY (id)
)

CREATE TABLE "beers" (
    id SERIAL NOT NULL,
    "name" varchar(300) NOT NULL,
   	price numeric(15, 2) NOT null,
   	CONSTRAINT beer PRIMARY KEY (id)
)

CREATE TABLE "logs" (
    id SERIAL NOT NULL,
    "user_id" INTEGER NOT NULL,
    "message" varchar(1000) NOT NULL,
   	"done_at" TIMESTAMP WITHOUT TIME ZONE NOT null,
   	CONSTRAINT log_pk PRIMARY KEY (id)
)

CREATE TABLE "beer_purchase" (
    id SERIAL NOT NULL,
    "user_id" INTEGER NOT NULL,
    "beer_id" INTEGER NOT NULL,
   	total_value numeric(15, 2) NOT null,
   	CONSTRAINT beer_purchase_pk PRIMARY KEY (id)
)