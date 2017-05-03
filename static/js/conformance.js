
var allpassed = false;
function reset() {

    console.log("reset");

    $.ajax({
        url: '/track/resetRecord',
        type: 'POST',
        async: true,

        timeout: 3000,
        dataType: 'json',
        beforeSend: function (xhr) {

        },
        success: function (data, textStatus, jqXHR) {

            if (data == true) {
                for (var i = 1; i <= steps; i++) {
                    $("#btn" + i).attr("disabled",true);
                    $("#step" + i + "_status").html("Failed");
                    $("#step" + i).hide();
                }
                $("#step" + 1 + "_status").html("Failed");
                $("#step" + 1).hide();
                $("#result").hide();
            } else {
                alert("Reset failed")
            }

        },
        error: function (xhr, textStatus) {

        },
        complete: function () {

        }
    })
}


function checkStatus(step) {

   // console.log("checkStatus");

    $.ajax({
        url: '/track/getRecordStatus',
        type: 'POST',
        async: true,
        data: {
            username: $("#username").val(),
            step: step
        },
        timeout: 3000,
        dataType: 'json',
        beforeSend: function (xhr) {

        },
        success: function (data, textStatus, jqXHR) {
            $("#step" + step).show();
            if (data == true) {
                $("#step" + step + "_status").html("<font color='green'>Passed</font>");
               
            } else {
                $("#step" + step + "_status").html("<font color='red'>Failed</font>");
            }
//            var nextStep = step + 1;
//            $("#btn" + nextStep).attr("disabled", false);

            

            for (var i = 1; i <= steps; i++) {
                if ($("#step" + i + "_status").html() == "<font color='green'>Passed</font>")
                    allpassed = true;
                else {
                    allpassed = false;
                    break;
                }
            }

            if (allpassed) {
                $("#result").show();
            }

        },
        error: function (xhr, textStatus) {

        },
        complete: function () {

        }
    })

}


function choose_step(isAutoPolit,isTrading,step){
	 $.ajax({
	        url: '/track/chooseUserStep', 
	        type: 'POST',
	        async: true,
	        data: {	        
	        	isAutoPolit:isAutoPolit,
	        	isTrading:isTrading,
	            step: step
	        },
	        timeout: 3000,
	        dataType: 'json',
	        beforeSend: function (xhr) {

	        },
	        success: function (data, textStatus, jqXHR) {	     	        	 
	            if (data == true) {
	            	for (var i = 1; i <= steps; i++) {
	            		if(i != step)
	                    	$("#btn" + i).attr("disabled",true);	 
	            		else
	            			$("#btn" + i).attr("disabled",false);
	                }	            	
	            } 
	        },
	        error: function (xhr, textStatus) {

	        },
	        complete: function () {

	        }
	    })
}