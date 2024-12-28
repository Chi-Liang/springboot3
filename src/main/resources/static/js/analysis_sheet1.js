'use strict';
function analysis_subtract(){	
	var  invoice = $("#invoice").val();
	var  expenditure = $("#expenditure").val();
	var  car_debt = $("#carDebt").val();
	var  house_debt = $("#houseDebt").val();
	var  card_debt = $("#cardDebt").val();
	
	var filedArr = [];

    filedArr.push(invoice,expenditure,car_debt,house_debt,card_debt);

	var flag = checkVal(filedArr);
	
	//console.log(Number.isInteger(parseInt(invoice)));  
	
	if(flag===true){
		
	  var flag1 = checkInteger(filedArr);
	
	  if(flag1===true){
		  var monthlyBalanceResult = parseInt(invoice)-parseInt(expenditure)
										  -parseInt(car_debt)-parseInt(house_debt)-parseInt(card_debt);
										  
		  monthlyBalanceResult=monthlyBalanceResult<0?0:monthlyBalanceResult;

		  console.log(monthlyBalanceResult);						  
		  $("#textMonthlyBalanceResult").val(monthlyBalanceResult);			  
	  }  
	}
	else{
		  alert("請填入數字!");	
	}	
}

$("#btnMonthlyBalanceResult").on("click",function(){
    analysis_subtract();
});