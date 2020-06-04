 $(document).ready(function() {
   $('.materialboxed').materialbox();
   $('.modal').modal();

   if (user_role != "ROLE_USER") {
     document.getElementsByClassName("buybtn")[0].style.display = "none";
   }
 });

 $("#deleteConfirmButton").click(function() {
   var artId = $("#artId").text();

   alert(artId);
   $.ajax({
     type: "DELETE",
     url: "/artist/image",
     headers: {
       "imageId": artId
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
       window.location = "/artist/dashboard";

     },
     error: function(e) {

       $("#result").text(e.responseText);
       $("#errorslist").html(e.responseText);
       //            console.log("ERROR : ", e);
       $("#btnSubmit").prop("disabled", false);
     }
   });

 });
