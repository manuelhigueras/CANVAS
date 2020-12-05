/*
 * SCRIPT PARA CREAR POR DEFECTO LA BD CON DATOS 
*/
--NOT NULL GENERATED ALWAYS AS IDENTITY

CREATE TABLE TAREAS(
    ID_TAREA INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    DESCRIPCION VARCHAR(30) NOT NULL,
    ESTADO VARCHAR(11) NOT NULL DEFAULT 'TO DO',
        PRIMARY KEY (ID_TAREA),
        CHECK (ESTADO IN ('TO DO', 'IN PROGRESS', 'DONE'))
);

--DROP TABLE TAREAS;

-- ALTER TABLE TAREAS ADD COLUMN ARCHIVADO INTEGER;

-- ALTER TABLE TAREAS ADD PRIMARY KEY (ID_TAREA);
-- ALTER TABLE TAREAS ADD CHECK(ESTADO IN ('TO DO', 'IN PROGRESS', 'DONE'));

-- INSERT INTO TAREAS (DESCRIPCION, ESTADO)
--     VALUES ('Montar instalacion', 'TO DO'),
--            ('A la espera de recepcion', 'IN PROGRESS'),
--            ('Comprar materiales', 'DONE');

INSERT INTO TAREAS (DESCRIPCION, ESTADO)
    VALUES ('Elaborar servicios de tarea','TO DO'),
           ('Agregar una tarea (SERVICIO)','TO DO'),
           ('Modificar una tarea (SERVICIO)', 'TO DO'),
           ('Eliminar una tarea (SERVICIO)', 'TO DO'),
           ('Elb view espec de tarea (GUI)', 'TO DO');

SELECT * FROM TAREAS WHERE ESTADO = 'IN PROGRESS';
select * from tareas;

INSERT INTO TAREAS (DESCRIPCION, ESTADO)
    VALUES ('Elab clases domain de tarea','IN PROGRESS'),
           ('Elab cls excepcion de tarea', 'IN PROGRESS'),
           ('Elab cls JFM de tarea', 'IN PROGRESS'),
           ('Crear MDN (package)', 'DONE'),
           ('dpd DBY V10.14.2.0 pom.xml', 'DONE'),
           ('Add script SQL de bd', 'DONE'),
           ('Verif si corre la bd', 'DONE'),
           ('Crear prop de bd', 'DONE'),
           ('Elab pool de conexiones', 'DONE'),
           ('Verificar si corre la bd', 'DONE'),
           ('Crear propiedades de bd', 'DONE'),
           ('Elaborar pool de conexiones', 'DONE');

--UPDATE TAREAS SET ARCHIVADO = 0 WHERE ID_TAREA = 1
UPDATE TAREAS SET ARCHIVADO = 1
--UPDATE TAREAS SET ARCHIVADO = 0 WHERE ESTADO LIKE 'DONE' AND ARCHIVADO = 1

