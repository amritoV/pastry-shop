begin transaction;

-- Creazione dei domini

create domain IntegrGEZ as integer check (value >= 0);
create domain IntegrGZ as integer check (value > 0);
create domain RealGZ as real check (value > 0);
create domain StringS as varchar(100);
create domain StringM as varchar(500);
create domain StringL as varchar(2000);

CREATE EXTENSION pgcrypto;
create EXTENSION citext;
create domain Email as citext check (value ~ '^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$');



-- Creazione dello schema relazionale
create table Backoffice(
	nickname StringS not null,
	email Email not null,
	password varchar(255) not null,
	primary key (nickname)
	
);

create table UnitaMisura(
	nome StringM not null,
	primary key (nome)
);

create table Ingrediente(
	id serial not null,
	nome StringS not null,
	unitaMisura StringM not null,
	primary key (id),
	unique (nome),
	foreign key (unitaMisura) references UnitaMisura(nome)
);

create table TipologicaDolce(
	id serial not null,
	nome StringS not null,
	prezzo RealGZ not null,
	descrizione StringL,
	primary key (id),
	unique (nome)
);

create table Ingrediente_TipologicaDolce(
	ingrediente integer not null,
	tipologicaDolce integer not null,
	quantita RealGZ not null,
	primary key (ingrediente, tipologicaDolce),
	foreign key (ingrediente) references Ingrediente(id),
	foreign key (tipologicaDolce) references TipologicaDolce(id)
);

create table SchedaAnnuncio(
	id serial not null,
	tipologicaDolce integer not null,
	backoffice StringS not null,
	contatore IntegrGEZ not null,
	dataCreazione date not null,
	dataPubblicazione timestamp not null,
	primary key (id),
	foreign key (tipologicaDolce) references TipologicaDolce(id),
	foreign key (backoffice) references Backoffice(nickname),
	unique ( dataCreazione, tipologicaDolce),
	check ( dataCreazione <= date_trunc('hour', dataPubblicazione) )
);

commit;
