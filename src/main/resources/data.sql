INSERT INTO teams (id, name, commission, balance) VALUES
                                                      (1, 'Dynamo Kyiv', 0.05, 2000000.00),
                                                      (2, 'Shakhtar Donetsk', 0.07, 1500000.00),
                                                      (3, 'Dnipro-1', 0.04, 800000.00),
                                                      (4, 'Zorya Luhansk', 0.06, 900000.00);

INSERT INTO players (name, age, debut_date, team_id) VALUES
                                                         ('Andriy Yarmolenko', 34, '2007-01-01', 1),
                                                         ('Vitalii Mykolenko', 25, '2018-08-01', 1),
                                                         ('Taras Stepanenko', 34, '2009-03-15', 2),
                                                         ('Mykhailo Mudryk', 23, '2020-01-10', 2),
                                                         ('Artem Dovbyk', 27, '2017-07-01', 3),
                                                         ('Oleksandr Pikhalyonok', 27, '2015-05-15', 3),
                                                         ('Vladyslav Kochergin', 27, '2016-09-01', 4),
                                                         ('Maksym Lunev', 26, '2016-03-01', 4);