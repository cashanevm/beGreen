let map;
let new_marker;
let create_button;

let error_message = document.getElementById("error");
let start_map_position = { lat: 41.40046221020247, lng: 18.510486044050406 };
let selected_marker_position;
let ctx = window.location.origin + '/be-green/a/j/rest';
function initMap() {
    let loading_message = document.getElementById("loading");
    let spinner = document.getElementById('spinner');
    loading_message.textContent = "loading your location...";
    spinner.style.display = 'block';

    map = new google.maps.Map(document.getElementById("map"), {
        center: start_map_position,
        zoom: 3,
    });

    addShowMeButton();

    createNewMarker();

    showMarkersByUrl('/map/marker/all');

    showMe();

}

function setLatLng() {
    let latLng = selected_marker_position.toString()
        .replace('(', '')
        .replace(')', '')
        .split(', ')

    document.getElementById('lat').value = latLng.at(0);
    document.getElementById('lng').value = latLng.at(1);
}

function createNewMarker() {
    new_marker = new google.maps.Marker({
        position: start_map_position,
        map: map
    });
    new_marker.setVisible(false);

    map.addListener("click", (e) => {
        new_marker.setVisible(true);
        closeInfoWindow(create_button);
        moveMarker(e.latLng, map, new_marker)
        cleanErrorMessage();

        create_button = new google.maps.InfoWindow({
            content: "<button class=\"btn btn-success to-spin\" onclick=\"addNewMarker()\">Add new</button>",
        });
        create_button.open(map, new_marker);
    });
}

function cleanErrorMessage() {
    if (error_message !== null) {
        error_message.textContent = ""
    }
}

function moveMarker(latLng) {
    closeInfoWindow(create_button)
    new_marker.setPosition(latLng);
    selected_marker_position = latLng
    setLatLng();
};

function closeInfoWindow(a) {
    if (typeof a !== 'undefined') {
        a.close();
    }
}

let count = 0;
async function getById(id) {

    let order = '';
    const box = document.getElementById('green-blocks');
    const spinner = document.getElementById('spinner');
    spinner.style.display = 'block';

    if (count % 2 == 0) {
        order = ' order-md-2';
    }


    let loading_message = document.getElementById("loading");
    loading_message.textContent = "loading marker..."
    let url = ctx + '/map/marker/' + id;
    let nameO = '';

    try {
        const response = await fetch(url);
        const data = await response.json();
        nameO = data.name
    } catch (error) {
        console.log(error)
    }
    box.innerHTML += ' <div class="row featurette">\n' +
        '            <div class="col-md-7 ' + order + ' " >\n' +
        '                <h2 class="featurette-heading">' + nameO + '</h2>  ' +
        '                <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis\n' +
        '                    euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus,\n' +
        '                    tellus ac cursus commodo.</p>\n' +
        '               <button type="submit" class="btn btn-success to-spin disabled">Make an order </button>\n' +
        '            </div>\n' +
        '            <div class="col-md-5">\n' +
        '                <img class="featurette-image img-fluid mx-auto" src="/be-green/images/being-green.jpeg"\n' +
        '                     alt="Generic placeholder image">\n' +
        '            </div>\n' +
        '        </div>\n' +
        '\n' +
        '        <hr class="featurette-divider">';
    count++;
    loading_message.textContent = "";
    spinner.style.display = 'none';
}

async function showMarkersByUrl(urlPath) {
    let more_info
    const url = ctx + urlPath;


    try {
        const response = await fetch(url);
        const data = await response.json();
        const markers = [];

        Array.from(data).forEach(
            function createMarker( markerEntity ) {
                let position = new google.maps.LatLng(markerEntity.lat , markerEntity.lng);
                let title = markerEntity.name;
                let markerId = markerEntity.id;

                const marker = new google.maps.Marker({
                    position,
                    title
                });

                marker.addListener("click", () => {
                    closeInfoWindow(more_info);

                    more_info = new google.maps.InfoWindow({
                        content: "<div class=\"card\" style=\"width: 18rem;\">\n" +
                            "  <div class=\"card-body\">\n" +
                            "    <h5 class=\"card-title\">" + marker.getTitle() + "</h5>\n" +
                            "    <h6 class=\"card-subtitle mb-2 text-muted\">Card subtitle</h6>\n" +
                            "    <p class=\"card-text\">Some quick example text to build on the card title and make up the bulk of the card's content.</p>\n" +
                            "<button class=\"btn btn-success to-spin\" onclick=\"getById('" + markerId + "')\">More info</button>" +
                            "  </div>\n" +
                            "</div>",
                    });
                    more_info.open(map, marker);
                });
                markers.push(marker)
            }
        );
        new MarkerClusterer(map, markers);
    } catch (error) {
        console.log(error)
    }
}

function addNewMarker() {
    let form = document.querySelector('#set-marker');
    form.submit();
}

function showMe() {
    const spinner = document.getElementById('spinner');
    spinner.style.display = 'block';
    let infoWindow = new google.maps.InfoWindow();
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude,
                };

                infoWindow.setPosition(pos);
                infoWindow.setContent("Location found.");
                infoWindow.open(map);
                map.setCenter(pos);
                map.setZoom(16);
                let loading_message = document.getElementById("loading");
                loading_message.textContent = "";
                const spinner = document.getElementById('spinner');
                spinner.style.display = 'none';
            },
            () => {
                handleLocationError(true, infoWindow, map.getCenter());
            }
        );
    } else {
        handleLocationError(false, infoWindow, map.getCenter());
    }
}

function addShowMeButton() {
    const locationButton = document.getElementById("show-me");

    //locationButton.textContent = "Pan to Current Location";
    // locationButton.classList.add("custom-map-control-button");
    //map.controls[google.maps.ControlPosition.TOP_CENTER].push(locationButton);
    locationButton.addEventListener("click", () => {
        showMe(map)
    });
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setPosition(pos);
    infoWindow.setContent(
        browserHasGeolocation
            ? "Error: The Geolocation service failed."
            : "Error: Your browser doesn't support geolocation."
    );
    infoWindow.open(map);
}