/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     15-3-2019 12:01:40                           */
/*==============================================================*/

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('PLAYLIST') and o.name = 'FK_PLAYLIST_PLAYLIST__USER')
alter table PLAYLIST
   drop constraint FK_PLAYLIST_PLAYLIST__USER
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('TRACK_IN_PLAYLIST') and o.name = 'FK_TRACK_IN_TRACK_IN__TRACK')
alter table TRACK_IN_PLAYLIST
   drop constraint FK_TRACK_IN_TRACK_IN__TRACK
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('TRACK_IN_PLAYLIST') and o.name = 'FK_TRACK_IN_TRACK_IN__PLAYLIST')
alter table TRACK_IN_PLAYLIST
   drop constraint FK_TRACK_IN_TRACK_IN__PLAYLIST
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('PLAYLIST')
            and   name  = 'RELATIONSHIP_4_FK'
            and   indid > 0
            and   indid < 255)
   drop index PLAYLIST.RELATIONSHIP_4_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PLAYLIST')
            and   type = 'U')
   drop table PLAYLIST
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TRACK')
            and   type = 'U')
   drop table TRACK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('TRACK_IN_PLAYLIST')
            and   name  = 'TRACK_IN_PLAYLIST2_FK'
            and   indid > 0
            and   indid < 255)
   drop index TRACK_IN_PLAYLIST.TRACK_IN_PLAYLIST2_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('TRACK_IN_PLAYLIST')
            and   name  = 'TRACK_IN_PLAYLIST_FK'
            and   indid > 0
            and   indid < 255)
   drop index TRACK_IN_PLAYLIST.TRACK_IN_PLAYLIST_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TRACK_IN_PLAYLIST')
            and   type = 'U')
   drop table TRACK_IN_PLAYLIST
go

if exists (select 1
            from  sysobjects
           where  id = object_id('[USER]')
            and   type = 'U')
   drop table [USER]
go

if exists(select 1 from systypes where name='ALBUM')
   drop type ALBUM
go

if exists(select 1 from systypes where name='DESCRIPTION')
   drop type DESCRIPTION
go

if exists(select 1 from systypes where name='DURATION')
   drop type DURATION
go

if exists(select 1 from systypes where name='ID')
   drop type ID
go

if exists(select 1 from systypes where name='NAME')
   drop type NAME
go

if exists(select 1 from systypes where name='OFFLINEAVAILABLE')
   drop type OFFLINEAVAILABLE
go

if exists(select 1 from systypes where name='OWNER')
   drop type OWNER
go

if exists(select 1 from systypes where name='PASSWORD')
   drop type PASSWORD
go

if exists(select 1 from systypes where name='PERFORMER')
   drop type PERFORMER
go

if exists(select 1 from systypes where name='PLAYCOUNT')
   drop type PLAYCOUNT
go

if exists(select 1 from systypes where name='PLAYLIST')
   drop type PLAYLIST
go

if exists(select 1 from systypes where name='PUBLICATIONDATE')
   drop type PUBLICATIONDATE
go

if exists(select 1 from systypes where name='TITLE')
   drop type TITLE
go

if exists(select 1 from systypes where name='TOKEN')
   drop type TOKEN
go

/*==============================================================*/
/* Domain: ALBUM                                                */
/*==============================================================*/
create type ALBUM
   from varchar(255)
go

/*==============================================================*/
/* Domain: DESCRIPTION                                          */
/*==============================================================*/
create type DESCRIPTION
   from varchar(255)
go

/*==============================================================*/
/* Domain: DURATION                                             */
/*==============================================================*/
create type DURATION
   from int
go

/*==============================================================*/
/* Domain: ID                                                   */
/*==============================================================*/
create type ID
   from int
go

/*==============================================================*/
/* Domain: NAME                                                 */
/*==============================================================*/
create type NAME
   from varchar(255)
go

/*==============================================================*/
/* Domain: OFFLINEAVAILABLE                                     */
/*==============================================================*/
create type OFFLINEAVAILABLE
   from bit
go

/*==============================================================*/
/* Domain: OWNER                                                */
/*==============================================================*/
create type OWNER
   from bit
go

/*==============================================================*/
/* Domain: PASSWORD                                             */
/*==============================================================*/
create type PASSWORD
   from varchar(255)
go

/*==============================================================*/
/* Domain: PERFORMER                                            */
/*==============================================================*/
create type PERFORMER
   from varchar(255)
go

/*==============================================================*/
/* Domain: PLAYCOUNT                                            */
/*==============================================================*/
create type PLAYCOUNT
   from int
go

/*==============================================================*/
/* Domain: PLAYLIST                                             */
/*==============================================================*/
create type PLAYLIST
   from varchar(255)
go

/*==============================================================*/
/* Domain: PUBLICATIONDATE                                      */
/*==============================================================*/
create type PUBLICATIONDATE
   from varchar(255)
go

/*==============================================================*/
/* Domain: TITLE                                                */
/*==============================================================*/
create type TITLE
   from varchar(255)
go

/*==============================================================*/
/* Domain: TOKEN                                                */
/*==============================================================*/
create type TOKEN
   from varchar(255)
go

/*==============================================================*/
/* Table: PLAYLIST                                              */
/*==============================================================*/
create table PLAYLIST (
   PLAYLIST_ID          INT IDENTITY(1,1)    not null,
   USERNAME             NAME                 not null,
   NAME                 NAME                 not null,
   OWNER                OWNER                not null DEFAULT 1,
   constraint PK_PLAYLIST primary key nonclustered (PLAYLIST_ID)
)
go

/*==============================================================*/
/* Index: RELATIONSHIP_4_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_4_FK on PLAYLIST (
USERNAME ASC
)
go

/*==============================================================*/
/* Table: TRACK                                                 */
/*==============================================================*/
create table TRACK (
   TRACK_ID             INT IDENTITY(1,1)    not null,
   TITLE                TITLE                not null,
   PERFORMER            PERFORMER            not null,
   DURATION             DURATION             not null,
   ALBUM                ALBUM                not null,
   PLAYCOUNT            PLAYCOUNT            null,
   PUBLICATIONDATE      PUBLICATIONDATE      null,
   DESCRIPTION          DESCRIPTION          null,
   OFFLINEAVAILABLE     OFFLINEAVAILABLE     null,
   constraint PK_TRACK primary key nonclustered (TRACK_ID)
)
go

/*==============================================================*/
/* Table: TRACK_IN_PLAYLIST                                     */
/*==============================================================*/
create table TRACK_IN_PLAYLIST (
   TRACK_ID             ID                   not null,
   PLAYLIST_ID          ID                   not null,
   constraint PK_TRACK_IN_PLAYLIST primary key (TRACK_ID, PLAYLIST_ID)
)
go

/*==============================================================*/
/* Index: TRACK_IN_PLAYLIST_FK                                  */
/*==============================================================*/
create index TRACK_IN_PLAYLIST_FK on TRACK_IN_PLAYLIST (
TRACK_ID ASC
)
go

/*==============================================================*/
/* Index: TRACK_IN_PLAYLIST2_FK                                 */
/*==============================================================*/
create index TRACK_IN_PLAYLIST2_FK on TRACK_IN_PLAYLIST (
PLAYLIST_ID ASC
)
go

/*==============================================================*/
/* Table: [USER]                                                */
/*==============================================================*/
create table [USER] (
   USERNAME             NAME                 not null,
   NAME                 NAME                 not null,
   PASSWORD             PASSWORD             not null,
   TOKEN                TOKEN                not null,
   constraint PK_USER primary key nonclustered (USERNAME)
)
go

alter table PLAYLIST
   add constraint FK_PLAYLIST_PLAYLIST__USER foreign key (USERNAME)
      references [USER] (USERNAME)
go

alter table TRACK_IN_PLAYLIST
   add constraint FK_TRACK_IN_TRACK_IN__TRACK foreign key (TRACK_ID)
      references TRACK (TRACK_ID)
go

alter table TRACK_IN_PLAYLIST
   add constraint FK_TRACK_IN_TRACK_IN__PLAYLIST foreign key (PLAYLIST_ID)
      references PLAYLIST (PLAYLIST_ID)
go

--Table insertions

INSERT INTO [USER] (USERNAME, NAME, PASSWORD, TOKEN)
VALUES ('diegocup', 'Diego Cup', 'cupdiego', ' ')
GO

INSERT INTO PLAYLIST(USERNAME, NAME, OWNER)
VALUES('diegocup', 'Heavy metal', 1)
GO

INSERT INTO PLAYLIST(USERNAME, NAME, OWNER)
VALUES('diegocup', 'Pop', 0)
GO

INSERT INTO TRACK
VALUES('Bohemian Rocksody', 'Rocky', 350, 'First album', 2, null, null, 0),
('Bohemian Rapsody', 'Queen', 30, 'First album', 1, null, null, 0),
('Bohemian Cucksody', 'Pepe', 30, 'First album', 1, null, null, 0)
GO

INSERT INTO TRACK_IN_PLAYLIST
VALUES(1, 1),
(2, 1),
(1, 2),
(3, 2)
GO


