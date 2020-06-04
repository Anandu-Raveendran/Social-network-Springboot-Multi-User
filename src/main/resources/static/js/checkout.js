var ourData = [];
var pinTerest = document.querySelector("#gallery");
var ourRequest = new XMLHttpRequest();
var skip = 0;
var limit = 10;
loadData();
$(window).scroll(function() {
  if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
    loadData()
  }
});

function loadData() {
  ourRequest.open('GET', '/artist/next_image_set?skip=' + skip + '&limit=' + limit);
  console.log(ourRequest);
  skip = skip + limit;
  ourRequest.onload = function() {
    ourData = JSON.parse(ourRequest.responseText);
    //   check if gallery is empty and show on the gallery
    if (ourData == null || ourData.length < 1) {
      //                            load button disappear if no elements
      document.getElementById("load-more-btn").style.display = "none";
      //                                    empty message comes when no img in the gallery at all
      if (document.getElementById("gallery").getElementsByClassName("pinImage").length < 1)
        document.getElementById("empty-gallery-msg").style.display = "block";
    } else {
      createLayout();
    }
  }
  ourRequest.send();
}


function createLayout() {
  for (var i = 0; i < ourData.length; i++) {
    buildPin(ourData[i]);
  }
}

function buildPin(o) {

  var pinWrapper = document.createElement("div");
  pinWrapper.setAttribute("class", "pinWrapper");
  pinTerest.appendChild(pinWrapper);

  var pinImage = document.createElement("div");
  pinImage.setAttribute("class", "pinImage card");
  pinWrapper.appendChild(pinImage);

  var img = document.createElement("img");
  img.setAttribute("src", o.location);
  img.setAttribute("alt", "Art image");
  pinImage.appendChild(img);

  var ArtName = document.createElement("b");
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
    var url = '/artist/image/' + this.id;
    window.open(url, '_self');
  });

  var buttonWrapper = document.createElement("div");
  buttonWrapper.setAttribute("class", "pinImage ");
  pinImage.appendChild(buttonWrapper);

  var BuyButton = document.createElement("button");
  BuyButton.setAttribute("class", "red btn right cost-button");
  BuyButton.innerHTML = "&#8377; " + o.cost;
  buttonWrapper.appendChild(BuyButton);
  BuyButton.id = o.id
  BuyButton.addEventListener('click', function(e) {
    var ourRequest = new XMLHttpRequest();
    var url = '/artist/image/' + this.id;
    window.open(url, '_self');
  });

  //share button
  var ShareButton = document.createElement("i");
  ShareButton.setAttribute("class", "left material-icons share-button tiny ");
  ShareButton.innerHTML = "share";
  buttonWrapper.appendChild(ShareButton);
  ShareButton.val = window.location.hostname + '/gallery/image/' + o.id;
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
