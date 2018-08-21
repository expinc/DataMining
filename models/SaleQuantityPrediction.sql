drop procedure CALCULATE_INVENTORY;
create procedure CALCULATE_INVENTORY() as
dateVar timestamp;
endDate timestamp;
purchaseQuantity decimal(21,6);
salesQuantity decimal(21,6);
begin

drop table DATE2STOCK;
create column table DATE2STOCK("Date" timestamp, "StockQuantity" decimal(21,6));

dateVar = to_timestamp('2011-01-01', 'YYYY-MM-DD');
endDate = to_timestamp('2017-03-31', 'YYYY-MM-DD');
while dateVar <= endDate do
	select sum(O1."Quantity") into purchaseQuantity from OPOR O0 inner join POR1 O1 on O0."DocEntry" = O1."DocEntry" where O0."DocDate" <= dateVar;
	select sum(O1."Quantity") into salesQuantity from ORDR O0 inner join RDR1 O1 on O0."DocEntry" = O1."DocEntry" where O0."DocDate" <= dateVar;
	insert into DATE2STOCK values (dateVar, purchaseQuantity - salesQuantity);
	
	dateVar = ADD_DAYS(dateVar, 1);
end while;

end;