CREATE TABLE niklauswetter.Flight (
    fNo int NOT NULL,
    cNo int NOT NULL,
    airline varchar(9) NOT NULL,
    bGate varchar(2) NOT NULL,
    bTime date,
    dTime date,
    duration int,
    dLoc varchar(3),
    aLoc varchar(3),
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
    fNo int NOT NULL,
    PRIMARY KEY (pNo)
);
GRANT SELECT ON niklauswetter.Ticket TO PUBLIC;