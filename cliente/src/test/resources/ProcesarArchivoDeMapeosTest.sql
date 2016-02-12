USE extensibilidad;


LOCK TABLES `mapeo` WRITE;


DELETE FROM `mapeo`;
INSERT INTO `mapeo` VALUES ('2','2','blanco','blanco'),('1','1','café','café'),('4',NULL,NULL,'Rojo'),(NULL,'2','Blanco',NULL),('3','3','naranja','naranja');

UNLOCK TABLES;
