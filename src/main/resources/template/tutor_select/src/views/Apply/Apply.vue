<template>
  <div class="apply-wrapper">
    <div class="apply-wrap">
      <ApplyInfo style="margin-bottom:2%"/>
      <Paganation style="position:absolute;bottom:2%"/>
    </div>
    <transition name="el-fade-in">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
import ApplyInfo from "./ApplyInfo/ApplyInfo.vue";
export default {
  name: "Apply",

  components: {
    ApplyInfo,
  },
};
</script>

<style scoped>
.apply-wrapper {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.apply-wrap{
    width: 90%;
    height: 90%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
}
</style>