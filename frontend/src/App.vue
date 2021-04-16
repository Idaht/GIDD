<template>
  <div id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </div>
  <router-view />
</template>

<style lang="scss">
@import url("https://fonts.googleapis.com/css2?family=Mulish&display=swap");

$font-stack: "Mulish", sans-serif;
$primary-color: #282828;
$secondary-color: #ea4b4b;
$padding: 0.6rem 1rem 0.6rem 1rem;

#app {
  font-family: "Mulish", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  font-size: 16px;
  letter-spacing: 0.04rem;
  display: grid;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}

button {
  background: #ea4b4b;
  border: 0px solid #000000;
  box-sizing: border-box;
  border-radius: 40px;
  padding: $padding;
  font-variant: small-caps;
  font-family: "mulish";
  font-weight: 600;
  color: white;
  font-size: 0.833rem;
}

input {
  background: #ffffff;
  box-sizing: border-box;
  border: 1px solid #9f9f9f;
  border-radius: 20px;
  padding: $padding;
  font: normal 1rem "mulish", sans-serif;
  color: $primary-color !important;
  margin: auto;
  display: inline;
}

a:link {
  color: $primary-color;
}
</style>
