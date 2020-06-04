var user_role;

// get the role of the user for the navbar
function getUserRole() {
  var xhttp = new XMLHttpRequest();
  xhttp.open("GET", "/getrole", true);
  xhttp.setRequestHeader("Content-type", "application/json");
  xhttp.send();
  xhttp.onload = function() {
    user_role = xhttp.responseText;
    setNavBarItemVisibility();
  }
}
getUserRole();

// get the nav bar elements not shown
function setNavBarItemVisibility() {
  if (user_role == "ROLE_USER") {

    //TODO: enable once the customer order feature is done
    //        document.getElementsByClassName("placeOrder-li")[0].style.display = "inline";
    //        document.getElementsByClassName("placeOrder-li")[1].style.display = "inline";
    document.getElementsByClassName("upload-li")[0].style.display = "inline";
    document.getElementsByClassName("upload-li")[1].style.display = "inline";

  } else if (user_role == "ROLE_ARTIST") {
    document.getElementsByClassName("dashboard-li")[0].style.display = "inline";
    document.getElementsByClassName("dashboard-li")[1].style.display = "inline";

    document.getElementsByClassName("cart-li")[0].style.display = "inline";
    document.getElementsByClassName("cart-li")[1].style.display = "inline";

    document.getElementsByClassName("Orders-li")[0].style.display = "inline";
    document.getElementsByClassName("Orders-li")[1].style.display = "inline";

  } else {

  }

}
