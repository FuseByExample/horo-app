drop table if exists horoscopes;
drop table if exists signs;
drop table if exists feeds;
drop table if exists CAMEL_MESSAGEPROCESSED;

-- required by Camel JdbcMessageIdRepository
create table CAMEL_MESSAGEPROCESSED (
    processorName varchar(255),
    messageId varchar(100),
    createdAt timestamp
);

create table feeds (
	feed_id serial primary key, 
	name varchar(64) not null, 
	active boolean not null default true 
);

create table signs ( 
	sign_id serial primary key, 
	name varchar(32) not null,
	start_month int not null,
	start_day int not null,
	end_month int not null,
	end_day int not null
);

-- The start dates of these values have been moved back by 1 as wikipedia sees star signs overlap on the day
-- and no instructions are available as to what time applies for calculation, and how timezones affect this.
-- You know, like in fields that aren't nonsense.
insert into signs (name, start_month, start_day, end_month, end_day) values
    ('Aries', 3, 19, 4, 19),
	('Taurus', 4, 20, 5, 20),
	('Gemini', 5, 21, 6, 20),
	('Cancer', 6, 21, 7, 21),
	('Leo', 7, 22, 8, 22),
	('Virgo', 8, 23, 9, 22),
	('Libra', 9, 23, 10, 23),
	('Scorpio', 10, 24, 11, 21),
	('Sagittarius', 11, 22, 12, 21),
	('Capricorn', 12, 22, 1, 19),
	('Aquarius', 1, 20, 2, 18),
	('Pisces', 2, 19, 3, 20);

create table horoscopes ( 
	horoscope_id serial primary key, 
	feed_id integer not null, 
	sign_id integer not null,
	predicts_for date not null, 
	entry text, 
	foreign key (feed_id) references feeds (feed_id), 
	foreign key (sign_id) references signs (sign_id) 
);

-- sample data
insert into feeds(name, active) values
    ('com.astrology', true),
    ('com.astrology.extended', true);

insert into horoscopes (sign_id, feed_id, predicts_for, entry)
select s.sign_id, f.feed_id,  current_date - 1, 'If the daily grind is getting you down right now, seek the comfort of your home, friends and family. These things will help you feel more grounded and content. So bow out of wild adventures right now -- and get back in touch with the truly important things in life. Your people would love a chance to spend some more time with you. Also, quite a few of your relationships could use a bit more of your attention, and the timing is perfect for that now.'
    from signs s, feeds f
    where s.name = 'Aries'
	and f.name = 'com.astrology';
