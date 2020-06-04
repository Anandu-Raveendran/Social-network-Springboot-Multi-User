 $(document).ready(function() {
   $('.materialboxed').materialbox();
   $('.modal').modal({
     dismissible: false
   });

   document.getElementById("processing-order").style.display = "none";
   if (user_role != "ROLE_USER") {
     document.getElementsByClassName("buybtn")[0].style.display = "none";
   }
 });

 $("#confirmButton").click(function() {
   var email = $("#email").text();
   var artId = $("#artId").text();

   $('.modal-content').hide();
   $('.buybtn').hide();
   $('#confirmButton').hide();
   $('#cancelButton').hide();
   document.getElementById("processing-order").style.display = "block";
   $.ajax({
     type: "POST",
     url: "/customer/order",
     headers: {
       "email": email,
       "artId": artId
     },
     //http://api.jquery.com/jQuery.ajax/
     //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
     processData: false, //prevent jQuery from automatically transforming the data into a query string
     cache: false,
     timeout: 600000,
     success: function(data) {

       $("#result").text(data);
       //            console.log("SUCCESS : ", data);
       $("#btnSubmit").prop("disabled", false);
       window.location = "/message?message=Congratulations! Order Placed Successfully. Our Artists will contact you, if you didn't already.&redirectUrl=/home&errorCode=0&buttonString=Keep shopping";
     },
     error: function(e) {

       $("#result").text(e.responseText);
       $("#errorslist").html(e.responseText);
       //            console.log("ERROR : ", e);
       $("#btnSubmit").prop("disabled", false);
     }
   });
 });
