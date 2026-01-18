DROP DATABASE IF EXISTS Baloncesto;
CREATE DATABASE Baloncesto;
USE Baloncesto;

CREATE TABLE Equipo(
                       idequipo INT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(90) NOT NULL,
                       localidad VARCHAR(120) NOT NULL,
                       version INT DEFAULT 0
);

CREATE TABLE Jugador(
                        idjugador INT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(90) NOT NULL,
                        edad INT NOT NULL,
                        dorsal INT NOT NULL,
                        idequipo INT NOT NULL,
                        borrado TINYINT(1) DEFAULT 0,
                        version INT DEFAULT 0,
                        FOREIGN KEY(idequipo) REFERENCES Equipo(idequipo) ON DELETE CASCADE
);

CREATE TABLE Partido(
                        idpartido INT AUTO_INCREMENT PRIMARY KEY,
                        fecha DATE NOT NULL,
                        equipo_local_id INT NOT NULL,
                        equipo_visitante_id INT NOT NULL,
                        puntos_local INT,
                        puntos_visitante INT,
                        version INT DEFAULT 0,
                        FOREIGN KEY(equipo_local_id) REFERENCES Equipo(idequipo) ON DELETE CASCADE,
                        FOREIGN KEY(equipo_visitante_id) REFERENCES Equipo(idequipo) ON DELETE CASCADE
);

CREATE TABLE JugadorPartido(
                        idjugador INT NOT NULL,
                        idpartido INT NOT NULL,
                        PRIMARY KEY(idjugador, idpartido),
                        FOREIGN KEY(idjugador) REFERENCES Jugador(idjugador) ON DELETE CASCADE,
                        FOREIGN KEY(idpartido) REFERENCES Partido(idpartido) ON DELETE CASCADE
);


INSERT INTO Equipo (idequipo, nombre, localidad, version) VALUES
                        (1, "Universidad de Salamanca", "Salamanca", 0),
                        (2, "Adarsa Maristas", "Palencia", 0),
                        (3, "Caja Rural RDL", "León", 0),
                        (4, "Baloncesto La Flecha", "Valladolid", 0),
                        (5, "CD Universidad de Valladolid", "Valladolid", 0),
                        (6, "CB Aldeamayor", "Valladolid", 0),
                        (7, "Club de Baloncesto Palencia", "Palencia", 0),
                        (8, "San Pablo Burgos", "Burgos", 0),
                        (9, "Súper Agropal Palencia", "Palencia", 0),
                        (10, "CD Maristas", "León", 0),
                        (11, "CD Filipenses", "Palencia", 0),
                        (12, "CD Nuestra Señora de la Merced", "León", 0),
                        (13, "CD Palencia Baloncesto", "Palencia", 0),
                        (14, "CB Tizona Univ. Burgos", "Burgos", 0),
                        (15, "Baloncesto Arroyo", "Arroyo de la Encomienda", 0),
                        (16, "CB Villa de Aranda", "Aranda de Duero", 0);


INSERT INTO Jugador (idjugador, nombre, edad, dorsal, idequipo, borrado, version) VALUES
                        (1, "David Martínez", 24, 20, 1, 0, 0),
                        (2, "Carlos Gómez", 22, 15, 2, 0, 0),
                        (3, "Javier Sánchez", 26, 10, 3, 0, 0),
                        (4, "Miguel Herrera", 23, 8, 4, 0, 0),
                        (5, "Alberto Ruiz", 25, 12, 5, 0, 0),
                        (6, "Luis Fernández", 21, 7, 6, 0, 0),
                        (7, "Sergio Torres", 24, 9, 7, 0, 0),
                        (8, "Juan Pérez", 28, 6, 8, 0, 0),
                        (9, "Andrés López", 22, 14, 9, 0, 0),
                        (10, "Iván García", 27, 11, 10, 0, 0),
                        (11, "Raúl Delgado", 25, 5, 11, 0, 0),
                        (12, "Fernando Castillo", 23, 16, 12, 0, 0),
                        (13, "Pablo Jiménez", 24, 17, 13, 0, 0),
                        (14, "Alejandro Morales", 22, 18, 14, 0, 0),
                        (15, "Óscar Navarro", 26, 21, 15, 0, 0),
                        (16, "Jorge Molina", 24, 13, 16, 0, 0);


INSERT INTO Partido (idpartido, fecha, equipo_local_id, equipo_visitante_id, puntos_local, puntos_visitante, version) VALUES
                        (1, '2025-11-01', 1, 2, 78, 65, 0),
                        (2, '2025-11-02', 3, 4, 85, 90, 0),
                        (3, '2025-11-03', 5, 6, 72, 70, 0),
                        (4, '2025-11-04', 7, 8, 88, 82, 0),
                        (5, '2025-11-05', 9, 10, 67, 70, 0),
                        (6, '2025-11-06', 11, 12, 75, 77, 0),
                        (7, '2025-11-07', 13, 14, 80, 79, 0),
                        (8, '2025-11-08', 15, 16, 69, 68, 0);

INSERT INTO JugadorPartido VALUES
                        (1, 1),
                        (6, 1),
                        (3, 2),
                        (4, 2),
                        (5, 3),
                        (6, 3),
                        (7, 4),
                        (8, 4),
                        (9, 5),
                        (10, 5),
                        (11, 6),
                        (12, 6),
                        (13, 7),
                        (14, 7),
                        (15, 8),
                        (16, 8);