$(document).ready(function() {
  $("#btnSubmit").click(function(event) {
    //stop submit the form, we will post it manually.
    event.preventDefault();
    fire_ajax_submit();
  });
});

// Uploaded image preview
var loadFile = function(event) {
  var output = document.getElementById('output');
  output.src = URL.createObjectURL(event.target.files[0]);
}

function fire_ajax_submit() {
  // Get form
  var form = $('#fileUploadForm')[0];
  var data = new FormData(form);
  $("#btnSubmit").prop("disabled", true);

  $.ajax({
    type: "POST",
    enctype: 'multipart/form-data',
    url: "/customer/upload",
    data: data,
    //http://api.jquery.com/jQuery.ajax/
    //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
    processData: false, //prevent jQuery from automatically transforming the data into a query string
    contentType: false,
    cache: false,
    timeout: 600000,
    success: function(data) {

      $("#result").text(data);
      //            console.log("SUCCESS : ", data);
      $("#btnSubmit").prop("disabled", false);
      window.location = "/message?message=Uploaded Successfully&redirectUrl=" +
        window.location.pathname + "&errorCode=0&buttonString=Upload another";
    },
    error: function(e) {

      $("#result").text(e.responseText);
      $("#errorslist").html(e.responseText);
      //            console.log("ERROR : ", e);
      $("#btnSubmit").prop("disabled", false);
    }
  });
}
