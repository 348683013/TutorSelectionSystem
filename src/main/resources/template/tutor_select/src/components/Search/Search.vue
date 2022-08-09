<template>
  <div>
    <div style="margin-top: 15px">
      <el-input
        :placeholder="select==='1'?'请输入姓名':'请输入男或女'"
        v-model="input"
        class="input-with-select"
        
      >
        <el-select v-model="select" slot="prepend"  class="selector">
          <el-option label="姓名" value="1"></el-option>
          <!-- <el-option label="年龄" value="2"></el-option> -->
          <el-option label="性别" value="2"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click="submit"></el-button>
      </el-input>
    </div>
  </div>
</template>

<script>
export default {
  name: "Search",
  data() {
    return {
      input: "",
      select: "1",
    };
  },
  methods:{
    submit(){
      if(this.select==="1"){
        this.$emit('getSearchData',this.input)
      }
      if(this.select==="2"){
        if(this.input==="男"||this.input==="女"){
          this.$emit('getSexData',this.input)
        }
        else{
          alert("请输入'男'或'女'")
          this.input=""
        }
      }
    }
  }
};
</script>

<style scoped>
.el-select .el-input {
  width: 130px;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
.selector{
    width: 100px;
}
</style>