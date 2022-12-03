CREATE TABLE ccnw.Flight (
    f# int NOT NULL,
    c# int NOT NULL,
    airline varchar(20) NOT NULL,
    bGate varchar(3) NOT NULL,
    bTime datetime,
    dTime datetime,
    duration int,
    dLoc varchar(255),
    aLoc varchar(255),
    PRIMARY KEY (f#),
);
CREATE TABLE ccnw.Passenger (
    p# int NOT NULL,
    isStudent boolean,
    isFreqFlyer boolean,
    isMilitary boolean,
    binHistory int,
    fName varchar(255),
    lName varchar(255),
    PRIMARY KEY (p#)
);
CREATE TABLE ccnw.Staff (
    s# int NOT NULL,
    jobTitle varchar(255),
    fName varchar(255),
    lName varchar(255),
    PRIMARY KEY (s#)
);
CREATE TABLE ccnw.Crew (
    c# int NOT NULL,
    pilotNo int,
    coPilotNo int,
    cabinNo int,
    groundNo int,
    PRIMARY KEY (c#)
);
CREATE TABLE ccnw.Ticket (
    p# int NOT NULL,
    f# int NOT NULL,
    PRIMARY KEY (p#),
);