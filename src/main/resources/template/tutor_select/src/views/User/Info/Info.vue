<template>
  <div style="width: 100%;margin-bottom:100px">
    <el-descriptions
      class="margin-top"
      title="个人信息"
      :column="3"
      :size="size"
      border
    >
      <template slot="extra">
        <el-button type="primary" @click="changePass" style="background-color:rgb(58, 153, 58);">修改密码</el-button>
      </template>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          {{infoAttr.name}}
        </template>
        kooriookami
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-check"></i>
          {{infoAttr.No}}
        </template>
        s21111111
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-reading"></i>
          {{infoAttr.class}}
        </template>
        生医一班
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          {{infoAttr.phone}}
        </template>
        18100000000
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-message"></i>
          {{infoAttr.email}}
        </template>
        1xxxxxxxxxx@qq.com
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-message"></i>
          {{infoAttr.status}}
        </template>
        <el-tag type="danger">未选取</el-tag>
        <el-tag type="warning">等待中</el-tag>
        <el-tag type="success">已选取：XXX</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          {{infoAttr.address}}
        </template>
        江苏省苏州市吴中区吴中大道 1188 号
      </el-descriptions-item>
    </el-descriptions>
    
  </div>
</template>

<script>
export default {
  name: "Info",
  data() {
    return {
      size: "",
      isChange:false
    };
  },
  methods: {
    changePass(){
        this.$router.push('user/change')
    }
  },
  props:["infoAttr"]
};
</script>

<style scoped>
</style>