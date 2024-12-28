'use strict';
function checkInteger(filedArr){
	var item="";
	var flag=true;
	for (var i = 0; i < filedArr.length; i++) {
	  item = filedArr[i].trim();
	  if(!Number.isInteger(parseInt(item))){
		alert("請勿填入非整數!");
		flag=false;
		break;  
	  }	  
	}	  
	return flag;
}	

function checkVal(filedArr){
	var item="";
	var flag=true;
	for (var i = 0; i < filedArr.length; i++) {
	  item = filedArr[i];
	  if(undefined===item || ""===item){
		alert("請勿填入空值!");
		flag=false;
		break;
      }	
	}	  
	return flag;
}	