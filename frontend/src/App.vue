<template>
  <div id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </div>
  <router-view />
</template>

<style lang="scss">
@import url('https://fonts.googleapis.com/css2?family=Mulish&display=swap');

$font-stack: 'Mulish', sans-serif;
$primary-color: #282828;
$secondary-color: #EA4B4B;
$padding: 0.6rem 1rem 0.6rem 1rem;

#app {
  font-family: 'Mulish', sans-serif;
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
    color: $secondary-color;

    &.router-link-exact {
      color: #42b983;
    }
  }
}

h1 {
  font-size: 1.44rem;
}

h2 {
  font-size: 1.2rem;
}

p {
  line-height: 150%;
}

button {
  background: #EA4B4B;
  border: 0px solid #000000;
  box-sizing: border-box;
  border-radius: 40px;
  padding: $padding;
  font-variant: small-caps;
  font-family: 'mulish';
  font-weight:600;
  color: white;
  font-size: 0.833rem;
}

input {
  background: #FFFFFF;
  border: 1px solid #9F9F9F;
  box-sizing: border-box;
  border-radius: 22px;
  padding: $padding;
  font-weight: normal;
  font-family: 'mulish';
  font-size: 1rem;
  color: $primary-color !important;
  margin: auto;
  display: inline;
}

a:link {
  color: $primary-color;
  
}
</style>
