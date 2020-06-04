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
    console.log(ourData);
    createLayout();
  }
  ourRequest.send();
}


function createLayout() {
  for (var i = 0; i < ourData.length; i++) {
    buildPin(ourData[i]);
  }
}

function buildPin(o) {

    console.log(o);

  var pinS = document.createElement("div");
  pinS.setAttribute("class", "pinS");

    col1.appendChild(pinS);

  var pinWrapper = document.createElement("div");
  pinWrapper.setAttribute("class", "card pinWrapper scale-transition ");
  pinS.appendChild(pinWrapper);

  var pinImage = document.createElement("div");
  pinImage.setAttribute("class", "pinImage");
  pinWrapper.appendChild(pinImage);

  var img = document.createElement("img");
  img.setAttribute("src", o.imgLocation);
  img.setAttribute("alt", "Art image");
  pinImage.appendChild(img);
  img.id = o.postId;

  img.addEventListener("click", function(e) {
    var ourRequest = new XMLHttpRequest();
    var url = '/gallery/image/' + this.id;
    window.open(url, '_blank');
  });


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

  var userName = document.createElement("b");
  userName.setAttribute("class", "pin GridTitle");
  userName.innerHTML = o.userName;
  titleWrapper.appendChild(userName);

  var userLocation = document.createElement("p");
  userLocation.setAttribute("class", "pin");
  userLocation.innerHTML = o.postTitle;
  pinImage.appendChild(userLocation);

  var postText = document.createElement("p");
  postText.setAttribute("class", "pin");
  postText.innerHTML = o.postText;
  pinImage.appendChild(postText);

  var postText = document.createElement("p");
  postText.setAttribute("class", "pin");
  postText.innerHTML = o.likesCount;
  pinImage.appendChild(postText);

  var postText = document.createElement("p");
  postText.setAttribute("class", "pin");
  postText.innerHTML = o.likedBy;
  pinImage.appendChild(postText);
//TODO: add top comments

  var buttonWrapper = document.createElement("div");
  buttonWrapper.setAttribute("class", "pinImage ");
  pinImage.appendChild(buttonWrapper);

  var LikeButton = document.createElement("div");
  LikeButton.setAttribute("class", "red btn tiny right cost-button scale-transition ");
  LikeButton.innerHTML = "Like";
  buttonWrapper.appendChild(LikeButton);
  LikeButton.id = o.postId;
  LikeButton.addEventListener('click', function(e) {

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
