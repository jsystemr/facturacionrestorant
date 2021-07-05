-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.61


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema bdrestaurante
--

CREATE DATABASE IF NOT EXISTS bdrestaurante;
USE bdrestaurante;

--
-- Temporary table structure for view `facturasabiertas`
--
DROP TABLE IF EXISTS `facturasabiertas`;
DROP VIEW IF EXISTS `facturasabiertas`;
CREATE TABLE `facturasabiertas` (
  `idFactura` int(8) unsigned zerofill,
  `fecha` date,
  `hora` time,
  `efectivo` decimal(10,2),
  `notas` varchar(255),
  `nrotarjeta` int(11),
  `tipofact` int(11),
  `idorden` int(11),
  `empresa` varchar(8),
  `usuario` varchar(20),
  `estadofact` int(11),
  `cliente` varchar(15),
  `totpag` double(19,2)
);

--
-- Temporary table structure for view `reportefacturas`
--
DROP TABLE IF EXISTS `reportefacturas`;
DROP VIEW IF EXISTS `reportefacturas`;
CREATE TABLE `reportefacturas` (
  `cantidad` int(5),
  `precio` float(10,2),
  `subtotal` double(19,2),
  `producto` varchar(8),
  `cliente` varchar(15),
  `idorden` int(11),
  `idFactura` int(8) unsigned zerofill,
  `fecha` date,
  `descrip` varchar(45),
  `decripcion` varchar(45),
  `descripcion` varchar(55),
  `descri` varchar(45),
  `nombre` varchar(45),
  `direccion` varchar(200)
);

--
-- Definition of table `cargo`
--

DROP TABLE IF EXISTS `cargo`;
CREATE TABLE `cargo` (
  `idCargo` int(11) NOT NULL,
  `descrip` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`idCargo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `cargo`
--

/*!40000 ALTER TABLE `cargo` DISABLE KEYS */;
INSERT INTO `cargo` (`idCargo`,`descrip`) VALUES 
 (1,0x676572656E7465);
/*!40000 ALTER TABLE `cargo` ENABLE KEYS */;


--
-- Definition of table `categoriaproducto`
--

DROP TABLE IF EXISTS `categoriaproducto`;
CREATE TABLE `categoriaproducto` (
  `idCategoria` varchar(8) COLLATE utf8_bin NOT NULL,
  `descrip` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `categoriaproducto`
--

/*!40000 ALTER TABLE `categoriaproducto` DISABLE KEYS */;
INSERT INTO `categoriaproducto` (`idCategoria`,`descrip`) VALUES 
 (0x31,0x434F4D494441),
 (0x32,0x424542494441),
 (0x33,0x504F5354524553);
/*!40000 ALTER TABLE `categoriaproducto` ENABLE KEYS */;


--
-- Definition of table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente` (
  `idCliente` varchar(15) COLLATE utf8_bin NOT NULL,
  `nombre` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `telefono` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `direccion` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `celular` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `tipocliente` int(11) NOT NULL,
  `rtncli` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`idCliente`),
  KEY `fk_cliente_tipocliente1` (`tipocliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Tabla que almacena toda la informacion de los clientes.';

--
-- Dumping data for table `cliente`
--

/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` (`idCliente`,`nombre`,`telefono`,`direccion`,`celular`,`tipocliente`,`rtncli`) VALUES 
 (0x30353031,0x6A75616E206361726C6F732072616D6F73,0x3333353039333230,'',NULL,0,''),
 (0x32,0x6D65736132,0x333333333333,0x707275656261,NULL,0,''),
 (0x393939,0x436F6E73756D69646F722046696E616C,0x30,'',NULL,0,''),
 (0x676572736F6E,0x476572736F6E205472656A6F,0x30,'',NULL,0,'');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;


--
-- Definition of table `correlativossar`
--

DROP TABLE IF EXISTS `correlativossar`;
CREATE TABLE `correlativossar` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fechacreacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fechaini` date DEFAULT NULL,
  `fechafin` date DEFAULT NULL,
  `rangoini` int(8) unsigned zerofill NOT NULL,
  `rangofin` int(8) unsigned zerofill NOT NULL,
  `formatonumero` varchar(65) NOT NULL,
  `cai` varchar(65) NOT NULL,
  `estado` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `correlativossar`
--

/*!40000 ALTER TABLE `correlativossar` DISABLE KEYS */;
INSERT INTO `correlativossar` (`id`,`fechacreacion`,`fechaini`,`fechafin`,`rangoini`,`rangofin`,`formatonumero`,`cai`,`estado`) VALUES 
 (1,'2018-07-23 23:09:57','2018-01-20','2019-02-06',00000001,00034000,'000-001-01','491A74-C62BFF-0A4A80-EB7E09-516DA3-5D',0x01);
/*!40000 ALTER TABLE `correlativossar` ENABLE KEYS */;


--
-- Definition of table `departamento`
--

DROP TABLE IF EXISTS `departamento`;
CREATE TABLE `departamento` (
  `idDepartamento` int(11) NOT NULL,
  `nombre` varchar(65) COLLATE utf8_bin DEFAULT NULL,
  `idpais` varchar(3) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`idDepartamento`,`idpais`),
  KEY `Pasi_departamento` (`idpais`),
  CONSTRAINT `Pasi_departamento` FOREIGN KEY (`idpais`) REFERENCES `pais` (`idPais`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `departamento`
--

/*!40000 ALTER TABLE `departamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `departamento` ENABLE KEYS */;


--
-- Definition of table `detallefactura`
--

DROP TABLE IF EXISTS `detallefactura`;
CREATE TABLE `detallefactura` (
  `nrolinea` int(11) NOT NULL AUTO_INCREMENT,
  `nomproducto` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `cantidad` int(5) DEFAULT '1',
  `precio` float(10,2) DEFAULT NULL,
  `comentarios` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `idFactura` int(10) NOT NULL,
  `fecha` date NOT NULL,
  `producto` varchar(8) COLLATE utf8_bin NOT NULL,
  `stadodet` int(10) unsigned DEFAULT '0',
  `descuento` decimal(10,2) DEFAULT '0.00',
  `isv` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`nrolinea`,`idFactura`,`fecha`),
  KEY `fk_detall_fac` (`idFactura`,`fecha`),
  KEY `fk_detall_prod` (`producto`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `detallefactura`
--

/*!40000 ALTER TABLE `detallefactura` DISABLE KEYS */;
INSERT INTO `detallefactura` (`nrolinea`,`nomproducto`,`cantidad`,`precio`,`comentarios`,`idFactura`,`fecha`,`producto`,`stadodet`,`descuento`,`isv`) VALUES 
 (1,0x70726F647563746F20646520707275656261,1,43.48,'',1,'2018-09-23',0x707275656261,1,'0.00','6.52'),
 (1,0x636170756363696E6F,1,34.78,'',2,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',3,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,5,34.78,'',4,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',5,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',6,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,3,34.78,'',7,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',8,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',9,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,2,34.78,'',10,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,2,34.78,0x5C,11,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',12,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',13,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',14,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',15,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',16,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,2,34.78,'',17,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',18,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',19,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,3,34.78,'',20,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,2,34.78,'',21,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,2,34.78,'',22,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',23,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',25,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',26,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',27,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',28,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,3,34.78,'',29,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,2,34.78,'',30,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',31,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',32,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',33,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',34,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',35,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',36,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',37,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,3,34.78,'',38,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',39,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',40,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',41,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',42,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',43,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,3,34.78,'',44,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,4,34.78,'',45,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,5,34.78,'',46,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',47,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',48,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',49,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,6,34.78,'',50,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',51,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',52,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',53,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',54,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,2,34.78,'',55,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x636170756363696E6F,1,34.78,'',56,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (1,0x6672656E6F732064656C616E7465726F73206465206D6F746F,1,78.26,'',57,'2018-10-03',0x6672656E6F73,1,'0.00','11.74'),
 (1,0x6672656E6F732064656C616E7465726F73206465206D6F746F,1,78.26,'',58,'2018-10-03',0x6672656E6F73,1,'0.00','11.74'),
 (1,0x4D616E6F206465206F62726120706F722063616D62696F20646520616365697465,1,86.96,'',59,'2018-10-03',0x6D616E6F6F627261,1,'0.00','13.04'),
 (2,0x636170756363696E6F,2,34.78,'',3,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (2,0x636170756363696E6F,5,34.78,'',4,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (2,0x636170756363696E6F,1,34.78,'',5,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (2,0x636170756363696E6F,1,34.78,'',6,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (2,0x636170756363696E6F,3,34.78,'',7,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (2,0x636170756363696E6F,1,34.78,'',32,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (2,0x636170756363696E6F,1,34.78,'',33,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (2,0x636170756363696E6F,1,34.78,'',35,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (2,0x636170756363696E6F,1,34.78,'',37,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (2,0x636170756363696E6F,1,34.78,'',39,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (2,0x636170756363696E6F,1,34.78,'',40,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (2,0x636170756363696E6F,1,34.78,'',41,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (2,0x636170756363696E6F,1,34.78,'',51,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (3,0x636170756363696E6F,1,34.78,'',5,'2018-09-23',0x6361666531,1,'0.00','5.22'),
 (3,0x636170756363696E6F,2,34.78,'',32,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (3,0x636170756363696E6F,1,34.78,'',33,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (3,0x636170756363696E6F,1,34.78,'',35,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (3,0x636170756363696E6F,1,34.78,'',40,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (3,0x636170756363696E6F,1,34.78,'',41,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (3,0x636170756363696E6F,1,34.78,'',51,'2018-10-02',0x6361666531,1,'0.00','5.22'),
 (4,0x636170756363696E6F,1,34.78,'',41,'2018-10-02',0x6361666531,1,'0.00','5.22');
/*!40000 ALTER TABLE `detallefactura` ENABLE KEYS */;


--
-- Definition of table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
CREATE TABLE `empleado` (
  `idempleado` decimal(10,0) NOT NULL,
  `nombre` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `telefono` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `direccion` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `usu` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `idCargo` int(11) NOT NULL,
  PRIMARY KEY (`idempleado`),
  KEY `fk_empleado_cargo1` (`idCargo`),
  CONSTRAINT `fk_empleado_cargo1` FOREIGN KEY (`idCargo`) REFERENCES `cargo` (`idCargo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `empleado`
--

/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;


--
-- Definition of table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
CREATE TABLE `empresa` (
  `idEmpresa` varchar(8) COLLATE utf8_bin NOT NULL,
  `nombre` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `eslogan` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `telefono` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `fax` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `web` varchar(55) COLLATE utf8_bin DEFAULT NULL,
  `correo` varchar(65) COLLATE utf8_bin DEFAULT NULL,
  `rtn` varchar(18) COLLATE utf8_bin DEFAULT NULL,
  `direccion` varchar(65) COLLATE utf8_bin DEFAULT NULL,
  `estado` smallint(6) DEFAULT '0',
  PRIMARY KEY (`idEmpresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `empresa`
--

/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` (`idEmpresa`,`nombre`,`eslogan`,`telefono`,`fax`,`web`,`correo`,`rtn`,`direccion`,`estado`) VALUES 
 (0x43414645,0x43414645544552494120454C2043454E54524F,0x454C2043414645204D4153205249434F20444520484F4E4455524153,0x3237373333333333,NULL,0x68747470733A2F2F7777772E66616365626F6F6B2E636F6D2F43414645,0x70697A7A6572696163616E746F6E65733230313840676D61696C2E636F6D,0x3033303131393837303238313139,0x426F2E20456C204361726D656E206176656E2E204761627269656C61204E75C3B1657A2C20456469662E20706173656F2046616D696C696172,1);
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;


--
-- Definition of table `encabezados`
--

DROP TABLE IF EXISTS `encabezados`;
CREATE TABLE `encabezados` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `empresa` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `estado` smallint(5) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_encabezados_empresa` (`empresa`),
  CONSTRAINT `FK_encabezados_empresa` FOREIGN KEY (`empresa`) REFERENCES `empresa` (`idEmpresa`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `encabezados`
--

/*!40000 ALTER TABLE `encabezados` DISABLE KEYS */;
INSERT INTO `encabezados` (`id`,`empresa`,`estado`) VALUES 
 (1,0x43414645,1);
/*!40000 ALTER TABLE `encabezados` ENABLE KEYS */;


--
-- Definition of table `estadofact`
--

DROP TABLE IF EXISTS `estadofact`;
CREATE TABLE `estadofact` (
  `idestadofact` int(11) NOT NULL,
  `descrip` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`idestadofact`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `estadofact`
--

/*!40000 ALTER TABLE `estadofact` DISABLE KEYS */;
INSERT INTO `estadofact` (`idestadofact`,`descrip`) VALUES 
 (1,0x41424945525441),
 (2,0x43455252414441);
/*!40000 ALTER TABLE `estadofact` ENABLE KEYS */;


--
-- Definition of table `factura`
--

DROP TABLE IF EXISTS `factura`;
CREATE TABLE `factura` (
  `idFactura` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `hora` time DEFAULT NULL,
  `efectivo` decimal(10,2) DEFAULT NULL,
  `notas` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `nrotarjeta` int(11) DEFAULT NULL,
  `tipofact` int(11) DEFAULT NULL,
  `idorden` int(11) DEFAULT NULL,
  `empresa` varchar(8) COLLATE utf8_bin NOT NULL,
  `usuario` varchar(20) COLLATE utf8_bin NOT NULL,
  `estadofact` int(11) NOT NULL,
  `cliente` varchar(15) COLLATE utf8_bin NOT NULL,
  `totalfac` decimal(15,2) DEFAULT NULL,
  PRIMARY KEY (`idFactura`,`fecha`),
  KEY `Fact_orden` (`idorden`),
  KEY `Fact_tipfact` (`tipofact`),
  KEY `fk_factura_empresa1` (`empresa`),
  KEY `fk_factura_usuario1` (`usuario`),
  KEY `fk_factura_estadofact1` (`estadofact`),
  KEY `fk_factura_cliente_idx` (`cliente`),
  CONSTRAINT `Fact_orden` FOREIGN KEY (`idorden`) REFERENCES `tiporden` (`idtiporden`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Fact_tipfact` FOREIGN KEY (`tipofact`) REFERENCES `tipofactura` (`idTipoFactura`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_factura_cliente` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`idCliente`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_factura_empresa1` FOREIGN KEY (`empresa`) REFERENCES `empresa` (`idEmpresa`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_factura_estadofact1` FOREIGN KEY (`estadofact`) REFERENCES `estadofact` (`idestadofact`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_factura_usuario1` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`login`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `factura`
--

/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
INSERT INTO `factura` (`idFactura`,`fecha`,`hora`,`efectivo`,`notas`,`nrotarjeta`,`tipofact`,`idorden`,`empresa`,`usuario`,`estadofact`,`cliente`,`totalfac`) VALUES 
 (00000001,'2018-09-23',NULL,'50.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'50.00'),
 (00000002,'2018-09-23',NULL,'40.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x30353031,'40.00'),
 (00000003,'2018-09-23',NULL,'200.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'120.00'),
 (00000004,'2018-09-23',NULL,'500.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'400.00'),
 (00000005,'2018-09-23',NULL,'50.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'120.00'),
 (00000006,'2018-09-23',NULL,'50.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'80.00'),
 (00000007,'2018-09-23',NULL,'200.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'240.00'),
 (00000008,'2018-09-23',NULL,'50.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000009,'2018-09-23',NULL,NULL,NULL,NULL,1,1,0x43414645,0x6A75616E,1,0x393939,'40.00'),
 (00000010,'2018-09-23',NULL,'80.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'80.00'),
 (00000011,'2018-09-23',NULL,NULL,NULL,NULL,1,1,0x43414645,0x6A75616E,1,0x393939,'80.00'),
 (00000012,'2018-09-23',NULL,'40.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000013,'2018-09-23',NULL,NULL,NULL,NULL,1,1,0x43414645,0x6A75616E,1,0x393939,'40.00'),
 (00000014,'2018-09-23',NULL,NULL,NULL,NULL,1,1,0x43414645,0x6A75616E,1,0x393939,'40.00'),
 (00000015,'2018-09-23',NULL,NULL,NULL,NULL,1,1,0x43414645,0x6A75616E,1,0x393939,'40.00'),
 (00000016,'2018-09-23',NULL,'50.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000017,'2018-09-23',NULL,'80.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'80.00'),
 (00000018,'2018-09-23',NULL,NULL,NULL,NULL,1,1,0x43414645,0x6A75616E,1,0x393939,'40.00'),
 (00000019,'2018-09-23',NULL,NULL,NULL,NULL,1,1,0x43414645,0x6A75616E,1,0x393939,'40.00'),
 (00000020,'2018-09-23',NULL,'120.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'120.00'),
 (00000021,'2018-09-23',NULL,'89.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'80.00'),
 (00000022,'2018-09-23',NULL,NULL,NULL,NULL,1,1,0x43414645,0x6A75616E,1,0x393939,'80.00'),
 (00000023,'2018-09-23',NULL,NULL,NULL,NULL,1,1,0x43414645,0x6A75616E,1,0x393939,'40.00'),
 (00000024,'2018-09-23',NULL,NULL,NULL,NULL,1,1,0x43414645,0x6A75616E,1,0x393939,'0.00'),
 (00000025,'2018-09-23',NULL,NULL,NULL,NULL,1,1,0x43414645,0x6A75616E,1,0x393939,'40.00'),
 (00000026,'2018-09-23',NULL,'50.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000027,'2018-10-02',NULL,'50.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000028,'2018-10-02',NULL,'40.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000029,'2018-10-02',NULL,'120.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'120.00'),
 (00000030,'2018-10-02',NULL,'100.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'80.00'),
 (00000031,'2018-10-02',NULL,'50.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000032,'2018-10-02',NULL,'200.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'160.00'),
 (00000033,'2018-10-02',NULL,'200.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'120.00'),
 (00000034,'2018-10-02',NULL,'50.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000035,'2018-10-02',NULL,'200.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'120.00'),
 (00000036,'2018-10-02',NULL,'50.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000037,'2018-10-02',NULL,'90.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'80.00'),
 (00000038,'2018-10-02',NULL,'200.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'120.00'),
 (00000039,'2018-10-02',NULL,'80.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'80.00'),
 (00000040,'2018-10-02',NULL,'90.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'80.00'),
 (00000041,'2018-10-02',NULL,'200.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'160.00'),
 (00000042,'2018-10-02',NULL,'40.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000043,'2018-10-02',NULL,'50.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000044,'2018-10-02',NULL,'150.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'120.00'),
 (00000045,'2018-10-02',NULL,'200.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'160.00'),
 (00000046,'2018-10-02',NULL,'200.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'200.00'),
 (00000047,'2018-10-02',NULL,'60.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000048,'2018-10-02',NULL,'50.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000049,'2018-10-02',NULL,'50.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000050,'2018-10-02',NULL,'300.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'240.00'),
 (00000051,'2018-10-02',NULL,'100.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'80.00'),
 (00000052,'2018-10-02',NULL,'40.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000053,'2018-10-02',NULL,'40.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000054,'2018-10-02',NULL,NULL,NULL,NULL,1,1,0x43414645,0x6A75616E,1,0x393939,'40.00'),
 (00000055,'2018-10-02',NULL,'90.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'80.00'),
 (00000056,'2018-10-02',NULL,'50.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'40.00'),
 (00000057,'2018-10-03',NULL,'90.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x32,'90.00'),
 (00000058,'2018-10-03',NULL,'90.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x32,'90.00'),
 (00000059,'2018-10-03',NULL,'100.00',NULL,NULL,1,1,0x43414645,0x6A75616E,2,0x393939,'100.00');
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;


--
-- Definition of table `historicaproductos`
--

DROP TABLE IF EXISTS `historicaproductos`;
CREATE TABLE `historicaproductos` (
  `fecha` date NOT NULL,
  `producto` varchar(25) NOT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  `estado` smallint(5) unsigned DEFAULT NULL,
  `tipo` varchar(25) DEFAULT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `historicaproductos`
--

/*!40000 ALTER TABLE `historicaproductos` DISABLE KEYS */;
INSERT INTO `historicaproductos` (`fecha`,`producto`,`cantidad`,`descripcion`,`estado`,`tipo`,`id`) VALUES 
 ('2018-09-15','',-1,NULL,1,'salida',1),
 ('2018-09-15','prueba',0,NULL,1,'ingreso',2),
 ('2018-09-15','prueba2',10,NULL,1,'ingreso',3),
 ('2018-09-15','prueba2',-1,NULL,1,'salida',4),
 ('2018-09-15','12',-1,NULL,1,'salida',5),
 ('2018-09-23','prueba',100,NULL,1,'ingreso',6),
 ('2018-09-23','prueba',-1,NULL,1,'salida',7),
 ('2018-09-23','cafe1',0,NULL,1,'ingreso',8),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',9),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',10),
 ('2018-09-23','cafe1',-5,NULL,1,'salida',11),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',12),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',13),
 ('2018-09-23','cafe1',-3,NULL,1,'salida',14),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',15),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',16),
 ('2018-09-23','cafe1',-2,NULL,1,'salida',17),
 ('2018-09-23','cafe1',-2,NULL,1,'salida',18),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',19),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',20),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',21),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',22),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',23),
 ('2018-09-23','cafe1',-2,NULL,1,'salida',24),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',25),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',26),
 ('2018-09-23','cafe1',-3,NULL,1,'salida',27),
 ('2018-09-23','cafe1',-2,NULL,1,'salida',28),
 ('2018-09-23','cafe1',-2,NULL,1,'salida',29),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',30),
 ('2018-09-23','cafe1',-1,NULL,1,'salida',31),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',32),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',33),
 ('2018-10-02','cafe1',-3,NULL,1,'salida',34),
 ('2018-10-02','cafe1',-2,NULL,1,'salida',35),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',36),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',37),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',38),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',39),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',40),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',41),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',42),
 ('2018-10-02','cafe1',-3,NULL,1,'salida',43),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',44),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',45),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',46),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',47),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',48),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',49),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',50),
 ('2018-10-02','cafe1',-3,NULL,1,'salida',51),
 ('2018-10-02','cafe1',-4,NULL,1,'salida',52),
 ('2018-10-02','cafe1',-5,NULL,1,'salida',53),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',54),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',55),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',56),
 ('2018-10-02','cafe1',-6,NULL,1,'salida',57),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',58),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',59),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',60),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',61),
 ('2018-10-02','cafe1',-2,NULL,1,'salida',62),
 ('2018-10-02','cafe1',-1,NULL,1,'salida',63),
 ('2018-10-03','frenos',100,NULL,1,'ingreso',64),
 ('2018-10-03','frenos',-1,NULL,1,'salida',65),
 ('2018-10-03','frenos',-1,NULL,1,'salida',66),
 ('2018-10-03','manoobra',0,NULL,1,'ingreso',67),
 ('2018-10-03','manoobra',-1,NULL,1,'salida',68);
/*!40000 ALTER TABLE `historicaproductos` ENABLE KEYS */;


--
-- Definition of table `historicorrelativo`
--

DROP TABLE IF EXISTS `historicorrelativo`;
CREATE TABLE `historicorrelativo` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nrofactura` int(8) unsigned zerofill NOT NULL,
  `idcorrelativo` int(10) unsigned NOT NULL,
  `fechacreacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `estado` smallint(5) unsigned NOT NULL DEFAULT '0',
  `cai` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `historicorrelativo`
--

/*!40000 ALTER TABLE `historicorrelativo` DISABLE KEYS */;
/*!40000 ALTER TABLE `historicorrelativo` ENABLE KEYS */;


--
-- Definition of table `impuestos`
--

DROP TABLE IF EXISTS `impuestos`;
CREATE TABLE `impuestos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `impuesto` decimal(15,2) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `impuestos`
--

/*!40000 ALTER TABLE `impuestos` DISABLE KEYS */;
INSERT INTO `impuestos` (`id`,`impuesto`,`descripcion`) VALUES 
 (1,'0.15','impuestos 15%'),
 (2,'0.00','0');
/*!40000 ALTER TABLE `impuestos` ENABLE KEYS */;


--
-- Definition of table `invevntarioproductos`
--

DROP TABLE IF EXISTS `invevntarioproductos`;
CREATE TABLE `invevntarioproductos` (
  `idInven` int(11) NOT NULL,
  `cantidadexis` float(10,2) DEFAULT NULL,
  `cantidadpedido` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `fecha` date NOT NULL,
  `producto` varchar(8) COLLATE utf8_bin NOT NULL,
  `categoria` varchar(8) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`idInven`,`fecha`),
  KEY `fk_invevntarioproductos_producto1` (`producto`,`categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `invevntarioproductos`
--

/*!40000 ALTER TABLE `invevntarioproductos` DISABLE KEYS */;
/*!40000 ALTER TABLE `invevntarioproductos` ENABLE KEYS */;


--
-- Definition of table `pais`
--

DROP TABLE IF EXISTS `pais`;
CREATE TABLE `pais` (
  `idPais` varchar(3) COLLATE utf8_bin NOT NULL,
  `pais` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`idPais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `pais`
--

/*!40000 ALTER TABLE `pais` DISABLE KEYS */;
/*!40000 ALTER TABLE `pais` ENABLE KEYS */;


--
-- Definition of table `producto`
--

DROP TABLE IF EXISTS `producto`;
CREATE TABLE `producto` (
  `idProducto` varchar(8) COLLATE utf8_bin NOT NULL,
  `nombre` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `descripcion` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `precio` float(10,2) DEFAULT NULL,
  `costo` float(10,2) DEFAULT '0.00',
  `categoria` varchar(8) COLLATE utf8_bin NOT NULL DEFAULT '1',
  `cantidadexis` int(10) NOT NULL,
  `imgruta` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `impuesto` decimal(5,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`idProducto`),
  KEY `FK_producto_cate` (`categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `producto`
--

/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` (`idProducto`,`nombre`,`descripcion`,`precio`,`costo`,`categoria`,`cantidadexis`,`imgruta`,`impuesto`) VALUES 
 (0x6361666531,0x636170756363696E6F,NULL,40.00,NULL,0x32,-114,NULL,'0.15'),
 (0x6672656E6F73,0x6672656E6F732064656C616E7465726F73206465206D6F746F,NULL,90.00,NULL,0x31,98,NULL,'0.15'),
 (0x6D616E6F6F627261,0x4D616E6F206465206F62726120706F722063616D62696F20646520616365697465,NULL,100.00,NULL,0x32,-1,NULL,'0.15'),
 (0x707275656261,0x70726F647563746F20646520707275656261,NULL,50.00,NULL,0x32,99,NULL,'0.15');
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;


--
-- Definition of table `puntosclientes`
--

DROP TABLE IF EXISTS `puntosclientes`;
CREATE TABLE `puntosclientes` (
  `idpuntos` int(11) NOT NULL AUTO_INCREMENT,
  `cliente` varchar(15) DEFAULT NULL,
  `factura` int(11) DEFAULT NULL,
  `totpago` decimal(15,2) DEFAULT '0.00',
  `puntos` decimal(15,2) DEFAULT '0.00',
  `puntosacumulados` decimal(15,2) DEFAULT '0.00',
  `estado` smallint(1) unsigned DEFAULT '0',
  `creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpuntos`),
  UNIQUE KEY `factura_UNIQUE` (`factura`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `puntosclientes`
--

/*!40000 ALTER TABLE `puntosclientes` DISABLE KEYS */;
INSERT INTO `puntosclientes` (`idpuntos`,`cliente`,`factura`,`totpago`,`puntos`,`puntosacumulados`,`estado`,`creacion`) VALUES 
 (1,'27732180',1,'615.00','24.60','24.60',0,'2015-11-20 12:31:58'),
 (2,'1',3,'1465.00','58.60','58.60',0,'2015-11-20 15:13:23'),
 (3,'27732180',16,'1275.00','51.00','75.60',0,'2016-01-12 10:04:00'),
 (4,'2',17,'400.00','16.00','16.00',0,'2016-02-11 20:59:35'),
 (5,'27732180',22,'250.00','10.00','85.60',0,'2018-01-21 17:21:25'),
 (6,'27732180',23,'125.00','5.00','90.60',0,'2018-01-21 17:34:44'),
 (9,'27732180',24,'460.00','18.40','109.00',0,'2018-01-21 19:59:00'),
 (10,'27732180',25,'680.00','27.20','136.20',0,'2018-01-23 20:19:10'),
 (11,'27732180',27,'1375.50','55.02','191.22',0,'2018-02-15 20:22:27'),
 (12,'27732180',28,'350.00','14.00','205.22',0,'2018-02-23 18:56:32'),
 (13,'27732180',29,'450.00','18.00','223.22',0,'2018-02-23 19:18:23'),
 (14,'27732180',30,'450.00','18.00','241.22',0,'2018-02-23 19:23:32'),
 (15,'27732180',31,'450.00','18.00','259.22',0,'2018-02-23 19:26:56'),
 (16,'27732180',32,'225.00','9.00','268.22',0,'2018-02-23 19:56:28'),
 (17,'27732180',33,'225.00','9.00','277.22',0,'2018-02-23 20:00:23'),
 (18,'27732180',35,'230.00','9.20','286.42',0,'2018-07-22 12:51:35');
/*!40000 ALTER TABLE `puntosclientes` ENABLE KEYS */;


--
-- Definition of table `puntosformula`
--

DROP TABLE IF EXISTS `puntosformula`;
CREATE TABLE `puntosformula` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `valor` decimal(5,2) DEFAULT '0.00',
  `estado` smallint(1) unsigned DEFAULT '0',
  `obs` varchar(245) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `puntosformula`
--

/*!40000 ALTER TABLE `puntosformula` DISABLE KEYS */;
/*!40000 ALTER TABLE `puntosformula` ENABLE KEYS */;


--
-- Definition of table `registrocanje`
--

DROP TABLE IF EXISTS `registrocanje`;
CREATE TABLE `registrocanje` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cliente` varchar(15) DEFAULT '0',
  `observaciones` varchar(45) DEFAULT '0',
  `puntoscanjeados` int(11) DEFAULT NULL,
  `saldopuntos` int(11) DEFAULT NULL,
  `producto` varchar(8) DEFAULT NULL,
  `nombrecliente` varchar(75) DEFAULT NULL,
  `nombreproducto` varchar(100) DEFAULT NULL,
  `fechacanje` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `registrocanje`
--

/*!40000 ALTER TABLE `registrocanje` DISABLE KEYS */;
INSERT INTO `registrocanje` (`id`,`cliente`,`observaciones`,`puntoscanjeados`,`saldopuntos`,`producto`,`nombrecliente`,`nombreproducto`,`fechacanje`) VALUES 
 (1,'100','',5000,902,'TEST','','TEST','2015-11-17 19:56:28'),
 (2,'27731322','se canjearon todos los puntos',2698,0,'TEST','','TEST','2015-11-17 20:12:23'),
 (3,'1','se canjearon la mayoria de puntos solo quedan',11000,270,'TEST','','TEST','2015-11-17 20:18:58'),
 (4,'2','se canjearon 10 mil puntos',10000,270,'TEST','','TEST','2015-11-17 20:25:25'),
 (5,'3','se canjeo 300',300,304,'TEST','','TEST','2015-11-17 20:42:29'),
 (6,'27732180','puntos canjeados 4 mil restan 837.64',4000,838,'TEST','','TEST','2015-11-17 21:15:36'),
 (7,'100','Cambio mil puntos llevo 1 combo test',1000,302,'TEST','','TEST','2015-11-19 13:33:09');
/*!40000 ALTER TABLE `registrocanje` ENABLE KEYS */;


--
-- Definition of table `tipocliente`
--

DROP TABLE IF EXISTS `tipocliente`;
CREATE TABLE `tipocliente` (
  `idTipoCliente` int(11) NOT NULL,
  `decripcion` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`idTipoCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `tipocliente`
--

/*!40000 ALTER TABLE `tipocliente` DISABLE KEYS */;
INSERT INTO `tipocliente` (`idTipoCliente`,`decripcion`) VALUES 
 (1,0x47454E455249434F);
/*!40000 ALTER TABLE `tipocliente` ENABLE KEYS */;


--
-- Definition of table `tipofactura`
--

DROP TABLE IF EXISTS `tipofactura`;
CREATE TABLE `tipofactura` (
  `idTipoFactura` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(55) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`idTipoFactura`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `tipofactura`
--

/*!40000 ALTER TABLE `tipofactura` DISABLE KEYS */;
INSERT INTO `tipofactura` (`idTipoFactura`,`descripcion`) VALUES 
 (1,0x454645435449564F),
 (2,0x5441524A455441),
 (3,0x4E554C41);
/*!40000 ALTER TABLE `tipofactura` ENABLE KEYS */;


--
-- Definition of table `tiporden`
--

DROP TABLE IF EXISTS `tiporden`;
CREATE TABLE `tiporden` (
  `idtiporden` int(11) NOT NULL,
  `descri` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`idtiporden`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `tiporden`
--

/*!40000 ALTER TABLE `tiporden` DISABLE KEYS */;
INSERT INTO `tiporden` (`idtiporden`,`descri`) VALUES 
 (1,0x434F4D4552),
 (2,0x4C4C45564152),
 (3,0x41444F4D4943494C494F);
/*!40000 ALTER TABLE `tiporden` ENABLE KEYS */;


--
-- Definition of table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `login` varchar(20) COLLATE utf8_bin NOT NULL,
  `clave` varchar(25) COLLATE utf8_bin NOT NULL,
  `nivel` int(11) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `usuario`
--

/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`login`,`clave`,`nivel`) VALUES 
 (0x63616A61,0x313233,2),
 (0x6A75616E,0x313233,1),
 (0x77656E6479,0x38393839,1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;


--
-- Definition of function `ultimosPuntosCliente`
--

DROP FUNCTION IF EXISTS `ultimosPuntosCliente`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `ultimosPuntosCliente`(varcli int) RETURNS decimal(15,2)
BEGIN
declare puntos decimal(15,2);
if isnull(varcli) then
	set puntos=0;
else
	set puntos:=(SELECT puntos FROM puntosclientes where cliente=varcli and idpuntos=(select max(idpuntos) from puntosclientes where cliente=varcli));
end if;
return puntos;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `facturasiguiente`
--

DROP PROCEDURE IF EXISTS `facturasiguiente`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `facturasiguiente`(in t varchar(15))
BEGIN
select max(idFactura)+1 as idFactura from factura where empresa=t;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `ultimafactura`
--

DROP PROCEDURE IF EXISTS `ultimafactura`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ultimafactura`(in t varchar(15))
BEGIN
select max(idFactura) as idFactura from factura where empresa=t;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `verfacturasabiertas`
--

DROP PROCEDURE IF EXISTS `verfacturasabiertas`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `verfacturasabiertas`()
BEGIN
  
  select * from facturasabiertas;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of view `facturasabiertas`
--

DROP TABLE IF EXISTS `facturasabiertas`;
DROP VIEW IF EXISTS `facturasabiertas`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `facturasabiertas` AS select `f`.`idFactura` AS `idFactura`,`f`.`fecha` AS `fecha`,`f`.`hora` AS `hora`,`f`.`efectivo` AS `efectivo`,`f`.`notas` AS `notas`,`f`.`nrotarjeta` AS `nrotarjeta`,`f`.`tipofact` AS `tipofact`,`f`.`idorden` AS `idorden`,`f`.`empresa` AS `empresa`,`f`.`usuario` AS `usuario`,`f`.`estadofact` AS `estadofact`,`f`.`cliente` AS `cliente`,(select sum((`detallefactura`.`cantidad` * `detallefactura`.`precio`)) from `detallefactura` where ((`detallefactura`.`idFactura` = `f`.`idFactura`) and (`detallefactura`.`fecha` = `f`.`fecha`))) AS `totpag` from `factura` `f`;

--
-- Definition of view `reportefacturas`
--

DROP TABLE IF EXISTS `reportefacturas`;
DROP VIEW IF EXISTS `reportefacturas`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `reportefacturas` AS select `detallefactura`.`cantidad` AS `cantidad`,`detallefactura`.`precio` AS `precio`,sum((((`detallefactura`.`cantidad` * `detallefactura`.`precio`) - `detallefactura`.`descuento`) + `detallefactura`.`isv`)) AS `subtotal`,`detallefactura`.`producto` AS `producto`,`factura`.`cliente` AS `cliente`,`factura`.`idorden` AS `idorden`,`factura`.`idFactura` AS `idFactura`,`factura`.`fecha` AS `fecha`,`estadofact`.`descrip` AS `descrip`,`tipocliente`.`decripcion` AS `decripcion`,`tipofactura`.`descripcion` AS `descripcion`,`tiporden`.`descri` AS `descri`,`cliente`.`nombre` AS `nombre`,`cliente`.`direccion` AS `direccion` from ((((((`detallefactura` join `factura` on(((`detallefactura`.`idFactura` = `factura`.`idFactura`) and (`detallefactura`.`fecha` = `factura`.`fecha`)))) join `estadofact` on((`factura`.`estadofact` = `estadofact`.`idestadofact`))) join `tipofactura` on((`factura`.`tipofact` = `tipofactura`.`idTipoFactura`))) join `tiporden` on((`factura`.`idorden` = `tiporden`.`idtiporden`))) join `cliente` on((`factura`.`cliente` = `cliente`.`idCliente`))) join `tipocliente` on((`cliente`.`tipocliente` = `tipocliente`.`idTipoCliente`))) group by `factura`.`idFactura` order by `factura`.`idFactura`;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
