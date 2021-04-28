<template>
    <div>
      <div @click="scrollBack" id="header-read-more-scroll">
        Les mer
        <i class="fa fa-caret-up"></i>
      </div>
        <h1>Om Gidd</h1>
        <div id="textbox1"> <!--Koper denne om det er behov for flere-->
            <h3>Lorem Impsum</h3>
            <h5>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce in finibus ipsum. 
            Nunc tincidunt turpis in felis pharetra ultrices. Integer sed molestie mi, eget hendrerit erat. 
            Quisque eget consectetur velit, vitae volutpat dui. 
            Donec purus elit, accumsan a ipsum ut, viverra ullamcorper massa. Nam ornare dapibus bibendum. 
            Donec viverra, lacus vel interdum fringilla, felis nibh cursus felis, non luctus sapien libero at quam. 
            Donec dignissim nibh eu ligula scelerisque pulvinar. Vivamus non vehicula metus, eget interdum nunc.
            Sed bibendum leo ac commodo venenatis. 
            Ut malesuada, justo a convallis malesuada, metus justo malesuada justo, id cursus neque purus at nisl. 
            Aliquam rhoncus tortor dui, vel mattis ex pellentesque eu. Donec volutpat vestibulum velit, ut egestas orci molestie a. 
            Vestibulum non cursus leo. In hac habitasse platea dictumst. Ut blandit erat purus, sed malesuada ligula vehicula ut. 
            Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.
            </h5>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import { useRouter } from "vue-router";

export default defineComponent({
    setup() {
        const router = useRouter();
        const scrollBack = ref(()=>{
            router.push("/");
        })

        return{
            scrollBack,
        }
    },

})
</script>
