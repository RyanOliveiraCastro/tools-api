CREATE TABLE IF NOT EXISTS tools(
                      id IDENTITY NOT NULL PRIMARY KEY,
                      title varchar(255) NOT NULL,
                      link varchar(255) NOT NULL,
                      description varchar(255) NOT NULL,
                        tags varchar(255) NULL
);

--insert into tag (name) values ('uso_externo');
--insert into tag (name) values ('uso_interno');
