==================================2012-03-11========================================================
ALTER TABLE cost_accounting ADD final_price INT(11);
COMMIT;

UPDATE cost_accounting SET sell_price = sell_price - tqyf;
COMMIT;

UPDATE cost_accounting SET final_price = sell_price + tqyf;
COMMIT;

#ALTER TABLE cost_accounting DROP final_price;


=======================================================================================================

alter table t_product_arrange add packages_accu INT DEFAULT '0',add count_accu FLOAT DEFAULT '0',add amount_accu FLOAT DEFAULT '0';

====================2018-05-18=====================================================
ALTER TABLE hhyz.trade MODIFY COLUMN amount DOUBLE NULL ;
ALTER TABLE hhyz.trade MODIFY COLUMN price DOUBLE NULL ;
ALTER TABLE hhyz.trade MODIFY COLUMN carriage DOUBLE NULL ;
ALTER TABLE hhyz.trade MODIFY COLUMN payment DOUBLE NULL ;
ALTER TABLE hhyz.trade MODIFY COLUMN balance DOUBLE NULL ;
