--All accounts are passive
create table client_account
( id identity primary key
, code varchar(64) not null
, description varchar(256) null
, overdraft   decimal not null default 0
, constraint client_account_p primary key(id)
, constraint client_account_u1 unique key(code)
);

create table account_balance
( id bigint primary key
, balance decimal not null default 0
, constraint account_balance_p primary key(id)
, constraint account_balance_f1 foreign key(id) references client_account(id)
);

create table transaction_log
( id identity primary key
, account_debit bigint
, account_credit bigint
, transaction_date timestamp with time zone
, amount decimal not null default 0
, constraint transaction_log_f1 foreign key(account_debit) references client_account(id)
, constraint transaction_log_f2 foreign key(account_credit) references client_account(id)
)
