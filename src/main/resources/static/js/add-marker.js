let map;
let new_marker;
let create_button;

let error_message = document.getElementById("error");
let start_map_position = { lat: 41.40046221020247, lng: 18.510486044050406 };
let selected_marker_position;
let ctx = window.location.origin + '/be-green/a/j/rest';
function initMap() {
    let loading_message = document.getElementById("loading");
    loading_message.textContent = "loading your location...";

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
            content: "<button onclick=\"addNewMarker()\">click to set</button>",
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

async function getById(id) {
    let name = document.querySelector('#name');
    name.textContent = "loading..."
    let url = ctx + '/map/marker/'+ id;

    try {
        const response = await fetch(url);
        const data = await response.json();
        name.textContent = data.name
    } catch (error) {
        console.log(error)
    }
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
                        content: "<p>" + marker.getTitle() + "</p>"+"<button onclick=\"getById('" + markerId +"')\">more info</button>",
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

    locationButton.textContent = "Pan to Current Location";
    locationButton.classList.add("custom-map-control-button");
    map.controls[google.maps.ControlPosition.TOP_CENTER].push(locationButton);
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