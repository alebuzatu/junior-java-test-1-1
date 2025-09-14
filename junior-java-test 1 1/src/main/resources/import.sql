DELETE FROM insurancepolicy;
DELETE FROM car;
DELETE FROM owner;

INSERT INTO owner (id, name, email) VALUES (1, 'Ana Pop', 'ana.pop@example.com');
INSERT INTO owner (id, name, email) VALUES (2, 'Bogdan Ionescu', 'bogdan.ionescu@example.com');

INSERT INTO car (id, vin, make, model, year_of_manufacture, owner_id) VALUES (1, 'VIN12345', 'Dacia', 'Logan', 2018, 1);
INSERT INTO car (id, vin, make, model, year_of_manufacture, owner_id) VALUES (2, 'VIN67890', 'VW', 'Golf', 2021, 2);

INSERT INTO insurancepolicy (id, car_id, provider, start_date, end_date) VALUES (1, 1, 'Allianz', DATE '2024-01-01', DATE '2024-12-31');
INSERT INTO insurancepolicy (id, car_id, provider, start_date, end_date) VALUES (2, 1, 'Groupama', DATE '2025-01-01', DATE '2025-12-31');
INSERT INTO insurancepolicy (id, car_id, provider, start_date, end_date) VALUES (3, 2, 'Allianz', DATE '2025-03-01', DATE '2025-09-30');

-- Șterger claims existente (dacă există)
DELETE FROM claim;

-- Adăug claims de test
INSERT INTO claim (car_id, claim_date, description, amount) VALUES (1, '2024-03-15', 'Accident frontal - coliziume cu alt vehicul', 2500.00);
INSERT INTO claim (car_id, claim_date, description, amount) VALUES (1, '2024-05-20', 'Geam spart - vandalism', 500.00);
INSERT INTO claim (car_id, claim_date, description, amount) VALUES (2, '2024-02-10', 'Accident lateral - deteriorare portiera', 1200.00);
INSERT INTO claim (car_id, claim_date, description, amount) VALUES (2, '2024-06-05', 'Defectiune motor - supraincălzire', 3500.00);
