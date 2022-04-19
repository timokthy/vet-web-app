-- SELECT P.FName, P.LName, P.Country FROM PARTICIPANT AS P, ATHLETE AS A WHERE A.OlympicID = P.OlympicID;-- 
SELECT * FROM ANIMAL;
SELECT MAX(A.Animal_ID) FROM ANIMAL AS A;
SELECT * FROM ANIMAL AS A WHERE A.Species = 'Dog';


SELECT * FROM COMMENT AS C ;
SELECT * FROM COMMENT AS C WHERE C.Animal_ID = '101';
SELECT * FROM COMMENT AS C, USERS AS U WHERE C.User_ID = U.User_ID;
SELECT C.Animal_ID, C.Comment_ID, C.Upload_Time, C.User_ID, C.Message, U.First_Name, U.Last_Name, U.User_Type FROM COMMENT AS C, USERS AS U WHERE C.User_ID = U.User_ID;
SELECT MAX(C.Comment_ID) FROM COMMENT AS C;

SELECT * FROM PHOTOS AS P ;

SELECT * FROM REMINDERS AS R;
SELECT R.Animal_ID, R.Reminder_ID, R.Reminder_Status, R.Due_Date, R.Date_Performed, R.Author_ID, R.Notes, 
U.First_Name, U.Last_Name, U.User_Type FROM REMINDERS AS R, USERS AS U WHERE R.Author_ID = U.User_ID AND R.Animal_ID='101';

SELECT * FROM REQUEST;
SELECT * FROM REQUEST AS R2;
SELECT * FROM REQUEST AS R2, Users as U WHERE R2.Requester_ID = U.User_ID;
SELECT R2.Animal_ID, R2.Request_ID, R2.Requester_ID, R2.Request_Date, R2.Checkout_Date, R2.Return_Date, 
R2.Reason, R2.Request_Status, U.First_Name, U.Last_Name,
A.Animal_Name, A.Species  FROM REQUEST AS R2, Users as U, ANIMAL AS A  WHERE R2.Requester_ID = U.User_ID AND R2.Animal_ID=A.Animal_ID;

SELECT * FROM DIAGNOSIS WHERE Animal_ID='102';
