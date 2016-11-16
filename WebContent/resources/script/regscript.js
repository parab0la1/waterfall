$(document).ready(function(){
		var errorsJsonFormat = $("#error-msg").html();
		var errorMessages = JSON.parse(errorsJsonFormat);
		var errorStyle = "error";
		console.log(errorMessages);
		
		for(var i = 0; i < errorMessages.length; i++){
			$(document.getElementById("reg-form:" + errorMessages[i])).addClass(errorStyle);

		}

});