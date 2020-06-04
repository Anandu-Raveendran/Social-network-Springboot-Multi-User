
//Set default to bangalore
var UserLocation = {lat: 12.9716, lng: 77.5946};
var geocoder, map, marker;
var location_textbox = document.getElementById("location_textbox");
var location_formated = document.getElementById("location_formated");

function getLocation(){
          console.log("getting device location");

          var on_success = function(position) {
          console.log("got device location");

            UserLocation = {
              lat: position.coords.latitude,
              lng: position.coords.longitude
            };
          };

          var on_fail = function(){
          }

          var options = { enableHighAccuracy: true };

        if (navigator.geolocation) {
           navigator.geolocation.getCurrentPosition(on_success, on_fail, options);

        }else{
            window.alert("Couldn't get device location. Enter address manually");
        }
}

 function initMap() {
            geocoder = new google.maps.Geocoder;
            infowindow = new google.maps.InfoWindow;

            map = new google.maps.Map(document.getElementById('map'), {
                center: UserLocation,
                zoom: 16,
                disableDefaultUI: true,
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                clickableIcons: false,
                gestureHandling: 'greedy',

                styles: [{
                      "featureType": "poi",
                      "stylers": [{ "visibility": "off" }]
                    }]
            });

            marker = new google.maps.Marker({
                  position: UserLocation,
                  map: map,
                  icon:"/images/map-marker.png"
            });


        // Create the DIV to hold the control and call the CenterControl()
        // constructor passing in this DIV.
        var centerControlDiv = document.createElement('div');
        var centerControl = new CenterControl(centerControlDiv, map);

        centerControlDiv.index = 1;
        map.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(centerControlDiv);



            google.maps.event.addListener(map, 'center_changed', function() {
                // 0.1 seconds after the center of the map has changed,
                // set back the marker position.
                window.setTimeout(function() {
                  UserLocation = map.getCenter();
                  marker.setPosition(UserLocation);
                }, 10);
            });

            google.maps.event.addListener(map, 'click', function(e) {
                  UserLocation = e.latLng;
                  map.panTo(UserLocation);
                  marker.setPosition(UserLocation);
            });

            google.maps.event.addDomListener(window, 'load', initMap);
 }


function updateMarkerAddress(response){
    console.log(response);
}

 function geocodePosition() {
        geocoder.geocode({'location': UserLocation}, function(results, status) {
          if (status === 'OK') {
            if (results[0]) {
                location_textbox.innerHTML =
                    '<i id="refresh-btn" class="material-icons left">location_on</i>' +
                    results[0].address_components[1].long_name;

                location_formated.value = results[0].formatted_address;
            } else {
              location_textbox.innerHTML = 'No results found';
            }
          } else {
            window.alert('Geocoder failed due to: ' + status);
          }
        });
}


/**
       * The CenterControl adds a control to the map that recenters the map on
       * Chicago.
       * This constructor takes the control DIV as an argument.
       * @constructor
       */
      function CenterControl(controlDiv, map) {

        var controlUI = document.createElement('div');
        controlDiv.setAttribute("class", "card location-btn waves-effect");

        controlDiv.appendChild(controlUI);


        var controlText = document.createElement('i');
        controlText.setAttribute("class", "material-icons");
        controlText.innerHTML = "my_location";

        controlUI.appendChild(controlText);

        // Setup the click event listeners: simply set the map to Chicago.
        controlUI.addEventListener('click', function() {
            getLocation();
            map.panTo(UserLocation);
            marker.setPosition(UserLocation);

        });

      }
