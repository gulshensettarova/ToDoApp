ALTER TABLE "userr"
    ADD COLUMN user_status_id INT;

ALTER TABLE "userr"
    ADD CONSTRAINT fk_user_status
        FOREIGN KEY (user_status_id) REFERENCES user_status(id);