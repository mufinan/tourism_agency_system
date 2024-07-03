-- Veritabanı oluşturma
CREATE DATABASE tourism_agency_system;

-- Tabloları oluşturma
-- tourism_agency_user tablosu
CREATE TABLE tourism_agency_user (
  id SERIAL PRIMARY KEY,
  username VARCHAR(255),
  password VARCHAR(255),
  type VARCHAR(50),
  name VARCHAR(255)
);

-- tourism_agency_user tablosu veri ekleme
INSERT INTO tourism_agency_user (username, password, type, name) VALUES
('mufi', '1234', 'employee', 'Mustafa Furkan İnan'),
('mufinan', '12345', 'admin', 'Mustafa Furkan İnan'),
('menes', '12346', 'employee', 'Enes İnan'),
('buse', '12347', 'employee', 'Buse İnan'),
('iku', '123', 'admin', 'İlknur Mert');

-- otel tablosu
CREATE TABLE otel (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255),
  region VARCHAR(255),
  city VARCHAR(255),
  address VARCHAR(255),
  email VARCHAR(255),
  phone VARCHAR(11),
  star INT,
  features VARCHAR(255)
);

-- otel tablosu veri ekleme
INSERT INTO otel (name, region, city, address, email, phone, star, features) VALUES
('Golden Sun Hotel', 'Kadıköy', 'İstanbul', 'Caferağa Mah. Moda Cad. No:42 Kadıköy, İstanbul', 'goldensun@hotel.com', '02161234567', 4, 'Ücretsiz Otopark, Ücretsiz WiFi, Yüzme Havuzu'),
('Blue Sky Resort', 'Lara', 'Antalya', 'Güzeloba Mah. Barınaklar Bulv. No:58 Lara, Antalya', 'bluesky@resort.com', '02421234568', 5, 'Fitness Center, Hotel Concierge, SPA'),
('Green Valley Hotel', 'Beşiktaş', 'İstanbul', 'Abbasağa Mah. Yıldız Cad. No:24 Beşiktaş, İstanbul', 'greenvalley@hotel.com', '02121234569', 4, '7/24 Oda Servisi, Ücretsiz WiFi, Fitness Center'),
('Ocean Breeze Hotel', 'Karşıyaka', 'İzmir', 'Bostanlı Mah. Cemal Gürsel Cad. No:56 Karşıyaka, İzmir', 'oceanbreeze@hotel.com', '02321234572', 4, 'Ücretsiz WiFi, Ücretsiz Otopark, SPA'),
('Cappadocia Cave Hotel', 'Göreme', 'Nevşehir', 'Müze Cad. No:18 Göreme, Nevşehir', 'cappadociacave@hotel.com', '03841234575', 4, 'SPA, Ücretsiz WiFi, Yüzme Havuzu');


-- lodgings tablosu
CREATE TABLE lodgings (
  id SERIAL PRIMARY KEY,
  otel_id INT,
  type VARCHAR(50)
);

-- lodgings tablosu veri ekleme
INSERT INTO lodgings (otel_id, type) VALUES
(1, 'Ultra Herşey Dahil'),
(2, 'Herşey Dahil'),
(3, 'Oda Kahvaltı'),
(4, 'Tam Pansiyon'),
(5, 'Yarım Pansiyon');


-- features tablosu
CREATE TABLE features (
  id SERIAL PRIMARY KEY,
  type VARCHAR(50),
  name VARCHAR(255)
);

-- features tablosu veri ekleme
INSERT INTO features (type, name) VALUES
('tesis ozelligi', 'Kasa'),
('tesis ozelligi', 'Ücretsiz WiFi'),
('tesis ozelligi', 'Yüzme Havuzu'),
('tesis ozelligi', 'Fitness Center'),
('tesis ozelligi', 'Hotel Concierge'),
('tesis ozelligi', 'SPA'),
('tesis ozelligi', '7/24 Oda Servisi'),
('pansiyon ozelligi', 'Ultra Herşey Dahil'),
('pansiyon ozelligi', 'Herşey Dahil'),
('pansiyon ozelligi', 'Oda Kahvaltı'),
('pansiyon ozelligi', 'Tam Pansiyon'),
('pansiyon ozelligi', 'Yarım Pansiyon'),
('pansiyon ozelligi', 'Sadece Yatak'),
('pansiyon ozelligi', 'Alkol Hariç Full credit'),
('sezon ozellikleri', 'Kış Sezonu'),
('sezon ozellikleri', 'Yaz Sezonu'),
('oda ozelligi', 'Televizyon'),
('oda ozelligi', 'Minibar'),
('oda ozelligi', 'Oyun Konsolu'),
('oda ozelligi', 'Kasa'),
('oda ozelligi', 'Projeksiyon');

-- season tablosu
CREATE TABLE season (
  id SERIAL PRIMARY KEY,
  otel_id INT,
  start_date DATE,
  end_date DATE,
  name VARCHAR(255)
);

-- season tablosu veri ekleme
INSERT INTO season (otel_id,start_date,end_date,name) VALUES
(1, '2024-01-01', '2024-05-31', 'Kış Sezonu'),
(1, '2024-06-01', '2024-09-30', 'Yaz Sezonu'),
(2, '2024-01-01', '2024-05-31', 'Kış Sezonu'),
(2, '2024-06-01', '2024-09-30', 'Yaz Sezonu'),
(3, '2024-01-01', '2024-05-31', 'Kış Sezonu');


-- room tablosu
CREATE TABLE room (
  id SERIAL PRIMARY KEY,
  otel_id INT,
  lodgings_id INT,
  season_id INT,
  price_child INT,
  features VARCHAR(255),
  name VARCHAR(255),
  stock INT,
  bed_number INT,
  sqr_meter INT,
  room_type VARCHAR(50),
  price_adult INT
);
-- room tablosu veri ekleme
INSERT INTO room (otel_id, lodgings_id,	season_id,	price_child,	features,	name,	stock,	bed_number,	sqr_meter,	room_type,	price_adult) VALUES
(1,	1,	1,	500,	'Televizyon, Minibar, Oyun Konsolu, Kasa, Projeksiyon',	'SINGLE',	10,	1,	20,	'SINGLE',	1000),
(1,	1,	1,	1000,	'Televizyon, Minibar, Oyun Konsolu, Kasa, Projeksiyon',	'DOUBLE',	15,	2,	30,	'DOUBLE',	2000),
(2,	2,	4,	250,	'Televizyon',	'SINGLE',	10,	1,	10,	'SINGLE',	500),
(2,	2,	4,	375,	'Televizyon, Minibar',	'DOUBLE',	15,	2,	35,	'DOUBLE',	750),
(3,	3,	5,	1500,	'Televizyon, Minibar, Oyun Konsolu, Kasa, Projeksiyon',	'SINGLE',	30,	1,	35,	'SINGLE',	3000);

-- reservations tablosu
CREATE TABLE reservations (
  id SERIAL PRIMARY KEY,
  otel_id INT,
  employee_id INT,
  customer_name VARCHAR(255),
  customer_id VARCHAR(255),
  start_date DATE,
  end_date DATE,
  price INT
);
-- reservations tablosu veri ekleme
INSERT INTO reservations(otel_id,	employee_id,	customer_name,	customer_id,	start_date,	end_date,	price) VALUES
(1,	1,	'Patika Dev',	'11111111111',	'2024-01-01',	'2024-05-31',	2000),
(1,	1,	'PatikaDev Ailesi',	'11111111111',	'2024-01-01',	'2024-05-31',	25000),
(2,	1,	'Furkan İnan',	'11111111111',	'2024-06-01',	'2024-09-30',	6000),
(2,	1,	'İnan Ailesi',	'11111111111',	'2024-06-01',	'2024-09-30',	13500),
(3,	1,	'Yalnız Kurt',	'11111111111',	'2024-01-01',	'2024-05-31',	3000);


-- Index oluşturma

-- features tablosu indeks
CREATE INDEX idx_features_id ON features (id);

-- lodgings tablosu indeks
CREATE INDEX idx_lodgings_id ON lodgings (id);

-- otel tablosu indeks
CREATE INDEX idx_otel_id ON otel (id);

-- reservations tablosu indeks
CREATE INDEX idx_reservations_id ON reservations (id);

-- room tablosu indeks
CREATE INDEX idx_room_id ON room (id);

-- season tablosu indeks
CREATE INDEX idx_season_id ON season (id);

-- tourism_agency_user tablosu indeks
CREATE INDEX idx_tourism_agency_user_id ON tourism_agency_user (id);
