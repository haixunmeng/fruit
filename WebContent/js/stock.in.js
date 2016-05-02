$(function(){
	bindAddStockInDetail($(".add_stock_detail"));
	bindInNumInput($(".in_num"));
	
	$("#stock_in_submit").click(function(){
		
		var stock_details = new Array();
		$.each($(".stock_detail"), function(i, n){
			var good_name = $(this).children().children(".good_name").val();
			var value_unit = $(this).children().children(".value_unit").combobox("getValue");
			var in_price = $(this).children().children(".in_price").val();
			var in_num = $(this).children().children(".in_num").val();
			var total_price = $(this).children().children(".total_price").val();
			var remark = $(this).children().children(".remark").val();
			
			
			var stock_detail = {
				good_name : good_name,
				value_unit : value_unit,
				in_price: in_price,
				in_num: in_num,
				total_price: total_price,
				remark : remark
			}
			stock_details.push(stock_detail);
		});
		
		var stock = {
				token : $.cookie("token"),
				stock_details : stock_details,
				total_price : $("#total_price").val(),
				remark : $("#remark").val()
		}
		
		var res = post("/fruit/stock/stockIn.do", stock);
		
		alert(res.msg);
	});
});

var stock_detail_array = ["good", "value_unit", "in_price", "package_type", "package_weight", "package_deposit", "package_num", "gross_weight",
              			"net_weight", "total_deposit", "total_goods_price", "total_price",
              			"remark" ];

function bindAddStockInDetail(elem) {
	elem.click(function() {
		createNextStockDetail($(this).parent().parent());
		$(this).val("-").attr("class", "remove_stock_detail").unbind("click")
				.click(function() {
					$(this).parent().parent().remove();
				});
	});
}

function bindInNumInput(elem){
	elem.bind("input propertychange", function(){
		var in_price = $(this).parent().prevAll().children(".in_price").val();
		var in_num = $(this).val();
		
		var total_price = in_price * 10000 / 10000 * in_num;
		$(this).parent().nextAll().children(".total_price").val(total_price); 
		
		count_total();
	});
}

function createNextStockDetail(elem) {
	var nextDetail = '<tr class="stock_detail"><td><input type="text" class="good_name"/></td>'
			+ '<td><input type="text" class="value_unit"/></td>'
			+ '<td><input type="text" class="in_price"/></td>'
			+ '<td><input type="text" class="in_num"/></td>'
			+ '<td><input type="text" class="total_price"/></td>'
			+ '<td><input type="text" class="remark"/></td>'
			+ '<td><input type="button" class="add_stock_detail" value="+"/></td></tr>'

	elem.after(nextDetail);

	load_value_unit(elem.next().children().children(".value_unit"));
	
	bindInNumInput(elem.next().children().children(".in_num"));
	
	bindAddStockInDetail(elem.next().children().children(".add_stock_detail"));
}

function count_total(){
	
	var total_total_price = 0;
	
	$.each($(".stock_detail"), function(i, n){
		var total_price = $(this).children().children(".total_price").val();
		total_total_price = total_total_price*10000/10000 + total_price*10000/10000;
	});
	
	$("#total_price").val(total_total_price);
}