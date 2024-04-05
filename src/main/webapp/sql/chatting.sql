CREATE TABLE `CHATINGROOM` (
`CHATID`	VARCHAR(100),
`CHATINGNAME`	VARCHAR(100),
`CURRENTJOIN`	VARCHAR(100),
`MAXJOIN`	VARCHAR(100),
`Field`	VARCHAR(255),
`UserId`	VARCHAR(100),
`PROJECTID`	VARCHAR(100)
);

CREATE TABLE `CHATING` (
`TIME`	TIMESTAMP,
`USERNAME`	VARCHAR(100),
`MESSAGE`	VARCHAR(100),
`UserId`	VARCHAR(100),
`PROJECTID`	VARCHAR(100)
);