create materialized view v_basket_request_count as
select b.ext_id,
       b.basket_date_utc,
       b.basket_status,
       b.buyer_name,
       b.payment_method,
       b.product,
       count(cr.ext_id) as request_count
from basket b
         left outer join client_request cr
                         on b.ext_id = cr.ext_id
group by b.ext_id, b.basket_date_utc, b.basket_status, b.buyer_name, b.payment_method, b.product;

