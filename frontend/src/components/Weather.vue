<template>
    <div class="container">
        {{ temperature }}
        <img :src="iconPath" alt="No weather data" />
    </div>
</template>

<script lang="ts">
import notConfigAxios from 'axios';
import { defineComponent, onBeforeMount, ref, Ref } from 'vue'

export default defineComponent({
    name: "Weather",
    props: {
        latitude: Number,
        longitute: Number,
        time: {
            type: String,
            required: true,
        }
    },
    setup(props) {
        const temperature: Ref<string> = ref("");
        const iconPath: Ref<string> = ref("");
        onBeforeMount(async() => {
            if(props.latitude !== null && props.longitute !== null && props.time !== null) {
                const response = await notConfigAxios.get("https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=" + props.latitude + "&lon=" + props.longitute);

                let myHour = props.time.split(" ")[1].split(":")[0];
                let i = 0;
                for(let s in response.data["properties"]["timeseries"]) {
                    let timeStamp = response.data["properties"]["timeseries"][s]["time"];
                    if(timeStamp.split("T")[1].split(":")[0] === myHour && timeStamp.split("T")[0] === props.time.split(" ")[0]) {
                        break;
                    }
                    i++;
                }

                temperature.value = response.data["properties"]["timeseries"][i]["data"]["instant"]["details"]["air_temperature"];
                let symbol = response.data["properties"]["timeseries"][i]["data"]["next_1_hours"]["summary"]["symbol_code"];
                iconPath.value = require("../assets/weatherIcons/" + symbol + ".png")
            }
        });

        return {
            temperature,
            iconPath,
        };
    },
})
</script>

<style scoped>

</style>
