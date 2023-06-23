insert into `product` (name, price, detail_information_image) values ('lime1 square', 100000, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/dummy+detail.jpeg');
insert into `product` (name, price, detail_information_image) values ('lime1 trapezoid', 100000, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/dummy+detail.jpeg');


insert into `options` (name, code, product_id) values ('lime1 square black', 'lime1-sq-bk', 1);
insert into `options` (name, code, product_id) values ('lime1 square white', 'lime1-sq-wt', 1);
insert into `options` (name, code, product_id) values ('lime1 square pink', 'lime1-sq-pk', 1);

insert into `options` (name, code, product_id) values ('lime1 trapezoid black', 'lime1-trz-bk', 2);
insert into `options` (name, code, product_id) values ('lime1 trapezoid white', 'lime1-trz-wt', 2);
insert into `options` (name, code, product_id) values ('lime1 trapezoid pink', 'lime1-trz-pk', 2);

insert into `product_image` (`order`, url, option_id) values (1, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/square-black.png', 1);
insert into `product_image` (`order`, url, option_id) values (2, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/square-black.png', 1);
insert into `product_image` (`order`, url, option_id) values (3, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/square-black.png', 1);

insert into `product_image` (`order`, url, option_id) values (1, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/square-red.png', 2);
insert into `product_image` (`order`, url, option_id) values (2, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/square-red.png', 2);
insert into `product_image` (`order`, url, option_id) values (3, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/square-red.png', 2);

insert into `product_image` (`order`, url, option_id) values (1, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/square-pink.png', 3);
insert into `product_image` (`order`, url, option_id) values (2, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/square-pink.png', 3);
insert into `product_image` (`order`, url, option_id) values (3, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/square-pink.png', 3);

insert into `product_image` (`order`, url, option_id) values (1, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/trapezoid-black.png', 4);
insert into `product_image` (`order`, url, option_id) values (2, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/trapezoid-black.png', 4);
insert into `product_image` (`order`, url, option_id) values (3, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/trapezoid-black.png', 4);

insert into `product_image` (`order`, url, option_id) values (1, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/trapezoid-pink.png', 5);
insert into `product_image` (`order`, url, option_id) values (2, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/trapezoid-pink.png', 5);
insert into `product_image` (`order`, url, option_id) values (3, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/trapezoid-pink.png', 5);

insert into `product_image` (`order`, url, option_id) values (1, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/trapezoid-white.png', 6);
insert into `product_image` (`order`, url, option_id) values (2, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/trapezoid-white.png', 6);
insert into `product_image` (`order`, url, option_id) values (3, 'https://jns-test-s3.s3.ap-northeast-2.amazonaws.com/trapezoid-white.png', 6);

