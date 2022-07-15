<template>
  <div class="list-page">
    <div class="search-wrapper">
        <Search/>
    </div>
    <div class="list-wrapper">
        <Teacher/>
    </div>
    <div class="paganation">
      <Paganation/>
    </div>
    <transition name="el-fade-in">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
import Teacher from './Teacher/Teacher.vue'
export default {
  name: "List",
  components:{
    Teacher
  }
};
</script>

<style scoped>
    .list-page{
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    .search-wrapper{
        width: 500px;
        margin-top: 1   0px;
    }
    .list-wrapper{
        margin-top: 20px;
    }
    .paganation{
        margin-top: 35px;
    }
</style>