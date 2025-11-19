DROP DATABASE IF EXISTS Baloncesto;
CREATE DATABASE Baloncesto;
USE Baloncesto;

CREATE TABLE Equipo(
    idequipo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(90) NOT NULL,
    ciudad VARCHAR(120) NOT NULL
);

CREATE TABLE Jugador(
    idjugador INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(90) NOT NULL,
    edad INT NOT NULL,
    dorsal INT NOT NULL,
    idequipo INT NOT NULL,
    FOREIGN KEY(idequipo) REFERENCES Equipo(idequipo) ON DELETE CASCADE
);

CREATE TABLE Partido(
    idpartido INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    equipo_local_id INT NOT NULL,
    equipo_visitante_id INT NOT NULL,
    puntos_local INT,
    puntos_visitante INT,
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

-- Aquí los inserts (sin puntos y minutos)
INSERT INTO Equipo VALUES
    (1, "Universidad de Salamanca", "Salamanca"),
    (2, "Adarsa Maristas", "Palencia"),
    (3, "Caja Rural RDL", "León"),
    (4, "Baloncesto La Flecha", "Valladolid"),
    (5, "CD Universidad de Valladolid", "Valladolid"),
    (6, "CB Aldeamayor", "Valladolid"),
    (7, "Club de Baloncesto Palencia", "Palencia"),
    (8, "San Pablo Burgos", "Burgos"),
    (9, "Súper Agropal Palencia", "Palencia"),
    (10, "CD Maristas", "León"),
    (11, "CD Filipenses", "Palencia"),
    (12, "CD Nuestra Señora de la Merced", "León"),
    (13, "CD Palencia Baloncesto", "Palencia"),
    (14, "CB Tizona Univ. Burgos", "Burgos"),
    (15, "Baloncesto Arroyo", "Arroyo de la Encomienda"),
    (16, "CB Villa de Aranda", "Aranda de Duero");

INSERT INTO Jugador VALUES
    (1, "David Martínez", 24, 20, 1),
    (2, "Carlos Gómez", 22, 15, 2),
    (3, "Javier Sánchez", 26, 10, 3),
    (4, "Miguel Herrera", 23, 8, 4),
    (5, "Alberto Ruiz", 25, 12, 5),
    (6, "Luis Fernández", 21, 7, 6),
    (7, "Sergio Torres", 24, 9, 7),
    (8, "Juan Pérez", 28, 6, 8),
    (9, "Andrés López", 22, 14, 9),
    (10, "Iván García", 27, 11, 10),
    (11, "Raúl Delgado", 25, 5, 11),
    (12, "Fernando Castillo", 23, 16, 12),
    (13, "Pablo Jiménez", 24, 17, 13),
    (14, "Alejandro Morales", 22, 18, 14),
    (15, "Óscar Navarro", 26, 21, 15),
    (16, "Jorge Molina", 24, 13, 16);

INSERT INTO Partido VALUES
    (1, '2025-11-01', 1, 2, 78, 65),
    (2, '2025-11-02', 3, 4, 85, 90),
    (3, '2025-11-03', 5, 6, 72, 70),
    (4, '2025-11-04', 7, 8, 88, 82),
    (5, '2025-11-05', 9, 10, 67, 70),
    (6, '2025-11-06', 11, 12, 75, 77),
    (7, '2025-11-07', 13, 14, 80, 79),
    (8, '2025-11-08', 15, 16, 69, 68);

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
