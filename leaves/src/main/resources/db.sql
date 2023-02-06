CREATE TABLE `leaves` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `eid` bigint NOT NULL,
  `type` varchar(10) NOT NULL,
  `date` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `eid_idx` (`eid`),
  CONSTRAINT `eid` FOREIGN KEY (`eid`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
SELECT * FROM segmatek.employee;