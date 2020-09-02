

    
/**
 * Adds a  draggable marker to the map..
 *
 * @param {H.Map} map                      A HERE Map instance within the
 *                                         application
 * @param {H.mapevents.Behavior} behavior  Behavior implements
 *                                         default interactions for pan/zoom
 */
function addDraggableMarker(map, behavior){

  var marker = new H.map.Marker({lat:-35.4, lng:-61.1});
  // Ensure that the marker can receive drag events
  marker.draggable = true;
  map.addObject(marker);

  // disable the default draggability of the underlying map
  // when starting to drag a marker object:
  map.addEventListener('dragstart', function(ev) {
    var target = ev.target;
    if (target instanceof H.map.Marker) {
      behavior.disable();
    }
  }, false);

 map.addEventListener('tap', function (evt) {
    var coord = map.screenToGeo(evt.currentPointer.viewportX,
            evt.currentPointer.viewportY);
	marker.setPosition(map.screenToGeo(evt.currentPointer.viewportX, evt.currentPointer.viewportY));
	 document.getElementById('lat').value = coord.lat.toFixed(6);
	 document.getElementById('lng').value = coord.lng.toFixed(6);
	 
  });
  
  // re-enable the default draggability of the underlying map
  // when dragging has completed
  map.addEventListener('dragend', function(ev) {
	var target = ev.target;
    if (target instanceof mapsjs.map.Marker) {
      behavior.enable();
 	}
  }, false);
  
  // Listen to the drag event and move the position of the marker
  // as necessary

}

/**
 * Boilerplate map initialization code starts below:
 */

//Step 1: initialize communication with the platform
var platform = new H.service.Platform({
  app_id: 'devportal-demo-20180625',
  app_code: '9v2BkviRwi9Ot26kp2IysQ',
  useHTTPS: true
});
var pixelRatio = window.devicePixelRatio || 1;
var defaultLayers = platform.createDefaultLayers({
  tileSize: pixelRatio === 1 ? 256 : 512,
  ppi: pixelRatio === 1 ? undefined : 320
});

//Step 2: initialize a map - this map is centered over Boston
var map = new H.Map(document.getElementById('map'),
  defaultLayers.normal.map,{
  center: {lat:-35.4, lng:-61.1},
  zoom: 6,
  pixelRatio: pixelRatio
});

//Step 3: make the map interactive
// MapEvents enables the event system
// Behavior implements default interactions for pan/zoom (also on mobile touch environments)
var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

// Step 4: Create the default UI:
var ui = H.ui.UI.createDefault(map, defaultLayers, 'en-US');

// Add the click event listener.
addDraggableMarker(map, behavior);
