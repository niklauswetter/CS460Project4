insert into Ticket (pNo, fNo)
SELECT 1,1
FROM dual
WHERE NOT EXISTS(SELECT * FROM Ticket WHERE (pNo=1 AND fNo=1));