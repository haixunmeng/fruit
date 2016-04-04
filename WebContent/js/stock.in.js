$(function(){
	bindAddStockInDetail($(".add_stock_detail"));
	
	$("#stock_in_submit").click(function(){
		
		var stock_details = new Array();
		$.each($(".stock_detail"), function(i, n){
			var good_name = $(this).children().children(".good_name").val();
			var value_unit = $(this).children().children(".value_unit").combobox("getValue");
			var in_price = $(this).children().children(".in_price").val()|0;
			var package_type = $(this).children().children(".package_type").combobox("getValue");
			var package_weight = $(this).children().children(".package_weight").val()|0;
			var package_deposit = $(this).children().children(".package_deposit").val()|0;
			var package_num = $(this).children().children(".package_num").val()|0;
			var gross_weight = $(this).children().children(".gross_weight").val()|0;
			var net_weight = $(this).children().children(".net_weight").val()|0;
			var total_deposit = $(this).children().children(".total_deposit").val()|0;
			var total_goods_price = $(this).children().children(".total_goods_price").val()|0;
			var total_price = $(this).children().children(".total_price").val()|0;
			var remark = $(this).children().children(".remark").val();
			
			
			var stock_detail = {
				good_name : good_name,
				value_unit : value_unit,
				in_price: in_price,
				package_type: package_type,
				package_weight: package_weight,
				package_deposit: package_deposit,
				package_num: package_num,
				gross_weight: gross_weight,
				net_weight: net_weight,
				total_deposit: total_deposit,
				total_goods_price: total_goods_price,
				total_price: total_price,
				remark : remark
			}
			stock_details.push(stock_detail);
		});
		
		var stock = {
				token : $.cookie("token"),
				stock_details : stock_details,
				deposit : "0",
				good_price : "0",
				total_price : "0"
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

function createNextStockDetail(elem) {
	var nextDetail = '<tr class="stock_detail"><td><input type="text" class="good_name"/></td>'
			+ '<td><input type="text" class="value_unit"/></td>'
			+ '<td><input type="text" class="in_price"/></td>'
			+ '<td><input type="text" class="package_type"/></td>'
			+ '<td><input type="text" class="package_weight"/></td>'
			+ '<td><input type="text" class="package_deposit"/></td>'
			+ '<td><input type="text" class="package_num"/></td>'
			+ '<td><input type="text" class="gross_weight"/></td>'
			+ '<td><input type="text" class="net_weight"/></td>'
			+ '<td><input type="text" class="total_deposit"/></td>'
			+ '<td><input type="text" class="total_goods_price"/></td>'
			+ '<td><input type="text" class="total_price"/></td>'
			+ '<td><input type="text" class="remark"/></td>'
			+ '<td><input type="button" class="add_stock_detail" value="+"/></td></tr>'

	elem.after(nextDetail);

	load_value_unit(elem.next().children().children(".value_unit"));
	load_package_type(elem.next().children().children(".package_type"));

	bindAddStockInDetail(elem.next().children().children(".add_stock_detail"));
}