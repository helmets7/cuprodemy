CREATE DATABASE IF NOT EXISTS `cuprodemy` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `cuprodemy`;
CREATE TABLE `comentario` (
  `id` bigint(20) primary key auto_increment,
  `comentario` varchar(400) COLLATE utf8_unicode_ci NOT NULL,
  `firmatiempo` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `id_usuario` bigint(20) NOT NULL,
  `id_tipocomentario` bigint(20) NOT NULL,
  `id_curso` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `comentario` (`id`, `comentario`, `firmatiempo`, `id_usuario`, `id_tipocomentario`, `id_curso`) VALUES
(1, 'buen video', '2022-12-17 22:41:28', 3, 2, 1),
(2, 'qué características destacarías de typescript sobre javascript', '2022-12-17 23:16:39', 8, 2, 1),
(3, 'no me gustó', '2022-12-17 23:16:42', 4, 1, 1);

-- --------------------------------------------------------

CREATE TABLE `curso` (
  `id` bigint(20) primary key auto_increment,
  `nombre` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `descripcion` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `miniatura` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `video_url` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `duracion` time NOT NULL,
  `id_leccion` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `curso` (`id`, `nombre`, `descripcion`, `miniatura`, `video_url`, `duracion`, `id_leccion`) VALUES
(1, '[value-2]', '[value-3]', '[value-4]', '[value-5]', '00:00:01', 1);

-- --------------------------------------------------------

CREATE TABLE `leccion` (
  `id` bigint(20) primary key auto_increment,
  `descripcion` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `recurso` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `orden` varchar(250) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `leccion` (`id`, `descripcion`, `recurso`, `orden`) VALUES
(1, 'libro typescript', 'https://mega.nz/file/TldlTZID#1A90Wn8xYloDvekX8rQewI3Yh8HMJXlufRUEWEcOzNU', '1'),
(2, 'libro typescript', 'https://mega.nz/file/TldlTZID#1A90Wn8xYloDvekX8rQewI3Yh8HMJXlufRUEWEcOzNU', '2'),
(3, 'libro ts', 'https://mega.nz/file/TldlTZID#1A90Wn8xYloDvekX8rQewI3Yh8HMJXlufRUEWEcOzNU', '2.5');

-- --------------------------------------------------------

CREATE TABLE `tipocomentario` (
  `id` bigint(20) primary key auto_increment,
  `tipo` varchar(250) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `tipocomentario` (`id`, `tipo`) VALUES
(1, 'aportes'),
(2, 'preguntas');

-- --------------------------------------------------------

CREATE TABLE `tipousuario` (
  `id` bigint(20) primary key auto_increment,
  `nombre` varchar(250) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `tipousuario` (`id`, `nombre`) VALUES
(1, 'ADMIN'),
(2, 'USER'),
(3, 'VISITOR');

-- --------------------------------------------------------

CREATE TABLE `usuario` (
  `id` bigint(20) primary key auto_increment,
  `nombre` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `dni` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `apellido1` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `apellido2` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `nickname` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `pass` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `id_tipousuario` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `usuario` (`id`, `nombre`, `dni`, `apellido1`, `apellido2`, `nickname`, `pass`, `email`, `id_tipousuario`) VALUES
(4, 'jose', '32345678L', 'perez', 'garcia', 'jperez', '4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64', 'jose@gmail.com', 2),
(5, 'juan', '42345678M', 'gomez', 'garcia', 'jgomezs', '4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64', 'juan@gmail.com', 2),
(6, 'maria', '52345678N', 'garcia', 'gomez', 'mgomez', '4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64', 'maria@gmail.com', 2),
(9, 'juana', '11111111W', 'admin', 'administrador', 'admin', '4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64', 'pepe@pepe112.com', 1),
(10, 'ximos', '22222222E', 'solers', 'benavents', 'ximo', '4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64', 'ximo@gmail.csom', 2),
(11, 'fernando', '32345378L', 'perezo', 'garcio', 'jperez', '4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64', 'jose@gmail.com', 2),
(14, 'Cristina', '73993548H', 'Vilar', 'Blayimir', 'Cristina_Vilar', '4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64', 'Vilar_Cristina@daw.tk', 2),
(16, 'Alvaro', '55275381X', 'Blanco', 'Torres', 'Alvaro_Blanco', '4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64', 'Blanco_Alvaro@daw.tk', 2),
(17, 'joses', 'aaaaas', 'perezos', 'ssss', 'aaaaaa', '4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64', 'elliot@gmail.coms', 2);

-- --------------------------------------------------------

CREATE TABLE `usuario_curso` (
  `id` bigint(20) primary key auto_increment,
  `id_usuario` bigint(20) NOT NULL,
  `id_curso` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `usuario_curso` (`id`, `id_usuario`, `id_curso`) VALUES
(1, 4, 1);