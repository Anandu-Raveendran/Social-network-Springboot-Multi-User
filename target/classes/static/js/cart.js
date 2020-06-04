var ourData = [];
var pinTerest = document.querySelector("#gallery");

var ourRequest = new XMLHttpRequest();
var skip = 0;
var limit = 10;
loadData();

function clear_cart() {
  var xhr = new XMLHttpRequest();
  var url = '/shoppingCart/clearall';
  xhr.open("DELETE", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4 && xhr.status === 200) {
      Materialize.toast("Cart cleared", 2000)
      window.location.href = "/";

    }
  };
  console.log("PUT  " + url);
  xhr.send();

}

function loadData() {
  ourRequest.open('GET', '/shoppingCart');
  console.log(ourRequest);
  skip = skip + limit;
  ourRequest.onload = function() {
    ourData = JSON.parse(ourRequest.responseText);
    //   check if gallery is empty and show on the gallery
    if (ourData == null || ourData.shoppingCarts.length < 1) {
      //                            load button disappear if no elements
      var moreButton = document.getElementById("load-more-btn");
      moreButton.setAttribute("class","deleted-item");
      var noItem = document.getElementById("empty-cart-msg");
      noItem.setAttribute("class", "created-item");

    } else {
      createLayout();
    }
  }
  ourRequest.send();
}


function createLayout() {

    BuildBill(ourData);

  for (var i = 0; i < ourData.shoppingCarts.length; i++) {
    buildPin(ourData.shoppingCarts[i]);
  }
}


function bill_refresh(){
ourRequest.open('GET', '/shoppingCart');
  console.log(ourRequest);
  skip = skip + limit;
  ourRequest.onload = function() {
    ourData = JSON.parse(ourRequest.responseText);
    //   check if gallery is empty and show on the gallery
    if (ourData == null || ourData.shoppingCarts.length < 1) {
      //                            load button disappear if no elements
      var moreButton = document.getElementById("load-more-btn");
      moreButton.setAttribute("class","deleted-item");
      var noItem = document.getElementById("empty-cart-msg");
      noItem.setAttribute("class", "created-item");

    } else {
     BuildBill(ourData);
    }
  }
  ourRequest.send();
}

function BuildBill(ourdata1) {
    var total = document.querySelector("#total");
        total.innerHTML = "&#8377; " + ourdata1.total_cost;
    var delivery = document.querySelector("#delivery");
        delivery.innerHTML = "&#8377; " + ourdata1.delivery_charges;
    var taxes = document.querySelector("#taxes");
        taxes.innerHTML = "&#8377; " + ourdata1.taxes;
    var topay = document.querySelector("#topay");
        topay.innerHTML = "&#8377; " + ourdata1.toPay;
}

function buildPin(ourdata1) {
  var o = ourdata1.artPiece;
  console.log(o);

  var pinWrapper = document.createElement("ul");
  pinWrapper.setAttribute("class", "collection");
  pinTerest.appendChild(pinWrapper);

      var pinImage = document.createElement("li");
      pinImage.setAttribute("class", "collection-item avatar");
      pinWrapper.appendChild(pinImage);

          var img = document.createElement("img");
          img.setAttribute("class", "item-image circle");
          img.setAttribute("src", o.location);
          img.setAttribute("alt", "Art image");
          pinImage.appendChild(img);


          var titleWrapper = document.createElement("div");
          titleWrapper.setAttribute("class", "title");
          pinImage.appendChild(titleWrapper);

           var row1 = document.createElement("div");
           row1.setAttribute("class", " row ");
           titleWrapper.appendChild(row1);

              var ArtName = document.createElement("b");
              ArtName.setAttribute("class", "col s8 ");
              ArtName.innerHTML = o.art_name;
              row1.appendChild(ArtName);

              var cost = document.createElement("b");
              cost.setAttribute("class", "col s4 right cost-button");
              cost.innerHTML = "&#8377; " + o.cost;
              row1.appendChild(cost);
              cost.id = o.id
              img.id = o.id;
              img.addEventListener("click", function(e) {
                var ourRequest = new XMLHttpRequest();
                var url = '/artist/image/' + this.id;
                window.open(url, '_self');
              });


           var row2 = document.createElement("div");
           row2.setAttribute("class", "row");
           titleWrapper.appendChild(row2);

              var ArtistName = document.createElement("p");
              ArtistName.setAttribute("class", "col s8 ");
              ArtistName.innerHTML = o.artist.first_name;
              row2.appendChild(ArtistName);


//  //Delete button
//  var DeleteButton = document.createElement("i");
//  DeleteButton.setAttribute("class", "material-icons col s1");
//  DeleteButton.innerHTML = "delete";
//  row2.appendChild(DeleteButton);
//  DeleteButton.val = window.location.hostname + '/gallery/image/' + o.id;
//  DeleteButton.addEventListener('click', function(event) {
//    var xhr = new XMLHttpRequest();
//    var url = "/shoppingCart/" + ourdata1.id;
//    xhr.open("DELETE", url, true);
//    xhr.setRequestHeader("Content-Type", "application/json");
//    xhr.onreadystatechange = function() {
//      if (xhr.readyState === 4 && xhr.status === 200) {
//        pinWrapper.setAttribute("class", "deleted-item");
//        Materialize.toast(o.art_name + ' removed from cart', 2000);
//        bill_refresh();
//      }
//    };
//    console.log("PUT  " + url);
//    xhr.send();
//  });




           var stockOpp = document.createElement("div");
           stockOpp.setAttribute("class", " col s4 ");
           row2.appendChild(stockOpp);

           var innerStockOpp = document.createElement("div");
           innerStockOpp.setAttribute("class", "stock-box");
           stockOpp.appendChild(innerStockOpp);



  //minus button
  var MinusButton = document.createElement("i");
  MinusButton.setAttribute("class", "material-icons stock stock-left ");
  MinusButton.innerHTML = "remove";
  innerStockOpp.appendChild(MinusButton);
  MinusButton.id = o.id
  MinusButton.addEventListener('click', function(e) {

    ourdata1.stock--;
    if (ourdata1.stock > 0) { // when there is at leat one item.
      var xhr = new XMLHttpRequest();
      var url = "/shoppingCart/" + ourdata1.id;
      xhr.open("POST", url, true);
      xhr.setRequestHeader("Content-Type", "application/json");
      xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
          var json = JSON.parse(xhr.responseText);
          console.log(json);
          countTextbox.innerHTML = ourdata1.stock;
bill_refresh();
        }
      };
      var data = JSON.stringify({
        "productId": o.id,
        "stock": ourdata1.stock
      });
      console.log("PUT  " + url + " with data " + data);
      xhr.send(data);
    } else {
      // when we reduce the count to less that 1 that means delete the cart item from db
      var xhr = new XMLHttpRequest();
      var url = "/shoppingCart/" + ourdata1.id;
      xhr.open("DELETE", url, true);
      xhr.setRequestHeader("Content-Type", "application/json");
      xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
          pinWrapper.setAttribute("class", "deleted-item");
          Materialize.toast(o.art_name + ' removed from cart', 2000);
          bill_refresh();
        }
      };
      console.log("PUT  " + url);
      xhr.send();
    }
  });

  //count
  var countTextbox = document.createElement("a");
  countTextbox.setAttribute("class", "stock stock-center");
  countTextbox.innerHTML = ourdata1.stock;
  innerStockOpp.appendChild(countTextbox);
  countTextbox.id = o.id

  //plus button
  var PlusButton = document.createElement("i");
  PlusButton.setAttribute("class", "material-icons stock stock-right ");
  PlusButton.innerHTML = "add";
  innerStockOpp.appendChild(PlusButton);
  PlusButton.id = o.id
  PlusButton.addEventListener('click', function(e) {

    ourdata1.stock++;
    var xhr = new XMLHttpRequest();
    var url = "/shoppingCart/" + ourdata1.id;
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4 && xhr.status === 200) {
        var json = JSON.parse(xhr.responseText);
        console.log(json);
        countTextbox.innerHTML = ourdata1.stock;
bill_refresh();
      }
    };
    var data = JSON.stringify({
      "productId": o.id,
      "stock": ourdata1.stock
    });
    console.log("PUT  " + url + " with data " + data);
    xhr.send(data);

  });





}
