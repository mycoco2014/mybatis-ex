CREATE TABLE t_product (
	product_id		BIGINT PRIMARY KEY,
	product_name	VARCHAR(100) NOT NULL UNIQUE,
	unit_price		NUMERIC(10,2) NOT NULL,
	freight			NUMERIC(6,2) NOT NULL DEFAULT 0,
	product_type	VARCHAR(10) NOT NULL DEFAULT '1',
	description		TEXT NOT NULL,
	status			VARCHAR(10) NOT NULL,
	create_time		DATETIME NOT NULL,
	update_time		DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_main_order (
	order_id		BIGINT PRIMARY KEY,
	user_id			BIGINT NOT NULL,
	total_money		NUMERIC(12,2) NOT NULL,
	discount		NUMERIC(3,2) NOT NULL DEFAULT 1,
	total_freight	NUMERIC(6,2) NOT NULL DEFAULT 0,
	order_time		DATETIME NOT NULL,
	status			VARCHAR(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_child_order (
	order_id		BIGINT PRIMARY KEY,
	main_order_id	BIGINT NOT NULL,
	product_id		BIGINT NOT NULL,
	product_name	VARCHAR(100) NOT NULL,
	unit_price		NUMERIC(10,2) NOT NULL,
	buy_num			INT NOT NULL,
	freight			NUMERIC(6,2) NOT NULL DEFAULT 0,
	sub_total		NUMERIC(10,2) NOT NULL,
	order_time		DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;