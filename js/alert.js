function alertSuccess(message){
    $("#showAlert").append('<div class="alert alert-success alert-dismissible" style="margin-bottom: 0">'+
            '<a id="closeAlertSuccess" href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'+
            '<strong>' + message+ '</strong> '+
        '</div>')  
    const myDiv = document.getElementById("showAlert");
    function closeDiv() {
        // Hide the div by setting its display property to "none"
        myDiv.style.display = "none";
    }
        
        // Close the div after 5 seconds
    setTimeout(closeDiv, 10000);      
}

function alertFail(message){
    $("#showAlert").append('<div class="alert alert-danger alert-dismissible" style="margin-bottom: 0">'+
            '<a id="closeAlertFail" href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'+
            '<strong>'+ message +'</strong> '+
        '</div>')  
    const myDiv = document.getElementById("showAlert");
    function closeDiv() {
        // Hide the div by setting its display property to "none"
        myDiv.style.display = "none";
    }
        
        // Close the div after 5 seconds
    setTimeout(closeDiv, 10000);   
}
function alertWarning(message){
    $("#showAlert").append('<div class="alert alert-warning alert-dismissible" style="margin-bottom: 0">'+
            '<a id="closeAlertWarning" href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'+
            '<strong>'+ message+'</strong> ' +
        '</div>')    
        
    function closeDiv() {
        // Hide the div by setting its display property to "none"
        $("#showAlert").style.display = "none";
    }
        
        // Close the div after 5 seconds
    setTimeout(closeDiv, 10000);
}

