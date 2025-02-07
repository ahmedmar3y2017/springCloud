
CREATE TABLE  IF NOT EXISTS RESERVATIONS(
                             RESERVATION_ID SERIAL PRIMARY KEY,
                             ROOM_ID BIGINT NOT NULL,
                             GUEST_ID BIGINT NOT NULL,
                             RES_DATE DATE ,
                             STATUS varchar(20)
);

CREATE INDEX res_guest_idx on RESERVATIONS(GUEST_ID);
CREATE INDEX res_date_idx on RESERVATIONS(RES_DATE);

INSERT INTO RESERVATIONS (ROOM_ID, GUEST_ID, RES_DATE)
VALUES (1, 1, '2022-08-01');
INSERT INTO RESERVATIONS (ROOM_ID, GUEST_ID, RES_DATE)
VALUES (2, 10, '2022-08-01');
INSERT INTO RESERVATIONS (ROOM_ID, GUEST_ID, RES_DATE)
VALUES (3, 15, '2022-08-01');
INSERT INTO RESERVATIONS (ROOM_ID, GUEST_ID, RES_DATE)
VALUES (1, 1, '2022-08-02');
INSERT INTO RESERVATIONS (ROOM_ID, GUEST_ID, RES_DATE)
VALUES (1, 1, '2022-08-03');