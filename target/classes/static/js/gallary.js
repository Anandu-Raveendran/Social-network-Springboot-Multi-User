var cont = document.querySelector(".cont");


var ourData = [];
var col1 = null;
var col2 = null;
var col3 = null;
var col4 = null;
var col5 = null;
var colCount = 0;
var maxcols = 1;
var colLen1 = 0,
  colLen2 = 0,
  colLen3 = 0,
  colLen4 = 0,
  colLen5 = 0;
var userrole;
var ourRequest = new XMLHttpRequest();
var skip = 0;
var limit = 10;

$(document).ready(function() {
  getrole();
  initGalleryColumns();
  loadData();
});


function getrole() {
  var xhr = new XMLHttpRequest();
  var url = "/getrole";
  xhr.open("GET", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4 && xhr.status === 200) {
      userrole = xhr.response;
      console.log(userrole)
    }
  };
  xhr.send();

}

function initGalleryColumns() {

  col1 = document.createElement("div");
  col1.setAttribute("class", "col");
  col1.setAttribute("id", "col-1");
  cont.appendChild(col1);
  maxcols = 1;


  if (screen.width > 450) {
    col2 = document.createElement("div");
    col2.setAttribute("class", "col");
    col2.setAttribute("id", "col-2");
    cont.appendChild(col2);
    maxcols = 2;

  }
  if (screen.width > 600) {
    col3 = document.createElement("div");
    col3.setAttribute("class", "col");
    col3.setAttribute("id", "col-3");
    cont.appendChild(col3);
    maxcols = 3;

    col4 = document.createElement("div");
    col4.setAttribute("class", "col");
    col4.setAttribute("id", "col-4");
    cont.appendChild(col4);
    maxcols = 4;

  }
  if (screen.width > 1200) {
    col5 = document.createElement("div");
    col5.setAttribute("class", "col");
    col5.setAttribute("id", "col-5");
    cont.appendChild(col5);
    maxcols = 5;
  }
}

$(window).scroll(function() {
  if ($(window).scrollTop() + $(window).height() >= $(document).height() - 10) {
    loadData()
  }
});

function loadData() {
  ourRequest.open('GET', '/gallery/next_image_set?skip=' + skip + '&limit=' + limit);
  skip = skip + limit;
  ourRequest.onload = function() {
    ourData = JSON.parse(ourRequest.responseText);
    createLayout();
  }
  ourRequest.send();
}


function createLayout() {
  for (var i = 0; i < ourData.length; i++) {
    buildPin(ourData[i]);
  }
}

function getNextCol() {
  var min, colNum = 1;

  if (col2 != null)
    if (colLen2 < colLen1) {
      min = colLen2;
      colNum = 2;
    } else {
      min = colLen1;
      colNum = 1;
    }
  if (col3 != null)
    if (colLen3 < min) {
      min = colLen3;
      colNum = 3;
    }
  if (col4 != null)
    if (colLen4 < min) {
      min = colLen4;
      colNum = 4;
    }
  if (col5 != null)
    if (colLen5 < min) {
      min = colLen5;
      colNum = 5;
    }

  return colNum;
}

function buildPin(o) {
  var pinS = document.createElement("div");
  pinS.setAttribute("class", "pinS");

  colCount = getNextCol();
  if (colCount > 5) colCount = 1;
  if (colCount == 5) {
    if (col5 != null) {
      col5.appendChild(pinS);
    } else colCount = 1;
  } else if (colCount == 4) {
    if (col4 != null) {
      col4.appendChild(pinS);
    } else colCount = 1;
  } else if (colCount == 3) {
    if (col3 != null) {
      col3.appendChild(pinS);
    } else colCount = 1;
  } else if (colCount == 2) {
    if (col2 != null) {
      col2.appendChild(pinS);
    } else colCount = 1;
  }
  if (colCount == 1) {
    col1.appendChild(pinS);
  }



  var pinWrapper = document.createElement("div");
  pinWrapper.setAttribute("class", "card pinWrapper scale-transition ");
  pinS.appendChild(pinWrapper);

  var pinImage = document.createElement("div");
  pinImage.setAttribute("class", "pinImage");
  pinWrapper.appendChild(pinImage);

  var img = document.createElement("img");
  img.setAttribute("src", o.location);
  img.setAttribute("alt", "Art image");
  pinImage.appendChild(img);


  var height = img.height;
  if (height == 0)
    height = 100;
  switch (colCount) {
    case 1:
      colLen1 += height;
      break;
    case 2:
      colLen2 += height;
      break;
    case 3:
      colLen3 += height;
      break;
    case 4:
      colLen4 += height;
      break;
    case 5:
      colLen5 += height;
      break;
  }

  var titleWrapper = document.createElement("div");
  titleWrapper.setAttribute("class", "title-Wrapper ");
  pinImage.appendChild(titleWrapper);


  var cost = document.createElement("b");
  cost.setAttribute("class", "right cost-button");
  cost.innerHTML = "&#8377; " + o.cost;
  titleWrapper.appendChild(cost);
  cost.id = o.id

  var ArtName = document.createElement("b");
  ArtName.setAttribute("class", "pin GridTitle");
  ArtName.innerHTML = o.art_name;
  titleWrapper.appendChild(ArtName);

  var ArtistName = document.createElement("p");
  ArtistName.setAttribute("class", "pin");
  ArtistName.innerHTML = o.artist.first_name;
  pinImage.appendChild(ArtistName);

  img.id = o.id;
  img.addEventListener("click", function(e) {
    var ourRequest = new XMLHttpRequest();
    var url = '/gallery/image/' + this.id;
    window.open(url, '_blank');
  });

  var buttonWrapper = document.createElement("div");
  buttonWrapper.setAttribute("class", "pinImage ");
  pinImage.appendChild(buttonWrapper);

  var BuyButton = document.createElement("div");
  BuyButton.setAttribute("class", "red btn tiny right cost-button scale-transition ");
  BuyButton.innerHTML = "Add to Cart";
  buttonWrapper.appendChild(BuyButton);
  BuyButton.id = o.id
  BuyButton.addEventListener('click', function(e) {

    if (userrole == "UNAUTHORIZED"); {
      console.log("Unauthorised " + userrole);
      //                                window.location.replace("/login");
    }

    var xhr = new XMLHttpRequest();
    var url = "/shoppingCart";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4 && xhr.status === 200) {
        Materialize.toast(o.art_name + ' added to cart', 2000);
        BuyButton.setAttribute("class", "deleted-item");
        stockWrapper.setAttribute("class", "right created-item");
        console.log(xhr.status);
      }
    };
    var data = JSON.stringify({
      "productId": o.id,
      "stock": 1
    });
    console.log("POST  " + url + " with data " + data);
    xhr.send(data);
  });

  var stockWrapper = document.createElement("div");
  stockWrapper.setAttribute("class", "right deleted-item scale-transition scale-out");
  buttonWrapper.appendChild(stockWrapper);

  //minus button
  var MinusButton = document.createElement("i");
  MinusButton.setAttribute("class", "material-icons stock");
  MinusButton.innerHTML = "remove";
  stockWrapper.appendChild(MinusButton);
  MinusButton.id = o.id
  MinusButton.addEventListener('click', function(e) {

    //                                if(userrole.localeCompare("UNAUTHORIZED"));
    //                                        window.location.replace("/login");


    countTextbox.id--;
    if (countTextbox.id > 0) { // when there is at leat one item.
      var xhr = new XMLHttpRequest();
      var url = "/shoppingCart/" + o.id;
      xhr.open("POST", url, true);
      xhr.setRequestHeader("Content-Type", "application/json");
      xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
          countTextbox.innerHTML = countTextbox.id;
        }
      };
      var data = JSON.stringify({
        "productId": o.id,
        "stock": countTextbox.id
      });
      console.log("PUT  " + url + " with data " + data);
      xhr.send(data);
    } else {

    }
  });

  //count
  var countTextbox = document.createElement("p");
  countTextbox.setAttribute("class", " stock ");
  countTextbox.innerHTML = 1;
  stockWrapper.appendChild(countTextbox);
  countTextbox.id = 1

  //plus button
  var PlusButton = document.createElement("i");
  PlusButton.setAttribute("class", "material-icons stock ");
  PlusButton.innerHTML = "add";
  stockWrapper.appendChild(PlusButton);
  PlusButton.id = o.id
  PlusButton.addEventListener('click', function(e) {

    //                                    if(userrole.localeCompare("UNAUTHORIZED"));
    //                                        window.location.replace("/login");


    countTextbox.id++;
    var xhr = new XMLHttpRequest();
    var url = "/shoppingCart/-1";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4 && xhr.status === 200) {
        countTextbox.innerHTML = countTextbox.id;

      }
    };
    var data = JSON.stringify({
      "productId": o.id,
      "stock": countTextbox.id
    });
    console.log("PUT  " + url + " with data " + data);
    xhr.send(data);

  });

  //                var LikeButton = document.createElement("i");
  //                LikeButton.setAttribute("class", "left material-icons like-button ");
  //                LikeButton.innerHTML = "thumb_up";
  //                buttonWrapper.appendChild(LikeButton);

  var ShareButton = document.createElement("i");
  ShareButton.setAttribute("class", "left material-icons share-button tiny ");
  ShareButton.innerHTML = "share";
  buttonWrapper.appendChild(ShareButton);
  ShareButton.val = window.location.href + 'gallery/image/' + o.id;
  ShareButton.addEventListener('click', function(event) {

    var shareString = "Hey, checkout this art on Artshop.com " + String(this.val);

    var ShareStringText = document.createElement("textarea");
    ShareStringText.innerHTML = shareString;
    buttonWrapper.appendChild(ShareStringText);

    ShareStringText.select();

    try {
      var successful = document.execCommand('copy');
      var msg = successful ? 'successful' : 'unsuccessful';
      console.log('Copying text command was ' + msg);
    } catch (err) {
      console.log('Oops, unable to copy');
    }


    Materialize.toast('Share url copied to your clipboard', 2000)
  });
}
