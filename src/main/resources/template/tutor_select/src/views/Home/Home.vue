<template>
  <div class="home-wrap">
    <!-- <img src="./imgs/teacher1.png"> -->
    <div class="logo-img">
      <img src="./imgs/teacher2.png" />
    </div>
    <div class="content-wrap">
      <div class="content">
        时光飞逝,<br />现在来迎接你大学生涯的最后一项挑战<br />
        ---毕业设计吧! <br />期待你一命通关!
      </div>
      
      <div class="start-btn">
        <button class="btn" @click="toSelect">选择心仪的导师</button>
      </div>
      
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  methods: {
    toSelect(){
      this.$router.push('/pick1')
    }
  },
};
</script>

<style scoped>
.home-wrap {
  display: flex;
  justify-content: space-around;
  align-items: center;
    background-image: linear-gradient(to right,rgb(210, 229, 140),green);

}
.content {
  font-size: 50px;
  color: white;
  font-family:'kaiti';
}
.start-btn {
  margin-top: 40px;
  width: 400px;
  height: 150px;
}
.btn {
  display: inline-block;
  width: 100%;
  height: 100%;
  border-radius: 20px;
  font-size: 50px;
  font-weight: 700;
  background-color: orange;
  box-shadow: 0 10px 0 10px rgb(231, 111, 30);
  box-shadow: 0px 10px 0px rgba(255, 115, 0, 0.712),
    0px 10px 25px rgba(0, 0, 0, 0.7);
  
}
</style>