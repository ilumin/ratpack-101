DROP TABLE IF EXISTS todo;
CREATE TABLE todo (
  `id` bigint auto_increment primary key,
  `title` varchar(256),
  `completed` bool default false,
  `order` int default null
);

INSERT INTO todo (`title`, `completed`, `order`)
VALUES
  ('Yikes Subject 01', 1, 1),
  ('Yikes Subject 02', 0, 2),
  ('Yikes Subject 03', 0, 3),
;
