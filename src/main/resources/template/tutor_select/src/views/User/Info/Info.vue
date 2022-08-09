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
        {{userInfo.realname}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-check"></i>
          {{infoAttr.No}}
        </template>
        {{userInfo.accountNumber}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-reading"></i>
          {{infoAttr.class}}
        </template>
        {{userInfo.className}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          {{infoAttr.phone}}
        </template>
        {{userInfo.telephone}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-message"></i>
          {{infoAttr.email}}
        </template>
        {{userInfo.email}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-message"></i>
          {{infoAttr.status}}
        </template>
        <el-tag type="danger" v-show="userInfo.hasTutor==='0'">未被选</el-tag>
        <!-- <el-tag type="warning">等待中</el-tag> -->
        <el-tag type="success" v-show="userInfo.hasTutor!=='0'">已选取：{{userInfo.hasTutor}}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          {{infoAttr.address}}
        </template>
        {{userInfo.address}}
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
  props:["infoAttr","userInfo"]
};
</script>

<style scoped>
</style>