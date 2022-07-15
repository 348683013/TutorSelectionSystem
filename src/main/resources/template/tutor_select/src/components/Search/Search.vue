<template>
  <div>
    <div style="margin-top: 15px">
      <el-input
        placeholder="请输入内容"
        v-model="input3"
        class="input-with-select"
        
      >
        <el-select v-model="select" slot="prepend" placeholder="请选择" class="selector">
          <el-option label="姓名" value="1"></el-option>
          <el-option label="年龄" value="2"></el-option>
          <el-option label="性别" value="3"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search"></el-button>
      </el-input>
    </div>
  </div>
</template>

<script>
export default {
  name: "Search",
  data() {
    return {
      input1: "",
      input2: "",
      input3: "",
      select: "",
    };
  },
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