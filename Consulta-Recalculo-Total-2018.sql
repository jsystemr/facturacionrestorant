SELECT * FROM factura f;
SELECT * FROM empresa e;
SELECT * FROM factura f;



SELECT d.nomproducto,d.cantidad,d.precio,d.isv,d.idfactura,d.fecha,d.producto,f.totalfac FROM detallefactura d inner join factura f on
d.idfactura=f.idfactura where d.fecha between '2018-11-01' and '2018-11-30' order by d.idfactura;

/*Aqui busca los detalles que tienen mas de una linea*/
SELECT nrolinea,d.nomproducto,d.precio,d.cantidad,d.comentarios,d.idfactura,d.fecha,d.producto,d.stadodet,d.descuento,d.isv FROM detallefactura d inner join factura f on
d.idfactura=f.idfactura where d.fecha between '2018-11-01' and '2018-11-30' and precio>0 and nrolinea>1 order by d.idfactura;
/**Total sumado*/
SELECT sum((d.precio+d.isv)*d.cantidad) FROM detallefactura d inner join factura f on
d.idfactura=f.idfactura where d.fecha between '2018-11-01' and '2018-11-30' and precio>0 and nrolinea>1 order by d.idfactura;


/*Luego debe actualizar el efectivo, pagado y el total a pagar deben ser iguales*/
/***Consulta para comprobar**********/
SELECT ((d.precio+d.isv)*d.cantidad) as total,d.idfactura,f.totalfac FROM detallefactura d
inner join factura f on d.idfactura=f.idfactura
where d.fecha between '2018-11-01' and '2018-11-30' and precio>0 and nrolinea=1 group by d.idfactura  order by d.idfactura;


delete from detallefactura where fecha between '2018-11-01' and '2018-11-30' and precio>0 and nrolinea>1;

update factura f set f.totalfac=(SELECT sum((d.precio+d.isv)*d.cantidad) as total FROM detallefactura d where d.idfactura=f.idfactura) where f.fecha between '2018-11-01' and '2018-11-30';



