<template>
  <div class="header">
    <div class="logo-wrap">
      <img src="@/assets/logo.png"/>
    </div>
    <div class="title-wrap">毕业设计导师选择平台</div>
  </div>
</template>

<script>
export default {
  name: "Header",
};
</script>

<style scoped>
.header {
  height: 80px;
  background-image: linear-gradient(to right, rgb(37, 143, 37), white);
  display: flex;
  align-items: center;
}
.logo-wrap {
  height: 85%;
  margin-left: 15px;
}
img {
  height: 100%;
}
.title-wrap {
  color: white;
  font-size: 24px;
  font-weight: bold;
  margin-left: 20px;
  font-family: "kaiti";
}
</style>