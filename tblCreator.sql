CREATE TABLE niklauswetter.Flight (
    fNo int NOT NULL,
    cNo int NOT NULL,
    airline varchar(255) NOT NULL,
    bGate varchar(255) NOT NULL,
    bTime TIMESTAMP,
    dTime TIMESTAMP,
    duration int,
    dLoc varchar(255),
    aLoc varchar(255),
    PRIMARY KEY (fNo)
);
GRANT SELECT ON niklauswetter.Flight TO PUBLIC;

CREATE TABLE niklauswetter.Passenger (
    pNo int NOT NULL,
    isStudent NUMBER(1),
    isFreqFlyer NUMBER(1),
    isMilitary NUMBER(1),
    fName varchar(255),
    lName varchar(255),
    PRIMARY KEY (pNo)
);
GRANT SELECT ON niklauswetter.Passenger TO PUBLIC;

CREATE TABLE niklauswetter.Staff (
    sNo int NOT NULL,
    cNo int,
    jobTitle varchar(255),
    fName varchar(255),
    lName varchar(255),
    PRIMARY KEY (sNo)
);
GRANT SELECT ON niklauswetter.Staff TO PUBLIC;

CREATE TABLE niklauswetter.Ticket (
    pNo int NOT NULL,
    fNo int NOT NULL
);
GRANT SELECT ON niklauswetter.Ticket TO PUBLIC;