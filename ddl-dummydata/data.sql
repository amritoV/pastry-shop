begin transaction;

set constraints all deferred;



INSERT INTO Backoffice(nickname, email, password) VALUES
('Maria', 'maria@pasticceria.it', crypt('M@ria', gen_salt('bf'))),
('Giuseppina', 'giusy88@pasticceria.it', crypt('giusyGiusy12', gen_salt('bf')));

INSERT INTO UnitaMisura(nome) VALUES
('ml'),
('cl'),
('l'),
('g'),
('kg');

Insert INTO Ingrediente(id, nome, unitaMisura) VALUES
(1, 'farina0','g'),
(2, 'farina00','g'),
(3, 'succo di limone','ml'),
(4, 'lievito di birra','g'),
(5, 'acqua','ml'),
(6, 'cioccolato fondente','g'),
(7, 'farina di riso', 'g'),
(8, 'mela','kg');

Insert INTO TipologicaDolce(id, nome, prezzo, descrizione) VALUES
(1, 'torta di mele', 12.99, 'torta tipica nostrana fatte con 100% mele della val di Fasso'),
(2, 'torta paradiso',7.00, null),
(3, 'torta millefoglie al cioccolato', 15.01, 'classica millefoglie con cioccolato fondente al 70%');

Insert INTO Ingrediente_TipologicaDolce(ingrediente, tipologicaDolce, quantita) VALUES
(7, 1, 800),
(5, 1, 1000),
(8, 1, 1.2),
(2, 2, 1020),
(5, 2, 1020),
(4, 2, 3),
(6, 2, 120);

Insert INTO schedaAnnuncio(tipologicaDolce, backoffice, contatore, dataCreazione, dataPubblicazione) VALUES
(1, 'Maria', 13,'2022-02-14','2022-02-14 15:30:00'),
(2, 'Giuseppina', 22,'2022-02-13','2022-02-14 15:54:00');

commit;
