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

var ourRequest = new XMLHttpRequest();
var skip = 0;
var limit = 10;

initGalleryColumns();

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

loadData();
$(window).scroll(function() {
  if ($(window).scrollTop() + $(window).height() >= $(document).height() - 10) {
    loadData()
  }
});

function loadData() {
  ourRequest.open('GET', '/gallery/next_image_set?skip=' + skip + '&limit=' + limit);
  console.log(ourRequest);
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
    if (colLen1 < colLen2) {
      min = colLen1;
      colNum = 1;
    } else {
      min = colLen2;
      colNum = 2;
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
  pinWrapper.setAttribute("class", "pinWrapper");
  pinS.appendChild(pinWrapper);

  var pinImage = document.createElement("div");
  pinImage.setAttribute("class", "card pinImage");
  pinWrapper.appendChild(pinImage);

  var img = document.createElement("img");
  img.setAttribute("src", o.location);
  img.setAttribute("alt", "Art image");
  pinImage.appendChild(img);


  switch (colCount) {
    case 1:
      colLen1 += img.height;
      break;
    case 2:
      colLen2 += img.height;
      break;
    case 3:
      colLen3 += img.height;
      break;
    case 4:
      colLen4 += img.height;
      break;
    case 5:
      colLen5 += img.height;
      break;
  }


  var ArtName = document.createElement("p");
  ArtName.setAttribute("class", "pin GridTitle");
  ArtName.innerHTML = o.art_name;
  pinImage.appendChild(ArtName);

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
  BuyButton.setAttribute("class", "red btn right cost-button");
  BuyButton.innerHTML = "&#8377; " + o.cost;
  buttonWrapper.appendChild(BuyButton);
  BuyButton.id = o.id
  BuyButton.addEventListener('click', function(e) {
    var ourRequest = new XMLHttpRequest();
    var url = '/gallery/image/' + this.id;
    window.open(url, '_blank');
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


    Materialize.toast('Share url copied to your clipboard', 5000)
  });
}
