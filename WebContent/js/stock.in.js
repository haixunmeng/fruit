$(function(){
	bindAddStockInDetail($(".add_stock_detail"));
});

function bindAddStockInDetail(elem){
	elem.click(function(){
		createNextStockDetail($(this).parent().parent());
		$(this).val("-").attr("class", "remove_stock_detail").unbind("click").click(function(){
			$(this).parent().parent().remove();
		});
	});
}


function createNextStockDetail(elem){
	var nextDetail = '<tr><td><input type="text" class="good"/></td>' +
			'<td><input type="text" class="value_unit"/></td>' +
			'<td><input type="text" class="in_price"/></td>' +
			'<td><input type="text" class="package_type"/></td>' +
			'<td><input type="text" class="package_weight"/></td>' +
			'<td><input type="text" class="package_deposit"/></td>' +
			'<td><input type="text" class="package_num"/></td>' +
			'<td><input type="text" class="gross_weight"/></td>' +
			'<td><input type="text" class="net_weight"/></td>' +
			'<td><input type="text" class="total_deposit"/></td>' +
			'<td><input type="text" class="total_goods_price"/></td>' +
			'<td><input type="text" class="total_price"/></td>' +
			'<td><input type="text" class="remark"/></td>' +
			'<td><input type="button" class="add_stock_detail" value="+"/></td></tr>'
			
	elem.after(nextDetail);
	
	load_value_unit(elem.next().children().children(".value_unit"));
	load_package_type(elem.next().children().children(".package_type"));
	
	bindAddStockInDetail(elem.next().children().children(".add_stock_detail"));
}