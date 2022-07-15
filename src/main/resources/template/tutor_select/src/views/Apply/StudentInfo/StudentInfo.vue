<template>
  <div id="mask">
    <div class="detail-wrapper">
      <div class="detail-wrap">
        <div class="back" @click="goBack">
          <i class="el-icon-close"></i>
        </div>
        <el-descriptions
          class="margin-top"
          title="申请人信息"
          :column="3"
          :size="size"
          border
        >
          <template slot="extra"> </template>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              姓名
            </template>
            kooriookami
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-check"></i>
              学号
            </template>
            s21111111
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-reading"></i>
              班级
            </template>
            生医一班
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              电话
            </template>
            18100000000
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-message"></i>
              E-mail
            </template>
            1xxxxxxxxxx@qq.com
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-message"></i>
              科研班
            </template>
            <el-tag type="danger">未加入</el-tag>
            <el-tag type="success">已加入：XXX</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              联系地址
            </template>
            江苏省苏州市吴中区吴中大道 1188 号
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "StudentInfo",
  data() {
    return {
      size: "",
    };
  },
  methods: {
    goBack() {
      this.$router.push("/apply");
    },
  },
};
</script>

<style scoped>
#mask {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 2;
  display: flex;
  justify-content: center;
  align-items: center;
}
.detail-wrapper {
  width: 45%;
  height: 40%;
  background-color: white;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}
.detail-wrap {
  width: 90%;
  height: 90%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.back {
  position: absolute;
  top: 5%;
  right: 3%;
  font-size: 24px;
}
.back:hover{
    color:rgb(64,158,255)
}
</style>