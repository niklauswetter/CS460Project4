CREATE TABLE ccnw.Flight (
    fNo int NOT NULL,
    cNo int NOT NULL,
    airline varchar(20) NOT NULL,
    bGate varchar(3) NOT NULL,
    bTime datetime,
    dTime datetime,
    duration int,
    dLoc varchar(255),
    aLoc varchar(255),
    PRIMARY KEY (fNo),
);
CREATE TABLE ccnw.Passenger (
    pNo int NOT NULL,
    isStudent boolean,
    isFreqFlyer boolean,
    isMilitary boolean,
    binHistory int,
    fName varchar(255),
    lName varchar(255),
    PRIMARY KEY (pNo)
);
CREATE TABLE ccnw.Staff (
    sNo int NOT NULL,
    cNo int,
    jobTitle varchar(255),
    fName varchar(255),
    lName varchar(255),
    PRIMARY KEY (sNo)
);
CREATE TABLE ccnw.Ticket (
    pNo int NOT NULL,
    fNo int NOT NULL,
    PRIMARY KEY (pNo),
);