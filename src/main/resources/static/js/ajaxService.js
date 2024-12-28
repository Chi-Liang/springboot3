function ajaxCall(url,method,data){
	
	var deferred = $.Deferred();
	var dataType;
	
	if(data){
		data = JSON.stringify(data);
		dataType = "json";
	}else{
		data = undefined;
	}
	
	$.ajax({
		url  : url,
        type :method,
        dataType : dataType,
        contentType : 'application/json; charset=utf-8', 
        data : data,
        async : true,
        cache : false,
        success : function(data,text,xhr) {
        	success(data,deferred,xhr);	
    	},
    	error : function(xhr) {
    		error(xhr,deferred);
    	}
    }) 
    
    return deferred.promise();
}

function success(data,deferred,response){
	if(data && (data == "success" || data.result == "success")){
		deferred.resolve(data);
	}else if(data && data.result == "fail"){
		deferred.reject(data.message);
	}else{
		deferred.reject("系統更新維護或網路異常");
	}
}

function error(xhr,deferred){
	try{
		var error = JSON.parse(xhr.responseText);
		deferred.reject(error);
	}catch(e){
		deferred.reject("系統更新維護或網路異常");
	}
}

function setSessionAttribute(data){
  	var form1 = document.createElement("form"); 
  	form1.id = "form1"; 
  	form1.name = "form1"; 
  	document.body.appendChild(form1); 
  	var input = document.createElement("input"); 
  	input.type = "text"; 
  	input.name = "mid"; 
  	input.value = data.memberVO.mid; 
  	form1.appendChild(input);
  	
  	var input = document.createElement("input"); 
  	input.type = "text"; 
  	input.name = "email"; 
  	input.value = data.memberVO.email; 
  	form1.appendChild(input); 
  	
  	var input = document.createElement("input"); 
  	input.type = "text"; 
  	input.name = "name"; 
  	input.value = data.memberVO.name; 
  	form1.appendChild(input); 
  	
  	var input = document.createElement("input"); 
  	input.type = "text"; 
  	input.name = "tel"; 
  	input.value = data.memberVO.tel; 
  	form1.appendChild(input); 
  	
  	form1.method = "POST"; 
  	//form1.action = "/fundodo-test/LoginServlet"; 
  	form1.action = "/fundodo/LoginServlet"; 
  	form1.submit(); 
  	document.body.removeChild(form1);
}
