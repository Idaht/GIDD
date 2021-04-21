<template>
    <div id="map" ref="mapDivRef">
    </div>
</template>

<script lang="ts">
    
    //This global interface is needed to make the initialization of the map possible
    declare global {
        interface Window {
            initMap: any;
        }
    }

    import { defineComponent, onMounted, PropType, ref } from "vue";
    import IActivity from "@/interfaces/IActivity.interface";
    import ICoordinates from "@/interfaces/ICoordinates.interface";
    import data from "@/../config.json";

    export default defineComponent({
        name: "Map",

        props: {
            center: { 
                type: Object as () => ICoordinates,
                required: true,
                },
            zoom: Number,
            mapTypeId: String,
            disableDefaultUI: Boolean,
            activityData: {
                type: Array,
                required: true,
            }
        },

        setup(props) {

            const map = ref();
            const mapDivRef = ref();

            onMounted(() => {
                const keyAPI = data.googleAPIKey; //Gets the API key from config.json file
                const sc = document.getElementById("123456");
                if (sc instanceof HTMLElement)
                {
                    document.head.removeChild(sc);
                }
                const script = document.createElement("script");
                script.id = "123456";
                script.src = `https://maps.googleapis.com/maps/api/js?key=${keyAPI}&callback=initMap`;
                script.async = true;
                document.head.appendChild(script);
            });

            window.initMap = () : void => {
                map.value = new window.google.maps.Map(mapDivRef.value as HTMLElement, {
                    center: { lat: props.center.lat, lng: props.center.lng },
                    zoom: props.zoom || 14,
                    mapTypeId: props.mapTypeId || "roadmap",
                    disableDefaultUI: props.disableDefaultUI || true,
                });

                const infoWindow = new window.google.maps.InfoWindow();
                console.log(props.activityData);
                console.log(JSON.stringify(props.activityData));            
                
                props.activityData.forEach(element => {
                    const activity = element as IActivity;
                    console.log("KAke: " + activity.city);
                    let marker = new window.google.maps.Marker({
                        position: { lat: activity.latitude, lng: activity.longitude },
                        title:"Hello World!"
                    });
                    
                    marker.setMap(map.value);
                    marker.setTitle(activity.description + " | " + activity.activityId);
                    const contentString = "<div id=\"activity\">" +
                        "<img title=\"Bilde av aktivitet\" src=\"\"/>" +
                        "<div><h3>" + activity.description + "</h3><h4>" + activity.startTime + " | " + activity.place + ", " + activity.city + "</h4></div></div>";
                    marker.addListener("click", () => {
                        infoWindow.close();
                        infoWindow.setContent(contentString);
                        infoWindow.open(marker.getMap(), marker);
                    });
                });
            };

            return {
                mapDivRef,
            }
        },
    });
</script>

<style scoped>

#map
{
    height: 100%;    
}

html, body
{
    height: 100%;
    width: 100%;
    margin: 0;
    padding: 0;
}

</style>
